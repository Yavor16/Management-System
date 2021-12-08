package Controllers.AdminSceneControllers.Categories;

import java.net.URL;
import java.util.*;

import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.AllChildCategories;
import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.Category;

import java.io.*;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.fxml.*;

public class ChooseCategoryController extends LoadCategories implements Initializable, AddCategories{
    @FXML
    TreeView<String> categoryView;
    @FXML
    Button addCategoryNameBttn;
    @FXML
    TextField categoryName;

    public static List<String> mainCategoryNames = new ArrayList<>();
    private TreeItem<String> selectedItem;
    public static String chosenCategory;
    public static String selcectedItemMainCategory;
    private Alert alert = new Alert(AlertType.ERROR);

    public void initialize(URL url, ResourceBundle rb ){

        loadCategories();
        
        categoryView.setContextMenu(CreateContextMenu());
        categoryView.setRoot(rootCategories);
        rootCategories.setExpanded(true);
        categoryView.setShowRoot(false);
        categoryName.setVisible(false);
        addCategoryNameBttn.setVisible(false);
    }
    private ContextMenu CreateContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        
        MenuItem addMenuItem = new MenuItem("Add Category");
        MenuItem deleteMenuItem = new MenuItem("Delete Category");
        
        addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                categoryName.setVisible(true);
                addCategoryNameBttn.setVisible(true);
            } 
        });
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                selectedItem = categoryView.getSelectionModel().getSelectedItem();
               
                deleteCategory(selectedItem);
            } 
        });
        contextMenu.getItems().addAll(addMenuItem, deleteMenuItem);
        
        return contextMenu;
    }
    private void deleteCategory(TreeItem<String> categoryToDel) {
        if (mainCategoryNames.contains(categoryToDel.getValue())) {
            mainCategoryNames.remove(categoryToDel.getValue());
        }
        for (int i = 0; i < categoryToDel.getChildren().size(); i++) {
            categoryToDel.getChildren().remove(categoryToDel.getChildren().get(i));
        }
        categoryToDel.getParent().getChildren().remove(categoryToDel);
        
        saveAndLoadCategoriesFromNewFile();
    }
    
    public void SelectItem(MouseEvent e) throws IOException{
        
        if (e.getButton() == MouseButton.PRIMARY) {
            selectedItem = categoryView.getSelectionModel().getSelectedItem();
            
            if (selectedItem != null) {
                chosenCategory = selectedItem.getValue();
                
                Stage stage = createAddNewProductStage();
                
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();  
                stage = (Stage)categoryView.getScene().getWindow();
                stage.close();
            }
        } else if(e.getButton() == MouseButton.MIDDLE){
            categoryView.getSelectionModel().select(null);
        }
    }
    private Stage createAddNewProductStage(){
        Stage stage = new Stage();

        try {
            stage.setScene(CreateScene(getNewSceneToCreate()));
        } catch (IOException e) {
            alert.setContentText("Could not find the file");
            alert.show();
        }
        
        return stage;
    }
    private String getNewSceneToCreate(){
        switch (getMainCategoryOfSelectedProduct(selectedItem)) {
            case "Technology":
                return "TechnologyAddScene";
            case "Food":
                return "VegetablesAddScene";
            case "Clothes":
                return "ClothesScene";
            default:
                selcectedItemMainCategory = getMainCategoryOfSelectedProduct(selectedItem);
                return "AddProduct";
        }
    }
    private String getMainCategoryOfSelectedProduct(TreeItem<String> item){
        String category = "";
        while(!item.getParent().getValue().equals("Categories")){
            category = item.getParent().getValue();
            item = item.getParent();
        }
        return category;
    }
    private Scene CreateScene(String fileName) throws IOException{
        URL url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        return scene;
    }
    public void AddCategoryName(ActionEvent e) {
        selectedItem = categoryView.getSelectionModel().getSelectedItem();
        TreeItem<String> newItem = new TreeItem<String>(categoryName.getText());

        if (selectedItem == null) {
            rootCategories.getChildren().add(newItem);
            mainCategoryNames.add(categoryName.getText());
        } else{
            selectedItem.getChildren().add(newItem);
            addNewCategoryToDB(categoryName.getText(), newItem);
        }
        hideControls();
        
        getMainCategories();
        saveAndLoadCategoriesFromNewFile();
    } 
    private void addNewCategoryToDB(String name, TreeItem<String>item){
        Category newCategory = new Category(getIdOfMainCategory(getMainCategoryOfSelectedProduct(item)), selectedItem.getValue(), name);
        AllChildCategories.childCategories.add(newCategory);
    }
    private int getIdOfMainCategory(String mainCat){
        for (Map.Entry<Integer, TreeItem<String>> category : addedCategories.entrySet()) {
            String nameOfCurrentCat = category.getValue().getValue(); 
            if (nameOfCurrentCat.equals(mainCat)) {
                return category.getKey();
            }
        }
        return 0;
    }
    private void hideControls(){
        categoryName.setVisible(false);
        categoryName.setText("");
        addCategoryNameBttn.setVisible(false);
    }
    private void saveAndLoadCategoriesFromNewFile(){
        saveMainCategories(mainCategoryNames);
        saveCategories();
    }

    private void getMainCategories(){
        mainCategoryNames.clear();

        for (TreeItem<String> name : rootCategories.getChildren()) {
            if (name.getParent().getValue().equals("Categories")) {
                mainCategoryNames.add(name.getValue());
            }
        }
    }
}
