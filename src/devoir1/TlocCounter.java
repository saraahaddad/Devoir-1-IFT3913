package devoir1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TlocCounter {
    protected static int computeTloc(String fileName) {
        int linesOfCode = 0;

        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            boolean isStartOfComment = false;

            while (fileReader.hasNext()) {                                  // read file line by line
                String line = fileReader.nextLine();
                if (line.trim().startsWith("/*")){
                    isStartOfComment = true;
                }
                if (!line.trim().startsWith("//") && !line.isEmpty() && !isStartOfComment){
                    linesOfCode++;
                }
                if (line.trim().endsWith("*/")){
                    isStartOfComment = false;
                }

            }

            fileReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Oops! File not found");
            e.printStackTrace();
        }
        return linesOfCode;
    }
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Error! Please enter the correct number of arguments");
        }
        String fileName = args[0];
        System.out.println("TLOC : " + computeTloc(fileName));
    }
}
