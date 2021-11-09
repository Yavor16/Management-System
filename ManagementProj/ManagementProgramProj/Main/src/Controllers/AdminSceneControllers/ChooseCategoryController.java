package Controllers.AdminSceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseCategoryController implements Initializable{
    @FXML
    TreeView<String> categoryView;
    
    List<String> parentsList = new ArrayList<>();

    public static String[][] technologyCategories = {
        {"Laptop", "TV", "Monitor", "Tablet"},
        {"Samsung", "Huawei", "Tablet"}
    };
    public static String[][] foodCategories = {
        {"Tomato", "Onion", "Cucumber", "Garlic", "Pumkin"},
        {"Orange", "Apple", "Peach", "Mango", "Banana"}
    };
    public static String[][] clothCategories = {
        {"T-Shirt", "Shorts", "Sandals", "Hawaiian shirt", "Jeans"},
        {"Skirt", "Bra", "Dress", "Sheath dress", "Hoodie"},
        {"Bodysuit","Romper","Sleepsuits","Babygrow"}
    };
    public static String chosenCategory;

    public void initialize(URL url, ResourceBundle rb ){

        TreeItem<String>categories = new TreeItem<>("Categories");

        categories.getChildren().add(InitializeTechnologyCateg());
        categories.getChildren().add(InitializeVegAndFruitCateg());
        categories.getChildren().add(InitializeClothes());
        

        categories.setExpanded(true);
        categoryView.setShowRoot(false);
    
        categoryView.setRoot(categories);
    }
    TreeItem<String> InitializeTechnologyCateg(){
        TreeItem<String>technology = new TreeItem<>("Technology");
        
        TreeItem<String>other = new TreeItem<>("Other Technologies");
        TreeItem<String>smarthphones = new TreeItem<>("Smarthphone");
        
        for (int i = 0; i < technologyCategories.length; i++) {
            for (int j = 0; j < technologyCategories[i].length; j++) {
                TreeItem<String>item = new TreeItem<>(technologyCategories[i][j]);
                if (i == 0) {
                    other.getChildren().add(item);
                }
                else{
                    smarthphones.getChildren().add(item);
                }
            }
        }

        technology.getChildren().add(other);
        technology.getChildren().add(smarthphones);

        parentsList.add(technology.getValue());
        parentsList.add(smarthphones.getValue());
        parentsList.add(other.getValue());

        return technology;
    }
    TreeItem<String> InitializeVegAndFruitCateg(){

        TreeItem<String>food = new TreeItem<>("Food");
        TreeItem<String>vegetables = new TreeItem<>("Vegetables");
        TreeItem<String>fruit = new TreeItem<>("Fruit");
        
        for (int i = 0; i < foodCategories.length; i++) {
            for (int j = 0; j < foodCategories[i].length; j++) {
                TreeItem<String>item = new TreeItem<>(foodCategories[i][j]);
                if (i == 0) {
                    vegetables.getChildren().add(item);
                }
                else{
                    fruit.getChildren().add(item);
                }
            }
        }

        parentsList.add(food.getValue());
        parentsList.add(vegetables.getValue());
        parentsList.add(fruit.getValue());


        food.getChildren().add(fruit);
        food.getChildren().add(vegetables);

        return food;
    }
    
    TreeItem<String> InitializeClothes(){
        TreeItem<String>clothes = new TreeItem<>("Clothes");
        TreeItem<String>male = new TreeItem<>("Male");
        TreeItem<String>female = new TreeItem<>("Female");
        TreeItem<String>kid = new TreeItem<>("Kid");
        
        for (int i = 0; i < clothCategories.length; i++) {
            for (int j = 0; j < clothCategories[i].length; j++) {
                TreeItem<String>item = new TreeItem<>(clothCategories[i][j]);
                if (i == 0) {
                    male.getChildren().add(item);
                }
                else if (i == 1){
                    female.getChildren().add(item);
                }
                else{
                    kid.getChildren().add(item);
                }
            }
        }

        parentsList.add(clothes.getValue());
        parentsList.add(male.getValue());
        parentsList.add(female.getValue());
        parentsList.add(kid.getValue());
       
        clothes.getChildren().add(male);
        clothes.getChildren().add(female);
        clothes.getChildren().add(kid);

        return clothes;
    }
    
    public void SelectItem() throws IOException{
        TreeItem<String> item = categoryView.getSelectionModel().getSelectedItem();
        if (item != null && !parentsList.contains(item.getValue())) {
            chosenCategory = item.getValue();
            Stage stage = new Stage();
            switch (item.getParent().getParent().getValue()) {
                case "Technology":
                    stage.setScene(CreateScene("TechnologyAddScene"));
                    break;
                case "Food":
                    stage.setScene(CreateScene("VegetablesAddScene"));
                    break;
                case "Clothes":
                    stage.setScene(CreateScene("ClothesScene"));
                    break;
            }
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();  
            stage = (Stage)categoryView.getScene().getWindow();
            stage.close();
        }
    } 
    Boolean IsParentSelected(TreeItem<String> item){
        for(String categ: parentsList){
            if (item.getValue() == categ) {
                return true;
            }
        }
        return false;            
    }
    Scene CreateScene(String fileName) throws IOException{
        URL url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        return scene;
    }
}
