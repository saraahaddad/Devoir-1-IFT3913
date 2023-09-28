package devoir1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// On assume que les lignes contenant
public class TassertCounter {
    protected static int computeAssert(String args) {
        int assertions = 0;
        boolean isStartOfComment = false;
        try {
            File file = new File(args);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (data.trim().startsWith("/*")){
                    isStartOfComment = true;
                }

                if (!data.trim().startsWith("//") && !data.startsWith("System.out.println") && !isStartOfComment) {
                    if (data.contains("assert")){
                        assertions++;
                    }
                    if (data.contains("fail")){
                        assertions++;
                    }
                }
                if (data.trim().endsWith("*/")){
                    isStartOfComment = false;
                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return assertions;
    }

    public static void main(String[] args) {
        if ((args.length) != 0)
            System.out.println("TASSERT : " + computeAssert(args[0]));
    }
}