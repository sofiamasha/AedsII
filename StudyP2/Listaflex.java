class Lista {
    private Celula inicio;

    public Lista() {
        inicio = null;
    }

    public void InserirnoInicio(int x) {
        Celula nova = new Celula(x);
        nova.prox = inicio;
        inicio = nova;
    }

    public void InserirnoFim(int x) {
        Celula nova = new Celula(x);

        if (inicio == null) {
            inicio = nova;
        } else {
            Celula i;
            for (i = inicio; i.prox != null; i = i.prox);
            i.prox = nova;
        }
    }

    public int RemoverdoInicio() throws Exception {
        if (inicio == null) {
            throw new Exception("lista vazia");
        }

        int resp = inicio.elemento;
        inicio = inicio.prox;
        return resp;
    }

    public void Inserir(int x, int pos) throws Exception {
        if (pos == 0) {
            InserirnoInicio(x);
            return;
        }

        Celula i = inicio;

        for (int j = 0; j < pos - 1; j++) {
            i = i.prox;
        }

        Celula nova = new Celula(x);

        nova.prox = i.prox;
        i.prox = nova;
    }

    public int Remover(int pos) throws Exception {
        if (inicio == null) {
            throw new Exception("Lista vazia");
        }

        if (pos == 0) {
            return RemoverdoInicio();
        }

        Celula i = inicio;

        for (int j = 0; j < pos - 1; j++) {
            i = i.prox;
        }

        Celula tmp = i.prox;
        int resp = tmp.elemento;

        i.prox = tmp.prox;

        return resp;
    }

    public void mostrar() {
        for (Celula i = inicio; i != null; i = i.prox) {
            System.out.println(i.elemento);
        }
    }
}