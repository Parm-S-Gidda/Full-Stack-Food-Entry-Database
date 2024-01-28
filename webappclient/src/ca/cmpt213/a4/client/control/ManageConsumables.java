package ca.cmpt213.a4.client.control;

import ca.cmpt213.a4.client.model.*;
import com.google.gson.*;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 *     ManageConsumables class handles reading and writing from json files,
 *     formatting the items created, removing items as well as interacting with the GenerateItems class
 *     which creates new items, and holding all items.
 */
public class ManageConsumables{

    private ArrayList<Consumable> allItems = new ArrayList<>();

    /**
     * calls the GenerateItems class and stores the consumable returned depending
     * on if the consumable item is valid
     * @param trackerFrame main jframe
     */
    public void addNewItem(JFrame trackerFrame) throws IOException {
        GenerateItems newItem;
        newItem = new GenerateItems();
        newItem.createItem(trackerFrame);
    }

    /**
     * returns a Array<string> with certain items depending on which
     * view the user wants (inputted value). Gets the items by
     * making a HTTP request to a local server
     * @param whichListView returns an arraylist with or without some items depending on which value is inputted
     *                      (ex. allItems, non expired, expired, expiring within 7 days arraylist)
     * @return returns ArrayList<String> return arraylist which contains the specific items which want to be viewed
     */
    public ArrayList<String> formattedAllItems(int whichListView) throws IOException, InterruptedException {
        StringBuilder jsonArrayString = new StringBuilder();
        String jsonArrayStringLine;
        ArrayList<String> formattedItems;
        URL url = null;

        if(whichListView == 1){
            url = new URL("http://localhost:8080/listAll");

        }else if(whichListView == 2){
            url = new URL ("http://localhost:8080/listExpired");

        }else if(whichListView == 3){
            url = new URL ("http://localhost:8080/listExpiringIn7Days");

        }else if(whichListView == 4){
            url = new URL ("http://localhost:8080/listNonExpired");
        }

        //Learned and adpated how to use HttpURLConnection to send Http requests and read responses from:
        //https://www.baeldung.com/httpurlconnection-post
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setDoOutput(true);

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "utf-8"))) {
            while ((jsonArrayStringLine = bufferedReader.readLine()) != null) {
                jsonArrayString.append(jsonArrayStringLine.trim());
            }
            formattedItems = jsonToConsumableArray(jsonArrayString.toString(), whichListView);
        }
        return formattedItems;
    }

    /**
     * Converts a string of json array format into a string array of consumables by
     * extracting consumable objects
     * @param jsonArrayString the string of json array format
     * @param whichListView int which determines indicates which set of data the user wants to see
     * @return string array which is the consumable objects as formatted strings
     */
    public ArrayList<String> jsonToConsumableArray(String jsonArrayString, int whichListView){
        ArrayList<Consumable> tempAllItems = new ArrayList<>();
        JsonArray jsonArray = JsonParser.parseString(jsonArrayString).getAsJsonArray();
        ConsumableFactory consumableFactory = new ConsumableFactory();
        Consumable newItem;

        for(JsonElement foodElement : jsonArray){
            JsonObject consumableJsonObject = foodElement.getAsJsonObject();

            //All data is extracted from the json object and stored
            String conName = consumableJsonObject.get("consumableName").getAsString();
            String conNotes = consumableJsonObject.get("consumableNotes").getAsString();
            double conPrice = consumableJsonObject.get("consumablePrice").getAsDouble();
            int expiryYear = consumableJsonObject.get("expiryYear").getAsInt();
            int expiryMonth = consumableJsonObject.get("expiryMonth").getAsInt();
            int expiryDay = consumableJsonObject.get("expiryDay").getAsInt();
            boolean condIsDrink = consumableJsonObject.get("isDrink").getAsBoolean();
            long conID = consumableJsonObject.get("id").getAsLong();

            if(condIsDrink){

                double conVolume = consumableJsonObject.get("volume").getAsDouble();
                newItem = consumableFactory.getInstance("Drink");
                newItem.changeIsDrink(true);

                //Learned how to access child method from parent object from:
                //https://stackoverflow.com/questions/11466441/call-a-child-class-method-from-a-parent-class-object
                //Response from "techfoobar"
                ((DrinkItems)newItem).changeVolume(conVolume);

            } else{
                double conWeight = consumableJsonObject.get("weight").getAsDouble();
                newItem = consumableFactory.getInstance("Food");
                newItem.changeIsDrink(false);
                ((FoodItems)newItem).changeWeight(conWeight);
            }
            newItem.changeName(conName);
            newItem.changeNotes(conNotes);
            newItem.changePrice(conPrice);
            newItem.changeExpiryYear(expiryYear);
            newItem.changeExpiryMonth(expiryMonth);
            newItem.changeExpiryDay(expiryDay);
            newItem.setId(conID);

            tempAllItems.add(newItem);
        }

        if(whichListView == 1){
            allItems = tempAllItems;
        }
        return consumablesToStringArray(tempAllItems);
    }

    /**
     * Converts a consumables array to a string array which is the consumables in a certain format
     * @param consumablesArray consumables array which needs to converted
     * @return string array which is formatted for each consumable object
     */
    public ArrayList<String> consumablesToStringArray(ArrayList<Consumable> consumablesArray){

        ArrayList<String> consumablesAsStrings = new ArrayList<>();
        String consumableAsString;
        Consumable tempConsumable;
        for(int i = 0; i < consumablesArray.size(); i++){

            tempConsumable = consumablesArray.get(i);
            if(tempConsumable.getIsDrink()){
                consumableAsString = tempConsumable.toString();
            }else{
                consumableAsString = tempConsumable.toString();
            }
            consumablesAsStrings.add(consumableAsString);
        }
        return consumablesAsStrings;
    }

    /**
     * sends a HTTP request to local server with the id of the consumable object which the user
     * wants to remove. The server then removes it
     * @param removeIndex index of which item in the main array list is to be removed
     */
    public void removeItem(int removeIndex) throws IOException {
        long removeID;
        URL url;
        Consumable tempConsumable = allItems.get(removeIndex);
        removeID = tempConsumable.getId();

        //Learned and adpated how to use HttpURLConnection to send Http requests from:
        //https://www.baeldung.com/httpurlconnection-post
        url = new URL ("http://localhost:8080/removeItem/" + removeID);
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setDoOutput(true);
        new InputStreamReader(httpConnection.getInputStream(), "utf-8");
    }

    /**
     * sends a HTTP request to the local server so the server saves all consumables objects
     * @throws IOException Throws exception and ends program
     */
    public void endProgram() throws IOException {
        URL url;

        //Learned and adpated how to use HttpURLConnection to send Http requests and read responses from:
        //https://www.baeldung.com/httpurlconnection-post
        url = new URL("http://localhost:8080/exit");
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setDoOutput(true);

        new InputStreamReader(httpConnection.getInputStream(), "utf-8");

    }
}
