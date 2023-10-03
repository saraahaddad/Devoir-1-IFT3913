package devoir1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

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
                        .filter(line -> line.startsWith("package")).findFirst().orElse("");
                tloc = TlocCounter.computeTloc(file.getAbsolutePath());
                tassert = TassertCounter.computeAssert(file.getAbsolutePath());
                tcmp = tassert != 0 ? (float) tloc / tassert : 0;

                output.append(file.getAbsolutePath()).append(", ");

                //output.append(file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('\\'))).append(", ");


                if (!Objects.equals(packageName, "")) {
                    output.append(packageName, packageName.indexOf(' ') + 1, packageName.indexOf(';')).append(", ");
                }
                else{
                    output.append("No package name found, ");
                }
                output.append(className, 0, className.indexOf('.')).append(", ");
                output.append(tloc).append(", ");
                output.append(tassert).append(", ");
                output.append(tcmp).append("\n");
            }
        }
        return output.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1 && args.length != 3){
            System.out.println("Please enter the correct number of arguments!");
        }

        int filePathIndex = args[0].equals("-o") ? 2 : 0;

        String[] output = tls(args[filePathIndex]).split("\n");

        for (int i = 0; i < output.length; i++) {
            String s = output[i];
            String relPath = Utilities.getRelPath(args[filePathIndex], s.substring(0, s.indexOf(' ')));
            output[i] = s.replace(s.substring(0, s.indexOf(' ')), relPath);
            System.out.println(output[i]);
        }

        if (args[0].equals("-o")) {
            Utilities.createCSV(String.join("\n", output), args[1]);
        }
    }
}
