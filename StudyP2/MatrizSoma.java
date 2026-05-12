void preencherColuna(Matriz *matriz){

    Celula *linha = matriz->inicio;

    // percorre linhas
    while(linha != NULL){

        int soma = 0;

        Celula *coluna = linha;

        // soma até antes da última coluna
        while(coluna->dir != NULL){

            soma += coluna->elemento;

            coluna = coluna->dir;
        }

        // coluna agora está na última
        coluna->elemento = soma;

        linha = linha->inf;
    }
}