/*
 * TP03 - AEDS II - PUC Minas
 * Aluno: matricula 865115
 * Questao 04 - Ordenacao parcial por Heapsort (C). Utiliza um heap maximo de tamanho K para manter os K menores restaurantes ordenados pelo nome.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_REG 2000
#define MAX_LINHA 1024
#define MAX_TIPOS 10
#define K 10
#define MATRICULA "865115"

typedef struct { int ano, mes, dia; } Data;
typedef struct { int hora, minuto; } Hora;
typedef struct {
    int id; char nome[128], cidade[128];
    int capacidade; double avaliacao;
    char tipos_cozinha[MAX_TIPOS][64]; int n_tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura, horario_fechamento;
    Data data_abertura; bool aberto;
} Restaurante;
typedef struct { int tamanho; Restaurante* restaurantes[MAX_REG]; } ColecaoRestaurantes;

Data parse_data(char* s) { Data d; sscanf(s,"%d-%d-%d",&d.ano,&d.mes,&d.dia); return d; }
void formatar_data(Data* d, char* b) { sprintf(b,"%02d/%02d/%04d",d->dia,d->mes,d->ano); }
Hora parse_hora(char* s) { Hora h; sscanf(s,"%d:%d",&h.hora,&h.minuto); return h; }
void formatar_hora(Hora* h, char* b) { sprintf(b,"%02d:%02d",h->hora,h->minuto); }

Restaurante* parse_restaurante(char* s) {
    Restaurante* r = calloc(1, sizeof(Restaurante));
    char buf[MAX_LINHA]; strcpy(buf, s);
    char* t = strtok(buf, ","); r->id = atoi(t);
    t = strtok(NULL, ","); strcpy(r->nome, t);
    t = strtok(NULL, ","); strcpy(r->cidade, t);
    t = strtok(NULL, ","); r->capacidade = atoi(t);
    t = strtok(NULL, ","); r->avaliacao = atof(t);
    t = strtok(NULL, ","); char tc[256]; strcpy(tc, t); /* split adiado */
    t = strtok(NULL, ","); r->faixa_preco = (int)strlen(t);
    t = strtok(NULL, ","); char* d = strchr(t, '-'); *d = '\0';
    r->horario_abertura = parse_hora(t); r->horario_fechamento = parse_hora(d+1);
    t = strtok(NULL, ","); r->data_abertura = parse_data(t);
    t = strtok(NULL, ",\n\r"); r->aberto = (strcmp(t,"true")==0);
    char* t2 = strtok(tc, ";"); r->n_tipos_cozinha = 0;
    while (t2 && r->n_tipos_cozinha < MAX_TIPOS) { strcpy(r->tipos_cozinha[r->n_tipos_cozinha++], t2); t2 = strtok(NULL, ";"); }
    return r;
}
void formatar_restaurante(Restaurante* r, char* b) {
    char tc[512] = "["; for (int i=0;i<r->n_tipos_cozinha;i++){if(i>0)strcat(tc,","); strcat(tc,r->tipos_cozinha[i]);} strcat(tc,"]");
    char dol[8]=""; for (int i=0;i<r->faixa_preco;i++) strcat(dol,"$");
    char ha[8],hf[8],da[16];
    formatar_hora(&r->horario_abertura, ha); formatar_hora(&r->horario_fechamento, hf);
    formatar_data(&r->data_abertura, da);
    sprintf(b,"[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        r->id,r->nome,r->cidade,r->capacidade,r->avaliacao,tc,dol,ha,hf,da, r->aberto?"true":"false");
}
void ler_csv_colecao(ColecaoRestaurantes* c, char* path) {
    FILE* f = fopen(path,"r"); if (!f) { fprintf(stderr,"Erro %s\n",path); exit(1); }
    char l[MAX_LINHA]; int first=1; c->tamanho=0;
    while (fgets(l,MAX_LINHA,f)) {
        if (l[0]=='\n'||l[0]=='\r') continue;
        if (first) { first=0; if (l[0]<'0'||l[0]>'9') continue; }
        l[strcspn(l,"\r\n")]='\0';
        c->restaurantes[c->tamanho++] = parse_restaurante(l);
    } fclose(f);
}
ColecaoRestaurantes* ler_csv() { ColecaoRestaurantes* c = calloc(1,sizeof(*c)); ler_csv_colecao(c,"/tmp/restaurantes.csv"); return c; }
Restaurante* buscar_por_id(ColecaoRestaurantes* c, int id) {
    for (int i=0;i<c->tamanho;i++) if (c->restaurantes[i]->id==id) return c->restaurantes[i];
    return NULL;
}

long comparacoes = 0, movimentacoes = 0;

static void swap_r(Restaurante** a, int i, int j) { Restaurante* t=a[i]; a[i]=a[j]; a[j]=t; movimentacoes+=3; }

static int cmp_chave(Restaurante* a, Restaurante* b) {
    if (a->data_abertura.ano  != b->data_abertura.ano)  return a->data_abertura.ano  - b->data_abertura.ano;
    if (a->data_abertura.mes  != b->data_abertura.mes)  return a->data_abertura.mes  - b->data_abertura.mes;
    if (a->data_abertura.dia  != b->data_abertura.dia)  return a->data_abertura.dia  - b->data_abertura.dia;
    return strcmp(a->nome, b->nome);
}

static void heapify_max(Restaurante** a, int i, int n) {
    while (1) {
        int l=2*i+1, r=2*i+2, mai=i;
        if (l<n) { comparacoes++; if (cmp_chave(a[l], a[mai]) > 0) mai = l; }
        if (r<n) { comparacoes++; if (cmp_chave(a[r], a[mai]) > 0) mai = r; }
        if (mai == i) break;
        swap_r(a, i, mai); i = mai;
    }
}

void heapsort_parcial(Restaurante** a, int n, int k) {
    if (k > n) k = n;
    // max-heap nos k primeiros: contera os k menores ao final
    for (int i = k/2 - 1; i >= 0; i--) heapify_max(a, i, k);
    for (int i = k; i < n; i++) {
        comparacoes++;
        if (cmp_chave(a[i], a[0]) < 0) {
            swap_r(a, 0, i);
            heapify_max(a, 0, k);
        }
    }
    // ordena heap em ordem crescente (extrai max para o fim)
    for (int i = k - 1; i > 0; i--) {
        swap_r(a, 0, i);
        heapify_max(a, 0, i);
    }
}

int main() {
    ColecaoRestaurantes* col = ler_csv();
    Restaurante* arr[MAX_REG]; int n = 0;
    char l[64];
    while (fgets(l,sizeof(l),stdin)) {
        l[strcspn(l,"\r\n")]='\0';
        if (!strcmp(l,"-1")) break; if (l[0]=='\0') continue;
        Restaurante* r = buscar_por_id(col, atoi(l));
        if (r) arr[n++] = r;
    }
    clock_t t0 = clock();
    heapsort_parcial(arr, n, K);
    clock_t t1 = clock();
    char b[1024];
    for (int i=0;i<n;i++){ formatar_restaurante(arr[i],b); printf("%s\n",b); }
    double seg = (double)(t1-t0)/CLOCKS_PER_SEC;
    FILE* log = fopen(MATRICULA "_heapsort_parcial.txt","w");
    fprintf(log,"%s\t%ld\t%ld\t%.6f\n", MATRICULA, comparacoes, movimentacoes, seg);
    fclose(log);
    return 0;
}

