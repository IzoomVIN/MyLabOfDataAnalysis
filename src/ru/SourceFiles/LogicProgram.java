package ru.SourceFiles;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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
        int[][] myData = new int[data.size()][2];

        BigInteger sumOfY = BigInteger.valueOf(0);
        BigInteger sumOfC = BigInteger.valueOf(0);

        for (int i = 0; i < data.size(); i++){
            myData[i][0] = data.get(i).getYear();
            myData[i][1] = data.get(i).getSuicidesCount();
            sumOfY = sumOfY.add(BigInteger.valueOf(data.get(i).getYear()));
            sumOfC = sumOfC.add(BigInteger.valueOf(data.get(i).getSuicidesCount()));
        }

        BigInteger sumCY = BigInteger.valueOf(0);
        BigInteger sumC2 = BigInteger.valueOf(0);
        BigInteger sumY2 = BigInteger.valueOf(0);

        for (int[] myDatum : myData) {
            sumCY = sumCY.add(BigInteger.valueOf(myDatum[0] * myDatum[1]).subtract(sumOfC.multiply(sumOfY)));
            sumC2 = sumC2.add(BigInteger.valueOf(myDatum[0] * myDatum[0]).subtract(sumOfC.multiply(sumOfC)));
            sumY2 = sumY2.add(BigInteger.valueOf(myDatum[1] * myDatum[1]).subtract(sumOfY.multiply(sumOfY)));
        }

        dataToCM[0][0] = 1;
        dataToCM[1][1] = 1;
        dataToCM[0][1] = Double.valueOf(new BigDecimal(
                sumCY.multiply(
                        BigInteger.valueOf(myData.length)
                )
        ).divide(
                new BigDecimal(
                        sumC2.multiply(
                                BigInteger.valueOf(myData.length)
                        ).multiply(
                                sumY2.multiply(
                                        BigInteger.valueOf(myData.length)
                                )
                        ).sqrt()
                ),
                MathContext.DECIMAL32
        ).toString());
        dataToCM[1][0] = dataToCM[0][1];

        return dataToCM;
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
