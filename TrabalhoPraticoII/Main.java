import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner arquivo = new Scanner(new File("/tmp/veiculos.csv"));

        // Pula o cabeçalho do arquivo
        arquivo.nextLine();

        Veiculo[] veiculos = new Veiculo[1000];

        int quantidade = 0;


        /*
         * FALTA FAZER:
         *
         * Ler cada linha do arquivo CSV.
         *
         * Depois separar os campos da linha.
         *
         * A conversão dos valores para int, double e boolean
         * ainda não foi feita.
         */


        /*
         * EXEMPLO DA PARTE QUE AINDA FALTA:
         *
         * String linha = arquivo.nextLine();
         * String[] partes = linha.split(",");
         *
         * Depois será necessário transformar os valores
         * das posições do vetor para os tipos corretos.
         */


        /*
         * FALTA FAZER:
         *
         * Criar os objetos Veiculo com os dados lidos
         * e armazená-los no vetor.
         *
         * veiculos[quantidade] = veiculo;
         * quantidade++;
         */


        arquivo.close();


        // ==========================
        // PESQUISA SEQUENCIAL
        // ==========================

        Scanner sc = new Scanner(System.in);

        int idProcurado = sc.nextInt();

        while (idProcurado != -1) {

            for (int i = 0; i < quantidade; i++) {

                if (veiculos[i].getId() == idProcurado) {

                    System.out.println(veiculos[i].format());

                    break;
                }
            }

            idProcurado = sc.nextInt();
        }

        sc.close();
    }
}