package controller;


import animatefx.animation.BounceIn;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Bloom;
import org.controlsfx.control.Notifications;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInFormController {


    public Pane pane1;
    public Pane pane2;
    public Pane pane3;
    public Pane pane4;

    public Circle circleDashFrame;
    public Circle circleDashFrame1;
    public Circle circleDashFrame11;
    public Circle circleDashFrame111;
    public ImageView imgLogin;
    public Pane pneRightSide;
    public AnchorPane apnMainPane;
    public JFXPasswordField pwdPassword;
    public FontAwesomeIconView icnEye;
    public JFXTextField txtPassword;
    public JFXTextField txtUserName;
    public JFXButton btnSignIn;
    public BorderPane borderPaneMain;
    public AnchorPane apnsideBlackPane;
    public Label lblforgot;
    public Label lblSignUp;
    public JFXComboBox cmbRole;
    public ImageView imgLock;


    public void initialize() throws SQLException, ClassNotFoundException {

        imgLock.setVisible(false);
        setEnableDisableSignIn();

        ObservableList<String> data= FXCollections.observableArrayList("Admin","Manager");
        cmbRole.setItems(data);

        //for password shown eye
        txtPassword.setVisible(false);

        //copy values for passwordField
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            pwdPassword.setText(newValue);
        });

        //Round Animation on Screen
        setRotate(circleDashFrame, true, 360, 400);
        setRotate(circleDashFrame1, true, 360, 400);
        setRotate(circleDashFrame11, true, 360, 400);
        setRotate(circleDashFrame111, true, 360, 400);

        //Change pane | Animation
        slideAnimation();

        //Image Bloom and blur
        Runnable r = new Bloom(imgLogin);
        new Thread(r).start();
    }

    private void slideAnimation()    {
        //start pane4
        FadeTransition p4 = new FadeTransition(Duration.seconds(3), pane4);
        p4.setFromValue(1);
        p4.setDelay(Duration.seconds(1));
        p4.setToValue(0);
//        p4.setCycleCount(1);
        p4.play();

        p4.setOnFinished(event4 -> {
            //start pane3
            FadeTransition p3 = new FadeTransition(Duration.seconds(3), pane3);
            p3.setFromValue(1);
            p3.setDelay(Duration.seconds(1));
            p3.setToValue(0);
            p3.play();

            p3.setOnFinished(event3 -> {
                //start pane2
                FadeTransition p2 = new FadeTransition(Duration.seconds(3), pane2);
                p2.setFromValue(1);
                p2.setDelay(Duration.seconds(1));
                p2.setToValue(0);
                p2.play();

                p2.setOnFinished(event2 -> {
                    //start pane2
                    FadeTransition p22 = new FadeTransition(Duration.seconds(3), pane2);
                    p22.setToValue(1);
                    p22.setDelay(Duration.seconds(1));
                    p22.setFromValue(0);
                    p22.play();

                    p22.setOnFinished(event22 -> {
                        //start pane3
                        FadeTransition p33 = new FadeTransition(Duration.seconds(3), pane3);
                        p33.setToValue(1);
                        p33.setDelay(Duration.seconds(1));
                        p33.setFromValue(0);
                        p33.play();

                        p33.setOnFinished(event33 -> {
                            //start pane3
                            FadeTransition p44 = new FadeTransition(Duration.seconds(3), pane4);
                            p44.setToValue(1);
                            p44.setDelay(Duration.seconds(1));
                            p44.setFromValue(0);
                            p44.play();
                            p44.setOnFinished(event44 -> {
                                slideAnimation();
                            });

                        });

                    });

                });

            });

        });

    }

    public void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition r1 = new RotateTransition(Duration.seconds(duration), c);
        r1.setAutoReverse(reverse);

        r1.setByAngle(angle);
        r1.setDelay(Duration.seconds(0));
        r1.setRate(70);
        r1.setCycleCount(400);
        r1.play();
    }


    public void signInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if(!cmbRole.getSelectionModel().isEmpty()){

            if(cmbRole.getValue().equals("Admin")){
                ResultSet result=CrudUtil.execute("SELECT UserName, Password FROM employee");

                while(result.next()){

                    if(result.getString(1).equals(txtUserName.getText()) && result.getString(2).equals(pwdPassword.getText())){
                        Stage stage = (Stage) apnMainPane.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
                        stage.centerOnScreen();

                        Notifications notifications = Notifications.create();
                        notifications.title("Login Successful");
                        notifications.text("Have a Nice Day....");
                        notifications.hideAfter(Duration.seconds(8));
                        notifications.position(Pos.BOTTOM_RIGHT);
                        notifications.graphic(new ImageView("asserts/images/us.png"));
//                        notifications.darkStyle();
                        notifications.show();
                    }else{
                        //call to Snack bar
                        errorMsg("Check  Username  Password  and  try  again!!! ");
                    }

                }

            }else if(cmbRole.getValue().equals("Manager")){
                ResultSet result=CrudUtil.execute("SELECT UserName, Password FROM employee");

                while(result.next()){

                    if(result.getString(1).equals(txtUserName.getText()) && result.getString(2).equals(pwdPassword.getText())){
                        Stage stage = (Stage) apnMainPane.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagerDashboardForm.fxml"))));
                        stage.centerOnScreen();

                    }else{
                        //call to Snack bar
                        errorMsg("Check  Username  Password  and  try  again!!! ");
                    }

                }
            }

        }else{
            errorMsg("Please Select Your Roll !!!");
        }


    }


    private void errorMsg(String error) {
        JFXSnackbar snackbar = new JFXSnackbar(pneRightSide);

        JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(new Label(error), Duration.seconds(3.33), null);
        snackbar.enqueue(snackbarEvent);

        snackbar.setPrefWidth(600);

    }

    public void eyeClickOnAction(MouseEvent mouseEvent) {

        if(icnEye.getGlyphName().equals("EYE_SLASH")){ // must show password
            icnEye.setGlyphName("EYE");

            txtPassword.setText(pwdPassword.getText()); //copy PwdPassword data to  txtPW
            pwdPassword.setVisible(false);  //PWField hidden
            txtPassword.setVisible(true);   //txtField Shown

        }else if(icnEye.getGlyphName().equals("EYE")){  // must hide  password
            icnEye.setGlyphName("EYE_SLASH");

            pwdPassword.setText(txtPassword.getText());
            txtPassword.setVisible(false); //txtField hide
            pwdPassword.setVisible(true);  //PWField shown

        }
    }

    public void forgotPasswordOnClick(MouseEvent mouseEvent) throws IOException {
        new BounceIn(lblforgot).play();

        Parent root= FXMLLoader.load(getClass().getResource("../view/ForgotPasswordForm.fxml"));
        Scene scene=btnSignIn.getScene();

//            root.translateYProperty().set(scene.getHeight());
        root.translateXProperty().set(scene.getWidth());

        apnMainPane.getChildren().add(root);

        Timeline timeline=new Timeline();
        KeyValue kv=new KeyValue(root.translateXProperty(),598, Interpolator.EASE_IN);
        KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(kf);

        timeline.setOnFinished(event -> {
//            apnMainPane.getChildren().remove(borderPaneMain);
//            apnMainPane.getChildren().remove(apnsideBlackPane);
        });
        timeline.play();
    }

    private void setEnableDisableSignIn() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT COUNT(empId)  FROM employee");

        if(result.next()) {

            if(result.getInt(1)>0) {
                lblSignUp.setDisable(true);
                imgLock.setVisible(true);
            }
        }
    }

    public void signUpClickAction(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        new BounceIn(lblSignUp).play();

        Parent root = FXMLLoader.load(getClass().getResource("../view/SignUpForm.fxml"));
        Scene scene = btnSignIn.getScene();

//            root.translateYProperty().set(scene.getHeight());
        root.translateXProperty().set(scene.getWidth());

        apnMainPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 598, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);

        timeline.setOnFinished(event -> {
//            apnMainPane.getChildren().remove(borderPaneMain);
//            apnMainPane.getChildren().remove(apnsideBlackPane);
        });
        timeline.play();
    }
}
