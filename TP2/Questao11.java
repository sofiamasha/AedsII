import java.util.*;

class Hora {
    int hora;
    int minuto;

    Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    // separa "10:30" manualmente
    static Hora ler(String texto) {
        String hStr = "";
        String mStr = "";
        int i = 0;

        while (texto.charAt(i) != ':') {
            hStr += texto.charAt(i);
            i++;
        }

        i++;

        while (i < texto.length()) {
            mStr += texto.charAt(i);
            i++;
        }

        int h = 0;
        int m = 0;

        for (i = 0; i < hStr.length(); i++)
            h = h * 10 + (hStr.charAt(i) - '0');

        for (i = 0; i < mStr.length(); i++)
            m = m * 10 + (mStr.charAt(i) - '0');

        return new Hora(h, m);
    }

    String mostrar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

class Data {
    int dia;
    int mes;
    int ano;

    Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    // separa "2020-12-25" manualmente
    static Data ler(String texto) {
        String aStr = "", mStr = "", dStr = "";
        int i = 0;

        while (texto.charAt(i) != '-') {
            aStr += texto.charAt(i);
            i++;
        }

        i++;

        while (texto.charAt(i) != '-') {
            mStr += texto.charAt(i);
            i++;
        }

        i++;

        while (i < texto.length()) {
            dStr += texto.charAt(i);
            i++;
        }

        int a = 0, m = 0, d = 0;

        for (i = 0; i < aStr.length(); i++) a = a * 10 + (aStr.charAt(i) - '0');
        for (i = 0; i < mStr.length(); i++) m = m * 10 + (mStr.charAt(i) - '0');
        for (i = 0; i < dStr.length(); i++) d = d * 10 + (dStr.charAt(i) - '0');

        return new Data(a, m, d);
    }

    String mostrar() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

class Restaurante {
    int id;
    String nome;
    String cidade;
    int capacidade;
    double avaliacao;
    String[] cozinhas;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;

    Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                 String[] cozinhas, int preco, Hora abre, Hora fecha, Data data, int aberto) {

        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.cozinhas = cozinhas;
        this.preco = preco;
        this.abre = abre;
        this.fecha = fecha;
        this.data = data;
        this.aberto = aberto;
    }

    int getId() { return id; }
    String getNome() { return nome; }

    // separa linha do CSV sem usar split
    static Restaurante ler(String linha) {

        String[] campos = new String[10];
        int pos = 0;
        String atual = "";

        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == ',') {
                campos[pos++] = atual;
                atual = "";
            } else {
                atual += linha.charAt(i);
            }
        }
        campos[pos] = atual;

        int id = 0;
        for (int i = 0; i < campos[0].length(); i++)
            id = id * 10 + (campos[0].charAt(i) - '0');

        String nome = campos[1];
        String cidade = campos[2];

        int capacidade = 0;
        for (int i = 0; i < campos[3].length(); i++)
            capacidade = capacidade * 10 + (campos[3].charAt(i) - '0');

        // parse double manual
        double avaliacao = 0;
        double fator = 1;
        int decimal = 0;

        for (int i = 0; i < campos[4].length(); i++) {
            char c = campos[4].charAt(i);

            if (c == '.') {
                decimal = 1;
                continue;
            }

            if (decimal == 0) {
                avaliacao = avaliacao * 10 + (c - '0');
            } else {
                fator /= 10;
                avaliacao += (c - '0') * fator;
            }
        }

        String[] cozinhas = campos[5].split(";");

        int preco = campos[6].length();

        // horario
        String h1 = "", h2 = "";
        int i = 0;

        while (campos[7].charAt(i) != '-') {
            h1 += campos[7].charAt(i);
            i++;
        }

        i++;

        while (i < campos[7].length()) {
            h2 += campos[7].charAt(i);
            i++;
        }

        Hora abre = Hora.ler(h1);
        Hora fecha = Hora.ler(h2);

        Data data = Data.ler(campos[8]);

