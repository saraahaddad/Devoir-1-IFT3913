package devoir1;
import java.io.File;

public class Tls {

    public static void tls(String folderPath){
        StringBuilder output = new StringBuilder();
        File[] filesInFolder = new File(folderPath).listFiles();
        int tloc;
        int tassert;
        float tcmp;
        assert filesInFolder != null;
        for (File file: filesInFolder){
            if (file.isDirectory()){
                tls(file.getAbsolutePath());
            }
            if (file.getName().contains("Test")){
                Class fileClass = file.getClass();
                tloc = TlocCounter.computeTloc(file.getAbsolutePath());
                tassert = TassertCounter.computeAssert(file.getAbsolutePath());
                tcmp = (float) tloc / tassert;
                output.append(file.getAbsolutePath()).append(' ');
                output.append(fileClass.getPackageName()).append(' ');
                output.append(fileClass.getSimpleName()).append(' ');
                output.append(tloc).append(' ');
                output.append(tassert).append(' ');
                output.append(tcmp).append(' ');
                System.out.println(output);
            }
        }
    }

    public static void main(String[] args){
        if (args.length == 0){
            System.out.println("Please enter the correct number of arguments!");
        }
        tls(args[0]);
    }
}
