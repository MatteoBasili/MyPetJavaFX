package com.application.mypetfx.login.data;

import com.application.mypetfx.login.LoginContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import com.application.mypetfx.utils.exceptions.InvalidInputException;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.BackingStoreException;

public class LoginInteractor {

    private static final Logger logger = Logger.getLogger(LoginInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private final DBProfile dbProfile;
    private final LoginContract.LoginListener loginListener;

    public LoginInteractor(LoginContract.LoginListener loginListener) {
        this.loginListener = loginListener;
        dbProfile = new DBProfile();
    }

    public void login(LoginCredentials loginCredentials) {

        if (hasInputError(loginCredentials)) {
            return;
        }

        // For show loading
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        //here is the solution
                        Platform.runLater(() -> checkLogin(loginCredentials));

                    }
                }, 1500);

    }

    private boolean hasInputError(LoginCredentials loginCredentials) {
        String username = loginCredentials.getUsername();
        String password = loginCredentials.getPassword();
        try {
            if (username.isEmpty()) {
                throw new InvalidInputException("The username is empty.");
            }
            if (password.isEmpty()) {
                throw new InvalidInputException("The password is empty.");
            }
            if (password.length() < 8 || password.length() > 32) {
                throw new InvalidInputException("The password must be between 8 and 32 characters.");
            }
            return false;
        } catch (InvalidInputException e) {
            this.loginListener.onFailed(e.getMessage());
            return true;
        }
    }

    private void checkLogin(LoginCredentials loginCredentials) {

        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            int role = checkDB(loginCredentials, connection);
            this.dbProfile.closeConnection(connection);

            if(role == 0) {
                this.loginListener.onFailed("Something went wrong...");
            }
            else if (role != 1 && role != 2) {
                this.loginListener.onFailed("Invalid credentials!");
            } else {
                this.loginListener.onSuccess(role);
            }
        }
        catch (ConnectionFailedException e) {
            this.loginListener.onFailed(e.getMessage());
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }

    }

    private int checkDB(LoginCredentials loginCredentials, Connection connection) {
        String username = loginCredentials.getUsername();
        String password = loginCredentials.getPassword();

        CallableStatement stmt = null;
        int role = 0;
        String query = "{ call login(?, ?, ?) }";
        // Preparing a CallableStatement to call a procedure
        try {
            stmt = connection.prepareCall(query);
            // Setting the values for the IN parameters
            stmt.setString(1, username);
            stmt.setString(2, password);
            // Registering the type of the OUT parameter
            stmt.registerOutParameter(3, Types.INTEGER);
            // Executing the CallableStatement
            stmt.execute();

            // Retrieving the value for role
            role = stmt.getInt(3);
        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        }
        finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return role;
    }

}
