import java.util.*;
import java.io.*;

class Hora {
    int h;
    int m;

    Hora(int h, int m) {
        this.h = h;
        this.m = m;
    }

    // faz o hh:mm
    static Hora ler(String s) {
        int i = 0;
        int h = 0;
        int m = 0;

        // monta a hora até achar :
        while (s.charAt(i) != ':') {
            h = h * 10 + (s.charAt(i) - '0');
            i++;
        }

        i++; // pula :

        // monta minuto
        while (i < s.length()) {
            m = m * 10 + (s.charAt(i) - '0');
            i++;
        }

        return new Hora(h, m);
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

    // separa 2027-04-01
    static Data ler(String s) {
        int i = 0;
        int a = 0, m = 0, d = 0;

        // ano
        while (s.charAt(i) != '-') {
            a = a * 10 + (s.charAt(i) - '0');
            i++;
        }

        i++;

        // mes
        while (s.charAt(i) != '-') {
            m = m * 10 + (s.charAt(i) - '0');
            i++;
        }

        i++;

        // dia
        while (i < s.length()) {
            d = d * 10 + (s.charAt(i) - '0');
            i++;
        }

        return new Data(a, m, d);
    }
}

class Restaurante {
    int id;
    String nome;
    String cidade;
    int capacidade;
    double avaliacao;
    String[] cozinha = new String[10];
    int qtdCozinha;
    int preco;
    Hora abre;
    Hora fecha;
    Data data;
    int aberto;

    static Restaurante ler(String linha) {

        Restaurante r = new Restaurante();

        int i = 0;
        int campo = 0;
        String temp = "";

        // percorre a linha inteira separando pelas ,
        while (i <= linha.length()) {

            if (i == linha.length() || linha.charAt(i) == ',') {

                // cada campo do csv vem p ca e decide ç onde vai

                if (campo == 0) {
                    int num = 0;
                    for (int j = 0; j < temp.length(); j++) {
                        num = num * 10 + (temp.charAt(j) - '0');
                    }
                    r.id = num;
                }

                if (campo == 1) {
                    r.nome = temp;
                }

                if (campo == 2) {
                    r.cidade = temp;
                }

                if (campo == 3) {
                    int num = 0;
                    for (int j = 0; j < temp.length(); j++) {
                        num = num * 10 + (temp.charAt(j) - '0');
                    }
                    r.capacidade = num;
                }

                if (campo == 4) {
                    r.avaliacao = Double.valueOf(temp);
                }

                if (campo == 5) {
                    // separa as cozinhas no ;
                    int k = 0;
                    String aux = "";

                    for (int j = 0; j <= temp.length(); j++) {
                        if (j == temp.length() || temp.charAt(j) == ';') {
                            r.cozinha[k] = aux;
                            k++;
                            aux = "";
                        } else {
                            aux += temp.charAt(j);
                        }
                    }

                    r.qtdCozinha = k;
                }

                if (campo == 6) {
                    r.preco = temp.length(); // quantidade de $
                }

                if (campo == 7) {
                    // separa horario tipo :10:10-22:00
                    String h1 = "";
                    String h2 = "";
                    int j = 0;

                    while (temp.charAt(j) != '-') {
                        h1 += temp.charAt(j);
                        j++;
                    }

                    j++;

                    while (j < temp.length()) {
                        h2 += temp.charAt(j);
                        j++;
                    }

                    r.abre = Hora.ler(h1);
                    r.fecha = Hora.ler(h2);
                }

                if (campo == 8) {
                    r.data = Data.ler(temp);
                }

                if (campo == 9) {
                    // transforma true/false em 1 ou 0
                    if (temp.trim().length() == 4 &&
                        temp.charAt(0) == 't') {
                        r.aberto = 1;
                    } else {
                        r.aberto = 0;
                    }
                }

                temp = "";
                campo++;

            } else {
                temp += linha.charAt(i);
            }

            i++;
        }

        return r;
    }
}

class Lista {
    Restaurante[] v = new Restaurante[1000];
    int n = 0;

    void ler(String caminho) throws Exception {

        Scanner sc = new Scanner(new File(caminho));

        sc.nextLine(); // pula cabecalho

        while (sc.hasNextLine()) {
            v[n] = Restaurante.ler(sc.nextLine());
            n++;
        }

        sc.close();
    }
}

public class Questao05 {
    public static void main(String[] args) throws Exception {

        Lista lista = new Lista();
        lista.ler("/tmp/restaurantes.csv");

        Scanner sc = new Scanner(System.in);

        int[] ids = new int[1000];
        int n = 0;

        int id = sc.nextInt();

        // guarda ids até -1
        while (id != -1) {
            ids[n] = id;
            n++;
            id = sc.nextInt();
        }

        Restaurante[] sel = new Restaurante[n];
        int t = 0;

        // pega os restaurantes que o usuario pediu
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < lista.n; j++) {
                if (lista.v[j].id == ids[i]) {
                    sel[t] = lista.v[j];
                    t++;
                    break;
                }
            }
        }

        sc.nextLine(); // p limpa o enter

        String nome = sc.nextLine();

        // repete até digitar fim
        while (!(nome.length() == 3 &&
                 nome.charAt(0) == 'F' &&
                 nome.charAt(1) == 'I' &&
                 nome.charAt(2) == 'M')) {

            int achou = 0;

            // busca sequencial 
            for (int i = 0; i < t; i++) {

                // compara nome letra por letra
                if (sel[i].nome.length() == nome.length()) {

                    int igual = 1;

                    for (int k = 0; k < nome.length(); k++) {
                        if (sel[i].nome.charAt(k) != nome.charAt(k)) {
                            igual = 0;
                            k = nome.length(); // sai do loop
                        }
                    }

                    if (igual == 1) {
                        achou = 1;
                        break;
                    }
                }
            }

            if (achou == 1) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }

            nome = sc.nextLine();
        }

        sc.close();
    }
}