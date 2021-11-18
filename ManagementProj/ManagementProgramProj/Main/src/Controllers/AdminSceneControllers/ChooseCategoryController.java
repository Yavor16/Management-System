package Controllers.AdminSceneControllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseCategoryController implements Initializable{
    @FXML
    TreeView<String> categoryView;
    @FXML
    Button addCategoryNameBttn;
    @FXML
    TextField categoryName;

    static List<TreeItem<String>> parentsList = new ArrayList<>();
    static TreeItem<String>categories;
    static List<String>mainCategoryNames = new ArrayList<>();
    TreeItem<String> selectedItem;
    public static String chosenCategory;

    public void initialize(URL url, ResourceBundle rb ){
        try {
            LoadCategories();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        
        categoryView.setContextMenu(CreateContextMenu());
        categoryView.setRoot(categories);
        categories.setExpanded(true);
        categoryView.setShowRoot(false);
        categoryName.setVisible(false);
        addCategoryNameBttn.setVisible(false);
    }
    ContextMenu CreateContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        
        MenuItem addMenuItem = new MenuItem("Add Child");
        MenuItem deleteMenuItem = new MenuItem("Delete Child");
        
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
                try {
                    DeleteCategory(selectedItem);
                } catch (Exception exe) {
                    
                    exe.getLocalizedMessage();
                } 
            } 
        });
        contextMenu.getItems().addAll(addMenuItem, deleteMenuItem);
        
        return contextMenu;
    }
    public static String selcectedItemMainCategory;
    
    public void SelectItem(MouseEvent e) throws IOException{
        
        if (e.getButton() == MouseButton.PRIMARY) {
            selectedItem = categoryView.getSelectionModel().getSelectedItem();
            
            if (selectedItem != null && !parentsList.contains(selectedItem)) {
                chosenCategory = selectedItem.getValue();
                Stage stage = new Stage();
                
                switch (GetMainCategory(selectedItem)) {
                    case "Technology":
                    stage.setScene(CreateScene("TechnologyAddScene"));
                    break;
                    case "Food":
                    stage.setScene(CreateScene("VegetablesAddScene"));
                    break;
                    case "Clothes":
                    stage.setScene(CreateScene("ClothesScene"));
                    break;
                    default:
                    stage.setScene(CreateScene("AddProduct"));
                    selcectedItemMainCategory = GetMainCategory(selectedItem);
                    break;
                }
                
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();  
                stage = (Stage)categoryView.getScene().getWindow();
                stage.close();
            }
        } else if(e.getButton() == MouseButton.MIDDLE){
            categoryView.getSelectionModel().select(null);
        }
    }
    Scene CreateScene(String fileName) throws IOException{
        URL url = new File("Main/src/Scenes/AdminScenes/" + fileName + ".fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        return scene;
    }
    public void AddCategoryName(ActionEvent e) throws IOException, ClassNotFoundException{
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

        File f = new File("tree_structure.txt");
        f.delete();
        SaveCategories(categories, "root");
        GetMainCategories();
    }
    String GetMainCategory(TreeItem<String> item){
        String category = "";
        while(!item.getParent().getValue().equals("Categories")){
            category = item.getParent().getValue();
            item = item.getParent();
        }
        return category;
    }
    void SaveCategories(TreeItem<String> root, String parent) {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(new File("tree_structure.txt"), true))){
            writer.println(root.getValue() + "=" + parent);

            for(TreeItem<String> child: root.getChildren()){
                if(child.getChildren().isEmpty()){
                    writer.println(child.getValue() + "=" + root.getValue());
                } else {
                    SaveCategories(child, child.getParent().getValue());
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    void LoadCategories() throws IOException, ClassNotFoundException{
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

            if (data.size() > 0) {
                List<TreeItem<String>> parentItems = GetChildrenNodes(data, categories.getValue().toString());
                categories.getChildren().addAll(parentItems);
            }
            RemoveEndNodes();
            GetMainCategories();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static List<TreeItem<String>> GetChildrenNodes(HashMap<String, String> hMap, String parent)
    {
        ArrayList<TreeItem<String>> children = new ArrayList<>();
        TreeItem<String> newChild = new TreeItem<>();
        for (Map.Entry<String, String> entry : hMap.entrySet()) {
            if(entry.getValue().equals(parent)) {
                newChild = new TreeItem<>(entry.getKey());
                newChild.getChildren().addAll(GetChildrenNodes(hMap, entry.getKey()));
                parentsList.add(newChild);
                children.add(newChild);
            }
        }
        return children;
    }
    static void RemoveEndNodes(){
        for (int i = 0; i < parentsList.size(); i++) {
            if (parentsList.get(i).getChildren().isEmpty()) {
                parentsList.remove(parentsList.get(i));
                i--;
            }
        }
    }
    void DeleteCategory(TreeItem<String> categoryToDel) throws ClassNotFoundException, IOException{
        for (int i = 0; i < categoryToDel.getChildren().size(); i++) {
            categoryToDel.getChildren().remove(categoryToDel.getChildren().get(i));
        }
        categoryToDel.getParent().getChildren().remove(categoryToDel);

        File f = new File("tree_structure.txt");
        f.delete();
        SaveCategories(categories, "root");
        LoadCategories();
        GetMainCategories();
    }
    public void GetMainCategories(){
        mainCategoryNames.clear();
        for (TreeItem<String> name : categories.getChildren()) {
            if (name.getParent().getValue().equals("Categories")) {
                mainCategoryNames.add(name.getValue());
            }
        }
    }
   
}
