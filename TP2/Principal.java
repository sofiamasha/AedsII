import java.io.File;
import java.util.Scanner;

class Hora{
  private int hora;
  private int minuto;

  public Hora(int hora, int minuto){
    this.hora = hora;
    this.minuto = minuto;
  }

  public int getHora(){ return hora; }
  public int getMinuto(){ return minuto; }

  public static Hora parseHora(String s){
    Scanner sc = new Scanner(s);
    sc.useDelimiter(":");

    int h = sc.nextInt();
    int m = sc.nextInt();

    sc.close();
    return new Hora(h, m);
  }

  public String formatar(){
    return String.format("%02d:%02d", hora, minuto);
  }
}

class Data{
  private int ano;
  private int mes;
  private int dia;

  public Data(int ano, int mes, int dia){
    this.ano = ano;
    this.mes = mes;
    this.dia = dia;
  }

  public int getAno(){ return ano; }
  public int getMes(){ return mes; }
  public int getDia(){ return dia; }

  public static Data parseData(String s){
    Scanner sc = new Scanner(s);
    sc.useDelimiter("-");

    int a = sc.nextInt();
    int m = sc.nextInt();
    int d = sc.nextInt();

    sc.close();
    return new Data(a, m, d);
  }

  public String formatar(){
    return String.format("%02d/%02d/%04d", dia, mes, ano);
  }
}

class Restaurante{
  private int id;
  private String nome;
  private String cidade;
  private int capacidade;
  private double avaliacao;
  private String[] tipos;
  private int preco;
  private Hora abre;
  private Hora fecha;
  private Data data;
  private boolean aberto;

  public Restaurante(int id, String nome, String cidade, int capacidade,
    double avaliacao, String[] tipos, int preco,
    Hora abre, Hora fecha, Data data, boolean aberto){

    this.id = id;
    this.nome = nome;
    this.cidade = cidade;
    this.capacidade = capacidade;
    this.avaliacao = avaliacao;
    this.tipos = tipos;
    this.preco = preco;
    this.abre = abre;
    this.fecha = fecha;
    this.data = data;
    this.aberto = aberto;
  }

  public int getId(){ return id; }

  public static int contaPreco(String s){
    int c = 0;
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) == '$') c++;
    }
    return c;
  }

  public static Restaurante parseRestaurante(String linha){
    Scanner sc = new Scanner(linha);

    // CSV do sistema usa vírgula
    sc.useDelimiter(",");

    int id = sc.nextInt();
    String nome = sc.next();
    String cidade = sc.next();
    int capacidade = sc.nextInt();

    double avaliacao = Double.parseDouble(sc.next());

    String tiposStr = sc.next();
    String precoStr = sc.next();
    int preco = contaPreco(precoStr);

    String horario = sc.next();

    Scanner scH = new Scanner(horario);
    scH.useDelimiter("-");
    Hora abre = Hora.parseHora(scH.next());
    Hora fecha = Hora.parseHora(scH.next());
    scH.close();

    Data data = Data.parseData(sc.next());

    String abertoStr = sc.next();
    boolean aberto = false;
    if(abertoStr.compareTo("true") == 0){
      aberto = true;
    }

    sc.close();

    String[] aux = new String[10];
    int n = 0;

    Scanner scT = new Scanner(tiposStr);
    scT.useDelimiter(";");

    while(scT.hasNext()){
      String t = scT.next();
      if(t.length() > 0){
        aux[n++] = t;
      }
    }
    scT.close();

    String[] tipos = new String[n];
    for(int i = 0; i < n; i++){
      tipos[i] = aux[i];
    }

    return new Restaurante(id, nome, cidade, capacidade,
      avaliacao, tipos, preco, abre, fecha, data, aberto);
  }

  public String formatar(){
    String lista = "";
    for(int i = 0; i < tipos.length; i++){
      lista += tipos[i];
      if(i < tipos.length - 1) lista += ",";
    }

    String p = "";
    for(int i = 0; i < preco; i++){
      p += "$";
    }

    return String.format("[%d ## %s ## %s ## %d ## %s ## [%s] ## %s ## %s-%s ## %s ## %b]",
      id, nome, cidade, capacidade, avaliacao + "",
      lista, p,
      abre.formatar(), fecha.formatar(),
      data.formatar(), aberto);
  }
}

class Colecao{
  private Restaurante[] vet;
  private int tam;

  public Colecao(int t){
    tam = t;
    vet = new Restaurante[t];
  }

  public void ler(String path) throws Exception{
    File f = new File(path);
    Scanner sc = new Scanner(f);

    if(sc.hasNextLine()) sc.nextLine();

    int i = 0;
    while(sc.hasNextLine()){
      vet[i++] = Restaurante.parseRestaurante(sc.nextLine());
    }

    sc.close();
  }

  public static Colecao carregar() throws Exception{
    File f = new File("/tmp/restaurantes.csv");
    Scanner sc = new Scanner(f);

    int total = 0;
    while(sc.hasNextLine()){
      sc.nextLine();
      total++;
    }
    sc.close();

    Colecao c = new Colecao(total - 1);
    c.ler("/tmp/restaurantes.csv");

    return c;
  }

  public Restaurante buscar(int id){
    for(int i = 0; i < tam; i++){
      if(vet[i].getId() == id){
        return vet[i];
      }
    }
    return null;
  }
}

public class Principal{
  public static void main(String[] args) throws Exception{
    Scanner sc = new Scanner(System.in);

    Colecao c = Colecao.carregar();

    int id = sc.nextInt();

    while(id != -1){
      Restaurante r = c.buscar(id);
      if(r != null){
        System.out.println(r.formatar());
      }
      id = sc.nextInt();
    }

    sc.close();
  }
}