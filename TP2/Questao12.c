#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* ===== STRUCTS BASICAS ===== */

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char cozinha[10][50];
    int qtdCozinha;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;
} Restaurante;

/* ===== FUNCOES DE LEITURA ===== */

// lê hora tipo 10:30
Hora lerHora(char* s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

// lê data tipo 2020-12-31
Data lerData(char* s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

/* ===== PARSE DO RESTAURANTE ===== */

Restaurante* lerRestaurante(char* linha) {

    Restaurante* r = (Restaurante*) malloc(sizeof(Restaurante));

    char tipos[200];
    char horario[50];
    char data[50];
    char aberto[10];

    // pega campos principais
    sscanf(linha, "%d,%[^,],%[^,],%d,%lf",
        &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao);

    // pega tipos cozinha
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%[^,]", tipos);

    r->qtdCozinha = 0;
    char* p = strtok(tipos, ";");

    // separa cada tipo
    while (p != NULL) {
        strcpy(r->cozinha[r->qtdCozinha], p);
        r->qtdCozinha++;
        p = strtok(NULL, ";");
    }

    // faixa preco
    char precoStr[10];
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%[^,]", precoStr);
    r->preco = strlen(precoStr);

    // horario
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%[^,]", horario);

    char h1[10], h2[10];
    sscanf(horario, "%[^-]-%s", h1, h2);

    r->abre = lerHora(h1);
    r->fecha = lerHora(h2);

    // data
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%[^,]", data);
    r->data = lerData(data);

    // aberto
    sscanf(linha, "%*d,%*[^,],%*[^,],%*d,%*lf,%*[^,],%*[^,],%*[^,],%*[^,],%s", aberto);

    if (strcmp(aberto, "true") == 0) r->aberto = 1;
    else r->aberto = 0;

    return r;
}

/* ===== PRINT BONITINHO ===== */

void mostrar(Restaurante* r) {

    char c[200] = "[";

    // junta cozinhas
    for (int i = 0; i < r->qtdCozinha; i++) {
        strcat(c, r->cozinha[i]);
        if (i < r->qtdCozinha - 1) strcat(c, ",");
    }
    strcat(c, "]");

    char preco[20] = "";

    // transforma numero em $$$
    for (int i = 0; i < r->preco; i++) strcat(preco, "$");

    printf("[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]\n",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
        c, preco,
        r->abre.hora, r->abre.minuto,
        r->fecha.hora, r->fecha.minuto,
        r->data.dia, r->data.mes, r->data.ano,
        r->aberto ? "true" : "false"
    );
}

/* ===== COLECAO ===== */

typedef struct {
    Restaurante* v[1000];
    int n;
} Lista;

void lerArquivo(Lista* l) {

    FILE* f = fopen("/tmp/restaurantes.csv", "r");
    char linha[1000];

    fgets(linha, 1000, f); // pula cabecalho

    l->n = 0;

    while (fgets(linha, 1000, f) != NULL) {
        linha[strcspn(linha, "\n")] = 0;
        l->v[l->n] = lerRestaurante(linha);
        l->n++;
    }

    fclose(f);
}

/* ===== PILHA ===== */

typedef struct {
    Restaurante* v[2000];
    int topo;
} Pilha;

// começa vazia
void iniciar(Pilha* p) {
    p->topo = -1;
}

// empilha (push)
void push(Pilha* p, Restaurante* r) {
    p->topo++;
    p->v[p->topo] = r;
}

// desempilha (pop)
Restaurante* pop(Pilha* p) {
    Restaurante* r = p->v[p->topo];
    p->topo--;
    return r;
}

// mostra do topo pra base (pilha raiz)
void mostrarPilha(Pilha* p) {
    for (int i = p->topo; i >= 0; i--) {
        mostrar(p->v[i]);
    }
}

/* ===== MAIN ===== */

int main() {

    Lista lista;
    lerArquivo(&lista);

    Pilha pilha;
    iniciar(&pilha);

    int id;

    // primeira parte: empilha pelos ids
    scanf("%d", &id);

    while (id != -1) {

        for (int i = 0; i < lista.n; i++) {
            if (lista.v[i]->id == id) {
                push(&pilha, lista.v[i]);
                break;
            }
        }

        scanf("%d", &id);
    }

    int n;
    scanf("%d", &n);

    // segunda parte: comandos
    for (int i = 0; i < n; i++) {

        char comando[10];
        scanf("%s", comando);

        // inserir (push)
        if (strcmp(comando, "I") == 0) {

            int idNovo;
            scanf("%d", &idNovo);

            for (int j = 0; j < lista.n; j++) {
                if (lista.v[j]->id == idNovo) {
                    push(&pilha, lista.v[j]);
                    break;
                }
            }

        }
        // remover (pop)
        else if (strcmp(comando, "R") == 0) {

            Restaurante* r = pop(&pilha);
            printf("(R)%s\n", r->nome);
        }
    }

    // mostra final
    mostrarPilha(&pilha);

    return 0;
}