package Controllers.AdminSceneControllers;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public abstract class TextFieldsChecks {
    private static Alert alert = new Alert(AlertType.ERROR);

    protected abstract Boolean areAllInputsValid();

    protected Boolean isNameTextValid(TextField nameText){
        if (nameText.getText().isEmpty()) {
            alert.setContentText("Name: Enter a name");
            alert.show();
            return false;
        }
        return true;
    }
    protected Boolean isPriceTextValid(TextField priceText){
        if(priceText.getText().isEmpty() == false){
            try {
                Float.parseFloat(priceText.getText());

                return true;
            } catch (NumberFormatException e) {
                alert.setContentText("Price: Invalid input");
                alert.show();
                
                return false;
            }
        } else{
            alert.setContentText("Price: Field cannot be empty!");
            alert.show();
            return false;
        }
    }
    protected Boolean isQuantityTextValid(TextField quantityText){
        if(!quantityText.getText().isEmpty()){
            if(quantityText.getText().matches("[0-9]+")){
                
                return true;
            } else{
                alert.setContentText("Quantity: Invalid input");
                alert.show();
                
                return false;
            }

        } else{
            alert.setContentText("Quantity: Field cannot be empty!");
            alert.show();
            
            return false;
        }
    }
    protected Boolean isSizeTextValid(TextField sizeText){
        if (sizeText.getText().isEmpty()) {
            alert.setContentText("Size: Enter a size");
            alert.show();
            
            return false;
        }
        return true;
    }
    protected Boolean isResolutionTextValid(TextField resolutionText){
        if (resolutionText.getText().isEmpty()) {
            alert.setContentText("Resolution: Enter a resolution");
            alert.show();
            return false;
        }
        return true;
    }
 
}
