/*Invers˜ao de String - Crie um m´etodo iterativo que recebe uma string como parˆametro
e retorna a string invertida. Na sa´ıda padr˜ao, para cada linha de entrada, escreva uma linha de
sa´ıda com a string invertida. Por exemplo, se a entrada for “abcde”, a sa´ıda deve ser “edcba”*/*/

#include <stdio.h>

int main(void){

    //printf("Digite uma palavra");

    char s[200];

    
     
    while(scanf("%s", s)!=EOF){
        int cont=0;
    for(int i=0; s[i] != '\0'; i++){
        cont++;
        
    }
    for(int i=cont-1; i>=0; i--){
        printf("%c", s[i]);
    }
    printf("\n");
    }
    return 0;
}