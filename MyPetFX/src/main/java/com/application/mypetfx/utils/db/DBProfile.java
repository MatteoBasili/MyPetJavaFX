package com.application.mypetfx.utils.db;

import java.sql.*;
import java.util.Properties;

import com.application.mypetfx.utils.properties.AppProperties;
import org.apache.log4j.Logger;

public class DBProfile {

    private static final Logger logger = Logger.getLogger(DBProfile.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";

    private final String ip = AppProperties.getInstance().getProperty("DB_IP_ADDRESS");
    private final String port = AppProperties.getInstance().getProperty("DB_PORT");
    private final String db = AppProperties.getInstance().getProperty("DB_NAME");
    private final String username = AppProperties.getInstance().getProperty("DB_USERNAME");
    private final String watchword = AppProperties.getInstance().getProperty("DB_WATCHWORD");

    public String getIp() {
        return this.ip;
    }

    public String getPort() {
        return this.port;
    }

    public String getDb() {
        return this.db;
    }

    public String getUsername() {
        return this.username;
    }

    public String getWatchword() {
        return this.watchword;
    }

    public Connection getConnection() {
        Connection connection = null;
        String connectionURL;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connectionURL = "jdbc:mysql://" + getIp() + ":" + getPort() + "/" + getDb() + "?user=" + getUsername() + "&password=" + getWatchword();
            Properties properties = new Properties();
            String timeout = "500";
            properties.put("connectTimeout", timeout);
            connection = DriverManager.getConnection(connectionURL, properties);
        } catch (SQLException se) {
            logger.error(SQL_EXCEPTION, se);
        } catch (Exception e) {
            logger.error("Error: ", e);
        }

        return connection;
    }

    public void closeStatement(CallableStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        }
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        }
    }

}
