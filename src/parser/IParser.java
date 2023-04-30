package src.parser;
public interface IParser {
    /*
    * Constante que simboliza que o consumo da string terminou
    */
    public static final char EOF = (char)-1;

    /*
    * Método que retorna o token atual. Ele retorna EOF caso
    * a string já tenha sido toda consumida.
    */
    public char lookahead();

    /*
    * Faz o papel de lexer. A cada chamada retorna o próximo
    * caractere (“token”) que não é um espaço em branco.
    */
    public char next();

    /*
    * Verifica se o lookahead combina com um dado char. Ele
    * avança para o próximo caractere caso combine, caso
    * contrário imprime um erro.
    */
    public void match(char c);

    /*
    * Imprime uma mensagem de erro, indicando a coluna onde o
    * erro ocorreu.
    */
    public void error(String msg);
    
    /*
    * Método que verifica a sintaxe de uma dada string,
    * retornando true caso ela seja aceita. Esse chama o método
    * que representa o não-terminal inicial da gramática.
    */
    public boolean parse(String string);
   }