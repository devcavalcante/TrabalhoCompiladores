package src.parser;

import java.util.regex.Pattern;

public class Parser implements IParser{
    private String input;
    private int lookahead;

    public Parser() {
        this.input = "";
        this.lookahead = 0;
    }

    @Override
    public char lookahead() {
        if (lookahead >= input.length()) {
            return EOF;
        }
        return input.charAt(lookahead);
    }

    @Override
    public char next() {
        while (lookahead < input.length() && Character.isWhitespace(input.charAt(lookahead))) {
            lookahead++;
        }
        if (lookahead >= input.length()) {
            return EOF;
        }
        char nextChar = input.charAt(lookahead);
        lookahead++;
        return nextChar;
    }

    @Override
    public void match(char c) {
        if (lookahead() == c) {
            next();
        } else {
            error("Erro de sintaxe: esperava '" + c + "' mas encontrou '" + lookahead() + "'");
        }
    }

    @Override
    public void error(String msg) {
        int col = (lookahead == 0) ? 1 : lookahead;
        throw new RuntimeException("Syntax error: " + msg + " na coluna " + col);
    }

    @Override
    public boolean parse(String string) {
        this.input = string;
        lookahead = 0;
        try{
            expr();
            return true;
        } catch (RuntimeException e) {
            System.err.println(e);
            return false;
        }
    }

    private void expr() {
        term();
        exprPrime();
    }

    private void exprPrime() {
        if (lookahead() == '+') {
            match('+');
            term();
            exprPrime();
        } else if (lookahead() == '-') {
            match('-');
            term();
            exprPrime();
        }
    }

    private void term() {
        unary();
        termPrime();
    }

    private void termPrime() {
        if (lookahead() == '*') {
            match('*');
            unary();
            termPrime();
        } else if (lookahead() == '/') {
            match('/');
            unary();
            termPrime();
        }
    }

    private void unary() {
        switch(lookahead()){
            case '+':
                match('+');
                unary();
                break;
            case '-':
                match('-');
                unary();
                break;
            case '<':
                match('<');
                id();
                break;
            case '>':
                match('>');
                id();
                break;
            default:
                post();
                break;
        }
    }

    private void post() {
        switch (lookahead()){
            case '<':
                id();
                match('<');
                break;
            case '>':
                id();
                match('>');
                break;
            default:
                factor();
                break;            
        }
    }

    private void factor() {
        if (lookahead() == '(') {
            match('(');
            expr();
            match(')');
        } else if (Pattern.matches("\\d", Character.toString(lookahead()))) {
            match(lookahead());
            if (lookahead() == '>' || lookahead() == '<') {
                error("Esperava um identificador mas encontrou '" + lookahead() + "'");
            }
        } else {
            id();
        }
    }

    private void id() {
        if (Pattern.matches("[a-z]", Character.toString(lookahead()))) {
            match(lookahead());
        } else {
            error("erro");
        }
    }
}
