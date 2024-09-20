#ifndef serial_h_
#define serial_h_

#define BAUD 9600

void uart_init(void);
void uart_putchar(char ch);
void uart_putstr(char* str);

#endif
