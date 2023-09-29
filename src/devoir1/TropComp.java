package devoir1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class TropComp {
    protected static String computeTropComp(String path, String Seuil) throws IOException {
        StringBuilder output = new StringBuilder();
        File[] files = new File(path).listFiles();
        String[] dataTropComp;

        assert files != null;

        // get tls data of files
        dataTropComp = Tls.tls(path).split("\n");
        // calculer le seuil
        // si le nombre de files calculer n'est pas un entier on prend le plancher
        int fileAmount = dataTropComp.length * Integer.parseInt(Seuil) / 100 ;

        HashMap<String, Double> tcmp = new HashMap<String, Double>();
        HashMap<String, Double> tloc = new HashMap<String, Double>();

        // for each file, fill hashmaps
        for (String file: dataTropComp){
            String[] dataFile = file.split(",");
            tloc.put(dataFile[0],Double.parseDouble(dataFile[3].trim()));
            tcmp.put(dataFile[0],Double.parseDouble(dataFile[5].trim()));
        }

        // order files by their tloc
        Map<String, Double> orderedTloc = MapUtil.sortByValue(tloc);
        // order files by their tcmp
        Map<String, Double> orderedTcmp = MapUtil.sortByValue(tcmp);

        // Construire nouvel array: On doit regarder les top de chaque hashmap et extraire le tls si un fichier
        // se trouve dans les top des deux categories

        List<String> keylist = new ArrayList<>();
        // print tls of top <seuil>%
        int added = 0;

        // extract amount wanted
        for (String key: orderedTcmp.keySet()){
            if (added < fileAmount){
                keylist.add(key);
                added++;
            }
        }

        // print tls of files wanted
        for (String file: orderedTloc.keySet()){
            if (fileAmount > 0){
                for (String key: keylist){
                    if(Objects.equals(key, file)){
                        output.append(Tls.tls(key));
                    }
                }
                fileAmount--;
            }

        }

        return output.toString();
    }
    public static void main(String[] args) throws IOException {
        if ((args.length) == 2){
            System.out.println(computeTropComp(args[0], args[1]));
        }
        else{
            System.out.println("Veuillez entrer un chemin de fichier ainsi que le seuil souhaite!");
        }

    }

    public class MapUtil {
        public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
            List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
            list.sort(Map.Entry.comparingByValue());

            Map<K, V> result = new LinkedHashMap<>();
            for (Map.Entry<K, V> entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }

            return result;
        }
    }

    // source : https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values

}

