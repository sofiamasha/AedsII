import java.util.*;

class Hora {
    int h;
    int m;

    Hora(int h, int m) {
        this.h = h;
        this.m = m;
    }

    // separa HH:MM manualmente
    static Hora ler(String s) {
        int i = 0;
        String hStr = "";
        String mStr = "";

        // pega a parte da hora até :
        while (s.charAt(i) != ':') {
            hStr += s.charAt(i);
            i++;
        }

        i++; // pula :

        // pega o resto (minuto)
        while (i < s.length()) {
            mStr += s.charAt(i);
            i++;
        }

        int h = Integer.valueOf(hStr);
        int m = Integer.valueOf(mStr);

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

    // separa "AAAA-MM-DD" manualmente
    static Data ler(String s) {
        String aStr = "";
        String mStr = "";
        String dStr = "";

        int i = 0;

        // ano
        while (s.charAt(i) != '-') {
            aStr += s.charAt(i);
            i++;
        }

        i++;

        // mes
        while (s.charAt(i) != '-') {
            mStr += s.charAt(i);
            i++;
        }

        i++;

        // dia
        while (i < s.length()) {
            dStr += s.charAt(i);
            i++;
        }

        int a = Integer.valueOf(aStr);
        int m = Integer.valueOf(mStr);
        int d = Integer.valueOf(dStr);

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
    int aberto; // 0 ou 1

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

    int getId() {
        return id;
    }

    String getCidade() {
        return cidade;
    }

    // transforma linha do csv em objeto
    static Restaurante ler(String s) {

        String[] p = s.split(",");

        int id = Integer.valueOf(p[0]);
        String nome = p[1];
        String cidade = p[2];
        int cap = Integer.valueOf(p[3]);
        double ava = Double.valueOf(p[4]);

        // separa tipos de cozinha no ;
        String[] cozinha = p[5].split(";");

        // quantidade de $ vira número
        int preco = p[6].length();

        // separa horario manualmente
        String horario = p[7];
        String h1 = "";
        String h2 = "";

        int i = 0;

        // pega antes do -
        while (horario.charAt(i) != '-') {
            h1 += horario.charAt(i);
            i++;
        }

        i++;

        // pega dps do -
        while (i < horario.length()) {
            h2 += horario.charAt(i);
            i++;
        }

        Hora abre = Hora.ler(h1);
        Hora fecha = Hora.ler(h2);

        Data data = Data.ler(p[8]);

        // converte true ou false pra 1 ou 0
        int aberto = 0;
        if (p[9].trim().compareTo("true") == 0) {
            aberto = 1;
        } else {
            aberto = 0;
        }

        return new Restaurante(id, nome, cidade, cap, ava, cozinha, preco, abre, fecha, data, aberto);
    }

    String mostrar() {

        String c = "";

        // junta cozinhas com virgula
        for (int i = 0; i < cozinha.length; i++) {
            c += cozinha[i];
            if (i < cozinha.length - 1) {
                c += ",";
            }
        }

        String precoStr = "";

        // monta $
        for (int i = 0; i < preco; i++) {
            precoStr += "$";
        }

        // converter 0/1 pra texto
        String abertoStr;
        if (aberto == 1) {
            abertoStr = "true";
        } else {
            abertoStr = "false";
        }

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

        sc.nextLine(); // pula primeira linha

        while (sc.hasNextLine()) {
            v[n] = Restaurante.ler(sc.nextLine());
            n++;
        }

        sc.close();
    }
}

public class Questao04 {
    public static void main(String[] args) throws Exception {

        Lista l = new Lista();
        l.ler("/tmp/restaurantes.csv");

        Scanner sc = new Scanner(System.in);

        int[] ids = new int[1000];
        int n = 0;

        int id = sc.nextInt();

        // guarda ids digitados
        while (id != -1) {
            ids[n] = id;
            n++;
            id = sc.nextInt();
        }

        Restaurante[] sel = new Restaurante[n];
        int t = 0;

        // pega só os restaurantes escolhidos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l.n; j++) {
                if (l.v[j].getId() == ids[i]) {
                    sel[t] = l.v[j];
                    t++;
                    break;
                }
            }
        }

        // insertion sort por cidade
        for (int i = 1; i < t; i++) {

            Restaurante atual = sel[i];
            int j = i - 1;

            // vai empurrando pra direita quem for maior
            while (j >= 0 && sel[j].getCidade().compareTo(atual.getCidade()) > 0) {
                sel[j + 1] = sel[j];
                j--;
            }

            // coloca na posição certa
            sel[j + 1] = atual;
        }

        // imprime
        for (int i = 0; i < t; i++) {
            System.out.println(sel[i].mostrar());
        }

        sc.close();
    }
}