package controller;

import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Customer;
import util.CrudUtil;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DashBoardHomeFormController {
    public Circle circleDashboard;
    public JFXButton btnAddCustomer;
    public JFXButton btnSearchPlant;
    public Label lblGoodWhat;
    public ImageView imgSeeImage;
    public Label lblPlantVarieties;
    public Label lblCropsVarieties;
    public Label lblSeedVaraities;
    public Label lblAllPendingOrder;

    public BarChart<String, Integer> barChartPendingOrder;
    public NumberAxis y;
    public CategoryAxis x;

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
//        initBarChart();
        setPendingOrder();
        setPlantVarieties();
        setRotate(circleDashboard, true, 360, 400);
        setDay();
        setBarChart();
        barChartPendingOrder.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
//        barChartPendingOrder.lookup(".")/*setLegendSide()*/;
    }

    private void setBarChart() throws SQLException, ClassNotFoundException {

        ResultSet result=CrudUtil.execute("SELECT handoverDate, qty  FROM `order` WHERE status='Pending'");

        LocalDate currentDate = LocalDate.now();
        int week1Qty=0;      //0->4
        int week1p5Qty=0;    //5->9
        int week2Qty=0;      //10->14
        int week2p5Qty=0;    //15->19
        int week3Qty=0;      //20->25
        int week3p5Qty=0;    //25->30/31

        int week1Qty2=0;
        int week1p5Qty2=0;
        int week2Qty2=0;
        int week2p5Qty2=0;
        int week3Qty2=0;
        int week3p5Qty2=0;




        while(result.next()){
            Date d=result.getDate(1);
            LocalDate localDate = d.toLocalDate();

            if(localDate.getMonthValue()==currentDate.getMonthValue()){

                if(localDate.getDayOfMonth()>25){
                    week3p5Qty+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>20){
                    week3Qty+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>15){
                    week2p5Qty+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>10){
                    week2Qty+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>5){
                    week1p5Qty+= result.getInt(2);

                }else {
                    week1Qty+= result.getInt(2);
                }

            }else if(localDate.getMonthValue()==(currentDate.getMonthValue()+1)) { //next month


                if(localDate.getDayOfMonth()>25){
                    week3p5Qty2+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>20){
                    week3Qty2+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>15){
                    week2p5Qty2+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>10){
                    week2Qty2+= result.getInt(2);

                }else if(localDate.getDayOfMonth()>5){
                    week1p5Qty2+= result.getInt(2);

                }else {
                    week1Qty2+= result.getInt(2);
                }
            }

        }

        XYChart.Series set =new XYChart.Series<>();
        set.setName(currentDate.getMonth().name()+" Pending");
        set.getData().add(new XYChart.Data("First 5 days",week1Qty) );
        set.getData().add(new XYChart.Data("2nd 5 days",week1p5Qty) );
        set.getData().add(new XYChart.Data("3rd 5 days",week2Qty) );
        set.getData().add(new XYChart.Data("4th 5 days",week2p5Qty) );
        set.getData().add(new XYChart.Data("5th 5 days",week3Qty) );
        set.getData().add(new XYChart.Data("Last 5 days",week3p5Qty) );

        XYChart.Series set2 =new XYChart.Series<>();


        String d=searchMonth(currentDate.getMonthValue());
        set2.setName(d);
        set2.getData().add(new XYChart.Data("First 5 days",week1Qty2) );
        set2.getData().add(new XYChart.Data("2nd 5 days",week1p5Qty2) );
        set2.getData().add(new XYChart.Data("3rd 5 days",week2Qty2) );
        set2.getData().add(new XYChart.Data("4th 5 days",week2p5Qty2) );
        set2.getData().add(new XYChart.Data("5th 5 days",week3Qty2) );
        set2.getData().add(new XYChart.Data("Last 5 days",week3p5Qty2) );


        barChartPendingOrder.getData().addAll(set,set2);


    }

    private String searchMonth(int month) {
        switch (month) {
            case 1: return "February Pending";
            case 2:return "March Pending";
            case 3:return "April Pending";
            case 4:return "May Pending";
            case 5:return "June Pending";
            case 6:return "Jule Pending";
            case 7:return "August Pending";
            case 8:return "September Pending";
            case 9:return "October Pending";
            case 10:return "November Pending";
            case 11:return "December Pending";
            case 12:return "The End of The year";
        }
        return"";
    }

    public void setDay() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();


            if (currentTime.getHour() >= 19) {
                //night
                lblGoodWhat.setText("Night......");
                imgSeeImage.setImage(null);
                imgSeeImage.setImage(new Image("asserts/images/nightpg.png"));

            } else if (currentTime.getHour() >= 15) {
                //evening
                lblGoodWhat.setText("Evening...");

                imgSeeImage.setImage(null);
                imgSeeImage.setImage(new Image("asserts/images/evepngN.png"));
            } else if (currentTime.getHour() >= 12) {
                //Afternoon
                lblGoodWhat.setText("Afternoon...");
                imgSeeImage.setImage(null);
                imgSeeImage.setImage(new Image("asserts/images/afterpng.png"));
            } else {
                //gm
                imgSeeImage.setImage(null);
                imgSeeImage.setImage(new Image("asserts/images/morningPgLa.png"));
                lblGoodWhat.setText("Morning...");
            }


        }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    private void initBarChart() {
        //Creating X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        //Adding labels for the axes
        yAxis.setLabel("Month");
        xAxis.setLabel("Orders");
        //Creating a Bar chart
//        BarChart barChart = new BarChart<>(xAxis, yAxis);

        //Preparing data for the bar chart
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("January");
        series1.getData().add(new XYChart.Data("Jan", 107));


        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Feb");
        series2.getData().add(new XYChart.Data("Feb", 31));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("March");
        series3.getData().add(new XYChart.Data("March", 635));
        series3.getData().add(new XYChart.Data("1900", 947));
        series3.getData().add(new XYChart.Data("2008", 4054));
        series3.getData().add(new XYChart.Data("2010", 4054));
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Europe");
        series4.getData().add(new XYChart.Data("1800", 203));
        series4.getData().add(new XYChart.Data("1900", 408));
        series4.getData().add(new XYChart.Data("2008", 732));
        series4.getData().add(new XYChart.Data("2010", 7325));
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("Oceania");
        series5.getData().add(new XYChart.Data("1800", 2));
        series5.getData().add(new XYChart.Data("1900", 6));
        series5.getData().add(new XYChart.Data("2008", 3434));
        series5.getData().add(new XYChart.Data("2010", 3434));

        XYChart.Series series6 = new XYChart.Series();
//        XYChart.Data series6 = new XYChart.Data<>("March",1511);

        series6.getData().add(new XYChart.Data("1800", 2000));
        series6.getData().add(new XYChart.Data("1900", 6));
        series6.getData().add(new XYChart.Data("2008", 34));
        series6.getData().add(new XYChart.Data("2010", 444));
        //Setting the data to bar chart
        barChartPendingOrder.getData().addAll(series1, series2, series3, series4, series5);
        //Setting the legend on the top
        barChartPendingOrder.setLegendSide(Side.RIGHT);
    }

    private void setPlantVarieties() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT COUNT(DISTINCT plantType)  FROM plant");
        if (result.next()) {
            lblPlantVarieties.setText(result.getString(1));
        }
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

    private void setPendingOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(status) FROM `order` WHERE `status` = 'Pending'");

        if (resultSet.next()) {
            lblAllPendingOrder.setText(resultSet.getString(1));
        }
    }

    public void btnMovedOnAction(MouseEvent mouseEvent) {
        if (((JFXButton) mouseEvent.getSource()).getText().equals("Customer")) {
            new Pulse(btnAddCustomer).play();
        } else {
            new Pulse(btnSearchPlant).play();
        }


    }

    public void btnSeePlants(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PlantShowTableForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
//        CustomerUpdateFormController controller = loader.getController();

        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void btnAddCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CustomerForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
//        CustomerUpdateFormController controller = loader.getController();

        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage(StageStyle.UTILITY);

        stage.setScene(scene);
        stage.show();


    }
}
