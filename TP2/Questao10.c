#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// tira o \n do final da string
void tirarEnter(char* s) {
    s[strcspn(s, "\r\n")] = '\0';
}

typedef struct {
    int hora;
    int minuto;
} Hora;

Hora lerhora(char* s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void mostrarhora(Hora* h, char* b) {
    sprintf(b, "%02d:%02d", h->hora, h->minuto);
}

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

Data lerdata(char* s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void mostrardata(Data* d, char* b) {
    sprintf(b, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos[10][50];
    int ntipos;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;
} Restaurante;

//  pega uma linha do csv e separa os dados
Restaurante* lerresto(char* s) {

    Restaurante* r = (Restaurante*) malloc(sizeof(Restaurante));

    char tipos[200];
    char data[20];
    char aberto[10];

    sscanf(s, "%*d,%*[^,],%*[^,],%*d,%*lf,%199[^,]", tipos);

    r->ntipos = 0;
    char* t = strtok(tipos, ";");

    // separa as cozinhas
    while (t != NULL) {
        strcpy(r->tipos[r->ntipos], t);
        r->ntipos++;
        t = strtok(NULL, ";");
    }

    char preco[10];
    sscanf(s, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%9[^,]", preco);
    r->preco = strlen(preco);

    char h[20];
    sscanf(s, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%19[^,]", h);

    char h1[10], h2[10];
    sscanf(h, "%9[^-]-%9s", h1, h2);

    r->abre = lerhora(h1);
    r->fecha = lerhora(h2);

    sscanf(s, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%19[^,]", data);
    r->data = lerdata(data);

    sscanf(s, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%*[^,],%9s", aberto);
    r->aberto = strcmp(aberto, "true") == 0 ? 1 : 0;

    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf",
        &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao);

    return r;
}

// monta a string p imprimir
void mostrar(Restaurante* r, char* b) {

    char coz[200] = "[";

    for (int i = 0; i < r->ntipos; i++) {
        strcat(coz, r->tipos[i]);
        if (i < r->ntipos - 1) strcat(coz, ",");
    }

    strcat(coz, "]");

    char preco[10] = "";
    for (int i = 0; i < r->preco; i++) strcat(preco, "$");

    char ha[10], hf[10], d[15];

    mostrarhora(&r->abre, ha);
    mostrarhora(&r->fecha, hf);
    mostrardata(&r->data, d);

    sprintf(b, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
        coz, preco, ha, hf, d,
        r->aberto ? "true" : "false");
}

typedef struct {
    int n;
    Restaurante* v[1000];
} Lista;

// lê o csv inteiro
void lerlista(Lista* l, char* path) {

    FILE* f = fopen(path, "r");

    char linha[1000];

    fgets(linha, sizeof(linha), f);

    l->n = 0;

    while (fgets(linha, sizeof(linha), f)) {
        tirarEnter(linha);
        l->v[l->n] = lerresto(linha);
        l->n++;
    }

    fclose(f);
}

//couting sort: ve o menor e a maior capacidade, cria o vetor de contagem
//conta qnrs tem de cada, dps soma acumulando e monta o array ordenado

void countingsort(Restaurante** arr, int n) {

    int min = arr[0]->capacidade;
    int max = arr[0]->capacidade;

    // acha menor e maior
    for (int i = 1; i < n; i++) {
        if (arr[i]->capacidade < min) min = arr[i]->capacidade;
        if (arr[i]->capacidade > max) max = arr[i]->capacidade;
    }

    int tam = max - min + 1;

    int* cont = (int*) malloc(tam * sizeof(int));
    Restaurante** resp = (Restaurante**) malloc(n * sizeof(Restaurante*));

    // zera o vetor
    for (int i = 0; i < tam; i++) cont[i] = 0;

    // conta quantos de cada capacidade
    for (int i = 0; i < n; i++) {
        cont[arr[i]->capacidade - min]++;
    }

    // soma acumulada (define as posições)
    for (int i = 1; i < tam; i++) {
        cont[i] += cont[i - 1];
    }

    // monta ordenado (de trás pra frente p não bagunçar)
    for (int i = n - 1; i >= 0; i--) {
        resp[cont[arr[i]->capacidade - min] - 1] = arr[i];
        cont[arr[i]->capacidade - min]--;
    }

    // copia de volta
    for (int i = 0; i < n; i++) {
        arr[i] = resp[i];
    }

    free(cont);
    free(resp);
}

int main() {

    Lista l;
    lerlista(&l, "/tmp/restaurantes.csv");

    int ids[1000];
    int n = 0;
    int id;

    scanf("%d", &id);

    while (id != -1) {
        ids[n++] = id;
        scanf("%d", &id);
    }

    Restaurante* arr[1000];
    int t = 0;

    // filtra pelos ids
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < l.n; j++) {
            if (l.v[j]->id == ids[i]) {
                arr[t++] = l.v[j];
                break;
            }
        }
    }

    countingsort(arr, t);

    for (int i = 0; i < t; i++) {
        char b[1000];
        mostrar(arr[i], b);
        printf("%s\n", b);
    }

    return 0;
}