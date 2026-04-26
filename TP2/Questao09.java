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
        String hs = "";
        String ms = "";
        int i = 0;

        while (s.charAt(i) != ':') {
            hs += s.charAt(i);
            i++;
        }

        i++;

        while (i < s.length()) {
            ms += s.charAt(i);
            i++;
        }

        int h = 0;
        int m = 0;

        h = h + (hs.charAt(0) - '0') * 10 + (hs.charAt(1) - '0');
        m = m + (ms.charAt(0) - '0') * 10 + (ms.charAt(1) - '0');

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

    // separa 2020-12-25
    static Data ler(String s) {
        String as = "";
        String ms = "";
        String ds = "";

        int i = 0;

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

        int a = 0;
        int m = 0;
        int d = 0;

        for (int j = 0; j < as.length(); j++) a = a * 10 + (as.charAt(j) - '0');
        for (int j = 0; j < ms.length(); j++) m = m * 10 + (ms.charAt(j) - '0');
        for (int j = 0; j < ds.length(); j++) d = d * 10 + (ds.charAt(j) - '0');

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

        int id = 0;
        for (int i = 0; i < p[0].length(); i++) {
            id = id * 10 + (p[0].charAt(i) - '0');
        }

        String nome = p[1];
        String cidade = p[2];

        int cap = 0;
        for (int i = 0; i < p[3].length(); i++) {
            cap = cap * 10 + (p[3].charAt(i) - '0');
        }

        double ava = Double.parseDouble(p[4]);

        String[] cozinha = p[5].split(";");
        int preco = p[6].length();

        // horario 
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

public class Questao09 {

    // compara datas (ano, mes, dia) e desempata por nome
    static int comparar(Restaurante a, Restaurante b) {

        Data da = a.getData();
        Data db = b.getData();

        if (da.a != db.a) return da.a - db.a;
        if (da.m != db.m) return da.m - db.m;
        if (da.d != db.d) return da.d - db.d;

        return a.getNome().compareTo(b.getNome());
    }

    // aqui começa o heap
    static void heapify(Restaurante[] arr, int n, int i) {

        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        // compara com filho esquerdo
        if (esq < n && comparar(arr[esq], arr[maior]) > 0) {
            maior = esq;
        }

        // compara com filho direito
        if (dir < n && comparar(arr[dir], arr[maior]) > 0) {
            maior = dir;
        }

        // se o maior não for o pai, troca
        if (maior != i) {

            Restaurante temp = arr[i];
            arr[i] = arr[maior];
            arr[maior] = temp;

            // chama de novo pq pode ter bagunçado embaixo
            heapify(arr, n, maior);
        }
    }

    // heapsort 
    static void heapsort(Restaurante[] arr, int n) {

        // monta o heap p organizar
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // vai tirando o maior e jogando pro final
        for (int i = n - 1; i > 0; i--) {

            Restaurante temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    public static void main(String[] args) throws Exception {

        Lista l = new Lista();
        l.ler("/tmp/restaurantes.csv");

        Scanner sc = new Scanner(System.in);

        int[] ids = new int[1000];
        int n = 0;

        int id = sc.nextInt();

        while (id != -1) {
            ids[n] = id;
            n++;
            id = sc.nextInt();
        }

        Restaurante[] arr = new Restaurante[n];
        int t = 0;

        // filtra os ids escolhidos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l.n; j++) {
                if (l.v[j].getId() == ids[i]) {
                    arr[t] = l.v[j];
                    t++;
                    break;
                }
            }
        }

        heapsort(arr, t);

        // imprime
        for (int i = 0; i < t; i++) {
            System.out.println(arr[i].mostrar());
        }

        sc.close();
    }
}