package syntax_analyzer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Stack;

public class SLRparsing {
    JSONArray SLRTable = new JSONArray();
    JSONObject SLRTableRow = new JSONObject();
    JSONArray CFGArray = new JSONArray();
    JSONObject CFGObject = new JSONObject();
    JSONArray InputArray = new JSONArray();
    JSONObject InputObject = new JSONObject();
    Stack<String> InputStack=new Stack<>();//삭제할 것
    Stack<Integer> stack = new Stack<>();
    String nextInputSymbol = "";
    int splitter;
    String ShowInputString = "";//삭제할 것

    public SLRparsing() {
        stack.push(0);
        splitter = 0;
    }

    public void run() {
        String decision;
        InitInputArray();
        InitSLRTable();
        InitCFGArray();
        InitShowInputString();
        System.out.println(ShowInputString);

        while (splitter < InputArray.size()) {
            InputObject = (JSONObject) InputArray.get(splitter);
            nextInputSymbol = InputObject.get("Terminals").toString();
            ShowStack();
            System.out.println("Input Symbol:" + nextInputSymbol);
            InputStack.push(nextInputSymbol);
            decision = MakeDecision(stack.peek(), nextInputSymbol);
            if (decision.equals("acc")) {
                System.out.println("Accept");
                break;
            }
            System.out.println("decision:" + decision);
            switch (decision.charAt(0)) {
                case 'r' -> {
                    CFGObject = (JSONObject) CFGArray.get(Integer.parseInt(decision.substring(1)));
                    int CFGSize = Integer.parseInt(CFGObject.get("size").toString());
                    for (int i = 0; i < CFGSize; i++) {
                        stack.pop();
                        InputStack.pop();
                    }
                    InputStack.pop();
                    ShowStack();
                    System.out.println("Replace[left]:" + CFGObject.get("left").toString() + ", Replace[right]:" + CFGObject.get("right").toString());
                    String GOTO = MakeDecision(stack.peek(), CFGObject.get("left").toString());
                    System.out.println();
                    stack.push(Integer.parseInt(GOTO));
                    InputStack.push(CFGObject.get("left").toString());
                }
                case 's' -> {
                    System.out.println();
                    stack.push(Integer.parseInt(decision.substring(1)));
                    splitter++;
                }
                default -> {
                    System.out.println();
                    stack.push(Integer.parseInt(decision.substring(1)));
                }
            }
        }
    }

    public void InitInputArray() {
        FileReader jsonFile;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonFile = new FileReader("./src/syntax_analyzer/input.json");
            InputArray = (JSONArray) jsonParser.parse(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InitSLRTable() {
        FileReader jsonFile;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonFile = new FileReader("./src/syntax_analyzer/SLRTable.json");
            SLRTable = (JSONArray) jsonParser.parse(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InitCFGArray() {
        FileReader jsonFile;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonFile = new FileReader("./src/syntax_analyzer/CFG.json");
            CFGArray = (JSONArray) jsonParser.parse(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowErrorPosition(int index) {
        String ErrorPositionStr = "";
        String UnexpectedToken;
        int start;

        if(index<5)
            start=0;
        else
            start=index-5;

        while (start < index + 10) {
            if(start==InputArray.size()-1)
                break;
            if (start==index) {
                ErrorPositionStr = ErrorPositionStr.concat("->");
            }

            InputObject = (JSONObject) InputArray.get(start++);
            ErrorPositionStr = ErrorPositionStr.concat(InputObject.get("Content").toString());
            ErrorPositionStr = ErrorPositionStr.concat(" ");
        }

        InputObject=(JSONObject) InputArray.get(index);
        UnexpectedToken=InputObject.get("Content").toString();

        System.out.println("Reject");
        System.out.println("Error position:" + ErrorPositionStr+"...");
        System.out.println("SyntaxError: Unexpected token:"+UnexpectedToken);
    }

    //삭제할것--------------------------------------------------
    public void InitShowInputString() {
        for (Object o : InputArray) {
            InputObject = (JSONObject) o;
            ShowInputString = ShowInputString.concat(InputObject.get("Terminals").toString());
            ShowInputString = ShowInputString.concat(" ");
        }
    }

    public void ShowStack() {
        int j=0;
        for (int i = 0; i < stack.size(); i++) {
            System.out.print(stack.elementAt(i) + " ");
            if(j<InputStack.size())
                System.out.print(InputStack.elementAt(j++)+" ");
        }
        System.out.println();
    }
    //----------------------------------------------------------

    public String MakeDecision(int row, String inputSymbol) {
        SLRTableRow = (JSONObject) SLRTable.get(row);
        if (SLRTableRow.get(inputSymbol).toString().length() == 0) {
            ShowErrorPosition(splitter);
            System.exit(0);
        }
        return SLRTableRow.get(inputSymbol).toString();
    }
}
