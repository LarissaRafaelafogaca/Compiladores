package br.com.facvest.controller;

import java.util.*;
import br.com.facvest.model.Delimitadores;
import br.com.facvest.model.Operadores;
import br.com.facvest.model.PalavrasReservadas;

public class AnaliseLexica {

    public String analisar(String codigoFonte) {
        StringBuilder log = new StringBuilder();
        int numLinha = 0;

        // Remove comentários de bloco (/* ... */), incluindo múltiplas linhas
        codigoFonte = codigoFonte.replaceAll("(?s)/\\*.*?\\*/", "");

        String[] linhas = codigoFonte.split("\n");

        for (String linha : linhas) {
            numLinha++;

            // Remove comentários de linha
            if (linha.contains("//")) {
                linha = linha.substring(0, linha.indexOf("//"));
            }

            List<String> tokens = tokenize(linha);

            for (String token : tokens) {
                token = token.trim();
                if (token.isEmpty()) continue;

                // Tipos de token
                if (isInteger(token)) {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", INTEIRO)\n");
                } else if (isFloat(token)) {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", REAL)\n");
                } else if (Arrays.asList(Operadores.OPERADORES).contains(token)) {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", OPERADOR)\n");
                } else if (Arrays.asList(Delimitadores.DELIMITADORES).contains(token)) {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", DELIMITADOR)\n");
                } else if (Arrays.asList(PalavrasReservadas.PALAVRAS_RESERVADAS).contains(token)) {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", PALAVRA RESERVADA)\n");
                } else if (token.startsWith("\"") && token.endsWith("\"") && token.length() > 1) {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", STRING)\n");
                } else {
                    log.append("Linha ").append(numLinha).append(": Token(").append(token).append(", IDENTIFICADOR)\n");
                }
            }
        }
        return log.toString();
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return str.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private List<String> tokenize(String linha) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();

        String[] operadoresCompostos = {"++", "--", "+=", "-=", "*=", "/=", "==", "!=", ">=", "<="};
        Set<String> opCompostosSet = new HashSet<>(Arrays.asList(operadoresCompostos));

        int i = 0;
        while (i < linha.length()) {
            char c = linha.charAt(i);

            // Trata strings
            if (c == '"') {
                token.setLength(0);
                token.append(c);
                i++;
                while (i < linha.length()) {
                    char next = linha.charAt(i);
                    token.append(next);
                    if (next == '"' && token.length() > 1) {
                        i++;
                        break;
                    }
                    i++;
                }
                tokens.add(token.toString());
                continue;
            }

            // Verifica operadores compostos
            if (i + 1 < linha.length()) {
                String doisChar = "" + c + linha.charAt(i + 1);
                if (opCompostosSet.contains(doisChar)) {
                    if (token.length() > 0) {
                        tokens.add(token.toString());
                        token.setLength(0);
                    }
                    tokens.add(doisChar);
                    i += 2;
                    continue;
                }
            }

            // Verifica delimitadores ou operadores simples
            if (Character.isWhitespace(c) || isDelimitadorOuOperador(c)) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                if (!Character.isWhitespace(c)) {
                    tokens.add(Character.toString(c));
                }
                i++;
                continue;
            }

            // Continua montando token atual
            token.append(c);
            i++;
        }

        if (token.length() > 0) {
            tokens.add(token.toString());
        }

        return tokens;
    }

    private boolean isDelimitadorOuOperador(char c) {
        String s = String.valueOf(c);
        return Arrays.asList(Operadores.OPERADORES).contains(s)
            || Arrays.asList(Delimitadores.DELIMITADORES).contains(s);
    }
}
