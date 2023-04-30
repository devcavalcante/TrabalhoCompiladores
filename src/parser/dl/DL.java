package src.parser.dl;

import src.parser.Parser;

public class DL {
	public static void main(String[] args) {
        String input = "a<3";
        Parser parser = new Parser();
        boolean syntaxCorrect = parser.parse(input);

        if (syntaxCorrect) {
            System.out.println("A sintaxe está correta!");
        } else {
            System.out.println("Erro de sintaxe encontrado.");
        }
	}
}