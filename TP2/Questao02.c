#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

// structs básicas
typedef struct {
  int dia, mes, ano;
} Data;

typedef struct {
  int h, m;
} Hora;

typedef struct {
  int id;
  char* nome;
  char* cidade;
  int capacidade;
  double nota;
  char** tipos;
  int preco;
  Hora abre;
  Hora fecha;
  Data data;
  bool aberto;
} Restaurante;

typedef struct {
  int tam;
  Restaurante* vet;
} Colecao;

// converte "2020-10-05"
Data lerData(char *s){
  Data d;
  sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
  return d;
}

void printData(Data* d, char* out){
  sprintf(out,"%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

// converte "12:30"
Hora lerHora(char *s){
  Hora h;
  sscanf(s,"%d:%d", &h.h, &h.m);
  return h;
}

void printHora(Hora* h, char* out){
  sprintf(out, "%02d:%02d", h->h, h->m);
}

// limpa memória alocada
void liberar(Restaurante* r){
  free(r->nome);
  free(r->cidade);
  free(r->tipos[0]);
  free(r->tipos);
}

// pega uma linha e vira struct
Restaurante* lerRest(char *linha){
  Restaurante* r = (Restaurante*)malloc(sizeof(Restaurante));

  char nome[100], cidade[100], tipo[100], preco[10];
  char h1[6], h2[6], data[11], aberto[10];

  sscanf(linha, "%d,%[^,],%[^,],%d,%lf,%[^,],%[^,],%[^-]-%[^,],%[^,],%[^\n]",
    &r->id, nome, cidade, &r->capacidade, &r->nota,
    tipo, preco, h1, h2, data, aberto);

  // limpa lixo do final
  for(int i=0; aberto[i]; i++){
    if(aberto[i]=='\n' || aberto[i]=='\r') aberto[i]=0;
  }

  r->aberto = (strcmp(aberto,"true")==0);

  r->abre = lerHora(h1);
  r->fecha = lerHora(h2);
  r->data = lerData(data);

  // copia nome
  r->nome = malloc(strlen(nome)+1);
  strcpy(r->nome, nome);

  r->cidade = malloc(strlen(cidade)+1);
  strcpy(r->cidade, cidade);

  // conta $
  r->preco = strlen(preco);

  // troca ; por ,
  for(int i=0; tipo[i]; i++){
    if(tipo[i]==';') tipo[i]=',';
  }

  r->tipos = malloc(sizeof(char*));
  r->tipos[0] = malloc(strlen(tipo)+1);
  strcpy(r->tipos[0], tipo);

  return r;
}

// formata saída
void printRest(Restaurante* r, char* out){
  char h1[6], h2[6], data[12];

  printHora(&r->abre, h1);
  printHora(&r->fecha, h2);
  printData(&r->data, data);

  char p[5];
  int i;
  for(i=0;i<r->preco;i++) p[i]='$';
  p[i]=0;

  sprintf(out,"[%d ## %s ## %s ## %d ## %.1lf ## [%s] ## %s ## %s-%s ## %s ## %s]",
    r->id, r->nome, r->cidade, r->capacidade, r->nota,
    r->tipos[0], p, h1, h2, data,
    r->aberto ? "true" : "false");
}

// lê arquivo
void carregar(Colecao* c, char* path){
  FILE* f = fopen(path,"r");
  if(!f) return;

  char linha[200];
  fgets(linha,200,f); // pula cabeçalho

  int i=0;
  while(fgets(linha,200,f)){
    Restaurante* r = lerRest(linha);
    c->vet[i++] = *r;
    free(r);
  }
  fclose(f);
}

// conta linhas e cria coleção
Colecao* iniciar(){
  FILE* f = fopen("/tmp/restaurantes.csv","r");
  if(!f) return NULL;

  int n=0;
  char linha[200];
  while(fgets(linha,200,f)) n++;
  fclose(f);

  Colecao* c = malloc(sizeof(Colecao));
  c->tam = n-1;
  c->vet = malloc((n-1)*sizeof(Restaurante));

  carregar(c,"/tmp/restaurantes.csv");

  return c;
}

// busca simples
int buscar(Colecao* c, int id){
  for(int i=0;i<c->tam;i++){
    if(c->vet[i].id==id) return i;
  }
  return -1;
}

// string -> int manual (sem atoi)
int toInt(char *s){
  int num=0;
  for(int i=0; s[i] && s[i]!='\n'; i++){
    num = num*10 + (s[i]-'0');
  }
  return num;
}

int main(){
  Colecao* c = iniciar();

  char entrada[10];
  scanf("%s", entrada);

  while(strcmp(entrada,"-1")!=0){
    int id = toInt(entrada);

    int pos = buscar(c,id);
    if(pos!=-1){
      char out[300];
      printRest(&c->vet[pos], out);
      printf("%s\n", out);
    }

    scanf("%s", entrada);
  }

  // limpeza
  for(int i=0;i<c->tam;i++){
    liberar(&c->vet[i]);
  }
  free(c->vet);
  free(c);

  return 0;
}