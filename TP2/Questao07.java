import java.util.*;

class Hora {
    int h;
    int m;

    Hora(int h, int m) {
        this.h = h;
        this.m = m;
    }

    // transforma string em numero 
    static int conv(String s) {
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            num = num * 10 + (s.charAt(i) - '0');
        }

        return num;
    }

    // separa hora tipo 10:30
    static Hora ler(String s) {
        String hs = "";
        String ms = "";
        int i = 0;

        // pega td antes do :
        while (s.charAt(i) != ':') {
            hs += s.charAt(i);
            i++;
        }

        i++; // pula :

        // pega o resto
        while (i < s.length()) {
            ms += s.charAt(i);
            i++;
        }

        int h = conv(hs);
        int m = conv(ms);

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

    // separa 2028-12-23
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

        int a = Hora.conv(as);
        int m = Hora.conv(ms);
        int d = Hora.conv(ds);

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
    String[] cozinha = new String[10];
    int qtd;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;

    Restaurante(int id, String nome, String cidade, int cap, double ava,
                String[] cozinha, int qtd, int preco, Hora abre, Hora fecha, Data data, int aberto) {

        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = cap;
        this.avaliacao = ava;
        this.cozinha = cozinha;
        this.qtd = qtd;
        this.preco = preco;
        this.abre = abre;
        this.fecha = fecha;
        this.data = data;
        this.aberto = aberto;
    }

    int getId() { return id; }
    String getNome() { return nome; }
    String getCidade() { return cidade; }

    // separa tudo na mão sem split
    static Restaurante ler(String s) {

        String[] p = new String[10];
        int pos = 0;
        String atual = "";

        // separa por virgula
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                p[pos] = atual;
                atual = "";
                pos++;
            } else {
                atual += s.charAt(i);
            }
        }
        p[pos] = atual;

        int id = Hora.conv(p[0]);
        String nome = p[1];
        String cidade = p[2];
        int cap = Hora.conv(p[3]);
        double ava = Double.valueOf(p[4]);

        // separar cozinhas
        String[] cozinha = new String[10];
        int qtd = 0;
        String temp = "";

        for (int i = 0; i < p[5].length(); i++) {
            if (p[5].charAt(i) == ';') {
                cozinha[qtd] = temp;
                temp = "";
                qtd++;
            } else {
                temp += p[5].charAt(i);
            }
        }
        cozinha[qtd] = temp;
        qtd++;

        int preco = p[6].length();

        // separar horario
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
        if (p[9].compareTo("true") == 0) {
            aberto = 1;
        }

        return new Restaurante(id, nome, cidade, cap, ava, cozinha, qtd, preco, abre, fecha, data, aberto);
    }

    String mostrar() {

        String c = "";

        // junta cozinhas
        for (int i = 0; i < qtd; i++) {
            c += cozinha[i];
            if (i < qtd - 1) {
                c += ",";
            }
        }

        String precoStr = "";

        // monta $
        for (int i = 0; i < preco; i++) {
            precoStr += "$";
        }

        String abertoStr;
        if (aberto == 1) abertoStr = "true";
        else abertoStr = "false";

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

public class Questao07 {

    // junta duas partes ordenadas
    static void merge(Restaurante[] arr, int ini, int meio, int fim) {

        int tam1 = meio - ini + 1;
        int tam2 = fim - meio;

        Restaurante[] esq = new Restaurante[tam1];
        Restaurante[] dir = new Restaurante[tam2];

        for (int i = 0; i < tam1; i++) esq[i] = arr[ini + i];
        for (int i = 0; i < tam2; i++) dir[i] = arr[meio + 1 + i];

        int i = 0, j = 0, k = ini;

        // compara cidade e nome
        while (i < tam1 && j < tam2) {

            int cmp = esq[i].getCidade().compareTo(dir[j].getCidade());

            if (cmp < 0 || (cmp == 0 && esq[i].getNome().compareTo(dir[j].getNome()) <= 0)) {
                arr[k] = esq[i];
                i++;
            } else {
                arr[k] = dir[j];
                j++;
            }
            k++;
        }

        while (i < tam1) {
            arr[k] = esq[i];
            i++;
            k++;
        }

        while (j < tam2) {
            arr[k] = dir[j];
            j++;
            k++;
        }
    }

    // divide tudo
    static void mergesort(Restaurante[] arr, int ini, int fim) {
        if (ini < fim) {
            int meio = (ini + fim) / 2;

            mergesort(arr, ini, meio);
            mergesort(arr, meio + 1, fim);

            merge(arr, ini, meio, fim);
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

        // pega os ids escolhidos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l.n; j++) {
                if (l.v[j].getId() == ids[i]) {
                    arr[t] = l.v[j];
                    t++;
                    break;
                }
            }
        }

        mergesort(arr, 0, t - 1);

        // imprime
        for (int i = 0; i < t; i++) {
            System.out.println(arr[i].mostrar());
        }

        sc.close();
    }
}