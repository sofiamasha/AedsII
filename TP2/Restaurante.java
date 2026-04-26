public class Restaurante {
    String nome;
    double nota;
    double preco;

    public Restaurante(String nome, double nota, double preco) {
        this.nome = nome;
        this.nota = nota;
        this.preco = preco;
    }

    public void mostrar() {
        System.out.println(nome + " | Nota: " + nota + " | Preço: " + preco);
    }
}