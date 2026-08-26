/*Soma de D´ıgitos - Crie um m´etodo iterativo que recebe um n´umero inteiro como
parˆametro e retorna a soma de seus d´ıgitos. Na sa´ıda padr˜ao, para cada linha de entrada,
escreva uma linha de sa´ıda com o resultado da soma dos d´ıgitos. Por exemplo, se a entrada for
12345, a sa´ıda deve ser 15.*/

#include <stdio.h>

int main(void){
    int n;
    
    //printf("Digite um numero");
   

    while( scanf("%d", &n!=EOF){
        int soma=0;
        while(n>0){
            soma+=n%10;
            n=n/10;
        }
    }
        printf("%d\n", soma);
    }

    return 0;
}