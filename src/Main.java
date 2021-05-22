import lexical_analyzer.LexicalAnalyzer;
import syntax_analyzer.SyntaxAnalyzer;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        //Validation for no input
        /*if(args.length == 0) {
            System.out.println("입력받은 파일이 없습니다.");
            System.exit(0);
        }*/

        //File file = new File(args[0]);
        File file1 = new File("./test.txt");
        //Init lexical_analyzer.dfa.lexical_analyzer.LexicalAnalyzer
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(file1);
        lexicalAnalyzer.execute();

        File file2 = new File("./output.txt");
        SyntaxAnalyzer syntaxAnalyzer=new SyntaxAnalyzer(file2);
        syntaxAnalyzer.execute();
    }
}
