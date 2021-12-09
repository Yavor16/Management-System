package Controllers.AdminSceneControllers.Categories;

import java.util.*;

import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.*;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.AllMainCategories;
import javafx.scene.control.TreeItem;

public abstract class LoadCategories {
    protected static TreeMap<Integer, TreeItem<String>> addedCategories = new TreeMap<>();

    protected static TreeItem<String> rootCategories = new TreeItem<>("Categories");

    protected static void loadCategories(){
        getAndLoadMainCategories();

    }
    private static void getAndLoadMainCategories(){
        AllMainCategories.getMainCategories();
        AllChildCategories.getChildrenCategories();

        rootCategories.getChildren().clear();

        for (Map.Entry<Integer,String> category : AllMainCategories.mainCategories.entrySet()) {
            TreeItem<String> newCategory = new TreeItem<>(category.getValue());

            newCategory.getChildren().addAll(getAndLoadChildrenCategories(category.getKey(), category.getValue()));
            rootCategories.getChildren().add(newCategory);
            addedCategories.put(category.getKey(), newCategory);
        }
    }

    private static List<TreeItem<String>> getAndLoadChildrenCategories(Integer mainId, String parent){
        ArrayList<TreeItem<String>> children = new ArrayList<>();
        TreeItem<String> newChild = new TreeItem<>();

        for (Category currentCategory : AllChildCategories.childCategories) {
            if(currentCategory.getMainCategoryId() == mainId && currentCategory.getParentCategoryName().equals(parent)) {
                newChild = new TreeItem<>(currentCategory.getChildCategory());
                children.add(newChild);

                newChild.getChildren().addAll(getAndLoadChildrenCategories(mainId, currentCategory.getChildCategory()));
                
            }
        }

        return children;
    }
}
