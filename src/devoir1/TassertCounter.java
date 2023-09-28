package devoir1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TassertCounter {
    protected static int computeAssert(String args) {
        int assertions = 0;
        try {
            File file = new File(args);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (!data.startsWith("//") && !data.startsWith("System.out.println")) {
                    if (data.contains("assert")){
                        assertions++;
                    }
                    if (data.contains("fail")){
                        assertions++;
                    }
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
        if ((args.length) != 0){
            System.out.println("TASSERT : " + computeAssert(args[0]) );
        }

    }
}