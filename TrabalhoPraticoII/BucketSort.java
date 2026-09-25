import java.io.*;
import java.util.*;

class LeitorCsv {
  public static Veiculo[] ler(String caminhoarquivo, Veiculo[] veiculos) {
    try {
      Scanner arq = new Scanner(new File(caminhoarquivo));

      if (arq.hasNextLine()) {
        arq.nextLine();
      }

      int i = 0;

      while (arq.hasNextLine() && i < veiculos.length) {
        String linha = arq.nextLine();
        veiculos[i] = Veiculo.parseVeiculo(linha);
        i++;
      }

      arq.close();

    } catch (FileNotFoundException e) {
      System.out.println("erro: arquivo não encontrado - " + caminhoarquivo);
    }

    return veiculos;
  }
}

class Data {
  private int ano;
  private int mes;
  private int dia;

  public Data(int nDia, int nMes, int nAno) {
    this.ano = nAno;
    this.mes = nMes;
    this.dia = nDia;
  }

  public int getDia() {
    return dia;
  }

  public int getMes() {
    return mes;
  }

  public int getAno() {
    return ano;
  }

  public static Data parseData(String s) {
    String[] campos = s.split("-");

    int ano = Integer.parseInt(campos[0]);
    int mes = Integer.parseInt(campos[1]);
    int dia = Integer.parseInt(campos[2]);

    return new Data(dia, mes, ano);
  }

  public String format() {
    return String.format("%02d/%02d/%04d", dia, mes, ano);
  }
}

class Veiculo {
  private int id;
  private String marca;
  private String modelo;
  private int ano;
  private String categoria;
  private String combustivel;
  private int cilindros;
  private float cilindrada;
  private String transmissao;
  private String tracao;
  private float consumo_cidade;
  private float consumo_estrada;
  private float co2;
  private boolean turbo;
  private Data data_registro;

  public Veiculo(int nId, String nMarca, String nModelo, int nAno,
      String nCategoria, String nCombustivel, int nCilindros,
      float nCilindrada, String nTransmissao, String nTracao,
      float nConsumo_cidade, float nConsumo_estrada, float nCo2,
      boolean nTurbo, String nData) {

    this.id = nId;
    this.marca = nMarca;
    this.modelo = nModelo;
    this.ano = nAno;
    this.categoria = nCategoria;
    this.combustivel = formatCombustivel(nCombustivel);
    this.cilindros = nCilindros;
    this.cilindrada = nCilindrada;
    this.transmissao = nTransmissao;
    this.tracao = nTracao;
    this.consumo_cidade = nConsumo_cidade;
    this.consumo_estrada = nConsumo_estrada;
    this.co2 = nCo2;
    this.turbo = nTurbo;
    this.data_registro = Data.parseData(nData);
  }

  public static Veiculo parseVeiculo(String s) {
    String[] campo = s.split(",");

    int id = Integer.parseInt(campo[0]);
    String marca = campo[1];
    String modelo = campo[2];
    int ano = Integer.parseInt(campo[3]);
    String categoria = campo[4];
    String combustivel = campo[5];
    int cilindros = Integer.parseInt(campo[6]);
    float cilindrada = Float.parseFloat(campo[7]);
    String transmissao = campo[8];
    String tracao = campo[9];
    float consumo_cidade = Float.parseFloat(campo[10]);
    float consumo_estrada = Float.parseFloat(campo[11]);
    float co2 = Float.parseFloat(campo[12]);
    boolean turbo = Boolean.parseBoolean(campo[13]);
    String data = campo[14];

    return new Veiculo(id, marca, modelo, ano, categoria, combustivel,
        cilindros, cilindrada, transmissao, tracao, consumo_cidade,
        consumo_estrada, co2, turbo, data);
  }

  public String formatCombustivel(String s) {
    String[] campo = s.split(";");

    String a = campo.length > 0 ? campo[0] : "";
    String b = campo.length > 1 ? campo[1] : "";

    if (campo.length < 2) {
      return a;
    } else {
      return a + "," + b;
    }
  }

