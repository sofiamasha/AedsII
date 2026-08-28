import java.util.Scanner;

public class ValidacaoSenha {

    // Ve se a senha tem todos os caractere pedidos
    public static boolean Valida(String s){

        int tam = s.length();

        // Se tiver menos de 8 caracteres ja nao pode
        if(tam < 8){
            return false;
        }

        int maiuscula = 0;
        int minuscula = 0;
        int numero = 0;
        int especial = 0;

        // Percorre a senha procurando os tipos
        for(int i = 0; i < tam; i++){

            char c = s.charAt(i);

            // Ve se é letra maiuscula
            if(c >= 'A' && c <= 'Z'){
                maiuscula++;
            }

            // Ve se é letra minuscula
            else if(c >= 'a' && c <= 'z'){
                minuscula++;
            }

            // Ve se é numero
            else if(c >= '0' && c <= '9'){
                numero++;
            }

            // Se nao for nenhum dos anteriores é especial
            else{
                especial++;
            }
        }

        // Precisa ter pelo menos um de cada
        if(maiuscula > 0 && minuscula > 0 && numero > 0 && especial > 0){
            return true;
        }

        return false;
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        String senha = sc.nextLine();

        // Le ate aparecer FIM
        while(senha.length() != 3 ||
              senha.charAt(0) != 'F' ||
              senha.charAt(1) != 'I' ||
              senha.charAt(2) != 'M'){

            boolean resp = Valida(senha);

            if(resp == true){
                System.out.println("SIM");
            }
            else{
                System.out.println("NAO");
            }

            // Le a proxima senha
            senha = sc.nextLine();
        }

        sc.close();
    }
}