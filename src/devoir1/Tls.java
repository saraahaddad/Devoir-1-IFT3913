package devoir1;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Tls {

    public static String tls(String folderPath) throws IOException {
        StringBuilder output = new StringBuilder();                // output line
        List<File> filesInFolder;                                  // create list of files using folder path
        filesInFolder = new File(folderPath).isDirectory() ?
                List.of(Objects.requireNonNull(new File(folderPath).listFiles())) : List.of(new File(folderPath));
        int tloc;       // store tloc count
        int tassert;    // store tassert count
        float tcmp;     // store tcmp

        for (File file: filesInFolder){
            if (file.isDirectory()){                               // recursively call function if file is a directory
                output.append(tls(file.getAbsolutePath()));
            }
            if (file.getName().contains("Test.java")){             // if not test file, ignore
                String className = file.getName();
                String packageName = Files.readAllLines(new File(file.getAbsolutePath()).toPath()).stream()
                        .filter(line -> line.startsWith("package")).findFirst().get();
                tloc = TlocCounter.computeTloc(file.getAbsolutePath());
                tassert = TassertCounter.computeAssert(file.getAbsolutePath());
                tcmp = tassert != 0 ? (float) tloc / tassert : tloc;
                output.append(file.getAbsolutePath()).append(", ");
                if (!Objects.equals(packageName, "")) {
                    output.append(packageName, packageName.indexOf(' ') + 1, packageName.indexOf(';')).append(", ");
                }
                else{
                    output.append("None").append(", ");
                }
                output.append(className, 0, className.indexOf('.')).append(", ");
                output.append(tloc).append(", ");
                output.append(tassert).append(", ");
                output.append(tcmp).append(", \n");
            }
        }
        return output.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Please enter the correct number of arguments!");
        }
        System.out.println(tls(args[0]));
    }
}
