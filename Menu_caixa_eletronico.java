import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Conta {
    private String titular;
    private String cpf; 
    private String agencia;
    private String tipoConta;
    private int numeroConta;
    private double saldo;
    private double limite;
    private String senha;
    private boolean contaAtivada;
    private int tentativasAcesso;
    private final int MAX_TENTATIVAS = 3;
    private List<String> extrato;

    public Conta(String titular, String cpf, String agencia, String tipoConta, int numeroConta, double saldo, double limite, String senha) {
        this.titular = titular;
        this.cpf = cpf;
        this.agencia = agencia;    
        this.tipoConta = tipoConta;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.limite = limite;
        this.senha = senha;
        this.contaAtivada = true;
        this.extrato = new ArrayList<>();
        registrarTransacao("Abertura de conta. Saldo: R$ " + String.format("%.2f", saldo));
    }

    private void registrarTransacao(String mensagem) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.extrato.add("[" + agora.format(formato) + "] " + mensagem);
    }

    public boolean validarSenha(String senhaInformada) {
        if (!contaAtivada) return false;
        if (this.senha.equals(senhaInformada)) {
            this.tentativasAcesso = 0;
            return true;
        } else {
            this.tentativasAcesso++;
            if (tentativasAcesso >= MAX_TENTATIVAS) {
                this.contaAtivada = false;
                System.out.println("\n[ALERTA] Conta BLOQUEADA por segurança.");
            } else {
                System.out.println("\n[AVISO] Senha incorreta! Tentativas restantes: " + (MAX_TENTATIVAS - tentativasAcesso));
            }
            return false;
        }
    }

    public void depositar(double valor, String metodo) {
        if (valor > 0) {
            this.saldo += valor;
            registrarTransacao("Depósito (" + metodo + "): + R$ " + String.format("%.2f", valor));
        }
    }

    public boolean sacar(double valor) {
        double saldoDisponivel = this.saldo + this.limite;
        if (valor > 0 && valor <= saldoDisponivel) {
            this.saldo -= valor;
            registrarTransacao("Saque efetuado: - R$ " + String.format("%.2f", valor));
            return true;
        }
        return false;
    }

    public boolean transferir(Conta destino, double valor) {
        if (destino != null && this.sacar(valor)) {
            destino.depositar(valor, "Transferência de " + this.titular);
            registrarTransacao("PIX para " + destino.getTitular() + ": - R$ " + String.format("%.2f", valor));
            return true;
        }
        return false;
    }

    public void exibirExtrato() {
        System.out.println("\n--- EXTRATO: " + this.titular + " ---");
        for (String t : extrato) System.out.println(t);
        System.out.println("---------------------------------");
        System.out.println("Saldo Atual: R$ " + String.format("%.2f", this.saldo));
        System.out.println("Limite Disp: R$ " + String.format("%.2f", getLimiteDisponivel()));
    }

    public double getLimiteDisponivel() {
        return (this.saldo < 0) ? (this.limite + this.saldo) : this.limite;
    }

    // Getters
    public int getNumeroConta() { return numeroConta; }
    public String getTitular() { return titular; }
    public String getCpf() { return cpf; }
    public boolean isContaAtivada() { return contaAtivada; }
    public double getSaldo() { return saldo; }
    public double getLimite() { return limite; }
}
