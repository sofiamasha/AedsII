#include <stdio.h>

int fim(char s[]){
    int resp = 0;

    if(s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0'){
        resp = 1;
    }

    return resp;
}

int main(void){

    char palavra[200];

    while(1){

        scanf(" %[^\n]", palavra);

        if(fim(palavra)){
            break;
        }

        // percorre a string da ultima letra ate a primeira
        int tam = 0;

        for(int i = 0; palavra[i] != '\0'; i++){
            tam++;
        }

        for(int i = tam - 1; i >= 0; i--){
            printf("%c", palavra[i]);
        }

        printf("\n");
    }

    return 0;
}