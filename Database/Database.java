/* Add the following to your java project XML file:
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>6.4.0.jre8</version>
</dependency>

and 

<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
*/


/* Create the following configuration file and replace the variables with your database information:
package config;

public class config {
    public static final String hostName = <YourDBHost>;
    public static final String dbName = <YourDBName>;
    public static final String user = <YourDBUser>;
    public static final String password = <YourDBPassword>;
}
*/

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import config.config;

public class Database {

 public static void main(String[] args) {
     // Create your SQL statement (return value will be null for SELECT queries).
        String sqlQuery = "";
        String dbResponse = connectDb(sqlQuery);
        System.out.println(dbResponse);
    }

 public static String connectDb(String sqlQuery) {
     // Connect to database
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", config.hostName, config.dbName, config.user, config.password);
        Connection connection = null;

        try {
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlQuery)) {
                        //save returned data into a string if applicable
                        String dbResponse = "";
                        while (resultSet.next()){
                            for(int i=1; i<=resultSet.getMetaData().getColumnCount(); i++){
                                dbResponse = dbResponse.concat(resultSet.getString(i) + " ");
                            }
                            dbResponse = dbResponse.concat("\n");
                        }
                connection.close();
                return dbResponse; //returns null if no data was returned
                }                   
        }
        catch (Exception e) {
                e.printStackTrace();
                return "exception";
        }
    }
}