package syntax_analyzer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Stack;

public class SLRparsing {
    FileReader jsonFile;
    JSONParser jsonParser=new JSONParser();
    JSONArray SLRTable=new JSONArray();
    JSONObject SLRTableRow=new JSONObject();

    Stack<Integer> stack=new Stack<>();
    int splitter;

    public SLRparsing(){
        stack.push(1);
        splitter =0;
    }

    public void InitSLRTable(){
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


    }

}
