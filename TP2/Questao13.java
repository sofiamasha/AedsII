import java.util.*;

class Hora {
    int h;
    int m;

    Hora(int h, int m) {
        this.h = h;
        this.m = m;
    }

    // separa tipo 10:30
    static Hora ler(String s) {
        int i = 0;
        String hs = "";
        String ms = "";

        while (s.charAt(i) != ':') {
            hs += s.charAt(i);
            i++;
        }

        i++;

        while (i < s.length()) {
            ms += s.charAt(i);
            i++;
        }

        int h = Integer.valueOf(hs);
        int m = Integer.valueOf(ms);

        return new Hora(h, m);
    }

    String mostrar() {
        return String.format("%02d:%02d", h, m);
    }
}

class Data {
    int d;
    int m;
    int a;

    Data(int a, int m, int d) {
        this.a = a;
        this.m = m;
        this.d = d;
    }

    // separa 2020-12-31
    static Data ler(String s) {
        int i = 0;
        String as = "";
        String ms = "";
        String ds = "";

        while (s.charAt(i) != '-') {
            as += s.charAt(i);
            i++;
        }

        i++;

        while (s.charAt(i) != '-') {
            ms += s.charAt(i);
            i++;
        }

        i++;

        while (i < s.length()) {
            ds += s.charAt(i);
            i++;
        }

        int a = Integer.valueOf(as);
        int m = Integer.valueOf(ms);
        int d = Integer.valueOf(ds);

        return new Data(a, m, d);
    }

    String mostrar() {
        return String.format("%02d/%02d/%04d", d, m, a);
    }
}

class Restaurante {
    int id;
    String nome;
    String cidade;
    int capacidade;
    double avaliacao;
    String[] cozinha;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;

    Restaurante(int id, String nome, String cidade, int cap, double ava,
                String[] cozinha, int preco, Hora abre, Hora fecha, Data data, int aberto) {

        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = cap;
        this.avaliacao = ava;
        this.cozinha = cozinha;
        this.preco = preco;
        this.abre = abre;
        this.fecha = fecha;
        this.data = data;
        this.aberto = aberto;
    }

    int getId() { return id; }
    String getNome() { return nome; }

    Data getData() { return data; }

    static Restaurante ler(String s) {

        String[] p = s.split(",");

        int id = Integer.valueOf(p[0]);
        String nome = p[1];
        String cidade = p[2];
        int cap = Integer.valueOf(p[3]);
        double ava = Double.valueOf(p[4]);

        String[] cozinha = p[5].split(";");
        int preco = p[6].length();

        // separa horario
        String h1 = "";
        String h2 = "";
        int i = 0;

        while (p[7].charAt(i) != '-') {
            h1 += p[7].charAt(i);
            i++;
        }

        i++;

        while (i < p[7].length()) {
            h2 += p[7].charAt(i);
            i++;
        }

        Hora abre = Hora.ler(h1);
        Hora fecha = Hora.ler(h2);

        Data data = Data.ler(p[8]);

        int aberto = 0;
        if (p[9].compareTo("true") == 0) aberto = 1;

        return new Restaurante(id, nome, cidade, cap, ava, cozinha, preco, abre, fecha, data, aberto);
    }

    String mostrar() {

        String c = "";

        for (int i = 0; i < cozinha.length; i++) {
            c += cozinha[i];
            if (i < cozinha.length - 1) c += ",";
        }

        String precoStr = "";
        for (int i = 0; i < preco; i++) precoStr += "$";

        String abertoStr = (aberto == 1) ? "true" : "false";

        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " +
               avaliacao + " ## [" + c + "] ## " + precoStr + " ## " +
               abre.mostrar() + "-" + fecha.mostrar() + " ## " +
               data.mostrar() + " ## " + abertoStr + "]";
    }
}

class Lista {
    Restaurante[] v = new Restaurante[1000];
    int n = 0;

    void ler(String path) throws Exception {
        Scanner sc = new Scanner(new java.io.File(path));

        sc.nextLine();

        while (sc.hasNextLine()) {
            v[n] = Restaurante.ler(sc.nextLine());
            n++;
        }

        sc.close();
    }
}

/* ===== FILA CIRCULAR ===== */

class Fila {

    Restaurante[] v = new Restaurante[5];
    int inicio = 0;
    int fim = 0;
    int tamanho = 0;

    // remove do inicio (fila normal)
    Restaurante remover() {
        Restaurante r = v[inicio];
        inicio = (inicio + 1) % 5;
        tamanho--;
        return r;
    }

    // insere no fim
    void inserir(Restaurante r) {

        // se estiver cheia remove antes
        if (tamanho == 5) {
            Restaurante removido = remover();
            System.out.println("(R)" + removido.getNome());
        }

        v[fim] = r;
        fim = (fim + 1) % 5;
        tamanho++;
    }

    // calcula media dos anos
    int media() {

        int soma = 0;
        int i = inicio;

        for (int j = 0; j < tamanho; j++) {
            soma += v[i].getData().a;
            i = (i + 1) % 5;
        }

        return Math.round((float)soma / tamanho);
    }

    void mostrar() {

        int i = inicio;

        for (int j = 0; j < tamanho; j++) {
            System.out.println(v[i].mostrar());
            i = (i + 1) % 5;
        }
    }
}

public class Questao13 {

    public static void main(String[] args) throws Exception {

        Lista l = new Lista();
        l.ler("/tmp/restaurantes.csv");

        Scanner sc = new Scanner(System.in);
        Fila fila = new Fila();

        int id = sc.nextInt();

        // primeira parte
        while (id != -1) {

            for (int i = 0; i < l.n; i++) {
                if (l.v[i].getId() == id) {

                    fila.inserir(l.v[i]);

                    System.out.println("(I)" + fila.media());
                    break;
                }
            }

            id = sc.nextInt();
        }

        int operacoes = sc.nextInt();

        // segunda parte
        for (int i = 0; i < operacoes; i++) {

            String comando = sc.next();

            if (comando.equals("I")) {

                int idNovo = sc.nextInt();

                for (int j = 0; j < l.n; j++) {
                    if (l.v[j].getId() == idNovo) {

                        fila.inserir(l.v[j]);
                        System.out.println("(I)" + fila.media());
                        break;
                    }
                }

            } else if (comando.equals("R")) {

                Restaurante r = fila.remover();
                System.out.println("(R)" + r.getNome());
            }
        }

        fila.mostrar();

        sc.close();
    }
}