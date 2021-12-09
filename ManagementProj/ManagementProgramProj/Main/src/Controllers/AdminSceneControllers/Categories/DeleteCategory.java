package Controllers.AdminSceneControllers.Categories;

import java.util.Map;

import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.*;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.AllMainCategories;
import javafx.scene.control.TreeItem;

public class DeleteCategory {
    public static void deleteCategory(TreeItem<String> categoryToDel){
        deleteMainCategory(categoryToDel);
        deleteChildCategories(categoryToDel);
        AddCategories.saveMainCategories();

        ResetCategories.resetChildCategories();
    }
    private static void deleteMainCategory(TreeItem<String> categoryToDel){
        String nameOfCategory = categoryToDel.getValue();
        int keyOfCategory = getMainCategoryId(nameOfCategory);

        if (AllMainCategories.mainCategories.values().contains(nameOfCategory)) {
            AllMainCategories.mainCategories.remove(keyOfCategory);
        }
    } 
    private static int getMainCategoryId(String value){
        for (Map.Entry<Integer, String> categ : AllMainCategories.mainCategories.entrySet()) {
            if (categ.getValue().equals(value)) {
                return categ.getKey();
            }
        }
        return 0;
    }
    private static void deleteChildCategories(TreeItem<String> categoryToDel){
        
        if (AllMainCategories.mainCategories.values().contains(categoryToDel.getValue()) == false) {
            
            if (categoryToDel.getChildren().size() > 0) {
                for (int i = 0; i < categoryToDel.getChildren().size(); i++) {
                    deleteChildCategories(categoryToDel.getChildren().get(i));
                }
            }
            Category currentCategory = getSpecificCategory(categoryToDel.getValue());
            AllChildCategories.childCategories.remove(currentCategory);
        
        }
    }
    private static Category getSpecificCategory(String catNameString){
        for (int i = 0; i < AllChildCategories.childCategories.size(); i++) {
            Category currentCategory = AllChildCategories.childCategories.get(i);

            if (currentCategory.getChildCategory().equals(catNameString)) {
                return currentCategory;
            }
        }
        return null;
    }
}
