package controller;

import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class IncomeReportFormController {
    public LineChart tblMonthly;
    public JFXTextField txtYear;

    public LineChart lineAnnualChart;
    public JFXTextField txtAnnualYearFrom;
    public JFXTextField txtAnnualYearTo;
    public Pane paneVisible;

    public void initialize() throws SQLException, ClassNotFoundException {
//        =================MONTH==============
        tblMonthly.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");

        txtYear.setText(String.valueOf(getNowYear()));
        setMonth();


//        =================YEAR==============

        paneVisible.setVisible(false);
        lineAnnualChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        txtAnnualYearTo.setText(String.valueOf(getNowYear()));
        txtAnnualYearFrom.setText(String.valueOf(getNowYear()-4));

        setYear();


    }

//    =================   MONTHLY   ================================

    private void setMonth() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT handoverDate,cost FROM `order` WHERE year(handoverDate)=?", Integer.parseInt(txtYear.getText()));

        double jan = 0;
        double feb = 0;
        double march = 0;
        double april = 0;
        double may = 0;
        double jun = 0;
        double july = 0;
        double aug = 0;
        double sep = 0;
        double oct = 0;
        double nov = 0;
        double dec = 0;


        while (result.next()) {
            LocalDate d = result.getDate(1).toLocalDate();

            switch (d.getMonthValue()) {
                case 1:
                    jan += result.getDouble(2);
                    break;
                case 2:
                    feb += result.getDouble(2);
                    break;
                case 3:
                    march += result.getDouble(2);
                    break;
                case 4:
                    april += result.getDouble(2);
                    break;
                case 5:
                    may += result.getDouble(2);
                    break;
                case 6:
                    jun += result.getDouble(2);
                    break;
                case 7:
                    july += result.getDouble(2);
                    break;
                case 8:
                    aug += result.getDouble(2);
                    break;
                case 9:
                    sep += result.getDouble(2);
                    break;
                case 10:
                    oct += result.getDouble(2);
                    break;
                case 11:
                    nov += result.getDouble(2);
                    break;
                case 12:
                    dec += result.getDouble(2);
                    break;
            }

        }


//       tblMonthly.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Monthly wise Income");
        //populating the series with data
        series.getData().add(new XYChart.Data("January", jan));
        series.getData().add(new XYChart.Data("February", feb));
        series.getData().add(new XYChart.Data("March", march));
        series.getData().add(new XYChart.Data("April", april));
        series.getData().add(new XYChart.Data("May", may));
        series.getData().add(new XYChart.Data("June", jun));
        series.getData().add(new XYChart.Data("July", july));
        series.getData().add(new XYChart.Data("August", aug));
        series.getData().add(new XYChart.Data("September", sep));
        series.getData().add(new XYChart.Data("October", oct));
        series.getData().add(new XYChart.Data("November", nov));
        series.getData().add(new XYChart.Data("December", dec));



        ResultSet resultRevenue = CrudUtil.execute("SELECT payedDate,Total FROM  payment  WHERE year(payedDate)=?", Integer.parseInt(txtYear.getText()));

        double janR = 0;
        double febR = 0;
        double marchR = 0;
        double aprilR = 0;
        double mayR = 0;
        double junR = 0;
        double julyR = 0;
        double augR = 0;
        double sepR = 0;
        double octR = 0;
        double novR = 0;
        double decR = 0;

        while (resultRevenue.next()) {
            LocalDate d = resultRevenue.getDate(1).toLocalDate();

            switch (d.getMonthValue()) {
                case 1:
                    janR += resultRevenue.getDouble(2);
                    break;
                case 2:
                    febR += resultRevenue.getDouble(2);
                    break;
                case 3:
                    marchR += resultRevenue.getDouble(2);
                    break;
                case 4:
                    aprilR += resultRevenue.getDouble(2);
                    break;
                case 5:
                    mayR += resultRevenue.getDouble(2);
                    break;
                case 6:
                    junR += resultRevenue.getDouble(2);
                    break;
                case 7:
                    julyR += resultRevenue.getDouble(2);
                    break;
                case 8:
                    augR += resultRevenue.getDouble(2);
                    break;
                case 9:
                    sepR += resultRevenue.getDouble(2);
                    break;
                case 10:
                    octR += resultRevenue.getDouble(2);
                    break;
                case 11:
                    novR += resultRevenue.getDouble(2);
                    break;
                case 12:
                    decR += resultRevenue.getDouble(2);
                    break;
            }

        }

        XYChart.Series seriesRe = new XYChart.Series();
        seriesRe.setName("Monthly wise Expenditure");
        //populating the series with data
        seriesRe.getData().add(new XYChart.Data("January", janR));
        seriesRe.getData().add(new XYChart.Data("February", febR));
        seriesRe.getData().add(new XYChart.Data("March", marchR));
        seriesRe.getData().add(new XYChart.Data("April", aprilR));
        seriesRe.getData().add(new XYChart.Data("May", mayR));
        seriesRe.getData().add(new XYChart.Data("June", junR));
        seriesRe.getData().add(new XYChart.Data("July", julyR));
        seriesRe.getData().add(new XYChart.Data("August", augR));
        seriesRe.getData().add(new XYChart.Data("September", sepR));
        seriesRe.getData().add(new XYChart.Data("October", octR));
        seriesRe.getData().add(new XYChart.Data("November", novR));
        seriesRe.getData().add(new XYChart.Data("December", decR));

        tblMonthly.getData().addAll(series,seriesRe);
    }


    private int getNowYear() {
        LocalDate date = LocalDate.now();
        return date.getYear();

    }


    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        tblMonthly.getData().clear();
        setMonth();
    }


//        =================   YEARLY   ================================

    private void setYear() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT handoverDate,cost FROM `order`");

        //use +1 to get earnings this entered year to other year.
        int count=Integer.parseInt(txtAnnualYearTo.getText())-Integer.parseInt(txtAnnualYearFrom.getText())+1;

        long[][] ar =new long[count][2];  //5


        int tempYear=Integer.parseInt(txtAnnualYearTo.getText());

        for(int i=count-1; i>=0;i--){
            ar[i][0]=tempYear;
            tempYear--;
        }

        for (long []x:ar
             ) {

        }

        while(result.next()) {
            //getLocalDate
            LocalDate d = result.getDate(1).toLocalDate();

            for (int i = 0; i < count; i++) {

                if(d.getYear()==ar[i][0]){

                    ar[i][1]+=result.getDouble(2);
                }
            }

        }

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Annually");
        //populating the series with data


        for(int i=0;i<ar.length;i++){
            series.getData().add(new XYChart.Data(String.valueOf(ar[i][0]), ar[i][1]));
        }

        lineAnnualChart.getData().add(series);
    }

    public void btnSearchAnnualOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, InterruptedException {
        lineAnnualChart.getData().clear();
        setYear();
        paneVisible.setVisible(true);
    }

    public void pneMouseClickOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        lineAnnualChart.getData().clear();
        setYear();
        paneVisible.setVisible(false);
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        tblMonthly.getData().clear();
        lineAnnualChart.getData().clear();
        initialize();
        paneVisible.setVisible(true);
    }
}
