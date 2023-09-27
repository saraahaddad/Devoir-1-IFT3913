package devoir1;
import java.io.File;

public class Tls {

    public static void tls(String folderPath){
        StringBuilder output = new StringBuilder();                 // output line
        File[] filesInFolder = new File(folderPath).listFiles();    // create list of files using folder path
        int tloc;       // store tloc count
        int tassert;    // store tassert count
        float tcmp;     // store tcmp
        assert filesInFolder != null;
        for (File file: filesInFolder){
            if (file.isDirectory()){                    // recursively call function if file is a directory
                tls(file.getAbsolutePath());
            }
            if (file.getName().contains("Test")){                                   // if not test file, ignore
                Class<Tls> tlsClass = Tls.class;                                    // extract class
                tloc = TlocCounter.computeTloc(file.getAbsolutePath());             // get tloc
                tassert = TassertCounter.computeAssert(file.getAbsolutePath());     // get tassert
                tcmp = (float) tloc / tassert;
                output.append(file.getAbsolutePath()).append(' ');                  // path of file
                output.append(tlsClass.getPackageName()).append(' ');               // package name
                output.append(tlsClass.getSimpleName()).append(' ');                // class name
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
