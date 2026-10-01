// Classe que representa o veterinário
public class Veterinario {
    // Atributos privados do veterinário
    private String nome;          // Nome do veterinário
    private String cpf;           // CPF do veterinário
    private String especialidade; // Especialidade médica
    private String telefone;      // Telefone de contato
    private boolean temSala;      // Controle para saber se já possui sala (Regra 1)

    // Construtor para inicializar o veterinário
    public Veterinario(String nome, String cpf, String especialidade, String telefone) {
        this.nome = nome;                     // Define o nome
        this.cpf = cpf;                       // Define o CPF
        this.especialidade = especialidade;   // Define a especialidade
        this.telefone = telefone;             // Define o telefone
        this.temSala = false;                 // Inicia sem sala vinculada
    }

    // Getters e Setters
    public String getNome() {
        return nome; // Retorna o nome
    }

    public String getCpf() {
        return cpf; // Retorna o CPF
    }

    public String getEspecialidade() {
        return especialidade; // Retorna a especialidade
    }

    public String getTelefone() {
        return telefone; // Retorna o telefone
    }

    public boolean isTemSala() {
        return temSala; // Retorna se o veterinário já tem sala
    }

    public void setTemSala(boolean temSala) {
        this.temSala = temSala; // Atualiza o status de posse de sala
    }

    // Método para exibir detalhes do veterinário
    public String exibeInformacoes() {
        return "Vet: " + nome + " (CPF: " + cpf + ") - Esp: " + especialidade + " - Tel: " + telefone; // Retorna resumo formatado
    }
}