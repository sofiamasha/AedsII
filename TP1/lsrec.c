#include <stdio.h>

// Tira o enter 
void tiraEnter(char s[]){
    for(int i = 0; s[i] != '\0'; i++){
        if(s[i] == '\n'){
            s[i] = '\0';
            break;
        }
    }
}

// Ve se a entrada é FIM
int Fim(char s[]){
    int resp = 0;

    if(s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0'){
        resp = 1;
    }

    return resp;
}

// Ve se a string tem somente vogais
int Vogal(char s[], int i){

    // Chegou no fim sem encontrar erro
    if(s[i] == '\0'){
        if(i == 0){
            return 0;
        }
        return 1;
    }

    // Se nao for uma vogal, ja pode parar
    if(!(s[i] == 'a' || s[i] == 'e' || s[i] == 'i' ||
         s[i] == 'o' || s[i] == 'u' ||
         s[i] == 'A' || s[i] == 'E' || s[i] == 'I' ||
         s[i] == 'O' || s[i] == 'U')){
        return 0;
    }

    // Continua verificando o proximo caractere
    return Vogal(s, i + 1);
}

// Ve se a string tem somente consoantes
int Consoante(char s[], int i){

    if(s[i] == '\0'){
        if(i == 0){
            return 0;
        }
        return 1;
    }

    // Primeiro precisa ser uma letra
    if(!((s[i] >= 'a' && s[i] <= 'z') ||
         (s[i] >= 'A' && s[i] <= 'Z'))){
        return 0;
    }

    // Se for vogal, entao nao é consoante
    if(s[i] == 'a' || s[i] == 'e' || s[i] == 'i' ||
       s[i] == 'o' || s[i] == 'u' ||
       s[i] == 'A' || s[i] == 'E' || s[i] == 'I' ||
       s[i] == 'O' || s[i] == 'U'){
        return 0;
    }

    // Continua com o proximo caractere
    return Consoante(s, i + 1);
}

// Ve se a string é um numero inteiro
int Inteiro(char s[], int i){

    if(s[i] == '\0'){
        if(i == 0){
            return 0;
        }
        return 1;
    }

    // Se nao for numero, nao é inteiro
    if(s[i] >= '0' && s[i] <= '9'){
        return Inteiro(s, i + 1);
    }

    return 0;
}

// Ve se a string é um numero real
int Real(char s[], int i, int ponto){

    if(s[i] == '\0'){
        if(i == 0){
            return 0;
        }
        return 1;
    }

    // Conta quantos pontos ou virgulas existem
    if(s[i] == '.' || s[i] == ','){
        ponto++;

        // So pode ter um separador
        if(ponto > 1){
            return 0;
        }

        return Real(s, i + 1, ponto);
    }

    // Se for numero continua
    if(s[i] >= '0' && s[i] <= '9'){
        return Real(s, i + 1, ponto);
    }

    return 0;
}

int main(void){

    char entrada[1000];

    while(fgets(entrada, 1000, stdin) != NULL){

        tiraEnter(entrada);

        if(Fim(entrada) == 1){
            break;
        }

        if(Vogal(entrada, 0) == 1){
            printf("SIM ");
        }
        else{
            printf("NAO ");
        }

        if(Consoante(entrada, 0) == 1){
            printf("SIM ");
        }
        else{
            printf("NAO ");
        }

        if(Inteiro(entrada, 0) == 1){
            printf("SIM ");
        }
        else{
            printf("NAO ");
        }

        if(Real(entrada, 0, 0) == 1){
            printf("SIM\n");
        }
        else{
            printf("NAO\n");
        }
    }

    return 0;
}