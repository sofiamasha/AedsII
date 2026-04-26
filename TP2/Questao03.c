#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
    int dia;
    int mes;
    int ano;
} Data;

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos[10][50];
    int qtd;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;
} Restaurante;

// transforma "HH:MM"
Hora pegaHora(char *s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

// transforma "AAAA-MM-DD"
Data pegaData(char *s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

// pega uma linha do csv e monta o restaurante
Restaurante* montar(char *linha) {
    Restaurante *r = malloc(sizeof(Restaurante));

    char tipos[200];
    char data[20];
    char aberto[10];

    // pega tipos
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%199[^,]", tipos);

    r->qtd = 0;
    char *p = strtok(tipos, ";");
    while (p != NULL) {
        strcpy(r->tipos[r->qtd], p);
        r->qtd++;
        p = strtok(NULL, ";");
    }

    // conta $
    char preco[10];
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%9[^,]", preco);
    r->preco = strlen(preco);

    // horario
    char h[20], h1[10], h2[10];
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%19[^,]", h);
    sscanf(h, "%[^-]-%s", h1, h2);
    r->abre = pegaHora(h1);
    r->fecha = pegaHora(h2);

    // data
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%19[^,]", data);
    r->data = pegaData(data);

    // aberto
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%*[^,],%9s", aberto);
    r->aberto = strcmp(aberto, "true") == 0;

    // resto normal
    sscanf(linha, "%d,%99[^,],%99[^,],%d,%lf",
        &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao);

    return r;
}

// monta string final
void mostrar(Restaurante *r, char *out) {
    char tipos[200] = "[";
    for (int i = 0; i < r->qtd; i++) {
        strcat(tipos, r->tipos[i]);
        if (i < r->qtd - 1) strcat(tipos, ",");
    }
    strcat(tipos, "]");

    char preco[10] = "";
    for (int i = 0; i < r->preco; i++) strcat(preco, "$");

    char h1[10], h2[10], data[20];
    sprintf(h1, "%02d:%02d", r->abre.hora, r->abre.minuto);
    sprintf(h2, "%02d:%02d", r->fecha.hora, r->fecha.minuto);
    sprintf(data, "%02d/%02d/%04d", r->data.dia, r->data.mes, r->data.ano);

    sprintf(out,
        "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
        tipos, preco, h1, h2, data,
        r->aberto ? "true" : "false"
    );
}

// selection sort pelo nome
void ordenar(Restaurante *v[], int n) {
    for (int i = 0; i < n - 1; i++) {
        int menor = i;

        // acha o menor nome
        for (int j = i + 1; j < n; j++) {
            if (strcmp(v[j]->nome, v[menor]->nome) < 0) {
                menor = j;
            }
        }

        // troca
        Restaurante *tmp = v[i];
        v[i] = v[menor];
        v[menor] = tmp;
    }
}

// leitura do csv
Restaurante *lista[1000];
int total = 0;

void ler() {
    FILE *f = fopen("/tmp/restaurantes.csv", "r");
    char linha[1000];

    fgets(linha, sizeof(linha), f); // ignora primeira linha

    while (fgets(linha, sizeof(linha), f)) {
        linha[strcspn(linha, "\n")] = 0; // tira quebra de linha
        lista[total++] = montar(linha);
    }

    fclose(f);
}

int main() {
    ler();

    Restaurante *resp[1000];
    int tam = 0;

    int id;
    scanf("%d", &id);

    // pega os ids digitados
    while (id != -1) {
        for (int i = 0; i < total; i++) {
            if (lista[i]->id == id) {
                resp[tam++] = lista[i];
                break;
            }
        }
        scanf("%d", &id);
    }

    // ordena pelo nome
    ordenar(resp, tam);

    // imprime tudo
    for (int i = 0; i < tam; i++) {
        char out[500];
        mostrar(resp[i], out);
        printf("%s\n", out);
    }

    return 0;
}