package ru.mephi.lab;

import org.sqlite.JDBC;

import java.sql.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHandler {

    // Constant of connect address
    private static final String CONNECT_ADDRESS = "jdbs:sqlite:../DB/%s.db";
    //Object of connect with DB
    private Connection connection;

    /**Use scheme of "Only class instance" for optimization of the memory*/
    private static DBHandler instance = null;
    public static synchronized DBHandler getInstance(String dBName) throws SQLException{
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
        this.connection = DriverManager.getConnection(String.format(this.CONNECT_ADDRESS, dBName));
    }

    public List<SuicideStatisticsRow> getAllRowsFromTable(String tableName){
        // Statement is use for completing SQL search
        try(Statement statement = this.connection.createStatement()){
            List<SuicideStatisticsRow> resultList = new ArrayList<>();
            // from resultSet we take rows of result SQL search
            // in ResultSet we give SQL code
            ResultSet resultSet = statement.executeQuery("");
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

    public void setInformationToTable(String dBName, SuicideStatisticsRow row){
        try(PreparedStatement statement = this.connection.prepareStatement(String.format("INSERT INTO %s ", dBName) +
                        "('Country', 'Year', 'Sex', 'Age', 'Suicides_Count', " +
                        "'Population', 'Suicides_to_100_K_Population') " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?")){
            statement.setObject(1, row.getCountry());
            statement.setObject(2, row.getYear());
            statement.setObject(3, row.getSex());
            statement.setObject(4, row.getAge());
            statement.setObject(5, row.getSuicidesCount());
            statement.setObject(6, row.getPopulation());
            statement.setObject(7, row.getSuicidesTo100KPopulation());

            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createTable(String tableName){
        try(Statement statement = this.connection.createStatement()){
            statement.execute(String.format("CREATE TABLE %s (\n", tableName)+
                    "Country TEXT PRIMARY KEY,\n"+
                    "Year INTEGER PRIMARY KEY,\n"+
                    "Sex TEXT PRIMARY KEY,\n"+
                    "Age TEXT PRIMARY KEY,\n"+
                    "Suicides_Count INTEGER,\n"+
                    "Population INTEGER,\n"+
                    "Suicides_to_100_K_Population INTEGER\n);");
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }
    }
}
