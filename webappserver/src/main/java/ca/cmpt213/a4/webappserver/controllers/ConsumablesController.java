package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ManageConsumables;
import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.DrinkItems;
import ca.cmpt213.a4.webappserver.model.FoodItems;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class contains all controllers of local server.
 */
@RestController
public class ConsumablesController {
    private ManageConsumables consumableManger = new ManageConsumables();

    /**
     * if user calls "ping" http request a system status message is returned
     * @return string telling the user the system is up
     */
    @ResponseStatus( HttpStatus.OK)
    @GetMapping("/ping")
    public String getGreetingMessage() {
        return "System is up!";
    }

    /**
     * upon the start of the program this method calls another method which
     * reads all objects from a json file and stores them
     * @throws IOException throws exception and ends program
     */
    @PostConstruct
    //Learned how to have method execute and server start up from:
    //https://www.baeldung.com/running-setup-logic-on-startup-in-spring
    public void readInJsonFile() throws IOException {
        consumableManger.extractFromJsonFile();
    }

    /**
     * If user calls "listAll" http request all items stored
     * in program is returned in certain order
     * @return returns a consumable array list which contains all consumable items
     */
    @ResponseStatus( HttpStatus.OK)
    @GetMapping("/listAll")
    public ArrayList<Consumable> getAllConsumables() {
        return consumableManger.formatItems(1);
    }

    /**
     * If user calls "listExpired" http request all items  stored
     * in program  that are expired are returned in certain order
     * @return returns a consumable array list which contains all consumable items which have expired
     */
    @ResponseStatus( HttpStatus.OK)
    @GetMapping("/listExpired")
    public ArrayList<Consumable> getExpiredConsumables() {
        return consumableManger.formatItems(2);
    }

    /**
     * If user calls "listExpiringIn7Days" http request all items  stored
     * in program that are expiring in 7 days are returned in certain order
     * @return returns a consumable array list which contains all consumable items that are expiring in 7 days
     */
    @ResponseStatus( HttpStatus.OK)
    @GetMapping("/listExpiringIn7Days")
    public ArrayList<Consumable> getConsumablesExpiringIn7Days() {
        return consumableManger.formatItems(3);
    }

    /**
     * If user calls "listNonExpired" http request all items  stored
     * in program  that are not expired are returned in certain order
     * @return returns a consumable array list which contains all consumable items which have not expired
     */
    @ResponseStatus( HttpStatus.OK)
    @GetMapping("/listNonExpired")
    public ArrayList<Consumable> getNonExpiredConsumables() {
        return consumableManger.formatItems(4);
    }

    /**
     * if user calls "exit" http request all items left in the program are saved into a json file called
     * "Consumables.json"
     * @throws IOException throws exception and ends program
     */
    @ResponseStatus( HttpStatus.OK)
    @GetMapping("/exit")
    public void exitAppServer() throws IOException {
        consumableManger.exitSave();
    }


    /**
     *  if user calls "removeItem" http request along with a consumable id the method calls another method
     *  which removes that consumable corresponding to the id from the program
     * @return consumable array list which is the updated consumable list which has the item removed
     */
    @ResponseStatus( HttpStatus.CREATED)
    @PostMapping("/removeItem/{id}")
    public ArrayList<Consumable> removeConsumableItem(@PathVariable("id") long removeID){
        return consumableManger.removeItem(removeID);
    }

    /**
     * if user calls "addDrinkItem" http request the method takes in the json body which was given by the user
     * and creates a consumable DrinkItems item and adds it to the main array list holding all consumables
     * @param drinkItem user gives a json format DrinkItems with all data of a consumable object
     * @return return updated consumable array list with new item
     */
    @ResponseStatus( HttpStatus.CREATED)
    @PostMapping("/addDrinkItem")
    public ArrayList<Consumable> createNewConsumable(@RequestBody DrinkItems drinkItem) {
        return consumableManger.addItem(drinkItem);
    }

    /**
     * if user calls "addFoodItem" http request the method takes in the json body which was given by the user
     * and creates a consumable FoodItems item and adds it to the main array list holding all consumables
     * @param foodItem user gives a json format FoodItems with all data of a consumable object
     * @return return updated consumable array list with new item
     */
    @ResponseStatus( HttpStatus.CREATED)
    @PostMapping("/addFoodItem")
    public ArrayList<Consumable> createNewConsumable(@RequestBody FoodItems foodItem) {
        return consumableManger.addItem(foodItem);
    }
}
