package devoir1;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TlocCounter {
    static boolean isStartOfComment = false;
    protected static int computeTloc(String fileName) throws IOException {
        return (int) Files.readAllLines(new File(fileName).toPath())    // get lines of file
                .stream()                                               // to go over each line
                .filter(line -> !lineIsComment(line))                   // filter out comments
                .count();                                               // count remaining elements
    }
    public static boolean lineIsComment(String line){
        if (line.trim().startsWith("/*")){      // beginning of multiline comment
            isStartOfComment = true;
            return true;                        // line is a comment
        }
        if (!line.trim().startsWith("//") && !line.trim().isEmpty() && !isStartOfComment){
            return false;
        }
        if (line.trim().endsWith("*/")){        // end of multiline comment
            isStartOfComment = false;
            return true;
        }
        return true;
    }
    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            System.out.println("Error! Please enter the correct number of arguments");
        }
        String fileName = args[0];
        System.out.println("TLOC : " + computeTloc(fileName));
    }
}
