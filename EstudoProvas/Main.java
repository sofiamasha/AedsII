import java.util.ArrayList; // Importa ArrayList para guardar listas de dados
import java.util.List;      // Importa a interface List
import java.util.Scanner;   // Importa Scanner para leitura do teclado

// Classe principal do sistema
public class Main {
    // Listas globais estáticas para armazenar os registros do sistema
    private static List<Veterinario> listaVeterinarios = new ArrayList<>(); // Armazena veterinários
    private static List<Sala> listaSalas = new ArrayList<>();               // Armazena salas
    private static List<Atendimento> listaAtendimentos = new ArrayList<>(); // Armazena atendimentos
    private static int contadorCodigoAtendimento = 1;                       // Contador para gerar código autoincrementado

    // Método solicitado no enunciado para criar 3 veterinários e 3 salas na inicialização
    public static void inicializarDados() {
        // Criando 3 veterinários
        Veterinario v1 = new Veterinario("Dra. Ana Silva", "111.222.333-44", "Cirurgia Geral", "31-98888-1111");  // Instancia veterinário 1
        Veterinario v2 = new Veterinario("Dr. Carlos Souza", "555.666.777-88", "Dermatologia", "31-97777-2222");  // Instancia veterinário 2
        Veterinario v3 = new Veterinario("Dra. Juliana Lima", "999.000.111-22", "Exóticos", "31-96666-3333");     // Instancia veterinário 3
        
        // Adicionando veterinários na lista
        listaVeterinarios.add(v1); // Guarda v1 na lista
        listaVeterinarios.add(v2); // Guarda v2 na lista
        listaVeterinarios.add(v3); // Guarda v3 na lista

        // Criando 3 salas
        Sala s1 = new Sala(101, "A", 2, "Cirurgia Geral");  // Instancia sala 101
        Sala s2 = new Sala(102, "A", 3, "Dermatologia");    // Instancia sala 102
        Sala s3 = new Sala(201, "B", 1, "Exóticos");        // Instancia sala 201

        // Adicionando salas na lista
        listaSalas.add(s1); // Guarda s1 na lista
        listaSalas.add(s2); // Guarda s2 na lista
        listaSalas.add(s3); // Guarda s3 na lista
        
        System.out.println("Dados iniciais (3 veterinários e 3 salas) criados com sucesso!"); // Mensagem de confirmação
    }

    // Método principal para rodar o sistema
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Cria o leitor do teclado
        inicializarDados();                       // Chama o método que gera os 3 veterinários e 3 salas

        int opcao = -1; // Variável para controlar a opção escolhida do menu
        do {
            // Exibição do menu principal
            System.out.println("\n========== SISTEMA VETERINÁRIO ==========");  // Título do menu
            System.out.println("1. Cadastrar atendimento");                     // Opção 1
            System.out.println("2. Associar um veterinário a uma sala");          // Opção 2
            System.out.println("3. Atribuir atendimento a uma sala");             // Opção 3
            System.out.println("4. Exibir atendimentos por sala");               // Opção 4
            System.out.println("5. Informar total de finalizados por sala");     // Opção 5
            System.out.println("6. Buscar atendimentos por status");             // Opção 6
            System.out.println("7. Exibir detalhes de um atendimento");          // Opção 7
            System.out.println("8. Finalizar um atendimento");                   // Opção extra útil para testar a Regra 4
            System.out.println("0. Sair");                                       // Opção para sair
            System.out.print("Escolha uma opção: ");                             // Solicita a escolha do usuário

            opcao = scanner.nextInt();      // Leitura da opção
            scanner.nextLine();             // Limpeza de buffer

            // Tratamento da escolha do usuário
            switch (opcao) {
                case 1:
                    cadastrarAtendimento(scanner); // Executa cadastro de atendimento
                    break;
                case 2:
                    associarVeterinarioSala(scanner); // Executa associação de vet com sala
                    break;
                case 3:
                    atribuirAtendimentoSala(scanner); // Executa atribuição do atendimento à sala
                    break;
                case 4:
                    exibirAtendimentosPorSala(scanner); // Exibe lista de atendimentos da sala
                    break;
                case 5:
                    informarFinalizadosPorSala(); // Mostra totais finalizados por sala
                    break;
                case 6:
                    buscarPorStatus(scanner); // Filtra e lista atendimentos pelo status
                    break;
                case 7:
                    exibirDetalhesAtendimento(scanner); // Mostra dados detalhados pelo código
                    break;
                case 8:
                    finalizarAtendimento(scanner); // Altera status para finalizado
                    break;
                case 0:
                    System.out.println("Encerrando o sistema..."); // Mensagem de término
                    break;
                default:
                    System.out.println("Opção inválida!"); // Caso escolha algo fora do menu
            }
        } while (opcao != 0); // Continua no loop até que o usuário escolha 0

