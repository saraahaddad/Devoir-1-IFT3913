package devoir1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TassertCounter {
    static boolean isStartOfComment = false;
    protected static int computeAssert(String fileName) throws IOException {
        return (int) Files.readAllLines(new File(fileName).toPath())    // get lines of file
                .stream()                                               // to go over each line
                .filter(line -> lineIsAssertion(line))                  // filter assertions
                .count();                                               // count remaining elements

    }

    public static boolean lineIsAssertion(String line){
        if (line.trim().contains("/*")){        // beginning of multiline comment
            isStartOfComment = true;            // line is a comment
        }

        if (line.trim().contains("*/")){        // end of multiline comment
            isStartOfComment = false;
        }

        if (!line.startsWith("import") && !line.trim().startsWith("//")
                && !line.startsWith("System.out.println") && !isStartOfComment){
            if (line.contains("assert") || line.contains("fail")){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        if ((args.length) != 0)
            System.out.println("TASSERT : " + computeAssert(args[0]));
    }
}