package implementation;

import java.util.ArrayList;
import java.util.List;
import sqlite_demonstration.SQLiteDatabase;

public class TestSQLiteDatabase {

    public static void main(String[] args) {

        //runTest1();
        //runTest2();
        runTest3();
    }

    public static void runTest2() {
        String[] databaseName = {"ab", "bc", "de", "fg"
        };
        for (String element : databaseName) {
            createSampleDatabase(element);
        }

    }

    public static void runTest3() {

        String database_name = "database";

        SQLiteDatabase sqlite = new SQLiteDatabase(database_name);

        sqlite.createTable(getCreatTableQuery());

        sqlite.insert(getInsertQueries());

        sqlite.select(getGenericSelectQuery());
        
        sqlite.insert(getInsertQueries2());

        sqlite.select(getGenericSelectQuery());

    }

    public static void runTest1() {
        String databaseName = "database";

        SQLiteDatabase sqlite = new SQLiteDatabase(databaseName);

        sqlite.createTable(getCreatTableQuery());

        sqlite.insert(getInsertQueries());

        sqlite.select(getGenericSelectQuery());

        sqlite.update(getUpdateQuery());

        sqlite.select(getGenericSelectQuery());

        sqlite.delete(getDeleteQuery());

        sqlite.select(getGenericSelectQuery());

        sqlite.getTableInfo("COMPANY");

        SQLiteDatabase sqlite2 = new SQLiteDatabase(databaseName);

        sqlite2.select(getGenericSelectQuery());
    }

    public static void createSampleDatabase(String inputDatabaseName) {

        String database_name = inputDatabaseName;

        SQLiteDatabase sqlite = new SQLiteDatabase(database_name);

        sqlite.createTable(getCreatTableQuery());

        sqlite.insert(getInsertQueries());

        sqlite.select(getGenericSelectQuery());

        sqlite.update(getUpdateQuery());

        sqlite.select(getGenericSelectQuery());

        sqlite.delete(getDeleteQuery());

        sqlite.select(getGenericSelectQuery());

        sqlite.getTableInfo("COMPANY");
    }

    public static List<String> getInsertQueries() {
        String sql = "";
        List<String> result = new ArrayList<String>();

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (5, 'James', 65, 'Rich-Mond ', 65000.00 );";
        result.add(sql);

        return result;

    }

    public static List<String> getInsertQueries2() {
        String sql = "";
        List<String> result = new ArrayList<String>();

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (5, 'Paul', 32, 'California', 20000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (6, 'Allen', 25, 'Texas', 15000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (7, 'Teddy', 23, 'Norway', 20000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (8, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
        result.add(sql);

        sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                + "VALUES (9, 'James', 65, 'Rich-Mond ', 65000.00 );";
        result.add(sql);

        return result;

    }

    public static String getGenericSelectQuery() {
        return "SELECT * FROM COMPANY";
    }

    public static String getCreatTableQuery() {
        return "CREATE TABLE COMPANY "
                + "(ID INT PRIMARY KEY     NOT NULL,"
                + " NAME           TEXT    NOT NULL, "
                + " AGE            INT     NOT NULL, "
                + " ADDRESS        CHAR(50), "
                + " SALARY         REAL)";
    }

    public static String getUpdateQuery() {
        return "UPDATE COMPANY set SALARY = 1000.00 where ID=1;";
    }

    public static String getDeleteQuery() {
        return "DELETE from COMPANY where ID=1;";
    }

}
