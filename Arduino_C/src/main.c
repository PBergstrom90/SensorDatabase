#include <avr/io.h>
#include "serial.h"
#include <stdio.h>
#include <stdlib.h>
#include <util/delay.h>

void adc_init(void)
{
    ADMUX = (1 << REFS0);
    ADCSRA |= (1 << ADEN) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0);
}

uint16_t adc_read(void)
{
    ADCSRA |= (1 << ADSC);
    while (ADCSRA & (1 << ADSC));
    return ADC;
}

double calc_celsius(uint16_t adc_value)
{
    double r1 = 10000;
    double r2 = r1 * (1023.0 / (float)adc_value - 1.0);
    double log_r2 = log(r2);

    const double c1 = 1.009249522e-03;
    const double c2 = 2.378405444e-04;
    const double c3 = 2.019202697e-07;
    double t = (1.0 / (c1 + c2 * log_r2 + c3 * log_r2 * log_r2 * log_r2));
    return t - 273.15f;
}

int main(void)
{
    uart_init();
    DDRB |= (1 << PB5);

    adc_init();

    for (;;) {
        double t_celsius = calc_celsius(adc_read());

        char buf[16];
        dtostrf(t_celsius, 0, 1, buf);
        uart_putstr(buf);

        PORTB |= (1 << PB5);
        _delay_ms(100);
        PORTB &= ~(1 << PB5);
        _delay_ms(1000);
    }

    return 0;
}