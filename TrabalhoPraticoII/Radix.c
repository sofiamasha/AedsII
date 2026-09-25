#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Data {
  int ano;
  int mes;
  int dia;
} Data;

typedef struct Veiculo {
  int id;
  char marca[50];
  char modelo[50];
  int ano;
  char categoria[50];
  char combustivel[50];
  int cilindros;
  double cilindrada;
  char transmissao[50];
  char tracao[50];
  double consumoCidade;
  double consumoEstrada;
  double co2;
  bool turbo;
  Data dataRegistro;
} Veiculo;

void parseData(char s[], Veiculo *x) {
  char campo[3][6];
  char *token;

  token = strtok(s, "-");
  int i = 0;
  while (token != NULL && i < 3) {
    strncpy(campo[i], token, 5);
    campo[i][5] = '\0';
    i++;
    token = strtok(NULL, "-");
  }

  x->dataRegistro.ano = atoi(campo[0]);
  x->dataRegistro.mes = atoi(campo[1]);
  x->dataRegistro.dia = atoi(campo[2]);
}

void parseCombustivel(char s[]) {
  int i = 0;

  while (s[i] != '\0') {
    if (s[i] == ';')
      s[i] = ',';
    i++;
  }
}

void parseVeiculo(char s[], Veiculo *x) {
  char campo[15][50];
  char *token;

  token = strtok(s, ",");
  int i = 0;

  while (token != NULL && i < 15) {
    strncpy(campo[i], token, 49);
    campo[i][49] = '\0';
    i++;
    token = strtok(NULL, ",");
  }

  x->id = atoi(campo[0]);
  strcpy(x->marca, campo[1]);
  strcpy(x->modelo, campo[2]);
  x->ano = atoi(campo[3]);
  strcpy(x->categoria, campo[4]);
  strcpy(x->combustivel, campo[5]);
  parseCombustivel(x->combustivel);
  x->cilindros = atoi(campo[6]);
  x->cilindrada = atof(campo[7]);
  strcpy(x->transmissao, campo[8]);
  strcpy(x->tracao, campo[9]);
  x->consumoCidade = atof(campo[10]);
  x->consumoEstrada = atof(campo[11]);
  x->co2 = atof(campo[12]);
  x->turbo = (strcmp(campo[13], "true") == 0);

  parseData(campo[14], x);
}

void formatData(Data *d, char *vet) {
  sprintf(vet, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

void formatVeiculo(Veiculo *v) {
  char dataStr[12];

  formatData(&v->dataRegistro, dataStr);

  printf("[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## "
         "%.2f ## %.2f ## %.1f ## %s ## %s]\n",
         v->id, v->marca, v->modelo, v->ano, v->categoria, v->combustivel,
         v->cilindros, v->cilindrada, v->transmissao, v->tracao,
         v->consumoCidade, v->consumoEstrada, v->co2,
         v->turbo ? "true" : "false", dataStr);
}

Veiculo *lerCsv(char caminhoArq[], Veiculo *veiculos) {
  FILE *arq = fopen(caminhoArq, "r");

  if (arq == NULL) {
    printf("Erro: não foi possível abrir o arquivo %s\n", caminhoArq);
    return veiculos;
  }

  char linha[200];

  // Pula o cabeçalho
  fgets(linha, sizeof linha, arq);

  int i = 0;

  while (fgets(linha, sizeof linha, arq) != NULL && i < 500) {
    parseVeiculo(linha, &veiculos[i]);
    i++;
  }

  fclose(arq);
  return veiculos;
}

// encontra o maior ano do vetor
int getMaxAno(Veiculo *v, int n) {
  int maior = v[0].ano;

  for (int i = 1; i < n; i++) {
    if (v[i].ano > maior)
      maior = v[i].ano;
  }

  return maior;
}

// ordena os veiculos de acordo com um digito do ano
void CountingSort(Veiculo *v, int n, int exp) {
  Veiculo ordenado[n];
  int cont[10];

  // começa todas as contagens em zero
  for (int i = 0; i < 10; i++) {
    cont[i] = 0;
  }

  // conta quantas vezes cada digito aparece
  for (int i = 0; i < n; i++) {
    cont[(v[i].ano / exp) % 10]++;
  }

  // soma as contagens para descobrir as posições
  for (int i = 1; i < 10; i++) {
    cont[i] += cont[i - 1];
  }

  // coloca os veiculos na posição certa
  for (int i = n - 1; i >= 0; i--) {
    int digito = (v[i].ano / exp) % 10;

    ordenado[cont[digito] - 1] = v[i];
    cont[digito]--;
  }

  // copia de volta para o vetor original
  for (int i = 0; i < n; i++) {
    v[i] = ordenado[i];
  }
}

void RadixSort(Veiculo *v, int n) {
  if (n <= 1)
    return;

  int maior = getMaxAno(v, n);

  // vai passando por cada casa do numero
  for (int exp = 1; maior / exp > 0; exp *= 10) {
    CountingSort(v, n, exp);
  }
}

int main() {
  Veiculo veiculos[500];
  Veiculo v[500];
  int x;
  int j = 0;

  lerCsv("/tmp/veiculos.csv", veiculos);

  while (scanf("%d", &x) == 1) {
    for (int i = 0; i < 500; i++) {
      if (veiculos[i].id == x) {
        v[j] = veiculos[i];
        j++;
        i = 500;
      }
    }
  }

  RadixSort(v, j);

  for (int i = 0; i < j; i++) {
    formatVeiculo(&v[i]);
  }

  return 0;
}