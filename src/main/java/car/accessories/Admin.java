
package car.accessories;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin extends User {

    private ProductCat cat;
    static final Logger logger = Logger.getLogger(Admin.class.getName());
    String sql;
    ConnectDB conDB = new ConnectDB();
    int rowsAffected;
    private static final String SOME_THING_WRONG_MESSAGE ="\nsome thing went wrong please try again later";
    private static final String UPDATE_REQUEST="UPDATE install_request SET status = ? WHERE rid =?";
    private static final String UPDATE_PCATALOG="UPDATE productcatalog SET";

    public Admin(String userEmail,String userPassword)
    {
        super(userEmail, userPassword);
        cat = new ProductCat();

    }




    public void addCustomer(String email, String userP) throws SQLException {
        sql = "INSERT INTO systemusers (user_email,user_password,user_type) VALUES (? , ? , ?)";

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, userP);
            stmt.setString(3, "customer");
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User added successfully.");
            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }
    public void updatePass(String email, String userP) throws SQLException {
        sql = "UPDATE systemusers SET `user_password`= ? WHERE `user_email` = ?";

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,userP);
            stmt.setString(2, email);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("the password updated successfully.");
            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }
    public void updateEmail(String mail, String newEmail) throws SQLException {
        sql = "UPDATE systemusers SET `user_email`= ? WHERE `user_email` = ?";

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,newEmail);
            stmt.setString(2, mail);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("the email updated successfully.");
            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void deleteUser(String email) throws SQLException {
        sql = "DELETE FROM systemusers WHERE user_email = ?";
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,email);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User deleted successfully.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }


        }




    }

    public void showProductCatalog() throws SQLException {
        cat.showProductsCatalogToAdmin();
    }

    public void addProduct(String productName, String productType, int productPrice) throws SQLException {
        sql = "INSERT INTO productcatalog (productName, productType, productPrice, isAvilable) VALUES (?, ?, ?, ?)";
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,productName);
            stmt.setString(2,productType);
            stmt.setInt(3,productPrice);
            stmt.setInt(4,1);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product added successfully.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void isUnavailable(int pid) throws SQLException {
        sql =UPDATE_PCATALOG+" isAvilable = ? WHERE id = ?";
        conDB.testConn();
        try(PreparedStatement stmt= conDB.getConnection().prepareStatement(sql)){
            stmt.setInt(1,0);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product is now un available.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void changeProductName(int pid,String newName) throws SQLException {
        sql=UPDATE_PCATALOG+" productName = ? WHERE id = ?";
        conDB.testConn();
        try(PreparedStatement stmt= conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,newName);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product name updated.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void changeProductType(int pid, String newType) throws SQLException {
        sql=UPDATE_PCATALOG+" productType = ? WHERE id = ?";
        conDB.testConn();
        try(PreparedStatement stmt= conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,newType);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product type updated.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void changeProductPrice(int pid, int newPrice) throws SQLException {
        sql=UPDATE_PCATALOG+" productPrice = ? WHERE id =? ";
        conDB.testConn();
        try(PreparedStatement stmt= conDB.getConnection().prepareStatement(sql)){
            stmt.setInt(1,newPrice);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product price updated.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void deleteProduct(int pid) throws SQLException {
        sql="DELETE FROM productcatalog WHERE id = ?";
        conDB.testConn();
        try(PreparedStatement stmt= conDB.getConnection().prepareStatement(sql)){
            stmt.setInt(1,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product is deleted successfully.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }
        }
    }

    public void showCustomer() throws SQLException {
        logger.log(Level.INFO, "|        id       |   Customer Email   |");
        sql="SELECT * FROM systemusers WHERE user_type = ?";
        conDB.testConn();
        try(PreparedStatement stmt= conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,"customer");
            try (ResultSet resultSet = stmt.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    StringBuilder rowData = new StringBuilder();

                    for (int i = 1; i <= 2; i++) {
                        String columnValue = resultSet.getString(i);


                        String formattedValue = String.format("%-18s",columnValue);

                        rowData.append(formattedValue);

                        if (i < columnCount) {
                            rowData.append("|"); // Add a vertical bar as a separator between columns
                        }
                    }
                    if (logger.isLoggable(Level.INFO)) {
                        logger.log(Level.INFO, rowData.toString());
                    }
                }
            }

        }
    }

    public void showScheduledAppointments() throws SQLException {
        sql = "SELECT `rid`,`productName`,`productType`,`email`,`carModel`,`preferredDate`,`status`" +
                " FROM install_request WHERE status NOT IN ('canceled', 'completed');";
        conDB.testConn();

        try (PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)) {

            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-15s | %-20s | %-20s | %-35s | %-30s | %-20s | %-15s |%n";
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format(format, "Request Number", "Product Name", "Product Type", "Customer Email", "Car Model", "Installation Time", "Status"));
            }
            int[] columnWidths={15,20,20,35,30,20,15};

            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();

                for (int i = 1; i <= numberOfColumns; i++) {

                    String columnValue = rSet.getString(i);
                    String formSpes="%-" + columnWidths[i-1] + "s";
                    String formattedColumn = String.format(formSpes, columnValue);
                    rowData.append(formattedColumn);
                    rowData.append(" | "); // Add separator between columns

                }
                String formatSpes="| %-15s ";
                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, String.format(formatSpes, rowData.toString()));
                }
            }
        }
    }

    public void scheduleNewAppointment(int rid, String mail,String time) throws SQLException {
        assignTO(rid,mail);
        setTime(rid,time);
        setScheduled(rid);

    }
    public void setScheduled(int rid) throws SQLException {

        conDB.testConn();
        try(PreparedStatement stmt =conDB.getConnection().prepareStatement(UPDATE_REQUEST)){
            stmt.setString(1,"scheduled");
            stmt.setInt(2,rid);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to scheduled.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }

        }

    }

    public void assignTO(int rid,String email) throws SQLException {
        sql = "UPDATE install_request SET assigned = ? WHERE rid =?";
        conDB.testConn();
        try(PreparedStatement stmt =conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setInt(2,rid);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("The request has been assigned to :%s", email));
                }
            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }

        }
    }

    public void setTime(int rid,String time) throws SQLException {
        sql = "UPDATE install_request SET preferredDate = TIMESTAMP(CONCAT(CAST(DATE(preferredDate) AS DATE), ' ', ?))"+
                " WHERE rid=?";
        conDB.testConn();
        try(PreparedStatement stmt =conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,time);
            stmt.setInt(2,rid);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request time scheduled.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }

        }
    }

    public void setStatusToCompleted(int rid) throws SQLException {

        conDB.testConn();
        try(PreparedStatement stmt =conDB.getConnection().prepareStatement(UPDATE_REQUEST)){
            stmt.setString(1,"completed");
            stmt.setInt(2,rid);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to completed.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }

        }
    }

    public void setStatusToCanceled(int rid) throws SQLException {
        conDB.testConn();
        try(PreparedStatement stmt =conDB.getConnection().prepareStatement(UPDATE_REQUEST)){
            stmt.setString(1,"canceled");
            stmt.setInt(2,rid);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to canceled.");

            } else {
                logger.warning(SOME_THING_WRONG_MESSAGE);
            }

        }
    }
}
