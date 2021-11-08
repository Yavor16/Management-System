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

    public static String chosenCategory;

    public void initialize(URL url, ResourceBundle rb ){

        TreeItem<String>categories = new TreeItem<>("Categories");

        categories.getChildren().addAll(InitializeTechnologyCateg(), InitializeVegAndFruitCateg(), InitializeClothes());

        categories.setExpanded(true);
        categoryView.setShowRoot(false);
    
        categoryView.setRoot(categories);
    }
    TreeItem<String> InitializeTechnologyCateg(){
        TreeItem<String>technology = new TreeItem<>("Technology");
        TreeItem<String>blackTechnology = new TreeItem<>("Black Technology");
        TreeItem<String>whiteTechnology = new TreeItem<>("White Technology");
        
        TreeItem<String>laptop = new TreeItem<>("Laptop");
        TreeItem<String>tv = new TreeItem<>("TV");
        TreeItem<String>monitor = new TreeItem<>("Monitor");
        TreeItem<String>smartphone = new TreeItem<>("Smartphone");
        TreeItem<String>tablet = new TreeItem<>("Tablet");
        
        TreeItem<String>microwave = new TreeItem<String>("Microwave");
        TreeItem<String>grill = new TreeItem<String>("Grill");
        TreeItem<String>oven = new TreeItem<String>("Oven");
        
        whiteTechnology.getChildren().addAll(microwave, grill, oven);
        blackTechnology.getChildren().addAll(laptop, tv, monitor, smartphone, tablet);
        technology.getChildren().addAll(blackTechnology,whiteTechnology);

        parentsList.add(technology.getValue());
        parentsList.add(blackTechnology.getValue());
        parentsList.add(whiteTechnology.getValue());

        return technology;
    }
    TreeItem<String> InitializeVegAndFruitCateg(){

        TreeItem<String>food = new TreeItem<>("Food");
        TreeItem<String>vegetables = new TreeItem<>("Vegetables");
        TreeItem<String>fruit = new TreeItem<>("Fruit");
        
        TreeItem<String>tomato = new TreeItem<>("Tomato");
        TreeItem<String>onion = new TreeItem<>("Onion");
        TreeItem<String>cucumber = new TreeItem<>("Cucumber");
        TreeItem<String>garlic = new TreeItem<>("Garlic");
        TreeItem<String>pumkin = new TreeItem<>("Pumkin");
        
        TreeItem<String>orange = new TreeItem<>("Orange");
        TreeItem<String>apple = new TreeItem<>("Apple");
        TreeItem<String>peach = new TreeItem<>("Peach");
        TreeItem<String>mango = new TreeItem<>("Mango");
        TreeItem<String>banana = new TreeItem<>("Banana");
        

        parentsList.add(food.getValue());
        parentsList.add(vegetables.getValue());
        parentsList.add(fruit.getValue());

        vegetables.getChildren().addAll(tomato, onion, cucumber, garlic, pumkin);
        fruit.getChildren().addAll(orange, apple, peach, mango, banana);

        food.getChildren().addAll(vegetables,fruit);

        return food;
    }
    TreeItem<String> InitializeClothes(){
        TreeItem<String>clothes = new TreeItem<>("Clothes");
        TreeItem<String>male = new TreeItem<>("Male");
        TreeItem<String>female = new TreeItem<>("Female");
        TreeItem<String>kid = new TreeItem<>("Kid");
        
        TreeItem<String>tshirt = new TreeItem<>("T-Shirt");
        TreeItem<String>shorts = new TreeItem<>("Shorts");
        TreeItem<String>sandals = new TreeItem<>("Sandals");
        TreeItem<String>hawaiianshirt = new TreeItem<>("Hawaiian shirt");
        TreeItem<String>jeans = new TreeItem<>("Jeans");
        
        TreeItem<String>skirt = new TreeItem<>("Skirt");
        TreeItem<String>bra = new TreeItem<>("Bra");
        TreeItem<String>dress = new TreeItem<>("Dress");
        TreeItem<String>sheathdress = new TreeItem<>("Sheath dress");
        TreeItem<String>hoodie = new TreeItem<>("Hoodie");
        
        TreeItem<String>bodysuit = new TreeItem<>("Bodysuit");
        TreeItem<String>romper = new TreeItem<>("Romper");
        TreeItem<String>sleesuits = new TreeItem<>("Sleepsuits");
        TreeItem<String>babygrow = new TreeItem<>("Babygrow");

        parentsList.add(clothes.getValue());
        parentsList.add(male.getValue());
        parentsList.add(female.getValue());
        parentsList.add(kid.getValue());
        //ObservableList l = new JSO
        male.getChildren().addAll(tshirt, shorts, sandals, hawaiianshirt, jeans);
        female.getChildren().addAll(skirt, bra, dress, sheathdress, hoodie);
        kid.getChildren().addAll(bodysuit, romper, sleesuits, babygrow);

        clothes.getChildren().addAll(male,female, kid);
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
