/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.areatecnica.sigf_v1.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author ianfr
 */
public class Conexion {
    
        private Connection conn = null;
        private Statement  statement = null;
        private String	   sql	= null;
        private ResultSet  resultSet = null;
        private Vector vector;
        private ArrayList rows;
        private ArrayList newRow;
        private ResultSetMetaData metaData;
        private String[] columnNames = {};
    
    /**
     * Creates a new instance of Conexion
     */
    public Conexion(){
        getConnection();
                
    }
    
    public Connection getConnection(){
        
        try{
       
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://www.areatecnica.cl:3306/sigf_v1?user=root&password=NintendO64";

        conn = DriverManager.getConnection(url);
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        catch (ClassNotFoundException ex) {
            System.err.println("Cannot find the database driver classes.");
            System.err.println(ex);
        }
        catch (SQLException ex) {
            System.err.println("Cannot connect to this database.");
            System.err.println(ex);
        }
        //Retornamos la conexi�n establecida.
    return conn;
}
    
    public void closeConexion(){
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void update(String sql){
        
        
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
       } catch (SQLException ex) {
            ex.printStackTrace();
        }
        statement = null;
    }
    
    public ResultSet executeSqlToResultSet(String sql){
        try {
            
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            resultSet = statement.executeQuery(sql);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            statement = null;
            return resultSet;
    }
    
    @SuppressWarnings("unchecked")
public ArrayList executeQuery(String sql){
        ArrayList<Object> array = new ArrayList<Object>();
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            resultSet = statement.executeQuery(sql);
            metaData = resultSet.getMetaData();

            int numberOfColumns =  metaData.getColumnCount();
            columnNames = new String[numberOfColumns];
            // Get the column names and cache them.
            // Then we can close the connection.
            for(int column = 0; column < numberOfColumns; column++) {
                columnNames[column] = metaData.getColumnLabel(column+1);
            }
            
            resultSet.beforeFirst();
            
            rows = new ArrayList();
            while (resultSet.next()) {
                newRow = new ArrayList();
                for (int i = 1; i <= getColumnCount(); i++) {
                        newRow.add(resultSet.getObject(i));
                }
                rows.add(newRow);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return array;
    }

    public int getColumnCount() {
        return columnNames.length;
    }
    
     public String getColumnName(int column) {
        if (columnNames[column] != null) {
            return columnNames[column];
        } else {
            return "";
        }
    }

    
    public int getRowCount() {
        return rows.size();
    }

    @SuppressWarnings("unchecked")
Vector executeSqlQueryToArray(String sql, String columnName) {
        
        try {
            vector = new Vector();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
            resultSet = statement.executeQuery(sql);
            
            resultSet.beforeFirst();
            while(resultSet.next()){
                vector.add(resultSet.getString(columnName));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;    
    }

    String[] getColumnNames() {
        return columnNames;
    }
     
     
}
