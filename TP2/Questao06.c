#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// tira o \n do final da linha
void tirarquebra(char* s) {
    s[strcspn(s, "\r\n")] = '\0';
}

typedef struct {
    int h;
    int m;
} Hora;

// le 10:30 e separa em hora e minuto
Hora lerhora(char* s) {
    Hora x;
    sscanf(s, "%d:%d", &x.h, &x.m);
    return x;
}

typedef struct {
    int a;
    int m;
    int d;
} Data;

// lê 2025-13-25
Data lerdata(char* s) {
    Data x;
    sscanf(s, "%d-%d-%d", &x.a, &x.m, &x.d);
    return x;
}

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos[10][50];
    int qtdtipos;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;
} Restaurante;

// transforma uma linha do csv em um restaurante
Restaurante* lerrest(char* linha) {
    Restaurante* r = (Restaurante*) malloc(sizeof(Restaurante));

    char tiposstr[200];
    char datastr[20];
    char abertostr[10];

    // pega só a parte das cozinhas,tipo chinesa;asiatíca
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%199[^,]", tiposstr);

    r->qtdtipos = 0;

    //percorre a string e separa quando encontra ;
    //ai chinesa;asiatica vira duas palavras separadas - cinhesa asiatica
    int i = 0;
    int j = 0;

    while (tiposstr[i] != '\0') {

        if (tiposstr[i] != ';') {
            // monta a palavra letra por letra
            r->tipos[r->qtdtipos][j] = tiposstr[i];
            j++;
        } else {
            // quando achar ;, termina a palavra atual
            r->tipos[r->qtdtipos][j] = '\0';

            // passa p próxima palavra
            r->qtdtipos++;
            j = 0;
        }

        i++;
    }

    // fecha o último tip
    r->tipos[r->qtdtipos][j] = '\0';
    r->qtdtipos++;

    // preço é quantidade de $
    char precostr[10];
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%9[^,]", precostr);
    r->preco = strlen(precostr);

    // pega horário tipo 10:00-22:00
    char horario[20];
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%19[^,]", horario);

    char h1[10], h2[10];

    // separa antes e depois do -
    sscanf(horario, "%9[^-]-%9s", h1, h2);

    r->abre = lerhora(h1);
    r->fecha = lerhora(h2);

    // pega data
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%19[^,]", datastr);
    r->data = lerdata(datastr);

    // converte true ou false p 1 ou 0
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%*[^,],%9s", abertostr);

    if (strcmp(abertostr, "true") == 0) {
        r->aberto = 1;
    } else {
        r->aberto = 0;
    }

    // pega id, nome, cidade ...
    sscanf(linha, "%d,%99[^,],%99[^,],%d,%lf",
        &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao);

    return r;
}

// ordena por nomecom busca binátia
void ordenar(Restaurante** v, int n) {

    for (int i = 0; i < n - 1; i++) {

        int menor = i;

        for (int j = i + 1; j < n; j++) {

            // se der < 0, significa que vem antes - mediante a comparacao do strcomp q compara 

            if (strcmp(v[j]->nome, v[menor]->nome) < 0) {
                menor = j;
            }
        }

        if (menor != i) {
            Restaurante* temp = v[i];
            v[i] = v[menor];
            v[menor] = temp;
        }
    }
}

// busca binária (divide o vetor no meio)
int buscabin(Restaurante** v, int n, char* nome) {

    int ini = 0;
    int fim = n - 1;

    // enquanto tiver intervalo pra procurar
    while (ini <= fim) {

        int meio = (ini + fim) / 2;

        int comp = strcmp(v[meio]->nome, nome);

        // se for igual, achou
        if (comp == 0) {
            return 1;
        }

        // se o nome buscado for maior, vai pra direita
        if (comp < 0) {
            ini = meio + 1;
        } else {
            // senão vai pra esquerda
            fim = meio - 1;
        }
    }

    return 0;
}

int main() {

    FILE* arq = fopen("/tmp/restaurantes.csv", "r");

    char linha[1000];

    fgets(linha, sizeof(linha), arq);

    Restaurante* base[1000];
    int total = 0;

    // lê todo o csv
    while (fgets(linha, sizeof(linha), arq) != NULL) {
        tirarquebra(linha);
        base[total] = lerrest(linha);
        total++;
    }

    fclose(arq);

    int ids[1000];
    int n = 0;
    int x;

    // lê ids até -1
    while (scanf("%d", &x) == 1 && x != -1) {
        ids[n] = x;
        n++;
    }

    Restaurante* sel[1000];
    int t = 0;

    // pega só os restaurantes escolhidos
    for (int i = 0; i < n; i++) {

        int achou = 0;

        for (int j = 0; j < total && achou == 0; j++) {

            if (base[j]->id == ids[i]) {
                sel[t] = base[j];
                t++;
                achou = 1;
            }
        }
    }

    // ordena antes da busca
    ordenar(sel, t);

    int c;
    while ((c = getchar()) != '\n' && c != EOF);

    char nome[100];

    // lê nomes até FIM
    while (fgets(nome, sizeof(nome), stdin) != NULL) {

        tirarquebra(nome);

        if (strcmp(nome, "FIM") == 0) {
            break;
        }

        if (buscabin(sel, t, nome) == 1) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}