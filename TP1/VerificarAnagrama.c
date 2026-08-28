#include <stdio.h>

// Ve se a entrada é FIM
int Fim(char s[]){
    int resp = 0;

    if(s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0'){
        resp = 1;
    }

    return resp;
}

// Ve se as duas palavras são anagramas
int Anagrama(char s1[], char s2[]){

    int tam1 = 0;
    int tam2 = 0;

    // Conta o tamanho da primeira palavra
    for(int i = 0; s1[i] != '\0'; i++){
        tam1++;
    }

    // Conta o tamanho da segunda palavra
    for(int i = 0; s2[i] != '\0'; i++){
        tam2++;
    }

    // Se os tamanhos forem diferentes, ja n éanagrama
    if(tam1 != tam2){
        return 0;
    }

    // Procura cada letra da primeira palavra na segunda
    for(int i = 0; i < tam1; i++){

        int achou = 0;

        for(int j = 0; j < tam2; j++){

            if(s1[i] == s2[j]){
                // Marca a letra para nao usar ela de novo
                s2[j] = '*';
                achou = 1;
                break;
            }
        }

        // Se n encontrou letra, nao é anagrama
        if(achou == 0){
            return 0;
        }
    }

    return 1;
}

int main(void){

    char p1[100];
    char p2[100];

    scanf("%s", p1);

    // le ate aparecer FIM
    while(Fim(p1) == 0){

        scanf("%s", p2);

        if(Anagrama(p1, p2) == 1){
            printf("SIM\n");
        }
        else{
            printf("NAO\n");
        }

        scanf("%s", p1);
    }

    return 0;
}