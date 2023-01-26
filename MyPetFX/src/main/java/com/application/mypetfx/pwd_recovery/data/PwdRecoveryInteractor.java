package com.application.mypetfx.pwd_recovery.data;

import com.application.mypetfx.pwd_recovery.PwdRecoveryContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.email.SendMail;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import com.application.mypetfx.utils.exceptions.InvalidInputException;
import com.application.mypetfx.utils.input.InputValidator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Timer;
import java.util.TimerTask;

public class PwdRecoveryInteractor {

    private static final Logger logger = Logger.getLogger(PwdRecoveryInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private final DBProfile dbProfile;
    private final PwdRecoveryContract.PWDRecoveryListener pwdRecoveryListener;

    public PwdRecoveryInteractor(PwdRecoveryContract.PWDRecoveryListener pwdRecoveryListener) {
        this.pwdRecoveryListener = pwdRecoveryListener;
        dbProfile = new DBProfile();
    }

    public void recoverPassword(PwdRecoveryCredentials pwdRecoveryCredentials) {

        if (hasInputError(pwdRecoveryCredentials)) {
            return;
        }

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> recover(pwdRecoveryCredentials));
                    }
                }, 0);

    }

    private boolean hasInputError(PwdRecoveryCredentials pwdRecoveryCredentials) {
        String email = pwdRecoveryCredentials.getEmail();
        String petName = pwdRecoveryCredentials.getPetName();

        InputValidator validator = new InputValidator();
        try {
            if (validator.isEmpty(email)) {
                throw new InvalidInputException("The email is empty.");
            }
            if (!validator.isValidEmail(email)) {
                throw new InvalidInputException("The email is invalid.");
            }

            if (validator.isEmpty(petName)) {
                throw new InvalidInputException("The pet name is empty.");
            }
            return false;
        } catch (InvalidInputException e) {
            this.pwdRecoveryListener.onFailed(e.getMessage());
            return true;
        }
    }

    private void recover(PwdRecoveryCredentials pwdRecoveryCredentials) {

        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            String password = takePwdFromDB(pwdRecoveryCredentials, connection);
            this.dbProfile.closeConnection(connection);

            if(password == null) {
                this.pwdRecoveryListener.onFailed("Invalid credentials!");
            } else if (password.equals("")) {
                this.pwdRecoveryListener.onFailed("Something went wrong...");
            } else {

                // Send email
                Task<Void> task = new Task<>() {
                    @Override
                    public Void call() {
                        sendEmail(pwdRecoveryCredentials.getEmail(), password);
                        return null;
                    }
                };
                task.setOnSucceeded(taskFinishEvent -> {
                    try {
                        this.pwdRecoveryListener.onSuccess();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                new Thread(task).start();

            }
        }
        catch (ConnectionFailedException e) {
            this.pwdRecoveryListener.onFailed(e.getMessage());
        }

    }

    private String takePwdFromDB(PwdRecoveryCredentials pwdRecoveryCredentials, Connection connection) {
        String email = pwdRecoveryCredentials.getEmail();
        String petName = pwdRecoveryCredentials.getPetName();

        CallableStatement stmt = null;
        String password = "";
        String query = "{ call recover_password(?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the values for the IN parameters
            stmt.setString(1, email);
            stmt.setString(2, petName);
            // Registering the type of the OUT parameter
            stmt.registerOutParameter(3, Types.VARCHAR);
            // Executing the CallableStatement
            stmt.execute();

            // Retrieving the value for role
            password = stmt.getString(3);
        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        }
        finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return password;
    }

    private void sendEmail(String email, String password) {
        String emailSubject = "PASSWORD RECOVERY";
        String emailMessage = "Here is your password:\n\n---   " + password + "   ---\n\nDon't forget it again, or our furry friends will get angry!";

        SendMail sm = new SendMail(email, emailSubject, emailMessage);
        sm.sendMail();
    }

}