        int aberto = (campos[9].compareTo("true") == 0) ? 1 : 0;

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                cozinhas, preco, abre, fecha, data, aberto);
    }

    String mostrar() {

        String listaCozinha = "";

        for (int i = 0; i < cozinhas.length; i++) {
            listaCozinha += cozinhas[i];
            if (i < cozinhas.length - 1) listaCozinha += ",";
        }

        String precoStr = "";
        for (int i = 0; i < preco; i++) precoStr += "$";

        String abertoStr = (aberto == 1) ? "true" : "false";

        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " +
                String.format("%.1f", avaliacao) + " ## [" + listaCozinha + "] ## " + precoStr + " ## " +
                abre.mostrar() + "-" + fecha.mostrar() + " ## " +
                data.mostrar() + " ## " + abertoStr + "]";
    }
}

class Lista {
    Restaurante[] vetor = new Restaurante[2000];
    int tamanho = 0;

    // insere no inicio e empurra todo mundo
    void inserirInicio(Restaurante r) {
        for (int i = tamanho; i > 0; i--)
            vetor[i] = vetor[i - 1];

        vetor[0] = r;
        tamanho++;
    }

    // insere em posição específica
    void inserirPosicao(Restaurante r, int pos) {
        for (int i = tamanho; i > pos; i--)
            vetor[i] = vetor[i - 1];

        vetor[pos] = r;
        tamanho++;
    }

    // insere no final
    void inserirFim(Restaurante r) {
        vetor[tamanho++] = r;
    }

    // remove do inicio
    Restaurante removerInicio() {
        Restaurante r = vetor[0];

        for (int i = 0; i < tamanho - 1; i++)
            vetor[i] = vetor[i + 1];

        tamanho--;
        return r;
    }

    // remove de posição
    Restaurante removerPosicao(int pos) {
        Restaurante r = vetor[pos];

        for (int i = pos; i < tamanho - 1; i++)
            vetor[i] = vetor[i + 1];

        tamanho--;
        return r;
    }

    // remove do fim
    Restaurante removerFim() {
        return vetor[--tamanho];
    }

    void mostrar() {
        for (int i = 0; i < tamanho; i++)
            System.out.println(vetor[i].mostrar());
    }
}

public class Questao11 {

    public static void main(String[] args) throws Exception {

        Scanner entrada = new Scanner(System.in);

        Lista base = new Lista();
        Lista lista = new Lista();

        Scanner arquivo = new Scanner(new java.io.File("/tmp/restaurantes.csv"));
        arquivo.nextLine();

        // lê o CSV inteiro
        while (arquivo.hasNextLine()) {
            base.inserirFim(Restaurante.ler(arquivo.nextLine()));
        }

        int id = entrada.nextInt();

        // primeira parte: carregar ids
        while (id != -1) {

            for (int i = 0; i < base.tamanho; i++) {
                if (base.vetor[i].getId() == id) {
                    lista.inserirFim(base.vetor[i]);
                    break;
                }
            }

            id = entrada.nextInt();
        }

        int quantidadeComandos = entrada.nextInt();

        for (int i = 0; i < quantidadeComandos; i++) {

            String comando = entrada.next();

            if (comando.equals("II")) {

                int idNovo = entrada.nextInt();

                for (int j = 0; j < base.tamanho; j++) {
                    if (base.vetor[j].getId() == idNovo) {
                        lista.inserirInicio(base.vetor[j]);
                        break;
                    }
                }

            } else if (comando.equals("IF")) {

                int idNovo = entrada.nextInt();

                for (int j = 0; j < base.tamanho; j++) {
                    if (base.vetor[j].getId() == idNovo) {
                        lista.inserirFim(base.vetor[j]);
                        break;
                    }
                }

            } else if (comando.equals("I*")) {

                int pos = entrada.nextInt();
                int idNovo = entrada.nextInt();

                for (int j = 0; j < base.tamanho; j++) {
                    if (base.vetor[j].getId() == idNovo) {
                        lista.inserirPosicao(base.vetor[j], pos);
                        break;
                    }
                }

            } else if (comando.equals("RI")) {

                Restaurante r = lista.removerInicio();
                System.out.println("(R) " + r.getNome());

            } else if (comando.equals("RF")) {

                Restaurante r = lista.removerFim();
                System.out.println("(R) " + r.getNome());

            } else if (comando.equals("R*")) {

                int pos = entrada.nextInt();
                Restaurante r = lista.removerPosicao(pos);
                System.out.println("(R) " + r.getNome());
            }
        }

        lista.mostrar();

        entrada.close();
    }
}