package Controllers.AdminSceneControllers.Categories; 

import Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories.*;
import Controllers.DataBaseFunctions.CategoryFunctions.MainCategories.*;
import javafx.scene.control.TreeItem;

public class AddCategories extends LoadCategories{
    public static void saveMainCategories(){
        AddMainCategories.addNewMainCatToDB();
    }
    public static void saveCategories() {
        AddChildrenCategories.addAllChildCategoriesToDB();
    }
    public static void addMainCategory(String name){
        int indexToAddNewMainCat = AllMainCategories.mainCategories.keySet().size() + 1;

        AllMainCategories.mainCategories.put(indexToAddNewMainCat, name);
    }
    public static void addChildCategory(String name, TreeItem<String>item){
        String mainCategoryName = getMainCategoryOfSelectedProduct(item);
        int idOfMainCategory = AddChildrenCategories.getIdOfMainCategory(mainCategoryName);
        String parentName = item.getParent().getValue();

        Category newCategory = new Category(idOfMainCategory, mainCategoryName, parentName, name);

        AllChildCategories.childCategories.add(newCategory);
    }
    public static String getMainCategoryOfSelectedProduct(TreeItem<String> item){
        String category = "";
        while(!item.getParent().getValue().equals("Categories")){
            category = item.getParent().getValue();
            item = item.getParent();
        }
        return category;
    }
}
