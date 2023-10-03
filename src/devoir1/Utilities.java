package devoir1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class Utilities {
    public static void createCSV(String content, String fileName) throws IOException {
        File csvFile = new File(fileName + ".csv");
        FileWriter fileWriter = new FileWriter(csvFile);
        fileWriter.write(content);
        fileWriter.close();
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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
        // source : https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
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

}
