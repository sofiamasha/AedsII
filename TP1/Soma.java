import java.util.Scanner;

public class Soma {

    // Soma os digitos com recursao
    public static int Soma(int n){

        // Quando chegar no ultimo digito, termina
        if(n < 10){
            return n;
        }

        // Soma o ultimo digito com o restante do numero
        return (n % 10) + Soma(n / 10);
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        // Le enquanto tiver numero
        while(sc.hasNextInt()){

            int n = sc.nextInt();

            int resp = Soma(n);

            System.out.println(resp);
        }

        sc.close();
    }
}