package controller;

import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


public class DashboardFormController {

    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public Pane apnMain;
    public BorderPane bPane;
    public Label lblTitle;

    public Label lblDate;
    public Label lblTime;


    public void initialize() throws IOException {

        setDate();
        new ZoomIn(bPane).play();
        HamburgerSlideCloseTransition transitions = new HamburgerSlideCloseTransition(hamburger);

        VBox box = FXMLLoader.load(getClass().getResource("../view/SidePaneDrawerForm.fxml"));
        drawer.setSidePane(box);

        bPane.setLeft(null);//Click On Action

        lordUi(box, MouseEvent.MOUSE_CLICKED, transitions);
        lordUi(box, MouseEvent.MOUSE_MOVED, transitions);


        hamburgerMove(hamburger, MouseEvent.MOUSE_CLICKED, transitions);


        setUi("../view/DashBoardHomeForm.fxml");

    }


    private void lordUi(VBox box, EventType<MouseEvent> parsedEvent, HamburgerSlideCloseTransition transitions) {
        for (Node node : box.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(parsedEvent, event -> {

                    switch (node.getAccessibleText()) {
                        case "m1":
                            setUi("../view/CustomerForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4CAF50"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                            lblTitle.setText("Customer Management");
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }

                            break;

                        case "m2":
                            setUi("../view/OrderForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#A2BF29"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                            lblTitle.setText("Order Management");
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }
//
                            break;

                        case "m3":
                            setUi("../view/PlantManagementForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4F4F4F"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                            lblTitle.setText("Plant Management");
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                            }
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }
//
                            break;

                        case "m4":
                            setUi("../view/StokeManagementForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D094DF"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());

                            lblTitle.setText("Stoke Management");
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }
//
                            break;

                        case "m5":
                            setUi("../view/SupplierManagementForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#d636d9"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                            lblTitle.setText("Suppliers Management");
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }
//

                            break;
                        case "m6":
                            setUi("../view/EmployeeManagementForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#603e87"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                            lblTitle.setText("Employee Management");
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }
//
                            break;

                        case "m7":
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                setUi("../view/IncomeReportForm.fxml");

//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#291a3b"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                                lblTitle.setText("Income Report");
                                if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                    drawer.close();
                                    bPane.setLeft(null);
                                    transitions.setRate(transitions.getRate() * -1);
                                    transitions.play();
                                }
//

                            }
                            break;

                        case "m8":
//
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();

                                BoxBlur blur = new BoxBlur(5, 5, 5);
                                bPane.setEffect(blur);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to LogOut?", ButtonType.YES, ButtonType.NO);
                                alert.setTitle("Log out");
                                alert.setHeaderText("Account Logout!!");
                                Optional<ButtonType> buttonType = alert.showAndWait();


                                if (buttonType.get().equals(ButtonType.YES)) {
                                    Stage stage = (Stage) bPane.getScene().getWindow();
                                    try {
                                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
                                        stage.centerOnScreen();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    bPane.setEffect(null);
                                }


                            }
//
                            break;

                        /*case "m9":
//                            apnMain.setBackground(new Background(new BackgroundFill(Paint.valueOf("#AEAEAE"), CornerRadii.EMPTY, Insets.EMPTY)));
//                            box.setBackground(apnMain.getBackground());
                            if (parsedEvent.getName().equals("MOUSE_CLICKED")) {
                                drawer.close();
                                bPane.setLeft(null);
                                transitions.setRate(transitions.getRate() * -1);
                                transitions.play();
                            }
//                            System.out.println("btn2");
                            break;*/

                    }

                });
            }

        }
    }

    private void hamburgerMove(JFXHamburger hamburger, EventType<MouseEvent> e, HamburgerSlideCloseTransition transitions) {
        transitions.setRate(-1); //normal     //1-Activated

        hamburger.addEventHandler(e, event -> {
            transitions.setRate(transitions.getRate() * -1);
            transitions.play();

            if (drawer.isOpened() || drawer.isOpening()) {
                drawer.close();
                bPane.setLeft(null);
            } else {
                bPane.setLeft(drawer);
                drawer.open();
            }
        });

        apnMain.addEventHandler(e, event -> {
            if (transitions.getRate() == 1) {   //because if not condition this hamburger always changed when click the dashboard(main pane)
                drawer.close();
                bPane.setLeft(null);
                transitions.setRate(transitions.getRate() * -1);
                transitions.play();
            }
        });


    }


    public void icnHomeOnAction(MouseEvent mouseEvent) {
        lblTitle.setText("DashBoard");
        apnMain.getChildren().clear();
        Parent parent2 = null;
        try {
            parent2 = FXMLLoader.load(getClass().getResource("../view/DashBoardHomeForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        apnMain.getChildren().add(parent2);
    }

    private void setUi(String ui) {
        apnMain.getChildren().clear();
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(ui));
            apnMain.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 public void setDate() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDate currentDate = LocalDate.now();

            //Displaying current time in 12hour format with AM/PM
            DateFormat dateFormat = new SimpleDateFormat("hh : mm : ss aa");
            String dateString = dateFormat.format(new Date()).toString();
            lblTime.setText(dateString);


//            System.out.println(currentDate.getMonthValue());

            String month = "";
            switch (currentDate.getMonthValue()) {
                case 1:
                    month = "Jan";
                    break;
                case 2:
                    month = "Feb";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "Sep";
                    break;
                case 10:
                    month = "Oct";
                    break;
                case 11:
                    month = "Nov";
                    break;
                case 12:
                    month = "Dec";
                    break;

            }
            lblDate.setText(currentDate.getYear() + "-" + month + "-" + currentDate.getDayOfMonth());


        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


}
