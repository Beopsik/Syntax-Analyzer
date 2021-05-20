package lexical_analyzer.dfa;

//Class that stores information about Token
public class Token {
    //variable about token name
    private String key;

    //variable about lexeme
    private String value = "";

    public Token() {
    }

    //Clear lexeme
    public void tokenClear() {
        this.value = "";
    }

    //Concatenate input with lexeme
    public void addValue(char ch) {
        value += ch;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
