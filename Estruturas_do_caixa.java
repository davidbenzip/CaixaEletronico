public class CaixaEletronico {
    
    private int cotaMinima;
    
    // O cofre físico do caixa eletrônico: Valor da Nota, Quantidade
    private int[][] cedulas = {
        {100, 50}, 
        {50,  50}, 
        {20,  50}, 
        {10,  50}, 
        {5,   50}, 
        {2,   50}
    };

    public String armazenaCotaMinima(int minimo) {
        if (minimo <= 0) {
            return "Erro: Cota mínima deve ser maior que zero.";
        }
        this.cotaMinima = minimo;
        return "Cota mínima configurada: R$ " + minimo;
    }

    public String sacarNotasFisicas(int valor) {
        if (valor < cotaMinima) {
            return "ERRO: O valor solicitado é menor que a cota mínima do caixa (R$ " + cotaMinima + ").";
        }
        
        int restante = valor;
        int[] usadas = new int[cedulas.length];

        for (int i = 0; i < cedulas.length; i++) {
            int nota = cedulas[i][0];
            int disponivel = cedulas[i][1];
            int contador = 0;
            
            while (restante >= nota && contador < disponivel) {
                restante -= nota;
                contador++;
            }
            usadas[i] = contador;
        }

        if (restante != 0) {
            return "ERRO: O caixa não possui cédulas adequadas para este valor exato.";
        }

        StringBuilder sb = new StringBuilder("SAQUE FÍSICO REALIZADO!\nNotas fornecidas:\n");
        for (int i = 0; i < cedulas.length; i++) {
            if (usadas[i] > 0) {
                cedulas[i][1] -= usadas[i]; // Tira as notas do cofre
                sb.append("R$ ").append(cedulas[i][0]).append(" -> ").append(usadas[i]).append(" nota(s)\n");
            }
        }
        return sb.toString();
    }
}
