package ca.cmpt213.a4.webappserver.model;

/**
 *     FoodItems class models the information of a food item.
 *     Only contains a weight field as this class extends the consumable class.
 *     As well as implements the toString method.
 *
 */
public class FoodItems extends Consumable {

    private double weight;

    FoodItems(){}

    /**
     * returns the weight of the item
     * @return double which is the weight of the item
     */
    public double getWeight(){
        return weight;
    }

    /**
     * changes the value of the items weight
     * @param newWeight new weight of the item
     */
    public void changeWeight(double newWeight){
        weight = newWeight;
    }

    /**
     * returns a formatted string which contains all the information about a food item
     * @return String returns all values of the item as one string
     */
    @Override
    public String toString() {

        String formattedFinalString;
        String formattedPrice = decimalFormat(".##", getConsumablePrice());
        long daysTillExpiry = returnDaysTillExpire();

        //Found out how to get newline characters in Jlabel using <br/> and <html> from
        //https://stackoverflow.com/questions/7447691/is-there-any-multiline-jlabel-exist
        //user: mrkhrts
        formattedFinalString = "<br/>FOOD ITEM" + "<br/>" +
                "Name: " + getConsumableName() + "<br/>" +
                "Notes: " + getConsumableNotes() + "<br/>" +
                "Price: " + formattedPrice + "<br/>" +
                "Weight: " + getWeight() + "<br/>";
        if (daysTillExpiry == 0) {

            formattedFinalString = formattedPrice + "Expiry Date: " + getExpiryYear() + "-" + getExpiryMonth() + "-" + getExpiryDay() + "<br/>" +
                    "Item Expires Today<br/><br/><html>";
        } else if (daysTillExpiry > 0) {

            formattedFinalString = formattedFinalString + "Expiry Date: " + getExpiryYear() + "-" + getExpiryMonth() + "-" + getExpiryDay() + "<br/>" +
                    "Item Expires in " + daysTillExpiry + " day(s).<br/><br/><html>";
        } else {
            daysTillExpiry = daysTillExpiry * -1;

            formattedFinalString = formattedFinalString + "Expiry Date: " + getExpiryYear() + "-" + getExpiryMonth() + "-" + getExpiryDay() + "<br/>" +
                    "Item Expired " + daysTillExpiry + " day(s) ago.<br/><br/><html>";
        }
        return formattedFinalString;
    }
}
