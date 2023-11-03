package com.example.fastmood2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegistrationController {

    @FXML
    private TextField fullName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;


    @FXML
    private ToggleButton toggleButton;
    @FXML
    private PasswordField adminKey;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;
    @FXML
    private Label registerLabel;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public final String key = "admin";
    public boolean admin = false;

    int id = 6;

    String call = "{call REGISTERCUSTOMER(?,?,?,?,?)}";
    String callAdmin = "{call REGISTERADMIN(?,?,?,?,?)}";

    public void activateAdminKey(ActionEvent event) throws IOException {

    }

    public void registerUser(ActionEvent event) throws IOException {
        registerLabel.setTextFill(Color.rgb(3, 125, 80));

        id++;
        String name = fullName.getText(); // eixe username.getText();
        String phone = phoneNumber.getText();
        String mail = email.getText();
        String user = username.getText();
        String pass = password.getText();

        String keyTextField = adminKey.getText();

        if (keyTextField.equals(key)) {
            admin = true;
        } else {
            admin = false;
        }


//        String sql = "insert into customers values("+ id +",'"+ name +"','"+ phone +"','"+ mail +"','"+ user +"','"+ pass +"')";
        Connection connection = null;
        //CallableStatement stmt = null;

        // new2
//        String call = "{callFindReservationsByDate REGISTERCUSTOMER(?,?,?,?,?)}";
//        String callAdmin = "{callFindReservationsByDate REGISTERADMIN(?,?,?,?,?)}";
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.6.21:1521:dblabs", "it185351", "Oreoskodikos_33");
            //stmt = connection.createStatement();
//            boolean result = statement.execute(sql);
//            System.out.println("inserted "+ result);
//            idCounter += 1;

//  new          statement.executeUpdate("INSERT INTO Customers(CID, FULLNAME, PHONE, EMAIL, USERNAME, PASSWORD) VALUES ('5', 'FN', 'PH', 'E', 'USR', 'PSWD')");


// new2            statement.executeQuery("REGISTERUSER("+ id +",'"+ name +"','"+ phone +"','"+ mail +"','"+ user +"','"+ pass +"')");

            if (admin==false) {
                try (CallableStatement stmt = connection.prepareCall(call)) {
//                    stmt.setInt(1, id);

//                    stmt.setString(1, name);
//                    stmt.setString(2, phone);
//                    stmt.setString(3, mail);
//                    stmt.setString(4, user);
//                    stmt.setString(5, pass);

                    stmt.setString(1, fullName.getText());
                    stmt.setString(2, phoneNumber.getText());
                    stmt.setString(3, email.getText());
                    stmt.setString(4, username.getText());
                    stmt.setString(5, password.getText());
                    stmt.execute();

                    System.out.println("CUSTOMER: Row Inserted!");
                    registerLabel.setText("Registration successful!");
                }
            } else if (admin) {
                try (CallableStatement stmt = connection.prepareCall(callAdmin)) {
//                    stmt.setInt(1, id);
                    stmt.setString(1, name);
                    stmt.setString(2, phone);
                    stmt.setString(3, mail);
                    stmt.setString(4, user);
                    stmt.setString(5, pass);
                    stmt.execute();
                    System.out.println("STAFF: Row Inserted!");
                    registerLabel.setText("Registration successful!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

    }

    public void helloScene(ActionEvent event) throws IOException {
        changeTheScene(event, "hello-view.fxml");
    }

    public void changeTheScene(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
