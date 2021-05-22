package syntax_analyzer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Stack;

public class SLRparsing {
    JSONArray SLRTable=new JSONArray();
    JSONObject SLRTableRow=new JSONObject();
    JSONArray CFGArray=new JSONArray();
    JSONObject CFGObject=new JSONObject();

    Stack<Integer> stack=new Stack<>();
    int splitter;
    String nextInputSymbol;
    JSONArray InputArray=new JSONArray();
    JSONObject InputObject=new JSONObject();

    String ShowInputString="";

    public SLRparsing(){
        stack.push(0);
        splitter=0;
    }

    //삭제할것--------------------------------------------------
    public void InitShowInputString(){
        for (Object o : InputArray) {
            InputObject = (JSONObject) o;
            ShowInputString+=InputObject.get("Terminals").toString();
            ShowInputString+=" ";
        }
    }
    //----------------------------------------------------------
    public void InitCFGArray(){
        FileReader jsonFile;
        JSONParser jsonParser=new JSONParser();
        try{
            jsonFile=new FileReader("C:\\Users\\문법식\\Desktop\\개인공부\\GitKraken\\Syntax-Analyzer\\src\\syntax_analyzer\\CFG.json");
            CFGArray=(JSONArray) jsonParser.parse(jsonFile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void InitInputArray(){
        FileReader jsonFile;
        JSONParser jsonParser=new JSONParser();
        try{
            jsonFile=new FileReader("C:\\Users\\문법식\\Desktop\\개인공부\\GitKraken\\Syntax-Analyzer\\input.json");
            InputArray=(JSONArray) jsonParser.parse(jsonFile);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void InitSLRTable(){
        FileReader jsonFile;
        JSONParser jsonParser=new JSONParser();
        try {
            jsonFile = new FileReader("C:\\Users\\문법식\\Desktop\\개인공부\\GitKraken\\Syntax-Analyzer\\src\\syntax_analyzer\\SLRTable.json");
            SLRTable=(JSONArray) jsonParser.parse(jsonFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ShowStack(){
        for(int i=0; i<stack.size(); i++){
            System.out.print(stack.elementAt(i)+" ");
        }
        System.out.println();
    }

    public String MakeDecision(int row, String inputSymbol){
        switch (row){
            case 14:
                if(inputSymbol.equals("rparen")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "ARG"));
                    stack.push(row);
                }
                break;
            case 17:
                if(inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "ODECL"));
                    stack.push(row);
                }
                break;
            case 31:
                if(inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "ODECL"));
                    stack.push(row);
                }
                break;
            case 32:
                if(inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "ODECL"));
                    stack.push(row);
                }
                break;
            case 34:
                if(inputSymbol.equals("rparen")){
                    ShowStack();
                    System.out.println("Input NonTerminal:MOREARGS");
                    row=Integer.parseInt(MakeDecision(row, "MOREARGS"));
                    System.out.println("row:"+row);
                    stack.push(row);
                }
                break;
            case 41:
                if(inputSymbol.equals("return")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "BLOCK"));
                    stack.push(row);
                }
                break;
            case 48:
                if(inputSymbol.equals("return")||inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "BLOCK"));
                    stack.push(row);
                }
                break;
            case 63:
                if(inputSymbol.equals("rparen")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "MOREARGS"));
                    stack.push(row);
                }
                break;
            case 75:
                if(inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "BLOCK"));
                    stack.push(row);
                }
                break;
            case 77:
                if(inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "BLOCK"));
                    stack.push(row);
                }
                break;
            case 80:
                if(inputSymbol.equals("return")||inputSymbol.equals("vtype")||inputSymbol.equals("id")||inputSymbol.equals("if")||inputSymbol.equals("while")||inputSymbol.equals("rbrace")){
                    ShowStack();
                    row=Integer.parseInt(MakeDecision(row, "ELSE"));
                    stack.push(row);
                }
                break;
        }
        SLRTableRow=(JSONObject) SLRTable.get(row);
        return SLRTableRow.get(inputSymbol).toString();
    }

    public  void run(){
        String decision;
        InitInputArray();
        InitSLRTable();
        InitCFGArray();
        InitShowInputString();
        System.out.println(ShowInputString);

        while (splitter<InputArray.size()) {
            InputObject = (JSONObject) InputArray.get(splitter);
            nextInputSymbol = InputObject.get("Terminals").toString();
            ShowStack();
            System.out.println("Input Symbol:"+nextInputSymbol);
            decision=MakeDecision(stack.peek(), nextInputSymbol);
            if(decision.equals("acc")){
                System.out.println("Accept");
                break;
            }
            System.out.println("decision:"+decision);
            switch (decision.charAt(0)){
                case 'r':
                    CFGObject=(JSONObject) CFGArray.get(Integer.parseInt(decision.substring(1)));
                    int CFGSize=Integer.parseInt(CFGObject.get("size").toString());
                    for(int i=0; i<CFGSize; i++)
                        stack.pop();
                    ShowStack();
                    System.out.println("Replace[left]:"+CFGObject.get("left").toString()+", Replace[right]:"+CFGObject.get("right").toString());
                    String GOTO=MakeDecision(stack.peek(), CFGObject.get("left").toString());
                    System.out.println();
                    stack.push(Integer.parseInt(GOTO));
                    break;
                case 's':
                    System.out.println();
                    stack.push(Integer.parseInt(decision.substring(1)));
                    splitter++;
                    break;
                default:
                    System.out.println();
                    stack.push(Integer.parseInt(decision.substring(1)));
                    break;
            }
        }
    }

}
