import java.util.ArrayList; // Importa a classe ArrayList para gerenciar listas
import java.util.List;      // Importa a interface List

// Classe que representa a sala de atendimento
public class Sala {
    // Atributos privados da sala
    private int numero;                          // Número da sala
    private String bloco;                        // Bloco onde se localiza a sala
    private int capacidadeMaxima;                // Capacidade máxima de animais
    private String tipoSala;                     // Tipo de sala (deve bater com o nome do procedimento)
    private Veterinario veterinarioResponsavel;  // Veterinário associado à sala
    private List<Atendimento> atendimentos;      // Lista de atendimentos em andamento na sala
    private int totalFinalizados;                // Contador de atendimentos finalizados na sala (Regra 4)

    // Construtor para inicializar a sala
    public Sala(int numero, String bloco, int capacidadeMaxima, String tipoSala) {
        this.numero = numero;                             // Define o número
        this.bloco = bloco;                               // Define o bloco
        this.capacidadeMaxima = capacidadeMaxima;         // Define a capacidade
        this.tipoSala = tipoSala;                         // Define o tipo
        this.veterinarioResponsavel = null;               // Inicia sem veterinário
        this.atendimentos = new ArrayList<>();            // Instancia a lista de atendimentos
        this.totalFinalizados = 0;                        // Inicializa o contador de finalizados com zero
    }

    // Getters e Setters
    public int getNumero() {
        return numero; // Retorna o número da sala
    }

    public String getBloco() {
        return bloco; // Retorna o bloco
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima; // Retorna a capacidade máxima
    }

    public String getTipoSala() {
        return tipoSala; // Retorna o tipo da sala
    }

    public Veterinario getVeterinarioResponsavel() {
        return veterinarioResponsavel; // Retorna o veterinário responsável
    }

    public void setVeterinarioResponsavel(Veterinario veterinarioResponsavel) {
        this.veterinarioResponsavel = veterinarioResponsavel; // Atribui o veterinário responsável
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos; // Retorna a lista de atendimentos ativos da sala
    }

    public int getTotalFinalizados() {
        return totalFinalizados; // Retorna a quantidade de atendimentos finalizados nesta sala
    }

    public void incrementarFinalizados() {
        this.totalFinalizados++; // Incrementa o contador quando um atendimento é finalizado
    }

    // Método para adicionar atendimento respeitando o tipo de procedimento e a capacidade
    public boolean adicionarAtendimento(Atendimento atendimento) {
        // Verifica se a sala já atingiu a capacidade máxima
        if (atendimentos.size() >= capacidadeMaxima) {
            System.out.println("Erro: Sala cheia! Capacidade máxima de " + capacidadeMaxima + " atingida."); // Exibe erro de capacidade
            return false; // Retorna falso pois não foi possível adicionar
        }
        // Verifica se o procedimento é compatível com o tipo de sala
        if (!atendimento.getProcedimento().getNome().equalsIgnoreCase(tipoSala)) {
            System.out.println("Erro: O procedimento '" + atendimento.getProcedimento().getNome() + "' não é compatível com o tipo da sala ('" + tipoSala + "')."); // Exibe erro de incompatibilidade
            return false; // Retorna falso pois o procedimento não bate com o tipo da sala
        }
        
        atendimentos.add(atendimento); // Adiciona o atendimento à lista da sala
        return true; // Retorna verdadeiro se tudo deu certo
    }

    // Método para remover atendimento da sala (usado ao finalizar)
    public void removerAtendimento(Atendimento atendimento) {
        atendimentos.remove(atendimento); // Remove o atendimento informado da lista da sala
    }
}