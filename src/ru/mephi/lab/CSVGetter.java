package ru.mephi.lab;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVGetter {
    CSVGetter(){}

    public List<SuicideStatisticsRow> getListData(String docName){
        String path = String.format("../../../../files/%s.csv", docName);
        List<SuicideStatisticsRow> resultList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line = "";
            String csvSplitBy = ",";

            while ((line = reader.readLine()) != null){
                String rowFromFile[] = line.split(csvSplitBy);
                SuicideStatisticsRow row = new SuicideStatisticsRow(rowFromFile[0], Integer.valueOf(rowFromFile[1]),
                        rowFromFile[2], rowFromFile[3], Integer.valueOf(rowFromFile[4]),
                        Integer.valueOf(rowFromFile[5]), Integer.valueOf(rowFromFile[6]));

                /**Check on replicate*/

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
