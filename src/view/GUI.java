package view;

import model.ICaixaEletronico;
import model.CaixaEletronico;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.SpringLayout;

public class GUI extends JFrame {

	private ICaixaEletronico caixa;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ICaixaEletronico caixa = new CaixaEletronico();
				GUI frame = new GUI(caixa);
				frame.setVisible(true);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public GUI(ICaixaEletronico caixa) {
		this.caixa = caixa;

		setTitle("Caixa Eletrônico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 689);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 30, 30));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		JLabel titulo = new JLabel("CAIXA ELETRÔNICO");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titulo.setForeground(Color.WHITE);

		Dimension tamanho = new Dimension(220, 40);

		JButton btnSaque = new JButton("Efetuar Saque");
		estilizarBotao(btnSaque);
		btnSaque.setPreferredSize(tamanho);
		btnSaque.addActionListener(e -> {
			Sacar frame = new Sacar(this.caixa);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
		});

		JButton btnRelatorio = new JButton("Relatório de Cédulas");
		estilizarBotao(btnRelatorio);
		btnRelatorio.setPreferredSize(tamanho);
		btnRelatorio.addActionListener(e ->
			JOptionPane.showMessageDialog(this, caixa.pegaRelatorioCedulas())
		);

		JButton btnValor = new JButton("Valor total disponível");
		estilizarBotao(btnValor);
		btnValor.setPreferredSize(tamanho);

		JButton btnReposicao = new JButton("Reposição de Cédulas");
		estilizarBotao(btnReposicao);
		btnReposicao.setPreferredSize(tamanho);

		JButton btnCota = new JButton("Cota Mínima");
		estilizarBotao(btnCota);
		btnCota.setPreferredSize(tamanho);

		JButton btnSair = new JButton("SAIR");
		estilizarBotao(btnSair);
		btnSair.setPreferredSize(tamanho);
		btnSair.setBackground(new Color(180, 40, 40));
		btnSair.addActionListener(e -> System.exit(0));

		layout.putConstraint(SpringLayout.NORTH, titulo, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titulo, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnSaque, 40, SpringLayout.SOUTH, titulo);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnSaque, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnRelatorio, 20, SpringLayout.SOUTH, btnSaque);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnRelatorio, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnValor, 20, SpringLayout.SOUTH, btnRelatorio);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnValor, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnReposicao, 20, SpringLayout.SOUTH, btnValor);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnReposicao, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnCota, 20, SpringLayout.SOUTH, btnReposicao);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnCota, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.SOUTH, btnSair, -20, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnSair, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		contentPane.add(titulo);
		contentPane.add(btnSaque);
		contentPane.add(btnRelatorio);
		contentPane.add(btnValor);
		contentPane.add(btnReposicao);
		contentPane.add(btnCota);
		contentPane.add(btnSair);
	}

	private void estilizarBotao(JButton btn) {
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(true);

		btn.setBackground(new Color(60, 63, 65));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		// hover
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btn.setBackground(new Color(75, 110, 175));
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				btn.setBackground(new Color(60, 63, 65));
			}
		});
	}
}