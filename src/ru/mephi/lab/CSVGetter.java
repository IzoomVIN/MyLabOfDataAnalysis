package ru.mephi.lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVGetter {
    CSVGetter(){}

    public List<SuicideStatisticsRow> getListData(String docName){
        String path = String.format("../../../../files/%s.csv", docName);
        List<SuicideStatisticsRow> resultList = new ArrayList<>();
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(path));
            String line = "";
            String csvSplitBy = ",";

            while ((line = reader.readLine()) != null){
                String[] rowFromFile = line.split(csvSplitBy);
                SuicideStatisticsRow row = new SuicideStatisticsRow(rowFromFile[0], Integer.valueOf(rowFromFile[1]),
                        rowFromFile[2], rowFromFile[3], Integer.valueOf(rowFromFile[4]),
                        Integer.valueOf(rowFromFile[5]), Integer.valueOf(rowFromFile[6]));

                /** Check to reality and null suicide count*/
                if (nullSuicideCountCheck(row) && checkToReality(row)) {
                    /** Check to replicate in result list*/
                    boolean FLAG = false;
                    for (SuicideStatisticsRow rowFromArray : resultList) {
                        if (row.equals(rowFromArray)) {
                            FLAG = true;
                            break;
                        }
                    }
                    if (!FLAG) {
                        resultList.add(row);
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return resultList;
    }

    private boolean nullSuicideCountCheck(SuicideStatisticsRow row){
        return !(((row.getAge() == null ) || (row.getAge().equals(""))) ||
                ((row.getCountry() == null ) || (row.getCountry().equals(""))) ||
                ((row.getSex() == null ) || (row.getSex().equals(""))));
    }

    private boolean checkToReality(SuicideStatisticsRow row){
        return ((row.getSuicidesCount() != row.getPopulation()) &&
                ((row.getPopulation() - row.getSuicidesCount()) < (int)row.getPopulation()*0.7));
    }
}
