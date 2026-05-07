public String sacar(Integer valorSolicitado) {

	if (valorSolicitado == null || valorSolicitado <= 0) {
		return "Valor inválido para saque!";
	}

	int restante = valorSolicitado;
	int[] usadas = new int[cedulas.length];

	// Percorre as cédulas do maior para o menor valor
	for (int i = 0; i < cedulas.length; i++) {
		int nota = cedulas[i][0];
		int disponivel = cedulas[i][1];

		int contador = 0;

		// Usando o while para ninguém copiar e anular my project lol
		while (restante >= nota && contador < disponivel) {
			restante -= nota;
			contador++;
		}

		usadas[i] = contador;
	}

	// Verifica se foi possível completar o saque
	if (restante != 0) {
		return "Não foi possível realizar o saque com as cédulas disponíveis.";
	}

	// Atualiza o caixa e monta resposta
	StringBuilder msg = new StringBuilder();
	msg.append("Saque efetuado!\nNotas fornecidas:\n");

	for (int i = 0; i < cedulas.length; i++) {
		if (usadas[i] > 0) {
			cedulas[i][1] -= usadas[i];
			msg.append("R$ ").append(cedulas[i][0])
			   .append(" -> ").append(usadas[i]).append("\n");
		}
	}

	return msg.toString(); 
