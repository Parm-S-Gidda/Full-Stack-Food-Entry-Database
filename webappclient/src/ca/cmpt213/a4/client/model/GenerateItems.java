package ca.cmpt213.a4.client.model;

import ca.cmpt213.a4.client.view.AddItemUI;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The GenerateItems class interacts with the add item gui as and
 * creates and returns new items
 */
public class GenerateItems{
    String itemName;
    String itemNotes;
    double itemPrice;
    double itemWeightOrVolume;
    boolean isDrink;
    String expiryDate;
    String expiryMonth;
    String expiryYear;
    String expiryDay;


    /**
     * Calls the add item GUI and sends a HTTP requst to the local server to create a new consumable item.
     * Updates current consumables item array
     * @param trackerFrame main jframe
     */
    public void createItem(JFrame trackerFrame) throws IOException {

        AddItemUI addItemDialog = new AddItemUI(trackerFrame);
        String[] newItemFields;
        newItemFields = addItemDialog.showAddItemDialog();
        String command;
        URL url;

        //check if user did not press enter in the GUI. If not create a new object
        if(!(newItemFields[0].equals("Cancel")) && !(newItemFields[1].equals("Cancel")) && !(newItemFields[3].equals("Cancel"))) {

            //extract info form returned array and create a new consumable item
            itemName = newItemFields[0];
            itemNotes = newItemFields[1];
            itemPrice = Double.parseDouble(newItemFields[3]);
            itemWeightOrVolume = Double.parseDouble(newItemFields[4]);
            expiryDate = newItemFields[5];

            if(newItemFields[2].equals("Drink Item")) {
                isDrink = true;
            } else {
                isDrink = false;
            }

            addItemExpiryDate(expiryDate);

            if(isDrink){
                url = new URL ("http://localhost:8080/addDrinkItem");
                command = "{\"consumableName\": \"" + itemName + "\", \"consumableNotes\": \""+ itemNotes +"\", \"consumablePrice\": \""+ itemPrice +"\", \"expiryMonth\": \""+ expiryMonth +"\", \"expiryDay\": \""+ expiryDay +"\", \"expiryYear\": \""+ expiryYear +"\", \"isDrink\": \""+ isDrink +"\", \"volume\": \""+ itemWeightOrVolume +"\"}";
            }else{
                url = new URL ("http://localhost:8080/addFoodItem");
                command = "{\"consumableName\": \"" + itemName + "\", \"consumableNotes\": \""+ itemNotes +"\", \"consumablePrice\": \""+ itemPrice +"\", \"expiryMonth\": \""+ expiryMonth +"\", \"expiryDay\": \""+ expiryDay +"\", \"expiryYear\": \""+ expiryYear +"\", \"isDrink\": \""+ isDrink +"\", \"weight\": \""+ itemWeightOrVolume +"\"}";
            }

            //Learned and adpated how to use HttpURLConnection to send Http requests and read responses from:
            //https://www.baeldung.com/httpurlconnection-post
            HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setDoOutput(true);

            try(OutputStream outputStream = httpConnection.getOutputStream()) {
                byte[] input = command.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }
            new InputStreamReader(httpConnection.getInputStream(), "utf-8");

        }
    }

    /**
     * Takes in a full expiry date string and breaks it up into
     * individual months, years, and days
     * @param expiryDate get the string of the whole expiry date
     */
    public void addItemExpiryDate(String expiryDate){

        expiryYear = new String();
        expiryMonth = new String();
        expiryDay = new String();
        char currentLetter;
        int datePart = 0;

        //Go through expiryDate string and extract month, date, and year separately
        for(int i = 0; i < expiryDate.length(); i++){

            currentLetter = expiryDate.charAt(i);
            if(datePart == 0){
                if(currentLetter != '-'){
                    expiryYear = expiryYear + currentLetter;
                }else{
                    datePart++;
                }
            }else if(datePart == 1){
                if(currentLetter != '-'){
                    expiryMonth = expiryMonth + currentLetter;
                }
                else{
                    datePart++;
                }
            }else if(datePart == 2){
                if(i <= (expiryDate.length()-1)){
                    expiryDay = expiryDay + currentLetter;
                }
            }
        }
    }

}
