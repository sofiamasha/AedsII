import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Veiculo[] lista = new Veiculo[1000];
        int total = 0;

        Scanner arquivo = new Scanner(new File("/tmp/veiculos.csv"));

        // pula a primeira linha pq ela tem os nomes das colunas
        arquivo.nextLine();

        while (arquivo.hasNextLine()) {

            String linha = arquivo.nextLine();

            // separa a linha nas informacoes do veiculo
            String[] dados = linha.split(",");

            int id = Integer.parseInt(dados[0]);
            String marca = dados[1];
            String modelo = dados[2];
            int ano = Integer.parseInt(dados[3]);
            String categoria = dados[4];

            // separa os combustiveis quando tem mais de um
            String[] combustivel = dados[5].split(";");

            int cilindros = Integer.parseInt(dados[6]);
            double cilindrada = Double.parseDouble(dados[7]);
            String transmissao = dados[8];
            String tracao = dados[9];
            double consumoCidade = Double.parseDouble(dados[10]);
            double consumoEstrada = Double.parseDouble(dados[11]);
            double co2 = Double.parseDouble(dados[12]);

            boolean turbo = dados[13].equals("verdadeiro");

            String[] data = dados[14].split("/");

            // pega cada parte da data pra criar o objeto Data
            int dia = Integer.parseInt(data[0]);
            int mes = Integer.parseInt(data[1]);
            int anoData = Integer.parseInt(data[2]);

            Data registro = new Data(dia, mes, anoData);

            lista[total] = new Veiculo(
                id,
                marca,
                modelo,
                ano,
                categoria,
                combustivel,
                cilindros,
                cilindrada,
                transmissao,
                tracao,
                consumoCidade,
                consumoEstrada,
                co2,
                turbo,
                registro
            );

            total++;
        }

        arquivo.close();

        Scanner teclado = new Scanner(System.in);

        int id = teclado.nextInt();

        while (id != -1) {

            // vai passando pelo vetor ate achar o id que foi digitado
            for (int i = 0; i < total; i++) {

                if (lista[i].getId() == id) {
                    System.out.println(lista[i].format());
                    break;
                }
            }

            id = teclado.nextInt();
        }

        teclado.close();
    }
}