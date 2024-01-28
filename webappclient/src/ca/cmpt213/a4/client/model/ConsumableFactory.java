package ca.cmpt213.a4.client.model;

/**
 * ConsumableFactory returns an instance of either a
 * foodItem or a drinkItem
 */
public class ConsumableFactory {

    /**
     * creates an instance of either a food or drink item depending on input
     * adapted from link given in Assignment2_Description
     * @param itemType which item instance you want to return
     * @return an instance of a consumable item which is either a drink item or a food item
     */
    public Consumable getInstance(String itemType){
        if(itemType == null){
            return null;
        } else if(itemType.equals("Food")){
            return new FoodItems();
        } else if(itemType.equals("Drink")){
            return new DrinkItems();
        }
        return null;
    }
}