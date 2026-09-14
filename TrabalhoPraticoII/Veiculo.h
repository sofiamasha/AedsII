#ifndef VEICULO_H
#define VEICULO_H

#include "Data.h"

typedef struct {

    int id;

    char marca[100];

    char modelo[100];

    int ano;

    char categoria[100];

    char combustivel[5][50];

    int cilindros;

    double cilindrada;

    char transmissao[50];

    char tracao[50];

    double consumoCidade;

    double consumoEstrada;

    double co2;

    int turbo;

    Data dataRegistro;

} Veiculo;

#endif