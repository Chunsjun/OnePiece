#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "stack.c"

int main(void)
{
    stack* st = stack_alloc();
    srand(time(NULL));

    for (int i = 0; i < 20; ++i) {
        int c;
        c = rand() % 100;
        stack_push(st, (void*)(long)c);
    }
    stack_push(st, (void*)(long)17);
    stack_each(st, print_element);

    printf("\n\nPilha ordenada por QuickSort:\n\n");
    stack_sort(st, sort_op);
    stack_each(st, print_element);

    printf("\n\nTamanho da Pilha:\n\n");
    int x = stack_size(st);
    printf("%i",x);

    printf("\n\nTerceiro da Pilha:\n\n");
    int y = stack_nth(st, 2);
    printf("%i",y);

    for (int i = 0; i < 5; ++i) {
        stack_pop(st);
    }
    printf("\n\nPilha apos 5 remocoes:\n\n");
    stack_each(st, print_element);
    printf("\n");

    printf("\n\Topo da Pilha:\n\n");
    int z = stack_top(st);
    printf("%i",z);
    printf("\n");

    for (int i = 0; i < 16; ++i) {
        stack_pop(st);
    }
    stack_free(st);
}
