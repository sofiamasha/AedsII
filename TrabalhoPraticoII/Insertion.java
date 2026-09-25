// metodos que ja foram comentados em questões anteriores não serão comentados aqui
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
        cilindros, cilindrada, transmissao, tracao,
        consumo_cidade, consumo_estrada, co2, turbo, data);
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
        cilindrada, transmissao, tracao, consumo_cidade,
        consumo_estrada, co2, turbo, data_registro.format());
  }
}

class Insercao {

  public static void ordenacao(Veiculo[] array, int n) {

    // começa do segundo elemento porque o primeiro ja está ordenado
    for (int i = 1; i < n; i++) {

      Veiculo tmp = array[i];

      // guarda a marca do veiculo que vai ser colocado na posição certa
      String chave = tmp.getMarca().toLowerCase();

      int j = i - 1;

      // enquanto a marca anterior for maior, joga ela uma posição para frente
      while (j >= 0 &&
          array[j].getMarca().toLowerCase().compareTo(chave) > 0) {

        array[j + 1] = array[j];
        j--;
      }

      // coloca o veiculo na posição correta
      array[j + 1] = tmp;
    }
  }
}

public class OrdInsercao {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    Veiculo[] veiculos = new Veiculo[500];
    Veiculo[] v = new Veiculo[500];

    int x;
    int j = 0;

    LeitorCsv.ler("/tmp/veiculos.csv", veiculos);

    x = sc.nextInt();

    while (x != -1) {

      for (int i = 0; i < 500; i++) {

        if (veiculos[i] != null && veiculos[i].getId() == x) {
          v[j] = veiculos[i];
          j++;
        }
      }

      x = sc.nextInt();
    }

    Insercao.ordenacao(v, j);

    for (int i = 0; i < j; i++) {
      System.out.println(v[i].format());
    }
  }
}