package Database;

import Model.GlobalConstains;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        Class.forName(GlobalConstains.SQLITE_DRIVER);
        SQLiteConfig config = new SQLiteConfig();
        config.setOpenMode(SQLiteOpenMode.FULLMUTEX);
        return DriverManager.getConnection(GlobalConstains.SQLITE_URL, config.toProperties());
    }

    synchronized public static void createTable()
    {
        try
        {
            Connection connection = getDbConnection();
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS history" +
                    "(name varchar(256) NOT NULL, " +
                    "count int NOT NULL);";
            statement.execute(query);
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error with create database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
