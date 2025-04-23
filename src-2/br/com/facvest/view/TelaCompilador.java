package br.com.facvest.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.facvest.controller.AnaliseLexica;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public class TelaCompilador extends JFrame {
	private JScrollPane scpFonte;
	private JTextArea txtFonte;
	private JScrollPane scpLog;
	private JTextArea txtLog;
	private JButton btnLexica;
	public TelaCompilador() {
		setTitle("Compilador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		getContentPane().add(getScpFonte());
		getContentPane().add(getScpLog());
		getContentPane().add(getBtnLexica());
		setVisible(true);
	}
	private JScrollPane getScpFonte() {
		if (scpFonte == null) {
			scpFonte = new JScrollPane();
			scpFonte.setBounds(42, 30, 450, 400);
			scpFonte.setViewportView(getTxtFonte());
		}
		return scpFonte;
	}
	private JTextArea getTxtFonte() {
		if (txtFonte == null) {
			txtFonte = new JTextArea();
		}
		return txtFonte;
	}
	private JScrollPane getScpLog() {
		if (scpLog == null) {
			scpLog = new JScrollPane();
			scpLog.setBounds(42, 440, 450, 110);
			scpLog.setViewportView(getTxtLog());
		}
		return scpLog;
	}
	private JTextArea getTxtLog() {
		if (txtLog == null) {
			txtLog = new JTextArea();
			txtLog.setFont(new Font(Font.DIALOG, 0, 12));
			txtLog.setEnabled(false);
		}
		return txtLog;
	}
	private JButton getBtnLexica() {
		if (btnLexica == null) {
			btnLexica = new JButton("An\u00E1lise L\u00E9xica");
			btnLexica.setMnemonic(KeyEvent.VK_L);
			btnLexica.setBounds(502, 32, 141, 23);
			btnLexica.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
			btnLexica.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String codigoFonte = txtFonte.getText();
					AnaliseLexica al = new AnaliseLexica();
					String log = al.analisar(codigoFonte);
					//String log = new AnaliseLexica().analisa(txtFonte.getText());
					txtLog.setText(log);
				}
			});
		}
		return btnLexica;
	}
}










