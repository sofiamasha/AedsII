/*Ciframento de C´esar - O Imperador J´ulio C´esar foi um dos principais nomes do Imp´erio
Romano. Entre suas contribui¸c˜oes, temos um algoritmo de criptografia chamado “Ciframento de
C´esar”. Segundo os historiadores, C´esar utilizava esse algoritmo para criptografar as mensagens
que enviava aos seus generais durante as batalhas. A ideia b´asica ´e um simples deslocamento
de caracteres. Assim, por exemplo, se a chave utilizada para criptografar as mensagens for 3,
todas as ocorrˆencias do caractere ’a’ s˜ao substitu´ıdas pelo caractere ’d’, as do ’b’ por ’e’, e assim
sucessivamente. Crie um m´etodo iterativo que recebe uma string como parˆametro e retorna
outra contendo a entrada de forma cifrada. Neste exerc´ıcio, suponha a chave de ciframento trˆes.
Na sa´ıda padr˜ao, para cada linha de entrada, escreva uma linha com a mensagem criptografada */
/
import java.util.Scanner;

public class Ciframento{
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);

        while(sc.hasNextLine()){
            String palavra=sc.nextLine();

            for(int i=0; i<palavra.length(); i++){
                char c=palavra.charAt(i);
                if(c>='a' && c<='z'){
                c=(char)(c+3);
                if(c>'z'){
                    c=(char)(c-26);
                }
                }
                System.out.print(c);
            }
            System.out.println();
        }
        sc.close();
    }

}