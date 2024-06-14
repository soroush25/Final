package src.model.tools;

import lombok.Getter;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class ConnectionProvider {
    @Getter
    private static ConnectionProvider connectionProvider = new ConnectionProvider();
    private static BasicDataSource basicDataSource = new BasicDataSource();

    private ConnectionProvider() {
    }

    public Connection getConnection() throws SQLException {
        basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        basicDataSource.setUrl("jdbc:oracle:thin:@10.211.55.6:1522:XE");
        basicDataSource.setUsername("user1");
        basicDataSource.setPassword("1234");
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxTotal(20);
        return basicDataSource.getConnection();
    }

    public int getNextId(String sequenceName) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + sequenceName + ".NEXTVAL AS NEXT_ID FROM DUAL");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("NEXT_ID");
    }
}
