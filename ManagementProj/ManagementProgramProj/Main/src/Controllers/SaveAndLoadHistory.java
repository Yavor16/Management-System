package Controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Controllers.UserSceneControllers.MainScene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SaveAndLoadHistory {

    public static void SaveFile(){
        if (!MainScene.queueForBill.isEmpty() ) {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt", false));
                List<String> bills = new ArrayList<>();
                String currentBill = "";
                
                for (VBox item : MainScene.queueForBill) {
                    currentBill = "";
                    for (var d : item.getChildren()) {
                        if (d instanceof Label) {
                            Label l = (Label)d;
                            currentBill += l.getText();
                        } else{
                            TextField tf = (TextField)d;
                            currentBill +=" " +  tf.getText();
                        }
                    }
                    bills.add(currentBill);
                }
                for (String string : bills) {
                    bw.write(string + "\n");
                }
                MainScene.queueForBill = new LinkedList<>();
                bw.close();
                
            } catch(IOException e){
                System.out.println(e);
            }
        }
    }
    public static void LoadFile(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String line;
            String[] words;
            List<String> products = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                words = line.split(" ");
                String date = words[0] + " " + words[1];

                for (int i = 2; i < words.length; i++) {
                    products.add(words[i]);        
                }
                CreateBill(date, products);
                products.clear();
            }
            br.close();
        } catch(IOException e){
            System.out.println(e);
        }
    }
    private static void CreateBill(String date,  List<String> products){

        VBox vb = new VBox();
        vb.prefWidth(247);

        Label lb = new Label();
        lb.setText(date);
        lb.setTextFill(Color.WHITE);
        lb.prefHeight(50);
        lb.prefWidth(247);
        vb.getChildren().add(lb);
    
        for (String prod : products) {
            TextField tf = new TextField();
            tf.setEditable(false);
            tf.setText(prod);
            vb.getChildren().add(tf);
        }
        MainScene.queueForBill.add(vb);
    }
}
