package Controllers.DataBaseFunctions.CategoryFunctions.ChildCategories;

public class Category {
    private int mainCategoryId;
    private final String mainCategoryName;
    private String parentCategoryName;
    private String childCategory;

    public Category(int maincategoryId, String mainCategoryName ,String parentCategoryName, String childCategory) {
        this.mainCategoryId = maincategoryId;
        this.mainCategoryName = mainCategoryName;
        this.childCategory = childCategory;
        this.parentCategoryName = parentCategoryName;
    }
    public String getMainCategoryName() {
        return mainCategoryName;
    }
    public int getMainCategoryId() {
        return mainCategoryId;
    }
    public String getParentCategoryName() {
        return parentCategoryName;
    }
    public String getChildCategory() {
        return childCategory;
    }
    public void setMainCategoryId(int maincategoryId) {
        this.mainCategoryId = maincategoryId;
    }
    public void setParentCategoryName(String parentCategoryId) {
        this.parentCategoryName = parentCategoryId;
    }
    public void setChildCategory(String childCategory) {
        this.childCategory = childCategory;
    }
}
