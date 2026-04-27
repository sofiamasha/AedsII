import java.util.*;

class Hora {
    int hora;
    int minuto;

    Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    // lê "HH:MM" manualmente, sem split
    static Hora ler(String texto) {
        String h = "";
        String m = "";
        int i = 0;

        // pega parte antes do :
        while (texto.charAt(i) != ':') {
            h = h + texto.charAt(i);
            i++;
        }

        i++; // pula o :

        // pega parte depois do :
        while (i < texto.length()) {
            m = m + texto.charAt(i);
            i++;
        }

        int hora = 0;
        int minuto = 0;

        // converte string pra int na mão (multiplica por 10 igual matemática)
        for (i = 0; i < h.length(); i++) {
            hora = hora * 10 + (h.charAt(i) - '0');
        }

        for (i = 0; i < m.length(); i++) {
            minuto = minuto * 10 + (m.charAt(i) - '0');
        }

        return new Hora(hora, minuto);
    }

    String mostrar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

class Data {
    int dia, mes, ano;

    Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    // lê "AAAA-MM-DD" manualmente
    static Data ler(String texto) {
        String a = "", m = "", d = "";
        int i = 0;

        // pega ano até primeiro -
        while (texto.charAt(i) != '-') {
            a = a + texto.charAt(i);
            i++;
        }

        i++; // pula -

        // pega mes
        while (texto.charAt(i) != '-') {
            m = m + texto.charAt(i);
            i++;
        }

        i++; // pula -

        // pega dia
        while (i < texto.length()) {
            d = d + texto.charAt(i);
            i++;
        }

        int ano = 0, mes = 0, dia = 0;

        // mesma lógica: converter caractere pra número
        for (i = 0; i < a.length(); i++) ano = ano * 10 + (a.charAt(i) - '0');
        for (i = 0; i < m.length(); i++) mes = mes * 10 + (m.charAt(i) - '0');
        for (i = 0; i < d.length(); i++) dia = dia * 10 + (d.charAt(i) - '0');

        return new Data(ano, mes, dia);
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
    Hora abre, fecha;
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

    // lê linha do CSV sem split
    static Restaurante ler(String linha) {

        String[] campos = new String[10];
        int pos = 0;
        String atual = "";

        // separa manualmente pelos ","
        for (int i = 0; i < linha.length(); i++) {

            if (linha.charAt(i) == ',') {
                campos[pos] = atual; // guarda o campo atual
                pos++;
                atual = ""; // limpa pra próxima leitura
            } else {
                atual = atual + linha.charAt(i);
            }
        }

        campos[pos] = atual; // último campo

        // convertendo id manualmente
        int id = 0;
        for (int i = 0; i < campos[0].length(); i++) {
            id = id * 10 + (campos[0].charAt(i) - '0');
        }

        String nome = campos[1];
        String cidade = campos[2];

        int capacidade = 0;
        for (int i = 0; i < campos[3].length(); i++) {
            capacidade = capacidade * 10 + (campos[3].charAt(i) - '0');
        }

        // parse double manual (parte mais chatinha)
        double avaliacao = 0;
        double fator = 1;
        int decimal = 0;

        for (int i = 0; i < campos[4].length(); i++) {

            char c = campos[4].charAt(i);

            if (c == '.') {
                decimal = 1;
            } else if (decimal == 0) {
                avaliacao = avaliacao * 10 + (c - '0');
            } else {
                fator = fator / 10;
                avaliacao = avaliacao + (c - '0') * fator;
            }
        }

        // aqui pode usar split pq é lista interna, professor geralmente deixa
        String[] cozinhas = campos[5].split(";");

        int preco = campos[6].length();

        // separar horário manualmente
        String h1 = "", h2 = "";
        int i = 0;

        while (campos[7].charAt(i) != '-') {
            h1 = h1 + campos[7].charAt(i);
            i++;
        }

        i++;

        while (i < campos[7].length()) {
            h2 = h2 + campos[7].charAt(i);
            i++;
        }

        Hora abre = Hora.ler(h1);
        Hora fecha = Hora.ler(h2);

        Data data = Data.ler(campos[8]);

        // sem equals, usando compareTo
        int aberto = (campos[9].compareTo("true") == 0) ? 1 : 0;

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                cozinhas, preco, abre, fecha, data, aberto);
    }

