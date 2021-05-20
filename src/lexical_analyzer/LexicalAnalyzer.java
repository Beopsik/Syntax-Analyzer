package lexical_analyzer;

import lexical_analyzer.dfa.*;

import java.io.*;

public class LexicalAnalyzer {

    private final File file;

    public LexicalAnalyzer(File file) {
        this.file = file;
    }

    public void execute() {

        String getOneLine;
        String input = "";
        try {
            //Read input file
            BufferedReader br = new BufferedReader(new FileReader(file));

            //Read one line at a time from the file and concatenate each other
            while ((getOneLine = br.readLine()) != null) {
                input += getOneLine;
            }
            //Init DFA
            DFA dfa = new DFA(input, 0);
            dfa.run();

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}