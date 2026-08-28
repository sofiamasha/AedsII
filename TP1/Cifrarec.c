#include <stdio.h>

// Ve se a entrada é FIM
int Fim(char s[]){
    int resp = 0;

    if(s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0'){
        resp = 1;
    }

    return resp;
}

// Faz o ciframento de cada caractere
void cifrar(char s[], int i){

    // Para quando chegar no final da string
    if(s[i] == '\0'){
        return;
    }

    // Soma 3 no codigo ASCII para andar no alfabeto
    s[i] = s[i] + 3;

    // Continua com o proximo caractere
    cifrar(s, i + 1);
}

int main(void){

    char entrada[1000];

    // Le uma linha por vez
    while(fgets(entrada, 1000, stdin) != NULL){

        // Tira o \n que o fgets coloca
        for(int i = 0; entrada[i] != '\0'; i++){
            if(entrada[i] == '\n'){
                entrada[i] = '\0';
                break;
            }
        }

        // Qnd encontrar FIM
        if(Fim(entrada)){
            break;
        }

        // Prega primeiro caractere
        cifrar(entrada, 0);

        printf("%s\n", entrada);
    }

    return 0;
}