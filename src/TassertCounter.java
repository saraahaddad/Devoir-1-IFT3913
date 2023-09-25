import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TassertCounter {
    static Pattern pattern = Pattern.compile("assert[A-Z]*");

    public static void main(String[] args) {
        if ((args.length) != 0)
            computeAssert(args[0]);
    }

    public static void computeAssert(String args) {
        int assertions = 0;
        try {
            File file = new File(args);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                if (!data.startsWith("//") && !data.startsWith("System.out.println")) {
                    if (data.startsWith("assert")){
                        if (pattern.matcher(data.substring(0,7)).matches() ) {
                            assertions++;
                        }
                    }
                    if (data.contains("fail")){
                        assertions++;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(assertions);
    }
}