package Controllers;

import static Controllers.ManageBills.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.*;
import java.io.*;

import javafx.scene.layout.VBox;

public class SaveAndLoadHistory {
    private static Alert alert = new Alert(AlertType.ERROR);
    
    public static void SaveFile(){
        if (!bills.isEmpty() ) {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt", false));
                List<String> newBills = new ArrayList<>();
                String currentBill = "";
                
                for (VBox item : bills) {
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
                    newBills.add(currentBill);
                }
                for (String string : newBills) {
                    bw.write(string + "\n");
                }
                bills = new LinkedList<>();
                bw.close();
                
            } catch(IOException e){
                alert.setHeaderText("");
                alert.setContentText("Cannot save your history");
                alert.show();
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
                createBill(date, products);
                products.clear();
            }
            br.close();
        } catch(IOException e){
            alert.setHeaderText("");
            alert.setContentText("Cannot load your history");
            alert.show();
        }
    }
}
