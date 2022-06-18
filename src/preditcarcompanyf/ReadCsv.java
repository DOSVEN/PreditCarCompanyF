package preditcarcompanyf;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadCsv {

    public static final String delimiter = ",";

    public static ArrayList<String[]> read(String csvFile) {
        try {
            ArrayList<String[]> data = new ArrayList<>();
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                String[] row = {};
                tempArr = line.split(delimiter);
                data.add(tempArr);
            }
            br.close();
            return data;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
