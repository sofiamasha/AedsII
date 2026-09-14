public class Veiculo {

    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao;
    private String tracao;
    private double consumoCidade;
    private double consumoEstrada;
    private double co2;
    private boolean turbo;
    private Data dataRegistro;

    public Veiculo(int id, String marca, String modelo, int ano,
                   String categoria, String[] combustivel,
                   int cilindros, double cilindrada,
                   String transmissao, String tracao,
                   double consumoCidade, double consumoEstrada,
                   double co2, boolean turbo, Data dataRegistro) {

        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.categoria = categoria;
        this.combustivel = combustivel;
        this.cilindros = cilindros;
        this.cilindrada = cilindrada;
        this.transmissao = transmissao;
        this.tracao = tracao;
        this.consumoCidade = consumoCidade;
        this.consumoEstrada = consumoEstrada;
        this.co2 = co2;
        this.turbo = turbo;
        this.dataRegistro = dataRegistro;
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

    public String[] getCombustivel() {
        return combustivel;
    }

    public int getCilindros() {
        return cilindros;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public String getTransmissao() {
        return transmissao;
    }

    public String getTracao() {
        return tracao;
    }

    public double getConsumoCidade() {
        return consumoCidade;
    }

    public double getConsumoEstrada() {
        return consumoEstrada;
    }

    public double getCo2() {
        return co2;
    }

    public boolean getTurbo() {
        return turbo;
    }

    public Data getDataRegistro() {
        return dataRegistro;
    }


    public String format() {

        String combustiveis = "";

        for (int i = 0; i < combustivel.length; i++) {

            combustiveis += combustivel[i];

            if (i < combustivel.length - 1) {
                combustiveis += ", ";
            }
        }

        return "[" + id + " ## "
                + marca + " ## "
                + modelo + " ## "
                + ano + " ## "
                + categoria + " ## ["
                + combustiveis + "] ## "
                + cilindros + " ## "
                + cilindrada + " ## "
                + transmissao + " ## "
                + tracao + " ## "
                + consumoCidade + " ## "
                + consumoEstrada + " ## "
                + co2 + " ## "
                + turbo + " ## "
                + dataRegistro.format() + "]";
    }
}