#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// tira o \n do final
void limpar(char* s) {
    int i = 0;
    while (s[i] != '\0') {
        if (s[i] == '\n') {
            s[i] = '\0';
        }
        i++;
    }
}

// hora
typedef struct {
    int h;
    int m;
} Hora;

Hora lerhora(char* s) {
    Hora x;
    sscanf(s, "%d:%d", &x.h, &x.m);
    return x;
}

// data
typedef struct {
    int d;
    int m;
    int a;
} Data;

Data lerdata(char* s) {
    Data x;
    sscanf(s, "%d-%d-%d", &x.a, &x.m, &x.d);
    return x;
}

// restaurante
typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int cap;
    double ava;

    char cozinha[10][50];
    int qtd;

    int preco;

    Hora abre;
    Hora fecha;

    Data data;

    int aberto;
} Restaurante;

// ler linha do csv
Restaurante* lerresto(char* linha) {
    Restaurante* r = (Restaurante*) malloc(sizeof(Restaurante));

    char tipos[200];
    char precoStr[20];
    char horario[30];
    char dataStr[30];
    char abertoStr[10];

    sscanf(linha, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%19[^,],%29[^,],%29[^,],%9s",
        &r->id, r->nome, r->cidade, &r->cap, &r->ava,
        tipos, precoStr, horario, dataStr, abertoStr);

    // separar cozinhas
    r->qtd = 0;
    int i = 0, j = 0;

    while (tipos[i] != '\0') {
        if (tipos[i] == ';') {
            r->cozinha[r->qtd][j] = '\0';
            r->qtd++;
            j = 0;
        } else {
            r->cozinha[r->qtd][j++] = tipos[i];
        }
        i++;
    }
    r->cozinha[r->qtd][j] = '\0';
    r->qtd++;

    r->preco = strlen(precoStr);

    // separar horario
    char h1[10], h2[10];
    i = 0; j = 0;

    while (horario[i] != '-') {
        h1[j++] = horario[i++];
    }
    h1[j] = '\0';

    i++; j = 0;

    while (horario[i] != '\0') {
        h2[j++] = horario[i++];
    }
    h2[j] = '\0';

    r->abre = lerhora(h1);
    r->fecha = lerhora(h2);

    r->data = lerdata(dataStr);

    if (strcmp(abertoStr, "true") == 0) {
        r->aberto = 1;
    } else {
        r->aberto = 0;
    }

    return r;
}

void mostrar(Restaurante* r) {

    printf("[%d ## %s ## %s ## %d ## %.1lf ## [",
           r->id, r->nome, r->cidade, r->cap, r->ava);

    for (int i = 0; i < r->qtd; i++) {
        printf("%s", r->cozinha[i]);
        if (i < r->qtd - 1) printf(",");
    }

    printf("] ## ");

    for (int i = 0; i < r->preco; i++) {
        printf("$");
    }

    printf(" ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]\n",
        r->abre.h, r->abre.m,
        r->fecha.h, r->fecha.m,
        r->data.d, r->data.m, r->data.a,
        r->aberto ? "true" : "false"
    );
}

// comparar avaliacao + nome
int comparar(Restaurante* a, Restaurante* b) {
    if (a->ava < b->ava) return -1;
    if (a->ava > b->ava) return 1;
    return strcmp(a->nome, b->nome);
}

// particao do quicksort
int dividir(Restaurante** v, int ini, int fim) {

    Restaurante* pivo = v[fim];
    int i = ini - 1;

    for (int j = ini; j < fim; j++) {
        if (comparar(v[j], pivo) <= 0) {
            i++;

            Restaurante* temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
    }

    Restaurante* temp = v[i + 1];
    v[i + 1] = v[fim];
    v[fim] = temp;

    return i + 1;
}

// quicksort
void quick(Restaurante** v, int ini, int fim) {

    if (ini < fim) {

        int p = dividir(v, ini, fim);

        quick(v, ini, p - 1);
        quick(v, p + 1, fim);
    }
}

int main() {

    FILE* f = fopen("/tmp/restaurantes.csv", "r");

    char linha[1000];

    Restaurante* lista[1000];
    int n = 0;

    fgets(linha, sizeof(linha), f);

    while (fgets(linha, sizeof(linha), f)) {
        limpar(linha);
        lista[n++] = lerresto(linha);
    }

    fclose(f);

    int ids[1000];
    int qtd = 0;
    int x;

    scanf("%d", &x);

    while (x != -1) {
        ids[qtd++] = x;
        scanf("%d", &x);
    }

    Restaurante* arr[1000];
    int t = 0;

    // filtrar ids
    for (int i = 0; i < qtd; i++) {
        for (int j = 0; j < n; j++) {
            if (lista[j]->id == ids[i]) {
                arr[t++] = lista[j];
                break;
            }
        }
    }

    // ordenar com quicksort
    quick(arr, 0, t - 1);

    // imprimir
    for (int i = 0; i < t; i++) {
        mostrar(arr[i]);
    }

    return 0;
}