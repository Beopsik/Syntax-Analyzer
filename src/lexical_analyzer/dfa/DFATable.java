package lexical_analyzer.dfa;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//Define all kind of tokens' DFA to transition table by json format
public class DFATable {
    public DFATable(){}

    //Identifier trasition table
    public JSONArray identifierDFATable(){
        String dfaTable="[" +                               //    '_'   letter  digit
                "{\"_\":1, \"letter\":2}"+                  //T0: T1      T2
                "{\"_\":3, \"letter\":4, \"digit\": 5}"+    //T1: T3      T4     T5
                "{\"_\":3, \"letter\":4, \"digit\": 5}"+    //T2: T3      T4     T5
                "{\"_\":3, \"letter\":4, \"digit\": 5}"+    //T3: T3      T4     T5
                "{\"_\":3, \"letter\":4, \"digit\": 5}"+    //T4: T3      T4     T5
                "{\"_\":3, \"letter\":4, \"digit\": 5}"+    //T5: T3      T4     T5
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Single Character trasition table
    public JSONArray singleCharacterDFATable(){
        String dfaTable="[" +                                 //    '    letter  digit blank
                "{\"'\":1}"+                                  //T0: T1
                "{\"digit\":2, \"letter\": 3, \"blank\":4}"+  //T1: T5     T3      T2    T4
                "{\"'\":5}"+                                  //T2: T5
                "{\"'\":5}"+                                  //T3: T5
                "{\"'\":5}"+                                  //T4: T5
                "{}"+                                         //T5
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Literal String trasition table
    public JSONArray literalStringDFATable(){
        String dfaTable="[" +                                                        //     "    digit  letter blank
                "{\"double quotes\":1}"+                                             //T0:  T1
                "{\"double quotes\":5, \"digit\":2, \"letter\": 3, \"blank\":4}"+    //T1:  T5     T2     T3     T4
                "{\"double quotes\":5, \"digit\":2, \"letter\": 3, \"blank\":4}"+    //T2:  T5     T2     T3     T4
                "{\"double quotes\":5, \"digit\":2, \"letter\": 3, \"blank\":4}"+    //T3:  T5     T2     T3     T4
                "{\"double quotes\":5, \"digit\":2, \"letter\": 3, \"blank\":4}"+    //T4:  T5     T2     T3     T4
                "{}"+                                                                //T5
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Signed Integer trasition table
    public JSONArray signedIntegerDFATable(){
        String dfaTable="[" +                           //    positive_digit   -   0   digit
                "{\"positive\":1, \"-\":2, \"0\":4}"+   //T0:      T1          T2  T4
                "{\"digit\":3}"+                        //T1:                            T3
                "{\"positive\":1}"+                     //T2:      T1
                "{\"digit\":3}"+                        //T3:                            T3
                "{}"+                                   //T4
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Boolean String trasition table
    public JSONArray booleanStringDFATable(){
        String dfaTable="[" +               //     t    r    u    e    f    a    l    s
                "{\"t\":1, \"f\":4}"+       //T0:  T1                  T1
                "{\"r\":2}"+                //T1:       T2
                "{\"u\":3}"+                //T2:            T3
                "{\"e\":8}"+                //T3:                 T8
                "{\"a\":5}"+                //T4:                           T5
                "{\"l\":6}"+                //T4:                                T6
                "{\"s\":7}"+                //T4:                                     T7
                "{\"e\":8}"+                //T4:                 T8
                "{}"+                       //T8
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Keyword trasition table
    public JSONArray keywordDFATable(){
        String dfaTable="[" +                                       //     i    f    e    l    s    w    h    c    a    r    t    u     n
                "{\"i\":1, \"e\":2, \"w\":5, \"c\":9, \"r\":13}"+   //T0:  T1        T2             T5        T9        T13
                "{\"f\":18}"+                                       //T1:       T18
                "{\"l\":3}"+                                        //T2:                 T3
                "{\"s\":4}"+                                        //T3:                      T4
                "{\"e\":18}"+                                       //T4:            T18
                "{\"h\":6}"+                                        //T5:                                T6
                "{\"i\":7}"+                                        //T6:  T7
                "{\"l\":8}"+                                        //T7:                 T8
                "{\"e\":18}"+                                       //T8:            T18
                "{\"l\":10}"+                                       //T9:                 T10
                "{\"a\":11}"+                                       //T10:                                         T11
                "{\"s\":12}"+                                       //T11:                     T12
                "{\"s\":18}"+                                       //T12:                     T18
                "{\"e\":14}"+                                       //T13:           T14
                "{\"t\":15}"+                                       //T14:                                                   T15
                "{\"u\":16}"+                                       //T15:                                                        T16
                "{\"r\":17}"+                                       //T16:                                              T17
                "{\"n\":18}"+                                       //T17:                                                              T18
                "{}"+                                               //T18
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Variable Type transition table
    public JSONArray variableTypeDFATable(){
        String dfaTable="[" +                             //     i    n    t    c    h    a    r    b    o    l    e    S    g
                "{\"i\":1, \"c\":3, \"b\":6, \"S\":12}"+  //T0:  T1             T3                  T6                  T12
                "{\"n\":2}"+                              //T1:       T2
                "{\"t\":17}"+                             //T2:            T3
                "{\"h\":4}"+                              //T3:                      T4
                "{\"a\":5}"+                              //T4:                           T5
                "{\"r\":17}"+                             //T5:                                T17
                "{\"o\":7}"+                              //T6:                                          T7
                "{\"o\":8}"+                              //T7:                                          T8
                "{\"l\":9}"+                              //T8:                                               T9
                "{\"e\":10}"+                             //T9:                                                     T10
                "{\"a\":11}"+                             //T10:                          T11
                "{\"n\":17}"+                             //T11:      T17
                "{\"t\":13}"+                             //T12:           T13
                "{\"r\":14}"+                             //T13:                               T14
                "{\"i\":15}"+                             //T14: T15
                "{\"n\":16}"+                             //T15:      T16
                "{\"g\":17}"+                             //T16:                                                             T17
                "{}"+                                     //T17
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Arithmetic Operator trasition table
    public JSONArray arithmeticOperatorDFATable(){
        String dfaTable="[" +                              //    +    -    *    /
                "{\"+\":1, \"-\":1, \"*\":1, \"/\":1}"+    //T0: T1   T1   T1   T1
                "{}"+                                      //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Assignment Operator trasition table
    public JSONArray assignmentOperatorDFATable(){
        String dfaTable="[" +                        //    =
                "{\"=\":1}"+                         //T0: T1
                "{}"+                                //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Comparison Operator trasition table
    public JSONArray ComparisonOperatorDFATable(){
        String dfaTable="[" +                             //    =    !    <    >
                "{\"=\":1, \"!\":2, \"<\":3, \">\":4}"+   //T0: T1   T2   T3   T4
                "{\"=\":5}"+                              //T1: T5
                "{\"=\":5}"+                              //T2: T5
                "{\"=\":5}"+                              //T3: T5
                "{\"=\":5}"+                              //T4: T5
                "{}"+                                     //T5
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Terminate Symbol trasition table
    public JSONArray terminateSymbolDFATable(){
        String dfaTable="[" +    //    ;
                "{\";\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Left Paren Symbol trasition table
    public JSONArray lParenDFATable(){
        String dfaTable="[" +    //    (
                "{\"(\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Right Paren Symbol trasition table
    public JSONArray rParenDFATable(){
        String dfaTable="[" +    //    )
                "{\")\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Left Brace Symbol trasition table
    public JSONArray lBraceDFATable(){
        String dfaTable="[" +    //    {
                "{\"{\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Right Brace Symbol trasition table
    public JSONArray rBraceDFATable(){
        String dfaTable="[" +    //    }
                "{\"}\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Left Branket Symbol trasition table
    public JSONArray lBranketDFATable(){
        String dfaTable="[" +    //    [
                "{\"[\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Right Branket Symbol trasition table
    public JSONArray rBranketDFATable(){
        String dfaTable="[" +    //    ]
                "{\"]\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //Comma Symbol trasition table
    public JSONArray commaDFATable(){
        String dfaTable="[" +    //    ,
                "{\",\":1}"+     //T0: T1
                "{}"+            //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    //White space trasition table
    public JSONArray whiteSpaceDFATable(){
        String dfaTable="[" +                     //    blank  \t    \n
                "{\" \":1, \"\t\":1, \"\n\":1}"+  //T0: T1     T1    T1
                "{}"+                             //T1
                "]";
        JSONParser jsonParser=new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(dfaTable);
        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }
    }
}
