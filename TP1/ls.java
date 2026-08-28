```java
public class ls {

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

    // Ve se a string tem so vogais
    public static boolean Vogal(String s) {

        if (s.length() == 0) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Teq ser uma letra
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }

            // Se não for vogal, para
            if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                    || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')) {
                return false;
            }
        }

        return true;
    }

    // Ve se a string tem so consoantes
    public static boolean Consoante(String s) {

        if (s.length() == 0) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Precisa ser uma letra
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }

            // Se for vogal, não pode ser só de consoantes
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                    || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                return false;
            }
        }

        return true;
    }

    // Ve se a string é um número inteiro
    public static boolean Inteiro(String s) {

        if (s.length() == 0) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Se tiver algum caractere que não seja numero, n é inteiro
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }

        return true;
    }

    // Ve se a string é um número real
    public static boolean Real(String s) {
        int ponto = 0;
        int digitos = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '.' || c == ',') {
                ponto++;

                // Só pode ter um ponto ou uma virgula
                if (ponto > 1) {
                    return false;
                }
            }
            else if (c >= '0' && c <= '9') {
                digitos++;
            }
            else {
                return false;
            }
        }

        // Precisa ter pelo menos um numero
        if (digitos == 0) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String entrada = "";

        // le até FIM
        while (Fim(entrada) == false) {
            entrada = MyIO.readLine();

            if (Fim(entrada) == false) {

                String resp = "";

                if (Vogal(entrada)) {
                    resp += "SIM ";
                }
                else {
                    resp += "NAO ";
                }

                if (Consoante(entrada)) {
                    resp += "SIM ";
                }
                else {
                    resp += "NAO ";
                }

                if (Inteiro(entrada)) {
                    resp += "SIM ";
                }
                else {
                    resp += "NAO ";
                }

                if (Real(entrada)) {
                    resp += "SIM";
                }
                else {
                    resp += "NAO";
                }

                MyIO.println(resp);
            }
        }
    }
}
```
