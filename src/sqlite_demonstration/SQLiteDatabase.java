package sqlite_demonstration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SQLiteDatabase {

    /*
    SQLite does not support joins containing more than 64 tables
    The maximum number of bytes in the text of an SQL statement is 1000000
    SQLite does not support joins containing more than 64 tables. 
    The SQLITE_MAX_FUNCTION_ARG parameter determines the maximum number of parameters 
        that can be passed to an SQL function. The default value of this limit is 100.
    The number of arguments to a function is sometimes stored in a signed character. 
        So there is a hard upper bound on SQLITE_MAX_FUNCTION_ARG of 127.
    A compound SELECT statement is two or more SELECT statements connected 
        by operators UNION, UNION ALL, EXCEPT, or INTERSECT. 
        We call each individual SELECT statement within a compound SELECT a "term".
        The maximum number of terms is SQLITE_MAX_COMPOUND_SELECT which defaults to 500.
    The theoretical maximum number of rows in a table is 2^64 
    
     */
    private String DATABASE_DIRECTORY = "./src/db/";
    private String DATABASE_NAME = "database.db";
    private static String CONNECTION_STRING = "";

    public SQLiteDatabase() {
        CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_DIRECTORY + DATABASE_NAME;
    }

    public SQLiteDatabase(String inputDatabaseName) {
        DATABASE_NAME = inputDatabaseName;
        CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_DIRECTORY + DATABASE_NAME;
    }

    public static Connection getDatbaseConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return connection;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**Opened database successfully");
        return connection;

    }

    public static void createTable(String inputSql) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            //System.out.println("**Opened database successfully in createTable() method");
            statement = connection.createStatement();

            String sql = inputSql;
            //execute Statement object
            statement.executeUpdate(sql);

            //close Statement object
            statement.close();
            //close Connectoin object
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**create table successfully");
    }

    public static void createTable(List<String> inputSql) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            //System.out.println("**Opened database successfully in createTable() method");
            statement = connection.createStatement();

            for (String element : inputSql) {
                String sql = element;
                //execute Statement object
                statement.executeUpdate(sql);
            }

            //close Statement object
            statement.close();
            //close Connectoin object
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**create table successfully");
    }

    public static void select(String inputSql) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully in select() method");

            statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(inputSql);
            displayResultSet(resultset);

            resultset.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**select query successfully");
    }

    /**
     *
     * @param inputSql
     * @return
     */
    public static ResultSet selectAndReturnResultSet(String inputSql) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully in select() method");

            statement = connection.createStatement();
            resultset = statement.executeQuery(inputSql);

            //resultset.close();
            statement.close();
            connection.close();

            return resultset;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return resultset;
        //System.out.println("**select query successfully");
    }

    public static void getTableInfo(String inputTableName) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully in getTableInfo() method");

            statement = connection.createStatement();
            //"PRAGMA table_info('issue');
            ResultSet resultset = statement.executeQuery("PRAGMA table_info('" + inputTableName + "');");
            ResultSetMetaData resultSetMetaData = resultset.getMetaData();
            int cols = resultSetMetaData.getColumnCount();
            while (resultset.next()) {
                for (int i = 1; i < cols; i++) {
                    String colName = resultSetMetaData.getColumnName(i);
                    String colVal = resultset.getString(i);
                    System.out.println(colName + ": " + colVal);
                }
                System.out.println("");
            }

            resultset.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**select query successfully");
    }

    public static void update(String inputSql) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully in update() method");

            statement = connection.createStatement();
            statement.executeUpdate(inputSql);
            connection.commit();

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**update query successfully");
    }

    public static void delete(String inputSql) {
        //System.out.println("----------------------------");
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully in delete() method");

            statement = connection.createStatement();
            statement.executeUpdate(inputSql);

            connection.commit();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**delete query successfully");
    }

    /*
    Statement object is vulnable to sql injection attack
    PreparedStatement object has faster performance than Statement for repeating queries
     */
    public static void insert(List<String> inputSql) {
        //System.out.println("----------------------------");
        Connection connection = getDatbaseConnection();
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully");
            statement = connection.createStatement();

            for (String sql : inputSql) {
                statement.executeUpdate(sql);
            }
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**insert query successfully");
    }

    public static void insertWithPreparedStatement(String inputQuery, List<String> label, List<String> value) {
        //System.out.println("----------------------------");
        Connection connection = getDatbaseConnection();
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = getDatbaseConnection();
            connection.setAutoCommit(false);
            //System.out.println("**Opened database successfully");
            preparedStatement = connection.prepareStatement(inputQuery);

            for (String sql : inputQuery) {
                
            }
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**insert query successfully");
    }

    public static void displayResultSet(ResultSet resultset) throws SQLException {
        ResultSetMetaData md = resultset.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        while (resultset.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), resultset.getObject(i));
            }
            list.add(row);
        }

        for (HashMap<String, Object> element : list) {
            //System.out.println("**" + element.toString());

            Set<String> keySetLabel = element.keySet();
            Iterator<String> itrKeySetLabel = keySetLabel.iterator();

            Collection<Object> keyValues = element.values();
            Iterator<Object> itrKeyValue = keyValues.iterator();

            while (itrKeySetLabel.hasNext()) {
                System.out.println(itrKeySetLabel.next() + " = " + itrKeyValue.next());
            }

            System.out.println("");

        }
    }

}//end class
