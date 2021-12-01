package Controllers;

import javafx.scene.control.*;
import java.util.*;

import Models.ItemToBuyModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ManageBills {
    public static Queue<VBox> bills = new LinkedList<>();
    private static int MAX_BILLS_AMOUNT = 10;

    public static void createBill(String date,  List<String> products){

        VBox vb = createVBoxForNewBill(date);
    
        for (String prod : products) {
            TextField tf = new TextField();
            tf.setEditable(false);
            tf.setText(prod);
            vb.getChildren().add(tf);
        }
        bills.add(vb);
    }
    private static VBox createVBoxForNewBill(String date){
        VBox vb = new VBox();
        vb.prefWidth(247);
        vb.getChildren().add(createLabelForNewBill(date));
    
        return vb;
    }
    private static Label createLabelForNewBill(String date){
        Label lb = new Label();
        lb.setText(date);
        lb.setTextFill(Color.WHITE);
        lb.prefHeight(50);
        lb.prefWidth(247);

        return lb;
    }
    public static void addNewBill(ObservableList<ItemToBuyModel>productsToBuy){
        VBox newVBox = createVBoxForNewBill(productsToBuy);
        
        if (bills.size() >= MAX_BILLS_AMOUNT) {
            bills.remove();
            bills.add(newVBox);
        } else{
            bills.add(newVBox);
        }
    }
    private static VBox createVBoxForNewBill(ObservableList<ItemToBuyModel>productsToBuy){
        VBox vb = new VBox();
        vb.prefWidth(247);
        
        vb.getChildren().add(createDateAndTimeLabel());
        
        for (ItemToBuyModel itemToBuyModel : productsToBuy) {
            TextField tf = new TextField();
            tf.setText(itemToBuyModel.GetProduct().GetName() + "=" + itemToBuyModel.GetAmount() +"x"+ itemToBuyModel.GetProduct().GetPrice());
            tf.setEditable(false);
            vb.getChildren().add(tf);
        }

        return vb;
    }
    private static Label createDateAndTimeLabel(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime now = LocalDateTime.now();

        Label label = new Label();
        label.setText(dtf.format(now));
        label.setTextFill(Color.WHITE);

        label.prefHeight(50);
        label.prefWidth(247);
        
        return label;
    }
}
