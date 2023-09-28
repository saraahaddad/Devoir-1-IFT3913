package devoir1;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tls {

    public static String tls(String folderPath) throws IOException {
        StringBuilder output = new StringBuilder();                // output line
        // create list of files using folder path
        ArrayList<File> filesInFolder = new ArrayList<>();
        if (new File(folderPath).isDirectory()){
            filesInFolder.addAll(List.of(Objects.requireNonNull(new File(folderPath).listFiles())));
        }
        else {
            filesInFolder.add(new File(folderPath));
        }
        int tloc;       // store tloc count
        int tassert;    // store tassert count
        float tcmp;     // store tcmp

        for (File file: filesInFolder){
            if (file.isDirectory()){                               // recursively call function if file is a directory
                tls(file.getAbsolutePath());
            }
            if (file.getName().contains("Test.java")){             // if not test file, ignore
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String packageName = reader.readLine();
                packageName = packageName.startsWith("package") ? packageName : "";
                String className = file.getName();
                tloc = TlocCounter.computeTloc(file.getAbsolutePath());
                tassert = TassertCounter.computeAssert(file.getAbsolutePath());
                if (tassert !=0){
                    tcmp = (float) tloc / tassert;
                }
                else{
                    tcmp = tloc;
                }
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
