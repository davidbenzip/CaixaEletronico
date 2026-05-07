import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CaixaEletronico caixa = new CaixaEletronico();
        List<Conta> banco = new ArrayList<>();

        // Banco de Dados Simulado
        banco.add(new Conta("Vitor Ferreira", "123456789", "0001", "Corrente", 101, 1500.0, 500.0, "1234"));
        banco.add(new Conta("Vanessa Silva", "987654321", "0001", "Corrente", 202, 200.0, 100.0, "4321"));

        // O sistema define a cota mínima nos bastidores (Ex: R$ 2,00)
        caixa.armazenaCotaMinima(2); 

        while (true) {
            System.out.println("\n=====================================");
            System.out.println("    BEM-VINDO AO CAIXA ELETRÔNICO    ");
            System.out.println("=====================================");

            // 1. Identificação via CPF
            System.out.print("Digite o seu CPF (ou '0' para desligar): ");
            String cpfDigitado = sc.nextLine().trim();
            
            if (cpfDigitado.equals("0")) {
                System.out.println("Encerrando o terminal. Até logo!");
                break;
            }

            Conta logada = null;
            for (Conta c : banco) {
                if (c.getCpf().equals(cpfDigitado)) {
                    logada = c;
                    break;
                }
            }

            if (logada == null) {
                System.out.println("\n[ERRO] CPF não encontrado em nosso sistema.");
                continue; 
            }
            
            if (!logada.isContaAtivada()) {
                System.out.println("\n[AVISO] Conta BLOQUEADA. Procure sua agência.");
                continue;
            }

            // 2. Autenticação
            System.out.print("Olá, " + logada.getTitular() + ". Digite sua SENHA: ");
            String senhaDigitada = sc.nextLine().trim();
            
            if (!logada.validarSenha(senhaDigitada)) {
                continue;
            }

            // 3. Menu Principal
            int opcao = 0;
            do {
                try {
                    System.out.println("\n=== MENU PRINCIPAL ===");
                    System.out.println("1 - Ver Saldo");
                    System.out.println("2 - Sacar");
                    System.out.println("3 - Depositar (Dinheiro ou Cheque)");
                    System.out.println("4 - Transferir (PIX)");
                    System.out.println("5 - Ver Extrato");
                    System.out.println("6 - Sair da Conta");
                    System.out.print("Escolha uma opção: ");
                    
                    opcao = Integer.parseInt(sc.nextLine().trim());

                    switch (opcao) {
                        case 1:
                            System.out.println("\n--- SALDO ---");
                            System.out.println("Saldo da Conta:    R$ " + String.format("%.2f", logada.getSaldo()));
                            System.out.println("Limite Disponível: R$ " + String.format("%.2f", logada.getLimiteDisponivel()));
                            break;

                        case 2:
                            System.out.print("\nDigite o valor para sacar (ou 0 para voltar): R$ ");
                            int vSaque = Integer.parseInt(sc.nextLine().trim());
                            
                            if (vSaque == 0) {
                                System.out.println("Operação cancelada.");
                                break;
                            }
                            
                            if (logada.getSaldo() + logada.getLimiteDisponivel() >= vSaque) {
                                String resultadoFisico = caixa.sacarNotasFisicas(vSaque);
                                if (!resultadoFisico.startsWith("ERRO")) {
                                    logada.sacar(vSaque);
                                    System.out.println("\n" + resultadoFisico);
                                } else {
                                    System.out.println("\n" + resultadoFisico); 
                                }
                            } else { 
                                System.out.println("\n[ERRO] Saldo e limite insuficientes."); 
                            }
                            break;

                        case 3:
                            System.out.println("\nTipo de Depósito: 1-Dinheiro | 2-Cheque | 0-Voltar");
                            System.out.print("Opção: ");
                            int tipoDep = Integer.parseInt(sc.nextLine().trim());
                            
                            if (tipoDep == 0) {
                                System.out.println("Operação cancelada.");
                                break;
                            }
                            
                            if (tipoDep == 1) {
                                System.out.print("Valor do Depósito em Dinheiro (ou 0 para voltar): R$ ");
                                double valorDep = Double.parseDouble(sc.nextLine().replace(",", ".").trim());
                                if (valorDep == 0) {
                                    System.out.println("Operação cancelada.");
                                    break;
                                }
                                logada.depositar(valorDep, "Dinheiro");
                                System.out.println("Depósito realizado com sucesso!");
                                
                            } else if (tipoDep == 2) {
                                System.out.println("\n--- DEPÓSITO EM CHEQUE ---");
                                // 1. Primeiro pede o número do cheque
                                System.out.print("Digite o NÚMERO DO CHEQUE (ou 0 para voltar): ");
                                String numCheque = sc.nextLine().trim();
                                
                                if (numCheque.equals("0")) {
                                    System.out.println("Operação cancelada.");
                                    break;
                                }

                                System.out.println("\n--- VALIDAÇÃO DE SEGURANÇA ---");
                                System.out.println("Por favor, confirme os dados da sua conta para o depósito.");
                                System.out.print("Confirme o NÚMERO DA SUA CONTA (ou 0 para voltar): ");
                                int confNum = Integer.parseInt(sc.nextLine().trim());
                                
                                if (confNum == 0) {
                                    System.out.println("Operação cancelada.");
                                    break;
                                }
                                
                                System.out.print("Confirme o seu CPF: ");
                                String confCpf = sc.nextLine().trim();
                                System.out.print("Confirme o seu Nome completo: ");
                                String confNome = sc.nextLine().trim();

                                if (confNum == logada.getNumeroConta() && confCpf.equals(logada.getCpf()) && confNome.equalsIgnoreCase(logada.getTitular())) {
                                    System.out.print("Digite sua SENHA para assinar o depósito: ");
                                    if (logada.validarSenha(sc.nextLine().trim())) {
                                        System.out.print("Valor do Cheque: R$ ");
                                        double valorCheque = Double.parseDouble(sc.nextLine().replace(",", ".").trim());
                                        
                                        // 2. Envia o número do cheque para ficar registrado no extrato bancário
                                        logada.depositar(valorCheque, "Cheque nº " + numCheque);
                                        System.out.println("Cheque processado e depositado com sucesso!");
                                    } else { 
                                        System.out.println("[ERRO] Senha incorreta. Operação cancelada."); 
                                    }
                                } else { 
                                    System.out.println("[ERRO] Os dados informados não conferem com a sua conta. Operação cancelada."); 
                                }
                            } else {
                                System.out.println("Opção de depósito inválida.");
                            }
                            break;

                        case 4:
                            System.out.println("\n--- ÁREA PIX ---");
                            System.out.print("Digite o NÚMERO DA CONTA de destino (ou 0 para voltar): ");
                            int numDestino = Integer.parseInt(sc.nextLine().trim());
                            
                            if (numDestino == 0) {
                                System.out.println("Operação cancelada.");
                                break;
                            }
                            
                            Conta contaDest = null;
                            for (Conta b : banco) {
                                if (b.getNumeroConta() == numDestino) {
                                    contaDest = b;
                                    break;
                                }
                            }
                            
                            if (contaDest == null) {
                                System.out.println("[ERRO] Conta destino não encontrada.");
                            } else if (contaDest == logada) {
                                System.out.println("[ERRO] Você não pode transferir para si mesmo.");
                            } else {
                                System.out.println("Destinatário: " + contaDest.getTitular());
                                System.out.print("Valor da transferência PIX (ou 0 para cancelar): R$ ");
                                double vPix = Double.parseDouble(sc.nextLine().replace(",", ".").trim());
                                
                                if (vPix == 0) {
                                    System.out.println("Operação cancelada.");
                                    break;
                                }
                                
                                if (logada.transferir(contaDest, vPix)) {
                                    System.out.println("PIX realizado com sucesso!");
                                }
                            }
                            break;

                        case 5: 
                            logada.exibirExtrato(); 
                            break;
                            
                        case 6:
                            System.out.println("\nObrigado por usar nosso banco. Retire seu cartão!");
                            break;
                            
                        default:
                            System.out.println("\nOpção inválida! Tente novamente.");
                    }
                } catch (Exception e) { 
                    System.out.println("\n[ERRO] Entrada inválida. Por favor, digite os valores corretamente."); 
                    opcao = 0; 
                }
            } while (opcao != 6);
        }
        sc.close();
    }
}
