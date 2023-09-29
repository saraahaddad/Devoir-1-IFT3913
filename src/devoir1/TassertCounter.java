package devoir1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

// On assume que les lignes contenant
public class TassertCounter {
    static boolean isStartOfComment = false;
    protected static int computeAssert(String fileName) throws IOException {
        return (int) Files.readAllLines(new File(fileName).toPath())    // get lines of file
                .stream()                                               // to go over each line
                .filter(line -> lineIsAssertion(line))                   // filter assertions
                .count();                                               // count remaining elements

    }

    public static boolean lineIsAssertion(String line){
        if (line.trim().startsWith("/*")){      // beginning of multiline comment
            isStartOfComment = true;            // line is a comment
        }
        if (!line.startsWith("import") && !line.trim().startsWith("//")
                && !line.startsWith("System.out.println") && !isStartOfComment){
            if (line.contains("assert") || line.contains("fail")){
                return true;
            }
        }
        if (line.trim().endsWith("*/")){        // end of multiline comment
            isStartOfComment = false;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        if ((args.length) != 0)
            System.out.println("TASSERT : " + computeAssert(args[0]));
    }
}