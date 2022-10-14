package controller;

import animatefx.animation.BounceOutRight;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class forgotPasswordsFormController {


    public AnchorPane DashboardContext;
    public FontAwesomeIconView icnArrow;
    public JFXTextField txtEmpId;
    public JFXTextField txtAddress;
    public JFXTextField txtUserName;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;

    public void icnClickOnAction(MouseEvent mouseEvent) throws IOException {
        new BounceOutRight(DashboardContext).play();

    }


    public void btnSearchOnAction(ActionEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM employee");

        while (result.next()){

            if(txtUserName.getText().equals(result.getString(7)) && txtEmail.getText().equals(result.getString(5)) ){

                txtPassword.setText(result.getString(8));
            }
        }
    }
}
