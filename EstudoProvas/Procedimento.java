// Classe que representa o procedimento do atendimento
public class Procedimento {
    // Atributos privados do procedimento
    private String nome;             // Nome do procedimento
    private int duracaoEstimada;     // Duração estimada em minutos
    private double valor;            // Valor do procedimento
    private String nivelComplexidade;// Nível de complexidade (ex: Baixa, Média, Alta)

    // Construtor completo para inicializar todos os atributos
    public Procedimento(String nome, int duracaoEstimada, double valor, String nivelComplexidade) {
        this.nome = nome;                                // Define o nome
        this.duracaoEstimada = duracaoEstimada;          // Define a duração
        this.valor = valor;                              // Define o valor
        this.nivelComplexidade = nivelComplexidade;      // Define a complexidade
    }

    // Getters e Setters para acesso e modificação dos atributos
    public String getNome() {
        return nome; // Retorna o nome do procedimento
    }

    public void setNome(String nome) {
        this.nome = nome; // Altera o nome do procedimento
    }

    public int getDuracaoEstimada() {
        return duracaoEstimada; // Retorna a duração estimada
    }

    public void setDuracaoEstimada(int duracaoEstimada) {
        this.duracaoEstimada = duracaoEstimada; // Altera a duração estimada
    }

    public double getValor() {
        return valor; // Retorna o valor do procedimento
    }

    public void setValor(double valor) {
        this.valor = valor; // Altera o valor
    }

    public String getNivelComplexidade() {
        return nivelComplexidade; // Retorna a complexidade
    }

    public void setNivelComplexidade(String nivelComplexidade) {
        this.nivelComplexidade = nivelComplexidade; // Altera a complexidade
    }

    // Método para exibir as informações do procedimento
    public String exibeInformacoes() {
        return "Procedimento: " + nome + " | Duração: " + duracaoEstimada + " min | Valor: R$ " + valor + " | Complexidade: " + nivelComplexidade; // Formata e retorna o texto
    }
}