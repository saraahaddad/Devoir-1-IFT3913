package devoir1;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TropComp {
    protected static String computeTropComp(String path, String Seuil) throws IOException {
        StringBuilder output = new StringBuilder();

        ArrayList<File> files = listAllFiles(path);

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
        Map<String, Double> orderedTloc = sortByValue(tlocs);
        List<String> tlocKeys = new ArrayList<>(orderedTloc.keySet());
        Collections.reverse(tlocKeys);

        // order files by their tcmp
        Map<String, Double> orderedTcmp = sortByValue(tcmps);
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
        if (!((args.length) == 2)) {
            System.out.println("Veuillez entrer un chemin de fichier ainsi que le seuil souhaite!");
        }
        String[] output = computeTropComp(args[0], args[1]).split("\n");
        for (String s : output) {
            String relPath = Tls.getRelPath(args[0], s.substring(0, s.indexOf(' ')));
            System.out.println(relPath + s.substring(s.indexOf(" ")).trim());
        }
    }

    public static ArrayList<File> listAllFiles(String filePath) {
        List<File> files = new File(filePath).isDirectory() ?
                List.of(Objects.requireNonNull(new File(filePath).listFiles())) : List.of(new File(filePath));

        ArrayList<File> filesInPath = new ArrayList<>(files);
        for (File file : files) {
            if (file.isDirectory()) {
                filesInPath.addAll(listAllFiles(file.getAbsolutePath()));
            } else if (!(filesInPath.contains(file))) {
                filesInPath.add(file);
            }
        }

        return filesInPath;
    }


    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


    // source : https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values

}

