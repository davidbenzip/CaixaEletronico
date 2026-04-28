package view;

import model.ICaixaEletronico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Sacar extends JFrame {

	private ICaixaEletronico caixa;
	private JPanel contentPane;
	private JTextField textField;

	public Sacar(ICaixaEletronico caixa) {
		this.caixa = caixa;

		setTitle("Saque");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 545, 339);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 30, 30));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		JLabel titulo = new JLabel("SAQUE");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setForeground(Color.WHITE);

		JLabel label = new JLabel("Valor:");
		label.setForeground(Color.WHITE);

		textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JButton btnSacar = new JButton("Sacar");
		estilizarBotao(btnSacar);
		btnSacar.setBackground(new Color(0, 150, 80));

		btnSacar.addActionListener(e -> {
			try {
				int valor = Integer.parseInt(textField.getText());
				caixa.sacar(valor);
				JOptionPane.showMessageDialog(this, "Saque realizado!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro no saque!");
			}
		});

		JButton btnSair = new JButton("Fechar");
		estilizarBotao(btnSair);
		btnSair.setBackground(new Color(180, 40, 40));
		btnSair.addActionListener(e -> dispose());

		layout.putConstraint(SpringLayout.NORTH, titulo, 10, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titulo, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.NORTH, label, 40, SpringLayout.SOUTH, titulo);
		layout.putConstraint(SpringLayout.WEST, label, 50, SpringLayout.WEST, contentPane);

		layout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, label);
		layout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.EAST, textField, -50, SpringLayout.EAST, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnSacar, 30, SpringLayout.SOUTH, textField);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnSacar, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		layout.putConstraint(SpringLayout.SOUTH, btnSair, -10, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnSair, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		contentPane.add(titulo);
		contentPane.add(label);
		contentPane.add(textField);
		contentPane.add(btnSacar);
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