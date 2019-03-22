package ru.SourceFiles;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.GUI.*;

import javax.swing.*;

public class LogicProgram{
    private DBHandler dbh;
    private MainMenuWindow mainMenu = new MainMenuWindow();
    private AddTableViewWindow aTView = new AddTableViewWindow();
    private ViewTableWindow vtView = new ViewTableWindow();

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
            fillingOfTable(tableName);
            mainMenu.stop();
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

    private void addDataToDB(){
        String tableName = aTView.getTableName();
        String fileName = aTView.getFileName();

        if (!checkOfFileExist(fileName)) {
            aTView.infoBox("file is not exist!", "Error");
            aTView.clearTextLines();
            return;
        }

        String message = dbh.createTable(tableName);

        if (checkOfTableExist(message)){
            aTView.infoBox(message, "Error");
            if (!checkOfTableInList(tableName)){
                mainMenu.addTableInList(tableName);
            }
            aTView.clearTextLines();
            return;
        }

        mainMenu.addTableInList(tableName);

        List<SuicideStatisticsRow> table= CSVGetter.getListData(fileName);

        for(SuicideStatisticsRow row: table){
            dbh.setInformationToTable(tableName, row);
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

    private void fillingOfTable(String tableName){

    }

    private boolean checkOfTableExist(String message){
        return message != null;
    }

    private boolean checkOfFileExist(String fileName){
        String path = String.format("src/ru/files/%s.csv", fileName);
        return new File(path).exists();
    }

    private boolean checkOfTableInList(String tableName){
        ArrayList <String> array = mainMenu.getAllTableName();
        for(String name: array){
            if (tableName.equals(name)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        LogicProgram lp = new LogicProgram();
        lp.start();
    }
}
