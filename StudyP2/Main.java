class Celula {
    Celula esq;
    Celula dir;
    Celula inf;
    Celula sup;

    int elemento;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        esq = dir = sup = inf = null;
    }
}

class Matriz {

    private Celula inicio;
    private int linha;
    private int coluna;

    public Matriz() {
        this(3, 3);
    }

    public Matriz(int l, int c) {

        this.linha = l;
        this.coluna = c;

        inicio = new Celula();

        // =========================
        // CRIA PRIMEIRA LINHA
        // =========================

        Celula i = inicio;

        for (int j = 1; j < coluna; j++) {

            i.dir = new Celula();
            i.dir.esq = i;

            i = i.dir;
        }

        // =========================
        // CRIA RESTANTE DAS LINHAS
        // =========================

        Celula linhaAtual = inicio;

        for (int lin = 1; lin < linha; lin++) {

            // cria primeira célula da linha de baixo
            linhaAtual.inf = new Celula();
            linhaAtual.inf.sup = linhaAtual;

            Celula superior = linhaAtual;
            Celula atual = linhaAtual.inf;

            // cria restante da linha
            for (int col = 1; col < coluna; col++) {

                // cria célula à direita
                atual.dir = new Celula();
                atual.dir.esq = atual;

                // anda na linha de cima
                superior = superior.dir;

                // conecta verticalmente
                atual.dir.sup = superior;
                superior.inf = atual.dir;

                // anda na linha atual
                atual = atual.dir;
            }

            // desce para próxima linha
            linhaAtual = linhaAtual.inf;
        }
    }

    // =========================
    // MOSTRAR MATRIZ
    // =========================

    public void mostrar() {

        for (Celula i = inicio; i != null; i = i.inf) {

            for (Celula j = i; j != null; j = j.dir) {

                System.out.print(j.elemento + " ");
            }

            System.out.println();
        }
    }
}
public class Main {

    public static void main(String[] args) {

        Matriz m = new Matriz(3, 3);

        m.mostrar();
    }
}

/*public void inserir(int valor, int l, int c) {

    Celula i = inicio;

    // desce linhas
    for(int x = 0; x < l; x++) {
        i = i.inf;
    }

    // anda colunas
    for(int y = 0; y < c; y++) {
        i = i.dir;
    }

    // insere valor
    i.elemento = valor;
}
public void preencher() {

    int valor = 1;

    for(Celula i = inicio; i != null; i = i.inf) {

        for(Celula j = i; j != null; j = j.dir) {

            j.elemento = valor;
            valor++;
        }
    }
}
public class Main {

    public static void main(String[] args) {

        Matriz m = new Matriz(3,3);

        m.preencher();

        m.mostrar();
    }
}
*/
