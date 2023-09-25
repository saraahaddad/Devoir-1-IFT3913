import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TassertCounter {

    public class Tassert {

        public int assertions = 0;

        public int computeAssert(String args) {
            try {
                File file = new File(args);
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    if (!(data.substring(0, 2).equals("//"))) {
                        if (data.startsWith("assert") || data.startsWith("fail")) {
                            this.assertions++;
                        }
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            return assertions;
        }

        public void main(String[] args) {
            if ((args.length) != 0)
                System.out.println(computeAssert(args[0]));
        }


    }
}
