package Controllers.AdminSceneControllers.Categories;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.*;
import java.io.*;

import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import java.net.URL;

public class CreateAddProductScene {
    private Alert alert = new Alert(AlertType.ERROR);
    private TreeItem<String> selectedItem;
    public static String selectedItemMainCategory;

    public CreateAddProductScene(TreeItem<String> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void openAndCloseNewAddProductStage(){
        Stage stage = createAddNewProductStage();
                
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();  
    }
    private Stage createAddNewProductStage(){
        Stage stage = new Stage();

        try {
            stage.setScene(CreateScene(getWhichSceneToCreate()));
        } catch (IOException e) {
            alert.setContentText("Could not find the file");
            alert.show();
        }
        
        return stage;
    }
    private String getWhichSceneToCreate(){
        selectedItemMainCategory = GetMainCategory.getMainCategoryOfSelectedProduct(selectedItem);

        switch (selectedItemMainCategory) {
            case "Technology":
                return "TechnologyAddScene";
            case "Food":
                return "VegetablesAddScene";
            case "Clothes":
                return "ClothesScene";
            default:
                return "AddProduct";
        }
    }
    private Scene CreateScene(String fileName) throws IOException{
        URL url = new File("Main/src/Views/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        return scene;
    }
}
