#include <avr/io.h>
#include "serial.h"

void uart_init(void)
{
    UBRR0 = (F_CPU / 16) / BAUD - 1;
    UCSR0B = (1 << TXEN0);
    UCSR0C = (3 << UCSZ00);
}

void uart_putchar(char ch)
{
    while (!(UCSR0A & (1 << UDRE0)));
    UDR0 = ch;
}

void uart_putstr(char* str)
{
    char ch;
    while ((ch = *str++) != '\0')
        uart_putchar(ch);
    uart_putchar('\n');
}
