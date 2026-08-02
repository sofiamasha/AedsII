import java.util.Scanner;
public class AvaliadorRPN {
static class Pilha {
private int[] elementos;
private int topo;
public Pilha(int capacidade) {
elementos = new int[capacidade];
topo = -1;
}
public void push(int valor) {
elementos[++topo] = valor;
}
public int pop() {
return elementos[topo--];
}
public boolean vazia() {
return topo == -1;
}
public int tamanho() {
return topo + 1;
}
}
public static int avaliar(String expressao) {
Pilha pilha = new Pilha(expressao.length());
for (int i = 0; i < expressao.length(); i++) {
char c = expressao.charAt(i);
if (c >= '0' && c <= '9') {
pilha.push(c - '0');
}
else if (c == '+' || c == '-' || c == '*' || c == '/') {
int v2 = pilha.pop();
int v1 = pilha.pop();
int resultado = 0;
if (c == '+') {
resultado = v1 + v2;
} else if (c == '-') {
resultado = v1 - v2;
} else if (c == '*') {
resultado = v1 * v2;
} else if (c == '/') {
resultado = v1 / v2;
}
pilha.push(resultado);
}
}
return pilha.pop();
}
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
while (scanner.hasNextLine()) {
String expressao = scanner.nextLine().trim();
if (expressao.isEmpty()) {
continue;
}
System.out.println(avaliar(expressao));
}
scanner.close();
}
}