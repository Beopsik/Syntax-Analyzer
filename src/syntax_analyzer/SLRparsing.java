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

    public SLRparsing(){
        stack.push(0);
        splitter=0;
    }
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

    public String MakeDecision(int row, String inputSymbol){
        SLRTableRow=(JSONObject) SLRTable.get(row);
        return SLRTableRow.get(inputSymbol).toString();
    }

    public  void run(){
        String decision;
        InitInputArray();
        InitSLRTable();

        while (splitter<InputArray.size()) {
            InputObject = (JSONObject) InputArray.get(splitter);
            nextInputSymbol = InputObject.get("Terminals").toString();
            decision=MakeDecision(stack.peek(), nextInputSymbol);

            switch (decision.charAt(0)){
                case 'r':
                    CFGObject=(JSONObject) CFGArray.get(Character.getNumericValue(decision.charAt(1)));
                    String CFGRight=CFGObject.get("right").toString();
                    break;
                case 's':
                    stack.push(Character.getNumericValue(decision.charAt(1)));
                    splitter++;
                    break;
                default:
                    break;
            }
        }
    }

}
