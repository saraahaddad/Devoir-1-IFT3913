package devoir1;
import java.io.*;

public class Tls {

    public static StringBuilder tls(String folderPath) throws IOException {
        StringBuilder output = new StringBuilder();                // output line
        File[] filesInFolder = new File(folderPath).listFiles();   // create list of files using folder path
        int tloc;       // store tloc count
        int tassert;    // store tassert count
        float tcmp;     // store tcmp
        assert filesInFolder != null;

        for (File file: filesInFolder){
            if (file.isDirectory()){                               // recursively call function if file is a directory
                tls(file.getAbsolutePath());
            }
            if (file.getName().contains("Test.java")){             // if not test file, ignore
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String packageName = reader.readLine();
                String className = file.getName();
                tloc = TlocCounter.computeTloc(file.getAbsolutePath());
                tassert = TassertCounter.computeAssert(file.getAbsolutePath());
                tcmp = (float) tloc / tassert;
                output.append(file.getAbsolutePath()).append(", ");
                output.append(packageName, packageName.indexOf(' ') + 1, packageName.indexOf(';')).append(", ");
                output.append(className, 0, className.indexOf('.')).append(", ");
                output.append(tloc).append(", ");
                output.append(tassert).append(", ");
                output.append(tcmp).append(", ");
                System.out.println(output);
            }
        }
        return output;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Please enter the correct number of arguments!");
        }
        System.out.println(tls(args[0]));
    }
}
