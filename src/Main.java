import cidades.modelo.Cidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        String cidades = entrada.nextLine();
        Cidade origem = obterCidade(cidades.split(",")[0]);
        Cidade destino = obterCidade(cidades.split(",")[1]);

        if (origem.isMeuVizinho(destino.ID)) {
            System.out.println("melhor trajeto " + origem.ID + " e " + destino.ID);
            System.out.println("distância = " + obterDistanciaEntre(origem, destino));
        } else {
            calcularTrajetoria(origem, destino);
            System.out.println("melhor trajeto " + formatarMensagemTragejo());
            System.out.println("distância = " + obterDistanciaEntre(trajetoCidades));
        }

        entrada.close();
    }

    private static List<String> trajetoCidades = new ArrayList<>();

    public static void calcularTrajetoria(Cidade origem, Cidade destino) {
        boolean achou = false;
        trajetoCidades.add(origem.ID);
        for (String v : origem.vizinhos) {
            Cidade cidade = obterCidade(v);
            if (cidade.isMeuVizinho(destino.ID)) {
                trajetoCidades.add(v);
                achou = true;
                break;
            }
        }

        if (!achou) {
            for (String v : origem.vizinhos) {
                Cidade cidade = obterCidade(v);
                for (String v2 : cidade.vizinhos) {
                    Cidade c2 = obterCidade(v2);
                    if (c2.isMeuVizinho(destino.ID)) {
                        trajetoCidades.add(v);
                        trajetoCidades.add(v2);
                        achou = true;
                        break;
                    }
                }
                if (achou) break;
            }
        }
        trajetoCidades.add(destino.ID);
    }

    private static Cidade obterCidade(String id) {
        try {
            return (Cidade) Class.forName("cidades.".concat(id)).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static double obterDistanciaEntre(List<String> cidades) {
        double total = 0;

        for (int i = 0; i < cidades.size(); i++) {
            if (i + 1 < cidades.size()) {
                Cidade cidade1 = obterCidade(cidades.get(i));
                Cidade cidade2 = obterCidade(cidades.get(i + 1));
                total += obterDistanciaEntre(cidade1, cidade2);
            }
        }
        return total;
    }


    private static double obterDistanciaEntre(Cidade cidade1, Cidade cidade2) {
        double x = Math.pow((cidade2.X - cidade1.X), 2);
        double y = Math.pow((cidade2.Y - cidade1.Y), 2);
        return Math.sqrt(x + y);
    }

    private static String formatarMensagemTragejo() {
        StringBuilder mensagem = new StringBuilder();
        int i = 0;
        for (String c : trajetoCidades) {
            i++;
            if (i < trajetoCidades.size() - 1) mensagem.append(c).append(", ");
            else if (i == trajetoCidades.size() -1 ) mensagem.append(c).append(" ");
            else mensagem.append("e ").append(c);
        }
        return mensagem.toString();
    }
}
