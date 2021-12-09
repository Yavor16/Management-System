package Controllers.AdminSceneControllers.Categories;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        selectedItemMainCategory = AddCategories.getMainCategoryOfSelectedProduct(selectedItem);

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
        URL url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        return scene;
    }
}
