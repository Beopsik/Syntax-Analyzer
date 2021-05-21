package syntax_analyzer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class SyntaxAnalyzer {
    private final File file;

    public SyntaxAnalyzer(File file) {
        this.file = file;
    }

    public void execute() {
        makeInput();
        SLRparsing slrparsing=new SLRparsing();
        slrparsing.run();
    }

    public void makeInput(){
        String getOneLine;
        String[] inputStr;
        int i=0;

        try {
            FileWriter writer=new FileWriter("C:\\Users\\문법식\\Desktop\\개인공부\\GitKraken\\Syntax-Analyzer\\input.json");
            BufferedReader br=new BufferedReader(new FileReader(file));
            JSONArray inputJsonFileArray=new JSONArray();

            while((getOneLine= br.readLine())!=null){
                JSONObject jsonObject=new JSONObject();
                JSONArray jsonArray=new JSONArray();
                inputStr=doSplit(getOneLine);
                switch (inputStr[0]){
                    case "ARITHMETICOPERATOR":
                        if(inputStr[1].equals("+")||inputStr[1].equals("-"))
                            inputStr[0]="addsub";
                        else
                            inputStr[0]="multdiv";

                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case  "COMPARISONOPERATOR":
                        inputStr[0]="comp";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "ASSIGNMENTOPERATOR":
                        inputStr[0]="assign";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "TERMINATESYMBOL":
                        inputStr[0]="semi";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "LPAREN":
                        inputStr[0]="lparen";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "RPAREN":
                        inputStr[0]="rparen";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "LBRACE":
                        inputStr[0]="lbrace";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "RBRACE":
                        inputStr[0]="rbrace";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "COMMA":
                        inputStr[0]="comma";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "SINGLECHARACTER":
                        inputStr[0]="character";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "LITERALSTRING":
                        inputStr[0]="literal";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "SIGNEDINTEGER":
                        inputStr[0]="num";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "KEYWORD":
                        inputStr[0]=inputStr[1];
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "VARIABLETYPE":
                        inputStr[0]="vtype";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "BOOLEANSTRING":
                        inputStr[0]="boolstr";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                    case "IDENTIFIER":
                        inputStr[0]="id";
                        jsonObject.put("Terminals", inputStr[0]);
                        jsonObject.put("Content", inputStr[1]);
                        inputJsonFileArray.add(jsonObject);
                        break;
                }
            }
            writer.write(inputJsonFileArray.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] doSplit(String str){
        String[] result=new String[2];
        int splitIndex=str.indexOf(":");

        result[0]=str.substring(0, splitIndex);
        result[1]=str.substring(splitIndex+1, str.length());

        return result;
    }
}
