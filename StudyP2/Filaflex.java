class Celula {
    int elemento;
    Celula prox;

    Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

// fila = FIFO
class Fila {
    private Celula inicio;
    private Celula fim;

    public Fila() {
        inicio = fim = null;
    }

    public void inserir(int x) {
        Celula nova = new Celula(x);

        if (inicio == null) {
            inicio = fim = nova;
        } else {
            fim.prox = nova;
            fim = nova;
        }
    }

    public int remover() throws Exception {
        if (inicio == null) {
            throw new Exception("Fila vazia");
        }

        int resp = inicio.elemento;
        inicio = inicio.prox;

        if (inicio == null) {
            fim = null;
        }

        return resp;
    }
}