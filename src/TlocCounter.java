import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TlocCounter {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Error! Please enter the correct number of arguments");
        }
        String fileName = args[0];
        computeTloc(fileName);
    }

    private static void computeTloc(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            int linesOfCode = 0;

            while (fileReader.hasNext()) {
                String line = fileReader.nextLine();
                if (!line.startsWith("//") && !line.isEmpty()) {
                    linesOfCode++;
                }
            }

            fileReader.close();
            System.out.println(linesOfCode);
        }
        catch (FileNotFoundException e){
            System.out.println("Oops! An error occured");
        }
    }
}
