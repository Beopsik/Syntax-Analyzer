package syntax_analyzer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class SLRparsing {
    //SLRTable JSONArray for storing entire SLRTable JSON file and SLRTableRow JSONObject for storing a row of SLRTable for specific state
    JSONArray SLRTable = new JSONArray();
    JSONObject SLRTableRow = new JSONObject();

    //CFGArray JSONArray for storing entire CFG JSON file and CFGObject JSONObject for storing specific CFG
    JSONArray CFGArray = new JSONArray();
    JSONObject CFGObject = new JSONObject();

    //InputArray JSONArray for storing entire input JSON file and InputObject JSONObject for storing specific input
    JSONArray InputArray = new JSONArray();
    JSONObject InputObject = new JSONObject();

    //Stack for storing states
    Stack<Integer> stack = new Stack<>();

    String nextInputSymbol = "";
    int splitter;

    //Store lexical analyzer input file name in variable used when an error occurs
    String lexerInputFileNameForError;

    public SLRparsing(String lexerInputFileNameForError) {
        stack.push(0);
        splitter = 0;
        this.lexerInputFileNameForError=lexerInputFileNameForError;
    }

    public void run() {
        String decision;
        InitInputArray();
        InitSLRTable();
        InitCFGArray();

        //Repeat statement to recognize input
        while (splitter < InputArray.size()) {
            //Get InputObject based on splitter and store terminal of InputObject to nextInputSymbol
            InputObject = (JSONObject) InputArray.get(splitter);
            nextInputSymbol = InputObject.get("Terminals").toString();

            //Get decision based on the current state and next input symbol
            decision = MakeDecision(stack.peek(), nextInputSymbol);

            //Exit repeat statement when accepting the input
            if (decision.equals("acc")) {
                System.out.println("Accept");
                break;
            }

            //Distinguish whether decision is reduce or shift
            switch (decision.charAt(0)) {
                case 'r' -> {
                    //Get CFGObject from the entire CFGArray based on the decision
                    CFGObject = (JSONObject) CFGArray.get(Integer.parseInt(decision.substring(1)));

                    //Pop states from the stack based on the CFG's right-hand side size
                    int CFGSize = Integer.parseInt(CFGObject.get("size").toString());
                    for (int i = 0; i < CFGSize; i++) {
                        stack.pop();
                    }

                    //Push GOTO number into the stack based on the current state and left-hand side of the CFG
                    String GOTO = MakeDecision(stack.peek(), CFGObject.get("left").toString());
                    stack.push(Integer.parseInt(GOTO));
                }
                case 's' -> {
                    //Push state into the stack and move the splitter to the right
                    stack.push(Integer.parseInt(decision.substring(1)));
                    splitter++;
                }
            }
        }
    }

    //Init InputArray
    public void InitInputArray() {
        FileReader jsonFile;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonFile = new FileReader("./input.json");
            InputArray = (JSONArray) jsonParser.parse(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Init SLRTable
    public void InitSLRTable() {
        FileReader jsonFile;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonFile = new FileReader("./SLRTable.json");
            SLRTable = (JSONArray) jsonParser.parse(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Init CFGArray
    public void InitCFGArray() {
        FileReader jsonFile;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonFile = new FileReader("./CFG.json");
            CFGArray = (JSONArray) jsonParser.parse(jsonFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Print Error Message
    public void ErrorMessage(int errorPosition){
        //Get lexical analyzer input file
        File inputFile = new File(lexerInputFileNameForError);

        //Variable for string a line of the inputFile
        String getOneLine="";

        String unexpectedToken;

        int index=0;
        int rowOfError=0;

        try {
            BufferedReader br=new BufferedReader(new FileReader(inputFile));

            //Repeat statement for detecting a row of error
            while(index<=errorPosition){
                getOneLine=br.readLine();

                while(true){
                    InputObject=(JSONObject) InputArray.get(index);
                    if(getOneLine.contains(InputObject.get("Content").toString())){
                        index++;
                    }else{
                        rowOfError++;
                        break;
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //Store the token at the position where the error occurred
        InputObject=(JSONObject) InputArray.get(errorPosition);
        unexpectedToken=InputObject.get("Content").toString();

        //Print error information
        System.out.println("\n\nReject");
        System.out.println("SyntaxError:    Unexpected token -> "+unexpectedToken);
        System.out.println("Error at:"+getOneLine+"     ("+lexerInputFileNameForError+": Line "+rowOfError+")");
    }

    public String MakeDecision(int row, String inputSymbol) {
        //Get SLRTableRow from the SLRTable based on the state
        SLRTableRow = (JSONObject) SLRTable.get(row);

        //Error when the checked SLRTable space is empty
        if (SLRTableRow.get(inputSymbol).toString().length() == 0) {
            ErrorMessage(splitter);
            System.exit(0);
        }

        //Return decision
        return SLRTableRow.get(inputSymbol).toString();
    }
}
