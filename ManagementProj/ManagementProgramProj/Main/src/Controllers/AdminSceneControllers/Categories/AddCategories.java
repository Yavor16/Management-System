package Controllers.AdminSceneControllers.Categories; 

import java.util.*;
import java.util.function.Supplier;

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
    public static boolean canAddNewCategory(String categoryName){
        for (Category category : AllChildCategories.childCategories) {
            if (category.getChildCategory().equals(categoryName)) {
                return false;
            }
        }
        for (Map.Entry<Integer, String> categ : AllMainCategories.mainCategories.entrySet()) {
            if (categ.getValue().equals(categoryName)) {
                return false;
            }
        }
        return true;
    }
    public static void addMainCategory(String name){
        int indexToAddNewMainCat = AllMainCategories.mainCategories.lastKey() + 1;
        AllMainCategories.mainCategories.put(indexToAddNewMainCat, name);
    }
    public static void addChildCategory(String name, TreeItem<String>item){
        int categoryId = getNewCateogryId.get();
        int idOfMainCategory = getMainCategoryId(item);
        int parentId = getParentCategoryId(item.getParent().getValue());
       
        Category newCategory = new Category(categoryId, idOfMainCategory, parentId, name);

        AllChildCategories.childCategories.add(newCategory);
    }
    private static Supplier<Integer> getNewCateogryId = ()-> {
        if (AllChildCategories.childCategories.size() == 0) {
            return 1;
        }
        return AllChildCategories.childCategories.stream().max(Comparator.comparing(Category::getCategoriId)).get().getCategoriId() + 1;
    };

    private static int getMainCategoryId(TreeItem<String> item){
        String mainCatName = GetMainCategory.getMainCategoryOfSelectedProduct(item);

        for (Map.Entry<Integer,String> cat : AllMainCategories.mainCategories.entrySet()) {
            if (cat.getValue().equals(mainCatName)) {
                return cat.getKey();
            }
        }
        return 0;
    }
    private static int getParentCategoryId(String name){

        for (Category category : AllChildCategories.childCategories) {
            
            if (category.getChildCategory().equals(name)) {
                System.out.println(category.getCategoriId());
                return category.getCategoriId();
            
            }
        }
        return 0;
    }
}
