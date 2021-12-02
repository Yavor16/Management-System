package Controllers.AdminSceneControllers;

import java.net.URL;
import java.util.*;


import java.io.*;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.fxml.*;

public class ChooseCategoryController implements Initializable{
    @FXML
    TreeView<String> categoryView;
    @FXML
    Button addCategoryNameBttn;
    @FXML
    TextField categoryName;

    private static List<TreeItem<String>> parentsList = new ArrayList<>();
    private static TreeItem<String>categories;
    static List<String> mainCategoryNames = new ArrayList<>();
    private TreeItem<String> selectedItem;
    public static String chosenCategory;
    public static String selcectedItemMainCategory;
    private Alert alert = new Alert(AlertType.ERROR);

    public void initialize(URL url, ResourceBundle rb ){

        loadCategories();
        
        categoryView.setContextMenu(CreateContextMenu());
        categoryView.setRoot(categories);
        categories.setExpanded(true);
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
            
            if (selectedItem != null && !parentsList.contains(selectedItem)) {
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
    private Scene CreateScene(String fileName) throws IOException{
        URL url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        return scene;
    }
    public void AddCategoryName(ActionEvent e) {
        selectedItem = categoryView.getSelectionModel().getSelectedItem();
        TreeItem<String> newItem = new TreeItem<String>(categoryName.getText());

        parentsList.add(selectedItem);
        if (selectedItem == null) {
            categories.getChildren().add(newItem);
            parentsList.add(newItem);
        } else{
            selectedItem.getChildren().add(newItem);
        }

        categoryName.setVisible(false);
        categoryName.setText("");
        addCategoryNameBttn.setVisible(false);

        saveAndLoadCategoriesFromNewFile();
       
        getMainCategories();
    }
    private String getMainCategoryOfSelectedProduct(TreeItem<String> item){
        String category = "";
        while(!item.getParent().getValue().equals("Categories")){
            category = item.getParent().getValue();
            item = item.getParent();
        }
        return category;
    }
    private static void removeEndNodes(){
        for (int i = 0; i < parentsList.size(); i++) {
            if (parentsList.get(i).getChildren().isEmpty()) {
                parentsList.remove(parentsList.get(i));
                i--;
            }
        }
    }
    private static List<TreeItem<String>> getChildrenNodes(HashMap<String, String> hMap, String parent)
    {
        ArrayList<TreeItem<String>> children = new ArrayList<>();
        TreeItem<String> newChild = new TreeItem<>();

        for (Map.Entry<String, String> entry : hMap.entrySet()) {
            if(entry.getValue().equals(parent)) {
                newChild = new TreeItem<>(entry.getKey());
                newChild.getChildren().addAll(getChildrenNodes(hMap, entry.getKey()));

                parentsList.add(newChild);
                children.add(newChild);
            }
        }
        return children;
    } 
    private void saveAndLoadCategoriesFromNewFile(){
        File f = new File("tree_structure.txt");
        f.delete();
    
        saveCategories(categories, "root");
        loadCategories();
        
    }private void saveCategories(TreeItem<String> root, String parent) {
        try(PrintWriter writer = new PrintWriter(createFileAndOutputStream())){
            
            writer.println(root.getValue() + "=" + parent);
            
            for(TreeItem<String> child: root.getChildren()){
                if(child.getChildren().isEmpty()){
                    writer.println(child.getValue() + "=" + root.getValue());
                } else {
                    saveCategories(child, child.getParent().getValue());
                }
            }
        } catch (FileNotFoundException e) {
            alert.setContentText("Could not find the save file");
            alert.show();
            return;
        }
    }
    private FileOutputStream createFileAndOutputStream() throws FileNotFoundException{
        
        File saveFile = new File("tree_structure.txt");
        FileOutputStream saveFileOutputStream = new FileOutputStream(saveFile, true);
        
        return saveFileOutputStream;
    }
    private void loadCategories(){
        categories = new TreeItem<>("Categories");
        HashMap<String, String> data = new HashMap<>();

        File file = new File("tree_structure.txt");

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String st;
            while((st=br.readLine()) != null) {
                String[] splitLine = st.split("=");
                data.put(splitLine[0], splitLine[1]);
            }

            getChildrenNodesAndToParent(data);

            removeEndNodes();
            getMainCategories();
        } catch (IOException ex) {
            alert.setContentText("Could not load save");
            alert.show();
        }
    }
    private void getChildrenNodesAndToParent(HashMap<String,String> data){
        if (data.size() > 0) {
            List<TreeItem<String>> parents = getChildrenNodes(data, categories.getValue().toString());
            categories.getChildren().addAll(parents);
        }
    }
    private void getMainCategories(){
        mainCategoryNames.clear();

        for (TreeItem<String> name : categories.getChildren()) {
            if (name.getParent().getValue().equals("Categories")) {
                mainCategoryNames.add(name.getValue());
            }
        }
    }
   
}
