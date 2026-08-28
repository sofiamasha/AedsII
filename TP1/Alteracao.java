import java.util.Random;

public class AlteracaoA {

    // Ve se a entrada é FIM
    public static boolean Fim(String s) {
        boolean resp = false;

        if (s.length() == 3) {
            if (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
                resp = true;
            }
        }

        return resp;
    }

    // Percorre a string e troca a primeira letra sorteada pela segunda
    public static String alterar(String s, char letra1, char letra2) {
        String resp = "";

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Se achar a letra sorteada, coloca a outra no lugar
            if (c == letra1) {
                resp = resp + letra2;
            } else {
                resp = resp + c;
            }
        }

        return resp;
    }

    public static void main(String[] args) {
        String entrada = "";
        Random gerador = new Random();

        // A seed 4 faz a sequência aleatória ser sempre a mesma
        gerador.setSeed(4);

        // Continua lendo até aparecer FIM
        while (Fim(entrada) == false) {
            entrada = MyIO.readLine();

            if (Fim(entrada) == false) {

                // Sorteia a primeira letra entre a e z
                char letra1 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));

                // Sorteia a segunda letra entre a e z
                char letra2 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));

                String resp = alterar(entrada, letra1, letra2);

                MyIO.println(resp);
            }
        }
    }
}