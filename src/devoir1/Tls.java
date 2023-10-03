package devoir1;

import java.io.File;
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
                output.append(tcmp).append(", \n");
            }
        }
        return output.toString();
    }

    public static String getRelPath(String folderPath, String filePath){
        String relPath = "";

        try {
            File file = new File(filePath);
            File folder = new File(folderPath);

            // convert the absolute path to URI
            URI fileURI = file.toURI();
            URI folderURI = folder.toURI();

            // create a relative path from the two paths
            URI relativePathURI = folderURI.relativize(fileURI);

            // convert the URI to string
            relPath = "./" + relativePathURI.getPath();

        } catch (Exception e) {
            e.getStackTrace();
        }
        return relPath;
        // source: https://www.programiz.com/java-programming/examples/get-relative-path

    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Please enter the correct number of arguments!");
        }

        String[] output = tls(args[0]).split("\n");
        for (String s : output) {
            String relPath = getRelPath(args[0], s.substring(0, s.indexOf(' ')) );
            System.out.println(relPath + s.substring(s.indexOf(" ")).trim());
        }
    }
}
