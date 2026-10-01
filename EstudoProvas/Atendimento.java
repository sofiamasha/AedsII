// Classe que representa um atendimento veterinário
public class Atendimento {
    // Atributos privados do atendimento
    private int codigo;                     // Código identificador do atendimento
    private String nomeAnimal;              // Nome do animal
    private String especie;                 // Espécie do animal
    private String nomeTutor;               // Nome do tutor/dono
    private String data;                    // Data do atendimento
    private String horario;                 // Horário do atendimento
    private StatusAtendimento status;       // Status atual (AGENDADO, EM_ANDAMENTO, FINALIZADO)
    private String observacoes;             // Observações clínicas
    private Procedimento procedimento;      // Procedimento associado
    private Sala salaAtribuida;             // Sala onde o atendimento ocorre ou ocorreu

    // Construtor completo
    public Atendimento(int codigo, String nomeAnimal, String especie, String nomeTutor, String data, String horario, String observacoes, Procedimento procedimento) {
        this.codigo = codigo;                         // Define o código
        this.nomeAnimal = nomeAnimal;                 // Define o nome do animal
        this.especie = especie;                       // Define a espécie
        this.nomeTutor = nomeTutor;                   // Define o tutor
        this.data = data;                             // Define a data
        this.horario = horario;                       // Define o horário
        this.status = StatusAtendimento.AGENDADO;     // Regra 3: Todo atendimento começa com status "AGENDADO"
        this.observacoes = observacoes;               // Define as observações
        this.procedimento = procedimento;             // Associa o procedimento
        this.salaAtribuida = null;                    // Regra 3: Atendimentos agendados não possuem sala atribuída
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo; // Retorna o código
    }

    public String getNomeAnimal() {
        return nomeAnimal; // Retorna o nome do animal
    }

    public String getEspecie() {
        return especie; // Retorna a espécie
    }

    public String getNomeTutor() {
        return nomeTutor; // Retorna o nome do tutor
    }

    public String getData() {
        return data; // Retorna a data
    }

    public String getHorario() {
        return horario; // Retorna o horário
    }

    public StatusAtendimento getStatus() {
        return status; // Retorna o status atual
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status; // Altera o status
    }

    public String getObservacoes() {
        return observacoes; // Retorna as observações
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes; // Atualiza as observações
    }

    public Procedimento getProcedimento() {
        return procedimento; // Retorna o procedimento
    }

    public Sala getSalaAtribuida() {
        return salaAtribuida; // Retorna a sala atribuída
    }

    public void setSalaAtribuida(Sala salaAtribuida) {
        this.salaAtribuida = salaAtribuida; // Atribui ou altera a sala
    }

    // Exibe os detalhes completos do atendimento (Funcionalidade 7)
    public void exibirDetalhesCompletos() {
        System.out.println("--- DETALHES DO ATENDIMENTO #" + codigo + " ---");                                     // Cabeçalho com o código
        System.out.println("Animal: " + nomeAnimal + " (" + especie + ") | Tutor: " + nomeTutor);                       // Exibe dados do animal e tutor
        System.out.println("Data/Hora: " + data + " às " + horario + " | Status: " + status);                           // Exibe data, hora e status
        System.out.println("Observações: " + observacoes);                                                              // Exibe observações
        System.out.println(procedimento.exibeInformacoes());                                                            // Exibe detalhes do procedimento
        if (salaAtribuida != null) {                                                                                    // Verifica se possui sala vinculada
            System.out.println("Sala: " + salaAtribuida.getNumero() + " - Bloco " + salaAtribuida.getBloco());         // Exibe informações da sala
            if (salaAtribuida.getVeterinarioResponsavel() != null) {                                                    // Verifica se a sala tem veterinário
                System.out.println("Veterinário: " + salaAtribuida.getVeterinarioResponsavel().getNome());             // Exibe nome do veterinário responsável
            } else {
                System.out.println("Veterinário: Sem veterinário vinculado à sala.");                                   // Informa ausência de veterinário
            }
        } else {
            System.out.println("Sala: Nenhuma sala atribuída no momento.");                                             // Informa que não tem sala atribuída
        }
        System.out.println("----------------------------------------");                                                 // Rodapé divisor
    }
}