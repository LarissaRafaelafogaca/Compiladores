package br.com.facvest.controller;

import java.math.BigInteger;

import br.com.facvest.model.Delimitadores;
import br.com.facvest.model.Operadores;
import br.com.facvest.model.PalavrasReservadas;

public class AnaliseLexica {

	public String analisar(String codigoFonte) {
		StringBuilder log = new StringBuilder();
		int numLinha = 0;
		boolean analisado = false;
		String[] linhas = codigoFonte.split("\n");
		for (String linha : linhas) {
			numLinha++;
			String[] palavras = linha.split(" ");
			for (String palavra : palavras) {
				analisado = false;
				palavra = palavra.trim();
				if (palavra.isEmpty()) {
					continue;
				}
				try {
					Integer.parseInt(palavra);
					log.append("Linha ").append(numLinha).append(": ");
					log.append("Token(").append(palavra).append(", INTEIRO)");
					log.append("\n");
					continue;
				} catch (NumberFormatException e) {
				}
				
				try {
					Float.parseFloat(palavra);
					log.append("Linha ").append(numLinha).append(": ");
					log.append("Token(").append(palavra).append(", REAL)");
					log.append("\n");
					continue;
				} catch (NumberFormatException e) {
				}
				
				for (String operador : Operadores.OPERADORES) {
					if (palavra.equals(operador)) {
						log.append("Linha ").append(numLinha).append(": ");
						log.append("Token(").append(palavra).append(", OPERADOR)");
						log.append("\n");
						analisado = true;
						break;
					}
				}
				if (analisado) {
					continue;
				}
				
				for (String delimitador : Delimitadores.DELIMITADORES) {
					if (palavra.equals(delimitador)) {
						log.append("Linha ").append(numLinha).append(": ");
						log.append("Token(").append(palavra).append(", DELIMITADOR)");
						log.append("\n");
						analisado = true;
						break;
					}
				}
				
				if (analisado) {
					if (palavra.equals(Delimitadores.DELIMITADORES[Delimitadores.COMENTARIO_LINHA])) {
						break;
					}
					continue;
				}
				
				for (String palavraReservada : PalavrasReservadas.PALAVRAS_RESERVADAS) {
					if (palavra.equals(palavraReservada)) {
						log.append("Linha ").append(numLinha).append(": ");
						log.append("Token(").append(palavra).append(", PALAVRA RESERVADA)");
						log.append("\n");
						analisado = true;
						break;
					}
				}
				if (analisado) {
					continue;
				}
				
				log.append("Linha ").append(numLinha).append(": ");
				log.append("Token(").append(palavra).append(", IDENTIFICADOR)");
				log.append("\n");
				
			}
		}
		/*for (int i = 0; i < linhas.length; i++) {
			String linha = linhas[i];
			
		}*/
		return log.toString();
	}
}
