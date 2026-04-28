package model;

import view.GUI;

public class CaixaEletronico implements ICaixaEletronico {
	private int[][] cedulas = {
			{ 100, 0 },
			{ 50, 0 },
			{ 20, 0 },
			{ 10, 0 },
			{ 5, 0 },
			{ 2, 0 }
	};
	
	

	//logica de fazer o relatorio de cedulas
	//Percorre a matriz para retornar uma string formatada das cédulas e quantidades disponíveis
	public String pegaRelatorioCedulas() {
		String resposta = "";
		for (int i = 0; i < cedulas.length; i++) {
			resposta += "Nota: " + cedulas[i][0] + " | QTD: " + cedulas[i][1] + "\n";
		}
		return resposta;
	}
	
	

	//logica de pega o valor total disponivel no caixa eletronico
	public String pegaValorTotalDisponivel() {
		String resposta = "";
		int total = 0;
		for (int i = 0; i < cedulas.length; i++) {
			total += cedulas[i][0]*cedulas[i][1];
		}
		resposta += total;
		return resposta;
	}

	
	
	// logica de fazer a reposicao de cedulas e criar uma mensagem //(resposta)ao
	// usuario
	/*
	 * O método percorre a matriz para verificar se encontra alguma cédula
	 * equivalente à dada pelo usuário, caso encontre, a quantidade é incrementada
	 */
	public String reposicaoCedulas(Integer cedula, Integer quantidade) {
		String resposta;
		if (quantidade <= 0) {
			resposta = "Quantidade inválida!";
			return resposta;
		}
		for (int i = 0; i < cedulas.length; i++) {
				if (cedulas [i][0] == cedula) {
					cedulas[i][1] += quantidade;
					resposta = "Reposição realizada!";
					return resposta;
				}
		
		}
		
		resposta = "Cédula inválida!";
		return resposta;
	}

	
	
	public String sacar(Integer valor) {
		String resposta = "";
//logica de sacar do caixa eletronico e criar um mensagem(resposta) ao // usuario
		return resposta;
	}

	public String armazenaCotaMinima(Integer minimo) {
		String resposta = "";
//logica de armazenar a cota minima para saque e criar um //mensagem(resposta)ao usuario
		return resposta;
	}

	public static void main(String arg[]) {
		CaixaEletronico caixa = new CaixaEletronico();
		caixa.reposicaoCedulas(100, 100);
		
		System.out.println(caixa.pegaRelatorioCedulas());
		System.out.println(caixa.pegaValorTotalDisponivel());
		
		GUI janela = new GUI(caixa);
		janela.setVisible(true);
	}
}