  public int getId() {
    return id;
  }

  public String getMarca() {
    return marca;
  }

  public String getModelo() {
    return modelo;
  }

  public int getAno() {
    return ano;
  }

  public String getCategoria() {
    return categoria;
  }

  public String getCombustivel() {
    return combustivel;
  }

  public int getCilindros() {
    return cilindros;
  }

  public float getCilindrada() {
    return cilindrada;
  }

  public String getTransmissao() {
    return transmissao;
  }

  public String getTracao() {
    return tracao;
  }

  public float getConsumo_cidade() {
    return consumo_cidade;
  }

  public float getConsumo_estrada() {
    return consumo_estrada;
  }

  public float getCo2() {
    return co2;
  }

  public boolean getTurbo() {
    return turbo;
  }

  public String getData() {
    return data_registro.format();
  }

  public String format() {
    return String.format(
        Locale.US,
        "[%d ## %s ## %s ## %d ## %s ## [%s] ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %b ## %s]",
        id, marca, modelo, ano, categoria, combustivel, cilindros,
        cilindrada, transmissao, tracao, consumo_cidade, consumo_estrada,
        co2, turbo, data_registro.format());
  }
}

class Insercao {

  public static void ordenacao(Veiculo[] array, int n) {

    for (int i = 1; i < n; i++) {
      Veiculo tmp = array[i];
      int j = i - 1;

      // coloca os veiculos do menor para o maior
      while (j >= 0 &&
          array[j].getCilindrada() > tmp.getCilindrada()) {

        array[j + 1] = array[j];
        j--;
      }

      array[j + 1] = tmp;
    }
  }
}

class Bucket {

  public static void ordenacao(Veiculo[] array, int n) {

    Veiculo[] ordenado = new Veiculo[n];

    int quantidadeBaldes = 10;
    double normalizacao = 8.1;

    // cria os 10 baldes
    Veiculo[][] baldes = new Veiculo[10][n];

    // guarda quantos elementos tem em cada balde
    int[] quantidade = new int[10];

    // coloca cada veiculo no seu balde
    for (int i = 0; i < n; i++) {

      int balde = (int) Math.floor(
          quantidadeBaldes *
          (array[i].getCilindrada() / normalizacao));

      // evita passar do ultimo balde
      if (balde >= quantidadeBaldes) {
        balde = quantidadeBaldes - 1;
      }

      baldes[balde][quantidade[balde]] = array[i];
      quantidade[balde]++;
    }

    // ordena cada balde usando insercao
    for (int i = 0; i < quantidadeBaldes; i++) {
      Insercao.ordenacao(baldes[i], quantidade[i]);
    }

    // junta todos os baldes no vetor
    int posicao = 0;

    for (int i = 0; i < quantidadeBaldes; i++) {
      for (int j = 0; j < quantidade[i]; j++) {
        ordenado[posicao] = baldes[i][j];
        posicao++;
      }
    }

    // copia o resultado para o vetor original
    for (int i = 0; i < n; i++) {
      array[i] = ordenado[i];
    }
  }
}

public class BucketSort {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    Veiculo[] veiculos = new Veiculo[500];
    veiculos = LeitorCsv.ler("/tmp/veiculos.csv", veiculos);

    Veiculo[] selecionados = new Veiculo[500];
    int quantidade = 0;

    while (sc.hasNextInt()) {
      int id = sc.nextInt();

      if (id == -1) {
        break;
      }

      for (int i = 0; i < veiculos.length; i++) {

        if (veiculos[i] != null && veiculos[i].getId() == id) {
          selecionados[quantidade] = veiculos[i];
          quantidade++;
          break;
        }
      }
    }

    Bucket.ordenacao(selecionados, quantidade);

    for (int i = 0; i < quantidade; i++) {
      System.out.println(selecionados[i].format());
    }

    sc.close();
  }
}