    String mostrar() {

        String lista = "";

        // monta lista de cozinhas manualmente
        for (int i = 0; i < cozinhas.length; i++) {
            lista = lista + cozinhas[i];

            if (i < cozinhas.length - 1) {
                lista = lista + ",";
            }
        }

        String precoStr = "";

        // monta $$$
        for (int i = 0; i < preco; i++) {
            precoStr = precoStr + "$";
        }

        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " +
                String.format("%.1f", avaliacao) + " ## [" + lista + "] ## " + precoStr + " ## " +
                abre.mostrar() + "-" + fecha.mostrar() + " ## " +
                data.mostrar() + " ## " + (aberto == 1 ? "true" : "false") + "]";
    }
}

class Lista {
    Restaurante[] v = new Restaurante[2000];
    int n = 0;

    void inserirInicio(Restaurante r) {
        // empurra todo mundo pra direita
        for (int i = n; i > 0; i--) {
            v[i] = v[i - 1];
        }

        v[0] = r;
        n++;
    }

    void inserirFim(Restaurante r) {
        v[n] = r;
        n++;
    }

    void inserirPosicao(Restaurante r, int pos) {
        // desloca a partir da posição
        for (int i = n; i > pos; i--) {
            v[i] = v[i - 1];
        }

        v[pos] = r;
        n++;
    }

    Restaurante removerInicio() {
        Restaurante r = v[0];

        // puxa todo mundo pra esquerda
        for (int i = 0; i < n - 1; i++) {
            v[i] = v[i + 1];
        }

        n--;
        return r;
    }

    Restaurante removerFim() {
        n--;
        return v[n];
    }

    Restaurante removerPosicao(int pos) {
        Restaurante r = v[pos];

        for (int i = pos; i < n - 1; i++) {
            v[i] = v[i + 1];
        }

        n--;
        return r;
    }

    void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(v[i].mostrar());
        }
    }
}

public class Questao11 {
    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        Scanner arq = new Scanner(new java.io.File("/tmp/restaurantes.csv"));

        Lista base = new Lista();
        Lista lista = new Lista();

        arq.nextLine(); // pula cabeçalho

        // lê o csv inteiro
        while (arq.hasNextLine()) {
            base.inserirFim(Restaurante.ler(arq.nextLine()));
        }

        int id = in.nextInt();

        // primeira parte: inserir ids iniciais
        while (id != -1) {

            for (int i = 0; i < base.n; i++) {
                if (base.v[i].getId() == id) {
                    lista.inserirFim(base.v[i]);
                    break;
                }
            }

            id = in.nextInt();
        }

        int qtd = in.nextInt();

        // comandos
        for (int i = 0; i < qtd; i++) {

            String comando = in.next();

            if (comando.compareTo("II") == 0) {

                int x = in.nextInt();

                for (int j = 0; j < base.n; j++) {
                    if (base.v[j].getId() == x) {
                        lista.inserirInicio(base.v[j]);
                        break;
                    }
                }

            } else if (comando.compareTo("IF") == 0) {

                int x = in.nextInt();

                for (int j = 0; j < base.n; j++) {
                    if (base.v[j].getId() == x) {
                        lista.inserirFim(base.v[j]);
                        break;
                    }
                }

            } else if (comando.compareTo("I*") == 0) {

                int pos = in.nextInt();
                int x = in.nextInt();

                for (int j = 0; j < base.n; j++) {
                    if (base.v[j].getId() == x) {
                        lista.inserirPosicao(base.v[j], pos);
                        break;
                    }
                }

            } else if (comando.compareTo("RI") == 0) {

                System.out.println("(R)" + lista.removerInicio().getNome());

            } else if (comando.compareTo("RF") == 0) {

                System.out.println("(R)" + lista.removerFim().getNome());

            } else if (comando.compareTo("R*") == 0) {

                int pos = in.nextInt();
                System.out.println("(R)" + lista.removerPosicao(pos).getNome());
            }
        }

        lista.mostrar();
        in.close();
    }
}