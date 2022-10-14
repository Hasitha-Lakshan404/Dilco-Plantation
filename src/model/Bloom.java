package model;

import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;

import java.util.Date;

public class Bloom extends Thread {
    public ImageView img;

    public Bloom(ImageView img) {
        // store parameter for later user
        this.img = img;
    }

    @Override
    public void run() {
        double i = 0;
        javafx.scene.effect.Bloom bl=new javafx.scene.effect.Bloom();
        while (i < 0.99) {
            bl.setThreshold(i);

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            img.setEffect(bl);

            i = i + 0.01;

        }

        bl.setThreshold(1);
        img.setEffect(bl);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BoxBlur blur = new BoxBlur(3,3,3);
        img.setEffect(blur);
    }
}