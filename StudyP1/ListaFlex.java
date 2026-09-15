public class Celula{

private int elemento;
private Celula prox;
    
    public Celula(){
        this.elemento=0;
        this.prox=null;
    }
    public Celula(int elemento){
        this.elemento=elemento;
        this.prox=null;
    }
}
public class Lista{
    Celula primeiro,ultimo;
    public Lista(){
        primeiro = new Celula();
        this.ultimo=primeiro;
    }
    public void InserirInicio(int x){

        Celula tmp = new Celula();
        tmp.prox=primeiro.prox;
                    primeiro.prox=tmp;
                    if(primeiro==ultimo){
                        ultimo=tmp;
                    }
                    tmp=null;

        
    }

    public void InserirFim(int x){
        Celula tmp=new Celula();
        tmp=ultimo.prox;
        tmp=null;
    }

    public int RemoverInicio(){
        int save;
        save=primeiro.prox.elemento;
        primeiro.prox=primeiro.prox.prox;
        return save;

    }
    public int RemoverFim(){
        for(Celula i=0; i.prox!=ultimo; i=primeiro.prox);
        int save;
        save=ultimo.elemento;
        ultimo=i;
        i=ultimo.prox;
        ultimo.prox=null;
        return save;
    }
    public int Inserir(int x, int pos)
    {
        
    }
}