package ca.cmpt213.a4.webappserver.control;

import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.ConsumableFactory;
import ca.cmpt213.a4.webappserver.model.DrinkItems;
import ca.cmpt213.a4.webappserver.model.FoodItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class manages all consumables that have been added to the program.
 * It removes items, reads in consumable objects from a json file, and formats
 * the objects
 */
public class ManageConsumables {
    private ArrayList<Consumable> consumables = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    /**
     * save all consumables in system to a json file
     * @throws IOException
     */
    public void exitSave() throws IOException {
        //learned how to write to json file from file linked in a4 description file
        //https://attacomsian.com/blog/jackson-read-write-json
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(Paths.get("Consumables.json").toFile(), consumables);
    }

    /**
     * add new item to array holding all items
     * @param consumableItem consumable that is going to be added
     * @return updated array with new item in it
     */
    public ArrayList<Consumable> addItem(Consumable consumableItem){
        //used atomic long id for differentiating items idea from lecture notes
        consumableItem.setId(nextId.incrementAndGet());
        consumables.add(consumableItem);
        return consumables;
    }

    /**
     * removes a certain consumable object depending on which id the user provided
     * @param removeID id of consumable item which need to be removed
     * @return the updated consumable array list which now no longer contains the removed item
     */
    public ArrayList<Consumable> removeItem(Long removeID){
        Consumable tempConsumable;
        for(int i = 0; i < consumables.size(); i++){
            tempConsumable = consumables.get(i);
            if(tempConsumable.getId() == removeID){
                consumables.remove(i);
                break;
            }
        }
        return consumables;
    }

    /**
     * read in json file and extract and created consumable objects and
     * add them to array list
     * @return consumable array list which contains all items read and extracted from file
     * @throws IOException throws exception and ends program
     */
    public void extractFromJsonFile() throws IOException {
        ArrayList<Consumable> tempAllItems = new ArrayList<>();
        File fileInput = new File("Consumables.json");

        //adapted code from Brian Fraser's video linked in the previous assignment
        if(fileInput.exists()){

            JsonElement fileElement = JsonParser.parseReader(new FileReader(fileInput));
            JsonArray jsonArrayObject = fileElement.getAsJsonArray();
            ConsumableFactory consumableFactory = new ConsumableFactory();
            Consumable newItem;

            //loop through the jsonArray which should contain as many objects as items that were added at the end of the previous run
            for(JsonElement foodElement : jsonArrayObject){
                JsonObject foodJsonObject = foodElement.getAsJsonObject();

                //All data is extracted from the json object and stored
                String conName = foodJsonObject.get("consumableName").getAsString();
                String conNotes = foodJsonObject.get("consumableNotes").getAsString();
                double conPrice = foodJsonObject.get("consumablePrice").getAsDouble();
                int expiryYear = foodJsonObject.get("expiryYear").getAsInt();
                int expiryMonth = foodJsonObject.get("expiryMonth").getAsInt();
                int expiryDay = foodJsonObject.get("expiryDay").getAsInt();
                boolean condIsDrink = foodJsonObject.get("isDrink").getAsBoolean();

                if(condIsDrink){
                    double conVolume = foodJsonObject.get("volume").getAsDouble();
                    newItem = consumableFactory.getInstance("Drink");
                    newItem.changeIsDrink(true);

                    //Learned how to access child method from parent object from:
                    //https://stackoverflow.com/questions/11466441/call-a-child-class-method-from-a-parent-class-object
                    //Response from "techfoobar"
                    ((DrinkItems)newItem).changeVolume(conVolume);

                } else{
                    double conWeight = foodJsonObject.get("weight").getAsDouble();
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
                consumables.add(newItem);
            }
        }
    }

    /**
     * depending on which view the user wants return a consumable array containing certain consumable items in a
     * specific order
     * @param formatNumber int represents which view the uer wants
     * @return consumable array list which contains only specific consumable items depending on format number
     */
    public ArrayList<Consumable> formatItems(int formatNumber){
        ArrayList<Consumable> formattedList = new ArrayList<>();

        Consumable tempConsumable;
        Collections.sort(consumables);

        for(int i = 0; i < consumables.size(); i++) {
            tempConsumable = consumables.get(i);

            if (formatNumber == 1) {
                formattedList = consumables;
                break;
            } else if (formatNumber == 2) {
                if (tempConsumable.returnDaysTillExpire() >= 0) {
                    continue;
                }
            } else if (formatNumber == 3) {
                if (tempConsumable.returnDaysTillExpire() < 0 || tempConsumable.returnDaysTillExpire() > 7) {
                    continue;
                }
            } else if (formatNumber == 4) {
                if (tempConsumable.returnDaysTillExpire() < 0) {
                    continue;
                }
            }
            formattedList.add(tempConsumable);
        }
        return formattedList;
    }
}
