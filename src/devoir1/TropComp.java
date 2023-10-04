package devoir1;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TropComp {
    protected static String computeTropComp(String path, String Seuil) throws IOException {
        StringBuilder output = new StringBuilder();

        ArrayList<File> files = Utilities.listAllFiles(path);

        double tloc;
        double tcmp;
        double tassert;

        HashMap<String, Double> tcmps = new HashMap<>();
        HashMap<String, Double> tlocs = new HashMap<>();

        for (File file : files) {
            if (file.getName().endsWith(".java")) {
                String filePath = file.getAbsolutePath();
                tloc = TlocCounter.computeTloc(filePath);
                tassert = TassertCounter.computeAssert(filePath);
                tcmp = tassert != 0 ? tloc / tassert : 0;

                if (tcmp != 0) {
                    tcmps.put(filePath, tcmp);
                    tlocs.put(filePath, tloc);
                }
            }

        }

        // calculer le seuil
        // si le nombre de files calculer n'est pas un entier on prend le plancher
        int fileAmount = tlocs.size() * Integer.parseInt(Seuil) / 100;

        // order files by their tloc
        Map<String, Double> orderedTloc = Utilities.sortByValue(tlocs);
        List<String> tlocKeys = new ArrayList<>(orderedTloc.keySet());
        Collections.reverse(tlocKeys);

        // order files by their tcmp
        Map<String, Double> orderedTcmp = Utilities.sortByValue(tcmps);
        List<String> tcmpKeys = new ArrayList<>(orderedTcmp.keySet());
        Collections.reverse(tcmpKeys);

        // Construire nouvel array: On doit regarder les top de chaque hashmap et extraire le tls si un fichier
        // se trouve dans les top des deux categories

        List<String> keylist = new ArrayList<>();
        // print tls of top <seuil>%
        int added = 0;

        // extract amount wanted
        for (String key : tcmpKeys) {
            if (added < fileAmount) {
                keylist.add(key);
                added++;
            }
        }

        // Add if files are also in top <seuil>% of tloc
        for (String file : tlocKeys) {
            if (fileAmount > 0) {
                for (String key : keylist) {
                    if (Objects.equals(key, file)) {
                        output.append(Tls.tls(key));
                    }
                }
                fileAmount--;
            }
        }

        return output.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2 && args.length != 4) {
            System.out.println("Veuillez entrer un chemin de fichier ainsi que le seuil souhaite!");
        }

        int filePathIndex = args[0].equals("-o") ? 2 : 0;
        String[] output = computeTropComp(args[filePathIndex], args[filePathIndex + 1]).split("\n");

        for (int i = 0; i < output.length; i++) {
            if (!output[i].isEmpty()) {
                String s = output[i];
                String relPath = Utilities.getRelPath(args[filePathIndex], s.substring(0, s.indexOf(' ')));
                output[i] = s.replace(s.substring(0, s.indexOf(' ')), relPath);
                System.out.println(output[i]);
            }
        }

        if (args[0].equals("-o")) {
            Utilities.createCSV(String.join("\n", output), args[1]);
        }
    }
}

