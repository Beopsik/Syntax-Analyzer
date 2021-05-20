package lexical_analyzer.dfa;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DFA {
    private final DFATable dfaTable = new DFATable();

    //Init tokens' transition table
    private final JSONArray arithmeticOperatorDFATable = dfaTable.arithmeticOperatorDFATable();
    private final JSONArray assignmentOperatorDFATable = dfaTable.assignmentOperatorDFATable();
    private final JSONArray comparisonOperatorDFATable = dfaTable.ComparisonOperatorDFATable();
    private final JSONArray terminateSymboleDFATable = dfaTable.terminateSymbolDFATable();
    private final JSONArray lParenDFATable = dfaTable.lParenDFATable();
    private final JSONArray rParenDFATable = dfaTable.rParenDFATable();
    private final JSONArray lBraceDFATable = dfaTable.lBraceDFATable();
    private final JSONArray rBraceDFATAble = dfaTable.rBraceDFATable();
    private final JSONArray lBranketDFATable = dfaTable.lBranketDFATable();
    private final JSONArray rBranketDFATable = dfaTable.rBranketDFATable();
    private final JSONArray commaDFATable = dfaTable.commaDFATable();
    private final JSONArray whiteSpaceDFATable = dfaTable.whiteSpaceDFATable();
    private final JSONArray singleCharacterDFATable = dfaTable.singleCharacterDFATable();
    private final JSONArray literalStringDFATable = dfaTable.literalStringDFATable();
    private final JSONArray signedIntegerDFATable = dfaTable.signedIntegerDFATable();
    private final JSONArray keywordDFATable = dfaTable.keywordDFATable();
    private final JSONArray variableTypeDFATable = dfaTable.variableTypeDFATable();
    private final JSONArray booleanStringDFATable = dfaTable.booleanStringDFATable();
    private final JSONArray indentifierDFATable = dfaTable.identifierDFATable();

    //Set tokens' priority
    private final static int ARITHMETICOPERATOR = 0;
    private final static int ASSIGNMENTOPERATOR = 1;
    private final static int COMPARISONOPERATOR = 2;
    private final static int TERMINATEOPERATOR = 3;
    private final static int LPAREN = 4;
    private final static int RPAREN = 5;
    private final static int LBRACE = 6;
    private final static int RBRACE = 7;
    private final static int LBRANKET = 8;
    private final static int RBRANKET = 9;
    private final static int COMMA = 10;
    private final static int SINGLECAHRACTER = 11;
    private final static int LITERALSTRING = 12;
    private final static int WHITESPACE = 13;
    private final static int SIGNEDINTEGER = 14;
    private final static int KEYWORD = 15;
    private final static int VARIABLETYPE = 16;
    private final static int BOOLEANSTRING = 17;
    private final static int IDENTIFIER = 18;

    //Declare String variable for storing the last recognized token type
    private String previousToken = "";

    //The number of tokens
    private final static int TOKEN_NUM = 19;

    //Declare list for storing the recognized tokens
    private final List<Token> tokenList = new ArrayList<>();

    private final Token[] token = new Token[TOKEN_NUM];
    private final State[] state = new State[TOKEN_NUM];

    private final String inputStr;
    private final int startPosition;
    public DFA(String inputStr, int postion) {
        this.inputStr = inputStr;
        this.startPosition = postion;
    }

    //Set tokens' key
    public void tokenInit() {
        token[ARITHMETICOPERATOR].setKey("ARITHMETICOPERATOR");
        token[COMPARISONOPERATOR].setKey("COMPARISONOPERATOR");
        token[ASSIGNMENTOPERATOR].setKey("ASSIGNMENTOPERATOR");
        token[TERMINATEOPERATOR].setKey("TERMINATESYMBOL");
        token[LPAREN].setKey("LPAREN");
        token[RPAREN].setKey("RPAREN");
        token[LBRACE].setKey("LBRACE");
        token[RBRACE].setKey("RBRACE");
        token[LBRANKET].setKey("LBRANKET");
        token[RBRANKET].setKey("RBRANKET");
        token[COMMA].setKey("COMMA");
        token[WHITESPACE].setKey("WHITESPACE");
        token[SINGLECAHRACTER].setKey("SINGLECHARACTER");
        token[LITERALSTRING].setKey("LITERALSTRING");
        token[SIGNEDINTEGER].setKey("SIGNEDINTEGER");
        token[KEYWORD].setKey("KEYWORD");
        token[VARIABLETYPE].setKey("VARIABLETYPE");
        token[BOOLEANSTRING].setKey("BOOLEANSTRING");
        token[IDENTIFIER].setKey("IDENTIFIER");
    }

    public void run() throws FileNotFoundException {

        //for making output text file
        PrintWriter pw = new PrintWriter("./output.txt");

        for (int i = 0; i < TOKEN_NUM; i++) {
            token[i] = new Token();
            state[i] = new State();
        }

        tokenInit();

        for (int i = startPosition; i < inputStr.length(); i++) {

            //Check all transition tables to see if input is recognizable
            arithmeticOperatorDFA(i);
            assignmentOperatorDFA(i);
            comparisonOperatorDFA(i);
            terminateSymbolDFA(i);
            lParenDFA(i);
            rParenDFA(i);
            lBraceDFA(i);
            rBraceDFA(i);
            lBranketDFA(i);
            rBranketDFA(i);
            commaDFA(i);
            singleCharacterDFA(i);
            literalStringDFA(i);
            whiteSpaceDFA(i);
            signedIntegerDFA(i);
            keywordDFA(i);
            variableTypeDFA(i);
            booleanStringDFA(i);
            identifierDFA(i);

            //The variable for checking the token list if it is cleared once
            boolean isNotClearTokenListEver = true;

            //The variable for checking the input if it is valid for any DFA
            boolean isRecognizeDFA = false;


            for (int j = 0; j < TOKEN_NUM; j++) {

                //Check the input is valid in DFA
                if (state[j].getisRunningState()) {

                    //if the token list is not cleared once, token list should be cleared
                    if (isNotClearTokenListEver) {
                        tokenList.clear();
                        isNotClearTokenListEver = false;
                        isRecognizeDFA = true;
                    }
                    //Check whether the state of DFA is terminal state
                    if (state[j].getIsFinalState()) {
                        //Check whether '-' input is signed integer or arithmetic operator when classifying token
                        if (token[j].getValue().equals("-") && (previousToken.equals("IDENTIFIER") || previousToken.equals("SIGNEDINTEGER"))) {
                            tokenList.add(token[j]);
                            state[SIGNEDINTEGER].setisRunningState(false);
                        } else {
                            tokenList.add(token[j]);
                        }
                    }
                }
            }

            //if the input is not valid for any DFA
            if (!isRecognizeDFA) {
                if (tokenList.isEmpty()) {
                    System.out.println("Occured error at " + inputStr.charAt(i));
                    return;
                } else {
                    Token result = tokenList.get(0);
                    previousToken = result.getKey();

                    if(result.getKey()!="WHITESPACE")
                        pw.println("<" + result.getKey() + ", " + result.getValue() + ">");

                    //Initialize current state to start state and clear the value of token
                    for (int k = 0; k < TOKEN_NUM; k++) {
                        token[k].tokenClear();
                        state[k].clear();
                    }
                    i--;
                }
                //Check the input is the last input
            } else if (i == inputStr.length() - 1) {
                if (tokenList.isEmpty()) {
                    pw.println("Occured error at " + inputStr.charAt(i));
                    return;
                } else {
                    Token result = tokenList.get(0);
                    previousToken = result.getKey();

                    if(result.getKey()!="WHITESPACE")
                        pw.println("<" + result.getKey() + ", " + result.getValue() + ">");
                }
            }
        }

        pw.close();
    }

    /*
        How to DFA function works
        1. Check if DFA is recognizable
            Ex) if (!state[ARITHMETICOPERATOR].getisRunningState())
        2. If DFA is recognizable, Get the input according to position
            Ex)  char ch = inputStr.charAt(position);
        3. Get the row data in transition table according to current state
            EX) JSONObject transition = (JSONObject) arithmeticOperatorDFATable.get(state[ARITHMETICOPERATOR].getStateLocation());
        4. Update current state to next state according to row data which is taken before step
            Ex) state[ARITHMETICOPERATOR].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
        5. Check the current state is terminal state
            Ex) if (!state[ARITHMETICOPERATOR].getIsFinalState() && state[ARITHMETICOPERATOR].getStateLocation() == 1)
        6. Catch exception when the input is not valid in DFA
            Ex) catch (NullPointerException e)
        7. Concatenate input with token's value
            Ex)token[ARITHMETICOPERATOR].addValue(ch);

        Usually follow the steps above, and additional functions are explained in each function.
    */
    public void arithmeticOperatorDFA(int position) {
        if (!state[ARITHMETICOPERATOR].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) arithmeticOperatorDFATable.get(state[ARITHMETICOPERATOR].getStateLocation());
        try {
            state[ARITHMETICOPERATOR].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[ARITHMETICOPERATOR].getIsFinalState() && state[ARITHMETICOPERATOR].getStateLocation() == 1)
                state[ARITHMETICOPERATOR].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[ARITHMETICOPERATOR].setisRunningState(false);
            return;
        }
        token[ARITHMETICOPERATOR].addValue(ch);
    }

    public void comparisonOperatorDFA(int position) {
        if (!state[COMPARISONOPERATOR].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) comparisonOperatorDFATable.get(state[COMPARISONOPERATOR].getStateLocation());
        try {
            state[COMPARISONOPERATOR].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[COMPARISONOPERATOR].getIsFinalState() && (state[COMPARISONOPERATOR].getStateLocation() == 3
                    || state[COMPARISONOPERATOR].getStateLocation() == 4 || state[COMPARISONOPERATOR].getStateLocation() == 5))
                state[COMPARISONOPERATOR].setIsFinalState(true);

        } catch (NullPointerException e) {
            state[COMPARISONOPERATOR].setisRunningState(false);
            return;
        }
        token[COMPARISONOPERATOR].addValue(ch);
    }

    public void assignmentOperatorDFA(int position) {
        if (!state[ASSIGNMENTOPERATOR].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) assignmentOperatorDFATable.get(state[ASSIGNMENTOPERATOR].getStateLocation());
        try {
            state[ASSIGNMENTOPERATOR].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[ASSIGNMENTOPERATOR].getIsFinalState() && state[ASSIGNMENTOPERATOR].getStateLocation() == 1)
                state[ASSIGNMENTOPERATOR].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[ASSIGNMENTOPERATOR].setisRunningState(false);
            return;
        }
        token[ASSIGNMENTOPERATOR].addValue(ch);
    }

    public void terminateSymbolDFA(int position) {
        if (!state[TERMINATEOPERATOR].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) terminateSymboleDFATable.get(state[TERMINATEOPERATOR].getStateLocation());
        try {
            state[TERMINATEOPERATOR].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[TERMINATEOPERATOR].getIsFinalState() && state[TERMINATEOPERATOR].getStateLocation() == 1)
                state[TERMINATEOPERATOR].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[TERMINATEOPERATOR].setisRunningState(false);
            return;
        }
        token[TERMINATEOPERATOR].addValue(ch);
    }

    public void lParenDFA(int position) {
        if (!state[LPAREN].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) lParenDFATable.get(state[LPAREN].getStateLocation());
        try {
            state[LPAREN].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[LPAREN].getIsFinalState() && state[LPAREN].getStateLocation() == 1)
                state[LPAREN].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[LPAREN].setisRunningState(false);
            return;
        }
        token[LPAREN].addValue(ch);
    }

    public void rParenDFA(int position) {
        if (!state[RPAREN].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) rParenDFATable.get(state[RPAREN].getStateLocation());
        try {
            state[RPAREN].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[RPAREN].getIsFinalState() && state[RPAREN].getStateLocation() == 1)
                state[RPAREN].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[RPAREN].setisRunningState(false);
            return;
        }
        token[RPAREN].addValue(ch);
    }

    public void lBraceDFA(int position) {
        if (!state[LBRACE].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) lBraceDFATable.get(state[LBRACE].getStateLocation());
        try {
            state[LBRACE].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[LBRACE].getIsFinalState() && state[LBRACE].getStateLocation() == 1)
                state[LBRACE].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[LBRACE].setisRunningState(false);
            return;
        }
        token[LBRACE].addValue(ch);
    }

    public void rBraceDFA(int position) {
        if (!state[RBRACE].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) rBraceDFATAble.get(state[RBRACE].getStateLocation());
        try {
            state[RBRACE].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[RBRACE].getIsFinalState() && state[RBRACE].getStateLocation() == 1)
                state[RBRACE].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[RBRACE].setisRunningState(false);
            return;
        }
        token[RBRACE].addValue(ch);
    }

    public void lBranketDFA(int position) {
        if (!state[LBRANKET].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) lBranketDFATable.get(state[LBRANKET].getStateLocation());
        try {
            state[LBRANKET].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[LBRANKET].getIsFinalState() && state[LBRANKET].getStateLocation() == 1)
                state[LBRANKET].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[LBRANKET].setisRunningState(false);
            return;
        }
        token[LBRANKET].addValue(ch);
    }

    public void rBranketDFA(int position) {
        if (!state[RBRANKET].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) rBranketDFATable.get(state[RBRANKET].getStateLocation());
        try {
            state[RBRANKET].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[RBRANKET].getIsFinalState() && state[RBRANKET].getStateLocation() == 1)
                state[RBRANKET].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[RBRANKET].setisRunningState(false);
            return;
        }
        token[RBRANKET].addValue(ch);
    }

    public void commaDFA(int position) {
        if (!state[COMMA].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) commaDFATable.get(state[COMMA].getStateLocation());
        try {
            state[COMMA].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[COMMA].getIsFinalState() && state[COMMA].getStateLocation() == 1)
                state[COMMA].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[COMMA].setisRunningState(false);
            return;
        }
        token[COMMA].addValue(ch);
    }

    public void whiteSpaceDFA(int position) {
        if (!state[WHITESPACE].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) whiteSpaceDFATable.get(state[WHITESPACE].getStateLocation());
        try {
            state[WHITESPACE].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[WHITESPACE].getIsFinalState() && state[WHITESPACE].getStateLocation() == 1)
                state[WHITESPACE].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[WHITESPACE].setisRunningState(false);
            return;
        }
        token[WHITESPACE].addValue(ch);
    }

    public void singleCharacterDFA(int position) {
        if (!state[SINGLECAHRACTER].getisRunningState())
            return;

        char ch = inputStr.charAt(position);
        String symbolType;

        //input classification
        if (ch == '\'')
            symbolType = "'";
        else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
            symbolType = "letter";
        else if (ch >= '0' && ch <= '9')
            symbolType = "digit";
        else if (ch == ' ')
            symbolType = "blank";
        else {
            //Process when the input is not valid in DFA
            state[SINGLECAHRACTER].setisRunningState(false);
            return;
        }

        try {
            JSONObject transition = (JSONObject) singleCharacterDFATable.get(state[SINGLECAHRACTER].getStateLocation());
            state[SINGLECAHRACTER].setStateLocation(Integer.parseInt(transition.get(symbolType).toString()));
            if (!state[SINGLECAHRACTER].getIsFinalState() && state[SINGLECAHRACTER].getStateLocation() == 5)
                state[SINGLECAHRACTER].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[SINGLECAHRACTER].setisRunningState(false);
            return;
        }

        //For deleting single quote in output
        if (ch != '\'') {
            token[SINGLECAHRACTER].addValue(ch);
        }
    }

    public void literalStringDFA(int position) {
        if (!state[LITERALSTRING].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        //input classification
        String symbolType;
        if (ch == '\"')
            symbolType = "double quotes";
        else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
            symbolType = "letter";
        else if (ch >= '0' && ch <= '9')
            symbolType = "digit";
        else if (ch == ' ')
            symbolType = "blank";
        else {
            //Process when the input is not valid in DFA
            state[LITERALSTRING].setisRunningState(false);
            return;
        }
        try {
            JSONObject transition = (JSONObject) literalStringDFATable.get(state[LITERALSTRING].getStateLocation());
            state[LITERALSTRING].setStateLocation(Integer.parseInt(transition.get(symbolType).toString()));
            if (!state[LITERALSTRING].getIsFinalState() && state[LITERALSTRING].getStateLocation() == 5)
                state[LITERALSTRING].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[LITERALSTRING].setisRunningState(false);
            return;
        }
        //For deleting double quote in output
        if (ch != '\"') {
            token[LITERALSTRING].addValue(ch);
        }
    }

    public void signedIntegerDFA(int position) {
        if (!state[SIGNEDINTEGER].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        //input classification
        String symbolType;
        if ((ch >= '1' && ch <= '9') && (state[SIGNEDINTEGER].getStateLocation() == 0
                || state[SIGNEDINTEGER].getStateLocation() == 2))
            symbolType = "positive";
        else if ((ch >= '0' && ch <= '9') && (state[SIGNEDINTEGER].getStateLocation() == 1
                || state[SIGNEDINTEGER].getStateLocation() == 3))
            symbolType = "digit";
        else if (ch == '-')
            symbolType = "-";
        else if (ch == '0' && state[SIGNEDINTEGER].getStateLocation() == 0)
            symbolType = "0";
        else {
            //Process when the input is not valid in DFA
            state[SIGNEDINTEGER].setisRunningState(false);
            return;
        }

        try {
            JSONObject transition = (JSONObject) signedIntegerDFATable.get(state[SIGNEDINTEGER].getStateLocation());
            state[SIGNEDINTEGER].setStateLocation(Integer.parseInt(transition.get(symbolType).toString()));
            if (!state[SIGNEDINTEGER].getIsFinalState() && (state[SIGNEDINTEGER].getStateLocation() == 1
                    || state[SIGNEDINTEGER].getStateLocation() == 3 || state[SIGNEDINTEGER].getStateLocation() == 4))
                state[SIGNEDINTEGER].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[SIGNEDINTEGER].setisRunningState(false);
            return;
        }
        token[SIGNEDINTEGER].addValue(ch);
    }

    public void keywordDFA(int position) {
        if (!state[KEYWORD].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) keywordDFATable.get(state[KEYWORD].getStateLocation());
        try {
            state[KEYWORD].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[KEYWORD].getIsFinalState() && state[KEYWORD].getStateLocation() == 18)
                state[KEYWORD].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[KEYWORD].setisRunningState(false);
            return;
        }
        token[KEYWORD].addValue(ch);
    }

    public void variableTypeDFA(int position) {
        if (!state[VARIABLETYPE].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) variableTypeDFATable.get(state[VARIABLETYPE].getStateLocation());
        try {
            state[VARIABLETYPE].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[VARIABLETYPE].getIsFinalState() && state[VARIABLETYPE].getStateLocation() == 17)
                state[VARIABLETYPE].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[VARIABLETYPE].setisRunningState(false);
            return;
        }
        token[VARIABLETYPE].addValue(ch);
    }

    public void booleanStringDFA(int position) {
        if (!state[BOOLEANSTRING].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        JSONObject transition = (JSONObject) booleanStringDFATable.get(state[BOOLEANSTRING].getStateLocation());
        try {
            state[BOOLEANSTRING].setStateLocation(Integer.parseInt(transition.get(Character.toString(ch)).toString()));
            if (!state[BOOLEANSTRING].getIsFinalState() && state[BOOLEANSTRING].getStateLocation() == 8)
                state[BOOLEANSTRING].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[BOOLEANSTRING].setisRunningState(false);
            return;
        }
        token[BOOLEANSTRING].addValue(ch);
    }

    public void identifierDFA(int position) {
        if (!state[IDENTIFIER].getisRunningState())
            return;

        char ch = inputStr.charAt(position);

        //input classification
        String symbolType;
        if (ch == '_')
            symbolType = "_";
        else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
            symbolType = "letter";
        else if (ch >= '0' && ch <= '9')
            symbolType = "digit";
        else {
            //Process when the input is not valid in DFA
            state[IDENTIFIER].setisRunningState(false);
            return;
        }

        try {
            JSONObject transition = (JSONObject) indentifierDFATable.get(state[IDENTIFIER].getStateLocation());
            state[IDENTIFIER].setStateLocation(Integer.parseInt(transition.get(symbolType).toString()));
            if (!state[IDENTIFIER].getIsFinalState() && (state[IDENTIFIER].getStateLocation() == 1
                    || state[IDENTIFIER].getStateLocation() == 2 || state[IDENTIFIER].getStateLocation() == 3
                    || state[IDENTIFIER].getStateLocation() == 4 || state[IDENTIFIER].getStateLocation() == 5))
                state[IDENTIFIER].setIsFinalState(true);
        } catch (NullPointerException e) {
            state[IDENTIFIER].setisRunningState(false);
            return;
        }
        token[IDENTIFIER].addValue(ch);
    }
}
