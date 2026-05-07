import java.util.Scanner;

class CaixaEletronico {

    private int cotaMinima;

    public String armazenaCotaMinima(int minimo) {

        String resposta = "";

        if (minimo <= 0) {
            resposta = "Valor mínimo inválido! Digite um valor maior que zero.";
        } else {
            this.cotaMinima = minimo;
            resposta = "Cota mínima definida com sucesso: R$ " + minimo;
        }

        return resposta;
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CaixaEletronico caixaEletronico = new CaixaEletronico();

        System.out.print("Digite o valor da cota mínima do caixa: ");
        int valorInformado = sc.nextInt();

        String mensagem = caixaEletronico.armazenaCotaMinima(valorInformado);

        System.out.println(mensagem);

        sc.close();
    }
} 
