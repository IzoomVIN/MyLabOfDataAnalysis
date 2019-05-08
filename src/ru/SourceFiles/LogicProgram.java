package ru.SourceFiles;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.GUI.*;


public class LogicProgram{
    private DBHandler dbh;
    private MainMenuWindow mainMenu = new MainMenuWindow();
    private AddTableViewWindow aTView = new AddTableViewWindow();
    private ViewTableWindow vtView;
    private GraphicParametrsWindow gPWindow;
    private GraphicViewWindow gView;
    private CorrelationMatrixViewWindow cMView;

    private LogicProgram(){
        try {
            dbh = DBHandler.getInstance("SuicideDB");
        }catch (SQLException e){
            e.printStackTrace();
        }
        addALtoATV();
        addALtoMM();
        addAllTableName();
    }

    private void start(){
        mainMenu.start();
    }

    private void addALtoMM(){
        mainMenu.setActionListenerID(e -> {
            mainMenu.stop();
            aTView.start();
        });

        mainMenu.setActionListenerVT(e -> {
            String tableName = mainMenu.getSelectedValue();
            List<SuicideStatisticsRow> data = dbh.getAllRowsFromTable(tableName);
            vtView = new ViewTableWindow(data, tableName);
            mainMenu.stop();
            addALtoVT();
            vtView.start();
        });
    }

    private void addALtoATV(){
        aTView.setActionListenerCancel(e -> {
            aTView.stop();
            mainMenu.start();
        });

        aTView.setActionListenerOK(e -> addDataToDB());
    }

    private void addALtoVT(){
        vtView.setActionListenerToCancelButton(e -> {
            mainMenu.start();
            vtView.stop();
            vtView = null;
            if (gPWindow != null){
                gPWindow.stop();
                gPWindow = null;
            }
        });

        vtView.setActionListenerToVGButton(e -> {
            vtView.stop();
            if (gPWindow == null){
                gPWindow = new GraphicParametrsWindow(vtView.getTableName());
            }
            addALtoGPW();
            gPWindow.start();
        });

        vtView.setActionListenerToVCMButton(e -> viewCorrMatrixLogic(vtView.getTableName()));
    }

    private void addALtoGPW(){
        gPWindow.setActionListenerToButCancel(e -> {
            vtView.start();
            gPWindow.stop();
            gPWindow = null;
        });

        gPWindow.setActionListenerToButtAll(e -> {
            List<PlotDataRow> data;

            data = dbh.getDataForAll(gPWindow.getTableName());

            gView = GraphicViewWindow.GraphicViewWindowCreate("All Countries", data, () ->{
                gView.stop();
                vtView.start();
                gView = null;
            });

            gView.start();

            gPWindow.stop();
            gPWindow = null;
        });

        gPWindow.setActionListenerToButtRus(e -> {
            List<PlotDataRow> data;

            data = dbh.getDataForRus(gPWindow.getTableName());

            gView = GraphicViewWindow.GraphicViewWindowCreate("In Russia", data, () ->{
                gView.stop();
                vtView.start();
                gView = null;
            });

            gView.start();

            gPWindow.stop();
            gPWindow = null;
        });
    }

    private void addDataToDB(){
        String tableName = aTView.getTableName();
        String fileName = aTView.getFileName();

        if (!checkOfFileExist(fileName)) {
            aTView.infoBox("file is not exist!", "Error");
            aTView.clearTextLines();
            return;
        }

        if (checkOfTableExist(tableName)) {
            aTView.infoBox("This table is exist", "Error");
            return;
        }

        dbh.createTable(tableName);
        mainMenu.addTableInList(tableName);

        List<SuicideStatisticsRow> table= CSVGetter.getListData(fileName);

        int count = 0;

        for(SuicideStatisticsRow row: table){
            dbh.setInformationToTable(tableName, row);
            System.out.printf("Count: %d\n",++count);
        }

        aTView.infoBox("Table is created", "Successful");

        aTView.stop();
        mainMenu.start();
    }

