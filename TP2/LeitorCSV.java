import java.io.*;
import java.util.*;

public class LeitorCSV {

    public static List<Restaurante> ler(String path) {
        List<Restaurante> lista = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String linha;

            br.readLine(); // pula cabeçalho

            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(",");

                Restaurante r = new Restaurante(
                    p[0],
                    Double.parseDouble(p[1]),
                    Double.parseDouble(p[2])
                );

                lista.add(r);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return lista;
    }
}