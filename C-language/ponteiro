#include <stdio.h>
int main()
{
    int v[5] = {1,2,3,4,5};
    int x = 4, *ptr;
    substituir(v, x, &ptr);
    return 0;
}
void substituir (int vetor[], int y, int *ponteiro)
{
    for(ponteiro = vetor ; ponteiro < vetor + 5; ponteiro ++)
    {
        *ponteiro = y;
        printf("%i ",*ponteiro);
    }
}
