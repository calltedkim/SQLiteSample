package sqlite_demonstration;

import java.sql.Connection;
import java.sql.DriverManager;
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

    private String DATABASE_DIRECTORY = "";
    private String DATABASE_NAME = "";
    private String CONNECTION_STRING = "";

    public SQLiteDatabase() {

    }

    public SQLiteDatabase(String inputDatabaseName) {
        DATABASE_NAME = inputDatabaseName;
        CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_DIRECTORY + inputDatabaseName;
    }

    public SQLiteDatabase(String inputDirectory, String inputDatabaseName) {
        DATABASE_DIRECTORY = inputDirectory;
        DATABASE_NAME = inputDatabaseName;
        CONNECTION_STRING = "jdbc:sqlite:" + DATABASE_DIRECTORY + DATABASE_NAME;
    }

    public String getDATABASE_DIRECTORY() {
        return DATABASE_DIRECTORY;
    }

    public void setDATABASE_DIRECTORY(String DATABASE_DIRECTORY) {
        this.DATABASE_DIRECTORY = DATABASE_DIRECTORY;
    }

    public String getDATABASE_NAME() {
        return DATABASE_NAME;
    }

    public void setDATABASE_NAME(String DATABASE_NAME) {
        this.DATABASE_NAME = DATABASE_NAME;
    }

    public Connection getDatbaseConnection() {
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

    public void createTable(String inputSql) {
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

    public void createTable(List<String> inputSql) {
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

    public void select(String inputSql) {
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
            processGenericResultSet(resultset);

            resultset.close();
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println("**select query successfully");
    }

    public void update(String inputSql) {
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

    public void delete(String inputSql) {
        System.out.println("----------------------------");
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

    public void insert(List<String> inputSql) {
        System.out.println("----------------------------");
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

    public void processResultSet(ResultSet resultset) throws SQLException {
        while (resultset.next()) {
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            int age = resultset.getInt("age");
            String address = resultset.getString("address");
            float salary = resultset.getFloat("salary");
            System.out.println("ID = " + id);
            System.out.println("NAME = " + name);
            System.out.println("AGE = " + age);
            System.out.println("ADDRESS = " + address);
            System.out.println("SALARY = " + salary);
            System.out.println();
        }
    }

    public void processGenericResultSet(ResultSet resultset) throws SQLException {
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

}