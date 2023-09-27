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

            while (fileReader.hasNext()) {
                String line = fileReader.nextLine();
                if (!line.startsWith("//") && !line.isEmpty()) {
                    linesOfCode++;
                }
            }

            fileReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Oops! File not found");
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
