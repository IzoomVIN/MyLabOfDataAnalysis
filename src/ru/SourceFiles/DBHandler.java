package ru.SourceFiles;

import org.sqlite.JDBC;

import java.sql.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHandler {

    // Constant of connect address
    private static final String CONNECT_ADDRESS = "jdbc:sqlite:src/ru/DB/%s.db";
    //Object of connect with DB
    private Connection connection;

    /**Use scheme of "Only class instance" for optimization of the memory*/
    private static DBHandler instance = null;
    static synchronized DBHandler getInstance(String dBName) throws SQLException{
        if (instance == null){
            instance = new DBHandler(dBName);
        }
        return instance;
    }

    /**Build of this class (class initialised from getInstance*/
    private DBHandler(String dBName) throws SQLException{
        //Registering driver for work with DB
        DriverManager.registerDriver(new JDBC());
        //Completed connect with DB
        this.connection = DriverManager.getConnection(String.format(CONNECT_ADDRESS, dBName));
    }

    public List<SuicideStatisticsRow> getAllRowsFromTable(String tableName){
        // Statement is use for completing SQL search
        try(Statement statement = this.connection.createStatement()){
            List<SuicideStatisticsRow> resultList = new ArrayList<>();
            // from resultSet we take rows of result SQL search
            // in ResultSet we give SQL code
            ResultSet resultSet = statement.executeQuery(String.format("SELECT*FROM %s;", tableName));
            //get all rows from result set and give result to resultList
            while (resultSet.next()){
                resultList.add(new SuicideStatisticsRow(resultSet.getString("Country"),
                        resultSet.getInt("Year"),
                        resultSet.getString("Sex"),
                        resultSet.getString("Age"),
                        resultSet.getInt("Suicides_Count"),
                        resultSet.getInt("Population"),
                        resultSet.getInt("Suicides_to_100_K_Population")));
            }
            return resultList;
        }catch(SQLException e){
            return Collections.emptyList();
        }
    }

    void setInformationToTable(String tableName, SuicideStatisticsRow row){
        try(PreparedStatement statement = this.connection.prepareStatement(String.
                format("INSERT INTO %s ", tableName) +
                        "('Country', 'Year', 'Sex', 'Age', 'Suicides_Count', " +
                        "'Population', 'Suicides_to_100_K_Population') " +
                        "VALUES(?,?,?,?,?,?,?")){
            statement.setObject(1, row.getCountry());
            statement.setObject(2, row.getYear());
            statement.setObject(3, row.getSex());
            statement.setObject(4, row.getAge());
            statement.setObject(5, row.getSuicidesCount());
            statement.setObject(6, row.getPopulation());
            statement.setObject(7, row.getSuicidesTo100KPopulation());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    String createTable(String tableName){
        try(Statement statement = this.connection.createStatement()){
            ResultSet names = statement.executeQuery("SELECT*FROM sqlite_master\n" +
                    "WHERE type='table';");
            if (names != null) {
                boolean FLAG = true;
                while(names.next()) {
                    if (tableName.equals(names.getString(""))) {
                        FLAG = false;
                        break;
                    }
                }
                if (!FLAG){
                    return "Table with this name is exist";
                }
            }
            executeCreateTable(statement, tableName);
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
        return null;
    }

    ArrayList<String> getAllTMfromDB(){
        ArrayList<String> outputArray = new ArrayList<>();
        try(Statement statement = this.connection.createStatement()) {
            ResultSet names = statement.executeQuery("SELECT*FROM sqlite_master\n" +
                    "WHERE type='table';");
            if (names != null) {
                while(names.next()) {
                    outputArray.add(names.getString(""));
                }
            }else{
                outputArray = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return outputArray;
    }

    private void executeCreateTable(Statement statement,String tableName){
        try {
            String sql = String.format("CREATE TABLE %s (\n", tableName) +
                    "Country TEXT PRIMARY KEY,\n" +
                    "Year INT PRIMARY KEY,\n" +
                    "Sex TEXT PRIMARY KEY,\n" +
                    "Age TEXT PRIMARY KEY,\n" +
                    "Suicides_Count INT,\n" +
                    "Population INT,\n" +
                    "Suicides_to_100_K_Population REAL);";
            statement.execute(sql);
        }catch (SQLException e){
//            e.printStackTrace();
        }
    }
}
