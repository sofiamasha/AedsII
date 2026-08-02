/*
 * TP03 - AEDS II - PUC Minas
 * Aluno: matricula 865115
 * Questao 03 - Ordenacao parcial por Quicksort (Java). Particiona o vetor e recursivamente ordena apenas as faixas que cruzam [0,K), produzindo os K menores ordenados pelo nome.
 */
import java.io.*;
import java.util.Locale;

class Data {
    private int ano, mes, dia;
    public Data(int a, int m, int d) { ano=a; mes=m; dia=d; }
    public int getAno() { return ano; } public int getMes() { return mes; } public int getDia() { return dia; }
    public static Data parseData(String s) { String[] p = s.split("-");
        return new Data(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2])); }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}
class Hora {
    private int hora, minuto;
    public Hora(int h, int m) { hora=h; minuto=m; }
    public int getHora() { return hora; } public int getMinuto() { return minuto; }
    public static Hora parseHora(String s) { String[] p = s.split(":");
        return new Hora(Integer.parseInt(p[0]), Integer.parseInt(p[1])); }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}
class Restaurante {
    private int id;
    private String nome, cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private Hora horarioAbertura, horarioFechamento;
    private Data dataAbertura;
    private boolean aberto;
    public int getId() { return id; } public String getNome() { return nome; } public double getAvaliacao() { return avaliacao; }
    public static Restaurante parseRestaurante(String s) {
        String[] p = s.split(",");
        Restaurante r = new Restaurante();
        r.id = Integer.parseInt(p[0].trim()); r.nome = p[1].trim(); r.cidade = p[2].trim();
        r.capacidade = Integer.parseInt(p[3].trim()); r.avaliacao = Double.parseDouble(p[4].trim());
        r.tiposCozinha = p[5].trim().split(";"); r.faixaPreco = p[6].trim().length();
        String[] h = p[7].trim().split("-");
        r.horarioAbertura = Hora.parseHora(h[0]); r.horarioFechamento = Hora.parseHora(h[1]);
        r.dataAbertura = Data.parseData(p[8].trim());
        r.aberto = Boolean.parseBoolean(p[9].trim());
        return r;
    }
    public String formatar() {
        StringBuilder tc = new StringBuilder("[");
        for (int i = 0; i < tiposCozinha.length; i++) { if (i>0) tc.append(","); tc.append(tiposCozinha[i]); }
        tc.append("]");
        StringBuilder d = new StringBuilder();
        for (int i = 0; i < faixaPreco; i++) d.append("$");
        return String.format(Locale.US, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
            id, nome, cidade, capacidade, avaliacao, tc.toString(), d.toString(),
            horarioAbertura.formatar(), horarioFechamento.formatar(), dataAbertura.formatar(),
            aberto ? "true" : "false");
    }
}
class ColecaoRestaurantes {
    private int tamanho = 0;
    private Restaurante[] restaurantes = new Restaurante[2000];
    public int getTamanho() { return tamanho; }
    public Restaurante[] getRestaurantes() { return restaurantes; }
    public void lerCsv(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line; boolean first = true;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) continue;
            if (first) { first = false; if (!Character.isDigit(line.charAt(0))) continue; }
            restaurantes[tamanho++] = Restaurante.parseRestaurante(line);
        }
        br.close();
    }
    public static ColecaoRestaurantes lerCsv() throws IOException {
        ColecaoRestaurantes c = new ColecaoRestaurantes(); c.lerCsv("/tmp/restaurantes.csv"); return c;
    }
    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) if (restaurantes[i].getId() == id) return restaurantes[i];
        return null;
    }
}

public class Main {
    static long comparacoes = 0, movimentacoes = 0;
    static final String MATRICULA = "865115";
    static final int K = 10;

    static void swap(Restaurante[] a, int i, int j) {
        Restaurante t = a[i]; a[i] = a[j]; a[j] = t; movimentacoes += 3;
    }

    static int compara(Restaurante a, Restaurante b) {
        int c = Double.compare(a.getAvaliacao(), b.getAvaliacao());
        if (c != 0) return c;
        return a.getNome().compareTo(b.getNome());
    }

    static int[] particao(Restaurante[] a, int lo, int hi) {
        Restaurante pivot = a[(lo + hi) / 2];
        int i = lo, j = hi;
        do {
            while (true) { comparacoes++; if (compara(a[i], pivot) >= 0) break; i++; }
            while (true) { comparacoes++; if (compara(a[j], pivot) <= 0) break; j--; }
            if (i <= j) { swap(a, i, j); i++; j--; }
        } while (i <= j);
        return new int[]{i, j};
    }

    // Quicksort parcial: recursa em cada lado apenas se intersectar [0, k)
    static void quicksortParcial(Restaurante[] a, int lo, int hi, int k) {
        if (lo < hi) {
            int[] p = particao(a, lo, hi);
            if (lo < p[1] && lo < k) quicksortParcial(a, lo, p[1], k);
            if (p[0] < hi && p[0] < k) quicksortParcial(a, p[0], hi, k);
        }
    }

    public static void main(String[] args) throws IOException {
        ColecaoRestaurantes col = ColecaoRestaurantes.lerCsv();
        Restaurante[] arr = new Restaurante[2000];
        int n = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String l;
        while ((l = br.readLine()) != null) {
            l = l.trim();
            if (l.equals("-1")) break;
            if (l.isEmpty()) continue;
            Restaurante r = col.buscarPorId(Integer.parseInt(l));
            if (r != null) arr[n++] = r;
        }
        long t0 = System.nanoTime();
        quicksortParcial(arr, 0, n - 1, K);
        long t1 = System.nanoTime();

        for (int i = 0; i < n; i++) System.out.println(arr[i].formatar());

        double seg = (t1 - t0) / 1_000_000_000.0;
        PrintWriter pw = new PrintWriter(new FileWriter(MATRICULA + "_quicksort_parcial.txt"));
        pw.printf(Locale.US, "%s\t%d\t%d\t%.6f%n", MATRICULA, comparacoes, movimentacoes, seg);
        pw.close();
    }
}

