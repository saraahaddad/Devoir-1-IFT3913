package devoir1;
import java.io.File;

public class Tls {

    public static String tls(String folderPath){
        String output = "";                 // output line
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
                output += (file.getAbsolutePath()) + (", ");                  // path of file
                output +=(tlsClass.getPackageName()) + (", ");               // package name
                output += (tlsClass.getSimpleName())+(", ");                // class name
                output+=(tloc)+(", ");
                output+=(tassert)+(", ");
                output+=(tcmp)+(", \n");

            }
        }
        return output;
    }

    public static void main(String[] args){
        if (args.length == 0){
            System.out.println("Please enter the correct number of arguments!");
        }
        System.out.println(tls(args[0]));
    }
}
