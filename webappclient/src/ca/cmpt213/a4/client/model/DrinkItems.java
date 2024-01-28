package ca.cmpt213.a4.client.model;

/**
 *     DrinkItems class models the information about a drink item.
 *     Data includes volume and it extends the Consumables class
 *     as well as implements the toString method
 */
public class DrinkItems extends Consumable {

    private double volume;

    DrinkItems(){}

    /**
     * return the volume of an item
     * @return double which is the volume of the item
     */
    public double getVolume(){
        return volume;
    }

    /**
     * change the volume of an object
     * @param newVolume new volume value
     */
    public void changeVolume(double newVolume){
        volume = newVolume;
    }

    /**
     * returns a formatted string which contains all the information about a drink item
     * @return String which contains all values in item as a formatted string
     */
    @Override
    public String toString() {

        String formattedFinalString;
        String formattedPrice = decimalFormat(".##", getPrice());
        long daysTillExpiry = getDaysTillExpire();

        //Found out how to get newline characters in Jlabel using <br/> and <html> from
        //https://stackoverflow.com/questions/7447691/is-there-any-multiline-jlabel-exist
        //user: mrkhrts
        formattedFinalString = "<br/>DRINK ITEM" + "<br/>" +
                "Name: " + getName() + "<br/>" +
                "Notes: " + getNotes() + "<br/>" +
                "Price: " + formattedPrice + "<br/>" +
                "Volume: " + getVolume() + "<br/>";
        if (daysTillExpiry == 0) {

            formattedFinalString = formattedFinalString + "Expiry Date: " + getExpiryYear() + "-" + getExpiryMonth() + "-" + getExpiryDay() + "<br/>" +
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

