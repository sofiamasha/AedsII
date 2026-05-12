class no{
    int elemento;
no esq;
no dir;

public class no(int elemento){
    this.elemento=elemento;
    esq=dir=null;
}
}


class arvore{
 private no raiz;
 
 public class arvore(){
     raiz=null;
 }
   public void inserir(int x){
       
       no novo=New no(x);
       if(raiz==null){
          raiz=novo;
       }
        else{
            no i=raiz;
            no pai=null;
            while(i!=null){
                pai=i;
                if(x<i.elemento){
                    i=i.esq;
                }
                else if(x>i.elemento){
                    i=i.dir;
                }
                else{
                    print("Erro");
                }
            }
            
            if(x<pai.elemento){
                pai.esq=novo;
            }
            else{
                pai.dir=novo;
            }
        }
   } 
}

public boolean pesquisar(int x){

    No i = raiz;

    while(i != null){

        if(x == i.elemento){
            return true;
        }

        else if(x < i.elemento){
            i = i.esq;
        }

        else{
            i = i.dir;
        }
    }

    return false;
}