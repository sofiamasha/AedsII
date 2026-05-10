class Celula{
    
    int elemento;
    Celula prox;
    
    Celula(int elemento){
        this.elemento=elemento;
        this.prox=null;
    }
    
    
}

class Pilha{
    private Celula topo;
    public Pilha(){
        topo=null;
    }
    
    public void push(int x){
        Celula nova = new Celula(x);
        
        nova.prox=topo;
        topo=nova;
    }
    public int pop() throws Exception{
            if(topo == null){
                throw new Exception("Pilha vazia");
            }
            
            int resp=topo.elemento;
            topo=topo.prox;
            
            return resp;
        }
    }

