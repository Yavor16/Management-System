package Controllers.AdminSceneControllers.Categories;

import java.net.URL;
import java.util.*;

import Controllers.DataBaseFunctions.ProductFunctionality.AllProducts;

import java.io.*;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.fxml.*;

public class ChooseCategoryController extends LoadCategories implements Initializable{
    @FXML
    TreeView<String> categoryView;
    @FXML
    Button addCategoryNameBttn;
    @FXML
    TextField categoryName;

    private TreeItem<String> selectedItem;
    public static String chosenCategory;

    public void initialize(URL url, ResourceBundle rb ){

        loadCategories();
        initializeCategoryView();
        
        categoryName.setVisible(false);
        addCategoryNameBttn.setVisible(false);
    }
    private void initializeCategoryView(){
        categoryView.setContextMenu(CreateContextMenu());
        categoryView.setRoot(rootCategories);
        rootCategories.setExpanded(true);
        categoryView.setShowRoot(false);
    }
    private ContextMenu CreateContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        
        contextMenu.getItems().addAll(createAddCateogoryMenuItem(), createDeleteCateogoryMenuItem());
        
        return contextMenu;
    }
    private MenuItem createAddCateogoryMenuItem(){
        MenuItem addMenuItem = new MenuItem("Add Category");

        addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                switchControlsVisibilty();
            } 
        });
    
        return addMenuItem;
    }
    private MenuItem createDeleteCateogoryMenuItem(){
        MenuItem deleteMenuItem = new MenuItem("Delete Category");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                selectedItem = categoryView.getSelectionModel().getSelectedItem();
                DeleteCategory.deleteCategory(selectedItem);
                saveAndLoadCategoriesFromNewFile();
                loadCategories();
                AllProducts.getProducts();
            } 
        });

        return deleteMenuItem;
    }
    public void SelectItem(MouseEvent e) throws IOException{
        if (e.getButton() == MouseButton.PRIMARY) {
            selectedItem = categoryView.getSelectionModel().getSelectedItem();
            
            if (selectedItem != null) {
                boolean hasCategoryNoChildren = selectedItem.getChildren().size() == 0;
                boolean hasParent = selectedItem.getParent().getValue() != "Categories"; 
                if (hasCategoryNoChildren && hasParent) {
                    
                    chosenCategory = selectedItem.getValue();
                    CreateAddProductScene newScene = new CreateAddProductScene(selectedItem);
                    newScene.openAndCloseNewAddProductStage();
                }
            }
        } else if(e.getButton() == MouseButton.MIDDLE){
            categoryView.getSelectionModel().select(null);
        }
    }
    public void AddCategoryName(ActionEvent e) {
        selectedItem = categoryView.getSelectionModel().getSelectedItem();
        String newCategoryName = categoryName.getText();
        
        if (AddCategories.canAddNewCategory(newCategoryName)) {
            
            createAndAddNewCategory(newCategoryName);
            
            switchControlsVisibilty();
            saveAndLoadCategoriesFromNewFile();
        } else{
            createAlertForExistingCategory();
        }
    } 
    private void createAndAddNewCategory(String newCategoryName){
        TreeItem<String> newItem = new TreeItem<String>(newCategoryName);
            
        if (selectedItem == null) {
            rootCategories.getChildren().add(newItem);
            AddCategories.addMainCategory(newCategoryName);
        } else{
            selectedItem.getChildren().add(newItem);
            AddCategories.addChildCategory(newCategoryName, newItem);
        }
    }
    private void switchControlsVisibilty(){
        if (categoryName.isVisible()) {
            categoryName.setVisible(false);
            categoryName.setText("");
            addCategoryNameBttn.setVisible(false);
        } else{
            categoryName.setVisible(true);
            addCategoryNameBttn.setVisible(true);
        }
    }
    private void saveAndLoadCategoriesFromNewFile(){
        AddCategories.saveMainCategories();
        AddCategories.saveCategories();
    }
    private void createAlertForExistingCategory(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("");
        alert.setContentText("This category already exists!");
        alert.setTitle("Invalid category name");
        alert.show();
    }
}