    private void addAllTableName(){
        try {
            ArrayList<String> names = dbh.getAllTMfromDB();

            for(String name: names){
                mainMenu.addTableInList(name);
            }
        }catch (NullPointerException e)
        {
            return;
        }
    }

    private void viewCorrMatrixLogic(String tableName){
        double[][] data = makeDataToCM(tableName);
        cMView = new CorrelationMatrixViewWindow(data, () -> {
            cMView.stop();
            vtView.start();
            cMView = null;
        });

        cMView.start();
        vtView.stop();
    }

    private double[][] makeDataToCM(String tableName){
        List<SuicideStatisticsRow> data = dbh.getAllRowsFromTable(tableName);
        double[][] dataToCM = new double[2][2];
        double[][] myData = normalizeData(data);

        BigDecimal midOfY = BigDecimal.valueOf(0);
        BigDecimal midOfC = BigDecimal.valueOf(0);
        BigDecimal sumCY = BigDecimal.valueOf(0);
        BigDecimal sumC2 = BigDecimal.valueOf(0);
        BigDecimal sumY2 = BigDecimal.valueOf(0);

        for (double[] row: myData){
            midOfY = midOfY.add(BigDecimal.valueOf(row[0]));
            midOfC = midOfC.add(BigDecimal.valueOf(row[1]));
        }

        midOfY = midOfY.divide(BigDecimal.valueOf(myData.length), MathContext.DECIMAL32);
        midOfC = midOfC.divide(BigDecimal.valueOf(myData.length), MathContext.DECIMAL32);

        for (double[] myDatum : myData) {
            BigDecimal localVariable = null;
            localVariable = BigDecimal.valueOf(myDatum[0]).subtract(midOfY);
            sumCY = sumCY.add(localVariable.multiply(
                    BigDecimal.valueOf(myDatum[1]).subtract(midOfC)
            ));

            localVariable = BigDecimal.valueOf(myDatum[0]).subtract(midOfY);
            localVariable = localVariable.multiply(localVariable);
            sumC2 = sumC2.add(localVariable);

            localVariable = BigDecimal.valueOf(myDatum[1]).subtract(midOfC);
            localVariable = localVariable.multiply(localVariable);
            sumY2 = sumY2.add(localVariable);
        }

        BigDecimal sqrtOfSumCY = sumY2.multiply(sumC2);
        sqrtOfSumCY = sqrtOfSumCY.sqrt(MathContext.DECIMAL32);

        dataToCM[0][0] = 1;
        dataToCM[1][1] = 1;
        dataToCM[0][1] = Double.valueOf(
                sumCY.divide(sqrtOfSumCY, MathContext.DECIMAL32).toString()
        );
        dataToCM[1][0] = dataToCM[0][1];

        return dataToCM;
    }

    private double[][] normalizeData(List<SuicideStatisticsRow> data){
        int maxYear = 0;
        int minYear = 3000;
        int maxSC = 0;
        int minSC = 3000000;

        double[][] result = new double[data.size()][2];

        for (SuicideStatisticsRow row : data){
            if (row.getYear() > maxYear){
                maxYear = row.getYear();
            }else if (row.getYear() < minYear){
                minYear = row.getYear();
            }

            if(row.getSuicidesCount() > maxSC){
                maxSC = row.getSuicidesCount();
            }else if (row.getSuicidesCount() < minSC){
                minSC = row.getSuicidesCount();
            }
        }

        for (int i = 0; i < data.size(); i++){
            result[i][0] = (double) (data.get(i).getYear() - minYear)/(maxYear - minYear);
            result[i][1] = (double) (data.get(i).getSuicidesCount() - minSC)/(maxSC - minSC);
        }

        return result;
    }

    private boolean checkOfTableExist(String tableName){
        for(String name: mainMenu.getAllTableName()){
            if (tableName.equals(name)){
                return true;
            }
        }
        return false;
    }

    private boolean checkOfFileExist(String fileName){
        String path = String.format("src/ru/files/%s.csv", fileName);
        return new File(path).exists();
    }

    public static void main(String[] args){
        LogicProgram lp = new LogicProgram();
        lp.start();
    }
}