        scanner.close(); // Fecha o leitor do teclado
    }

    // Funcionalidade 1: Cadastrar atendimento
    private static void cadastrarAtendimento(Scanner scanner) {
        System.out.println("\n--- Cadastrar Atendimento ---");                     // Título
        System.out.print("Nome do animal: ");                                        // Pergunta
        String nomeAnimal = scanner.nextLine();                                      // Lê nome do animal
        System.out.print("Espécie: ");                                               // Pergunta
        String especie = scanner.nextLine();                                         // Lê espécie
        System.out.print("Nome do tutor: ");                                         // Pergunta
        String nomeTutor = scanner.nextLine();                                       // Lê tutor
        System.out.print("Data (dd/mm/aaaa): ");                                     // Pergunta
        String data = scanner.nextLine();                                            // Lê data
        System.out.print("Horário (hh:mm): ");                                       // Pergunta
        String horario = scanner.nextLine();                                         // Lê horário
        System.out.print("Observações: ");                                           // Pergunta
        String observacoes = scanner.nextLine();                                     // Lê observações

        // Entrada dos dados do procedimento
        System.out.println("--- Dados do Procedimento ---");                          // Título da seção
        System.out.print("Nome do procedimento (ex: Cirurgia Geral, Dermatologia, Exóticos): "); // Pergunta
        String nomeProc = scanner.nextLine();                                        // Lê nome do procedimento
        System.out.print("Duração estimada (minutos): ");                            // Pergunta
        int duracao = scanner.nextInt();                                             // Lê duração
        System.out.print("Valor (R$): ");                                            // Pergunta
        double valor = scanner.nextDouble();                                         // Lê valor
        scanner.nextLine();                                                          // Limpa buffer
        System.out.print("Nível de complexidade (Baixa/Média/Alta): ");              // Pergunta
        String complexidade = scanner.nextLine();                                    // Lê complexidade

        // Cria o procedimento associado
        Procedimento procedimento = new Procedimento(nomeProc, duracao, valor, complexidade); // Cria instância de Procedimento
        
        // Cria o atendimento usando o contador atual
        Atendimento atendimento = new Atendimento(contadorCodigoAtendimento++, nomeAnimal, especie, nomeTutor, data, horario, observacoes, procedimento); // Instancia o atendimento

        listaAtendimentos.add(atendimento); // Armazena na lista global
        System.out.println("Atendimento cadastrado com sucesso! Código gerado: " + atendimento.getCodigo()); // Retorna o código gerado
    }

    // Funcionalidade 2: Associar um veterinário a uma sala (Regra 1: Vet pode ser responsável por apenas 1 sala)
    private static void associarVeterinarioSala(Scanner scanner) {
        System.out.println("\n--- Associar Veterinário a uma Sala ---");              // Título
        
        System.out.println("Veterinários disponíveis:");                            // Exibe lista
        for (int i = 0; i < listaVeterinarios.size(); i++) {                          // Percorre lista de veterinários
            Veterinario v = listaVeterinarios.get(i);                                 // Pega o item atual
            System.out.println(i + " - " + v.getNome() + " (Com sala? " + (v.isTemSala() ? "Sim" : "Não") + ")"); // Mostra dados
        }
        System.out.print("Escolha o número do veterinário: ");                       // Pergunta
        int idxVet = scanner.nextInt();                                               // Lê o índice do vet

        if (idxVet < 0 || idxVet >= listaVeterinarios.size()) {                       // Valida índice informado
            System.out.println("Veterinário inválido.");                              // Mensagem de erro
            return;                                                                   // Aborta método
        }

        Veterinario vet = listaVeterinarios.get(idxVet);                              // Pega objeto selecionado

        // Aplicação da Regra 1
        if (vet.isTemSala()) {                                                        // Verifica se já possui sala
            System.out.println("Regra de negócio violada: O veterinário " + vet.getNome() + " já é responsável por outra sala."); // Erro da regra 1
            return;                                                                   // Aborta método
        }

        System.out.println("Salas cadastradas:");                                     // Lista as salas
        for (int i = 0; i < listaSalas.size(); i++) {                                 // Percorre salas
            Sala s = listaSalas.get(i);                                               // Pega sala atual
            String nomVet = (s.getVeterinarioResponsavel() != null) ? s.getVeterinarioResponsavel().getNome() : "Sem veterinário"; // Nome do responsável atual
            System.out.println(i + " - Sala " + s.getNumero() + " (" + s.getTipoSala() + ") - Responsável: " + nomVet); // Mostra sala
        }
        System.out.print("Escolha o número da sala: ");                              // Pergunta
        int idxSala = scanner.nextInt();                                              // Lê o índice da sala

        if (idxSala < 0 || idxSala >= listaSalas.size()) {                            // Valida índice informado
            System.out.println("Sala inválida.");                                     // Mensagem de erro
            return;                                                                   // Aborta método
        }

        Sala sala = listaSalas.get(idxSala);                                          // Pega objeto sala selecionado

        // Se a sala já tinha um veterinário anterior, libera aquele veterinário
        if (sala.getVeterinarioResponsavel() != null) {                              // Verifica se sala já tinha vet
            sala.getVeterinarioResponsavel().setTemSala(false);                       // Libera o antigo vet
        }

        sala.setVeterinarioResponsavel(vet);                                          // Associa o novo vet à sala
        vet.setTemSala(true);                                                         // Marca o vet como possuidor de sala
        System.out.println("Veterinário " + vet.getNome() + " associado à Sala " + sala.getNumero() + " com sucesso!"); // Confirmação
    }

    // Funcionalidade 3: Atribuir atendimento a uma sala
    private static void atribuirAtendimentoSala(Scanner scanner) {
        System.out.println("\n--- Atribuir Atendimento a uma Sala ---");               // Título
        
        // Exibe atendimentos que estão com status AGENDADO (sem sala atribuída)
        List<Atendimento> agendados = new ArrayList<>();                              // Cria lista temporária
        for (Atendimento a : listaAtendimentos) {                                     // Varre atendimentos
            if (a.getStatus() == StatusAtendimento.AGENDADO) {                         // Filtra só os agendados
                agendados.add(a);                                                     // Guarda na lista temporária
            }
        }

        if (agendados.isEmpty()) {                                                    // Se lista estiver vazia
            System.out.println("Nenhum atendimento agendado aguardando sala.");       // Informa usuário
            return;                                                                   // Aborta método
        }

        System.out.println("Atendimentos Agendados:");                                 // Cabeçalho da lista
        for (int i = 0; i < agendados.size(); i++) {                                  // Varre lista filtrada
            Atendimento a = agendados.get(i);                                         // Pega atendimento
            System.out.println(i + " - Cód: " + a.getCodigo() + " | Animal: " + a.getNomeAnimal() + " | Procedimento: " + a.getProcedimento().getNome()); // Exibe linha
        }
        System.out.print("Escolha o atendimento: ");                                  // Solicita escolha
        int idxAtend = scanner.nextInt();                                             // Lê resposta

        if (idxAtend < 0 || idxAtend >= agendados.size()) {                           // Valida índice
            System.out.println("Atendimento inválido.");                              // Mensagem de erro
            return;                                                                   // Aborta método
        }

        Atendimento atendimento = agendados.get(idxAtend);                            // Resgata o atendimento escolhido

        System.out.println("Salas disponíveis:");                                     // Lista as salas do sistema
        for (int i = 0; i < listaSalas.size(); i++) {                                 // Varre lista de salas
            Sala s = listaSalas.get(i);                                               // Pega a sala
            System.out.println(i + " - Sala " + s.getNumero() + " (Tipo: " + s.getTipoSala() + " | Ocupação: " + s.getAtendimentos().size() + "/" + s.getCapacidadeMaxima() + ")"); // Mostra dados
        }
        System.out.print("Escolha a sala: ");                                         // Solicita escolha da sala
        int idxSala = scanner.nextInt();                                              // Lê resposta

        if (idxSala < 0 || idxSala >= listaSalas.size()) {                            // Valida índice da sala
            System.out.println("Sala inválida.");                                     // Mensagem de erro
            return;                                                                   // Aborta método
        }

        Sala sala = listaSalas.get(idxSala);                                          // Resgata a sala selecionada

        // Tenta adicionar na sala (valida compatibilidade de procedimento e capacidade)
        if (sala.adicionarAtendimento(atendimento)) {                                 // Invoca validação na classe Sala
            atendimento.setSalaAtribuida(sala);                                       // Associa a sala ao atendimento
            atendimento.setStatus(StatusAtendimento.EM_ANDAMENTO);                    // Muda status para "EM_ANDAMENTO"
            System.out.println("Atendimento atribuído com sucesso!");                 // Confirmação de sucesso
        }
    }

    // Funcionalidade 4: Exibir todos os atendimentos atribuídos a uma sala específica e total ao final
    private static void exibirAtendimentosPorSala(Scanner scanner) {
        System.out.println("\n--- Exibir Atendimentos de uma Sala ---");               // Título
        for (int i = 0; i < listaSalas.size(); i++) {                                 // Exibe salas para escolha
            System.out.println(i + " - Sala " + listaSalas.get(i).getNumero());       // Linha com número da sala
        }
        System.out.print("Escolha a sala: ");                                         // Pergunta
        int idxSala = scanner.nextInt();                                              // Lê opção

        if (idxSala < 0 || idxSala >= listaSalas.size()) {                            // Valida a opção
            System.out.println("Sala inválida.");                                     // Mensagem de erro
            return;                                                                   // Aborta método
        }

        Sala sala = listaSalas.get(idxSala);                                          // Resgata sala
        List<Atendimento> listaSala = sala.getAtendimentos();                         // Obtém a lista de atendimentos ativos da sala

        System.out.println("\n--- Atendimentos Ativos na Sala " + sala.getNumero() + " ---"); // Cabeçalho
        for (Atendimento a : listaSala) {                                             // Loop nos atendimentos
            System.out.println("Cód: " + a.getCodigo() + " | Animal: " + a.getNomeAnimal() + " | Procedimento: " + a.getProcedimento().getNome() + " | Status: " + a.getStatus()); // Imprime dados
        }
        System.out.println("TOTAL DE ATENDIMENTOS NA SALA: " + listaSala.size());     // Imprime total acumulado na sala no momento
    }

    // Funcionalidade 5: Informar a quantidade total de atendimentos finalizados por cada sala
    private static void informarFinalizadosPorSala() {
        System.out.println("\n--- Total de Atendimentos Finalizados por Sala ---");    // Título
        for (Sala s : listaSalas) {                                                   // Varre todas as salas cadastradas
            System.out.println("Sala " + s.getNumero() + " (Bloco " + s.getBloco() + "): " + s.getTotalFinalizados() + " atendimento(s) finalizado(s)"); // Mostra contagem acumulada da sala
        }
    }

    // Funcionalidade 6: Buscar atendimentos por status (exibindo detalhes, sala e veterinário)
    private static void buscarPorStatus(Scanner scanner) {
        System.out.println("\n--- Buscar Atendimentos por Status ---");              // Título
        System.out.println("1 - AGENDADO");                                          // Opção 1
        System.out.println("2 - EM_ANDAMENTO");                                      // Opção 2
        System.out.println("3 - FINALIZADO");                                        // Opção 3
        System.out.print("Opção: ");                                                  // Pergunta
        int st = scanner.nextInt();                                                   // Lê opção do status

        StatusAtendimento statusBuscado = null;                                       // Variável auxiliar do enum
        if (st == 1) statusBuscado = StatusAtendimento.AGENDADO;                      // Atribui status agendado
        else if (st == 2) statusBuscado = StatusAtendimento.EM_ANDAMENTO;             // Atribui status em andamento
        else if (st == 3) statusBuscado = StatusAtendimento.FINALIZADO;               // Atribui status finalizado
        else {
            System.out.println("Opção de status inválida.");                          // Opção incorreta
            return;                                                                   // Aborta método
        }

        System.out.println("\n--- Atendimentos com Status " + statusBuscado + " ---"); // Cabeçalho do resultado
        boolean encontrou = false;                                                    // Flag para controle
        for (Atendimento a : listaAtendimentos) {                                     // Varre a lista de atendimentos
            if (a.getStatus() == statusBuscado) {                                     // Compara status
                a.exibirDetalhesCompletos();                                          // Exibe dados incluindo sala e vet
                encontrou = true;                                                     // Sinaliza que achou ao menos um
            }
        }

        if (!encontrou) {                                                             // Se não achou nenhum com o status
            System.out.println("Nenhum atendimento encontrado com este status.");     // Informa o usuário
        }
    }

    // Funcionalidade 7: Exibir os detalhes completos de um atendimento específico
    private static void exibirDetalhesAtendimento(Scanner scanner) {
        System.out.println("\n--- Exibir Detalhes de Atendimento ---");               // Título
        System.out.print("Digite o código do atendimento: ");                         // Solicita código
        int cod = scanner.nextInt();                                                  // Lê código

        Atendimento achado = null;                                                    // Variável para armazenar a referência
        for (Atendimento a : listaAtendimentos) {                                     // Varre lista de atendimentos
            if (a.getCodigo() == cod) {                                               // Verifica correspondência de código
                achado = a;                                                           // Guarda objeto se achou
                break;                                                                // Sai do loop
            }
        }

        if (achado != null) {                                                         // Se o atendimento existe
            achado.exibirDetalhesCompletos();                                         // Chama o método interno de exibição completa
        } else {
            System.out.println("Atendimento não encontrado!");                        // Informa código não existente
        }
    }

    // Funcionalidade Auxiliar (Opção 8): Finalizar atendimento (Aplica Regra 4)
    private static void finalizarAtendimento(Scanner scanner) {
        System.out.println("\n--- Finalizar Atendimento ---");                         // Título
        System.out.print("Digite o código do atendimento a finalizar: ");              // Solicita o código
        int cod = scanner.nextInt();                                                  // Lê código

        Atendimento achado = null;                                                    // Referência para o atendimento
        for (Atendimento a : listaAtendimentos) {                                     // Varre a lista
            if (a.getCodigo() == cod) {                                               // Verifica o código
                achado = a;                                                           // Guarda
                break;                                                                // Interrompe o loop
            }
        }

        if (achado == null) {                                                         // Se não achar
            System.out.println("Atendimento não encontrado.");                        // Informa erro
            return;                                                                   // Aborta
        }

        if (achado.getStatus() != StatusAtendimento.EM_ANDAMENTO) {                   // Valida se ele está em andamento
            System.out.println("Apenas atendimentos em andamento podem ser finalizados."); // Notifica regra
            return;                                                                   // Aborta
        }

        // Aplicação da Regra 4:
        // O atendimento guarda a sala onde ocorreu, mas a sala remove da sua lista de ativos e incrementa o totalizador de finalizados.
        Sala sala = achado.getSalaAtribuida();                                        // Recupera a sala vinculada
        if (sala != null) {                                                           // Se tem sala vinculada
            sala.removerAtendimento(achado);                                          // Sala deixa de manter a referência do atendimento
            sala.incrementarFinalizados();                                            // Sala adiciona +1 aos seus finalizados
        }

        achado.setStatus(StatusAtendimento.FINALIZADO);                               // Atualiza o status do atendimento para FINALIZADO
        System.out.println("Atendimento #" + achado.getCodigo() + " finalizado com sucesso!"); // Confirmação
    }
}