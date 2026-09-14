#include <stdio.h>
#include <string.h>

#include "Data.h"
#include "Veiculo.h"


int main() {

    Veiculo veiculos[1000];

    int quantidade = 0;


    /*
     * FALTA FAZER:
     *
     * Abrir o arquivo:
     *
     * /tmp/veiculos.csv
     *
     * Depois será necessário ler cada linha
     * do arquivo.
     */


    /*
     * FALTA FAZER:
     *
     * Separar os campos da linha do CSV.
     *
     * Também será necessário converter os valores
     * que estão como texto para int, double etc.
     */


    /*
     * FALTA FAZER:
     *
     * Criar os veículos com os dados encontrados
     * no arquivo e colocar no vetor.
     *
     * veiculos[quantidade] = ...
     * quantidade++;
     */


    // ==========================
    // PESQUISA SEQUENCIAL
    // ==========================

    int idProcurado;

    scanf("%d", &idProcurado);

    while (idProcurado != -1) {

        for (int i = 0; i < quantidade; i++) {

            if (veiculos[i].id == idProcurado) {

                /*
                 * FALTA FAZER:
                 *
                 * Imprimir o veículo no formato solicitado
                 * pelo exercício.
                 */

                break;
            }
        }

        scanf("%d", &idProcurado);
    }

    return 0;
}