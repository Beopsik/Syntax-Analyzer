import lexical_analyzer.LexicalAnalyzer;
import syntax_analyzer.SLRparsing;
import syntax_analyzer.SyntaxAnalyzer;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        //Validation for no input
        /*if(args.length == 0) {
            System.out.println("입력받은 파일이 없습니다.");
            System.exit(0);
        }*/

        /*File file = new File("C:\\Users\\문법식\\Desktop\\개인공부\\GitKraken\\Syntax-Analyzer\\test.txt");
        //File file = new File(args[0]);

        //Init lexical_analyzer.dfa.lexical_analyzer.LexicalAnalyzer
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(file);
        lexicalAnalyzer.execute();*/

        File file = new File("C:\\Users\\문법식\\Desktop\\개인공부\\GitKraken\\Syntax-Analyzer\\output.txt");
        SyntaxAnalyzer syntaxAnalyzer=new SyntaxAnalyzer(file);
        syntaxAnalyzer.execute();
    }
}
