package ca.cmpt213.a4.client.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *     Consumable class models the information about a general consumable item.
 *     Data includes name, notes, price, expiry year, expiry month, id, and expiry day as well as if the item is a drink or food.
 *     It is the parent class to the FoodItems and DrinkItems class.
 *     It also implements the comparable interface
 */
public class Consumable implements Comparable<Consumable> {

    private String consumableName;
    private String consumableNotes;
    private double consumablePrice;
    private int expiryMonth;
    private int expiryDay;
    private int expiryYear;
    private boolean isDrink;
    private long id;

    /**
     * default constructor
     */
    public Consumable(){}

    /**
     *
     * @return long for item id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return string for item name
     */
    public String getName(){return consumableName;}

    /**
     *
     * @return string for item notes
     */
    public String getNotes(){return consumableNotes;}

    /**
     *
     * @return double for item price
     */
    public double getPrice(){return consumablePrice;}

    /**
     *
     * @return int for item expiry month
     */
    public int getExpiryMonth(){return expiryMonth;}

    /**
     *
     * @return int for item expiry day
     */
    public int getExpiryDay(){return expiryDay;}

    /**
     *
     * @return int for item expiry year
     */
    public int getExpiryYear(){return expiryYear;}

    /**
     *
     * @return boolean for item isDrink
     */
    public boolean getIsDrink(){return isDrink;}

    /**
     *
     * @param newName to change name
     */
    public void changeName(String newName){consumableName = newName;}

    /**
     *
     * @param newNotes to change notes
     */
    public void changeNotes(String newNotes){consumableNotes = newNotes;}

    /**
     *
     * @param newPrice to change price
     */
    public void changePrice(double newPrice ){consumablePrice = newPrice;}

    /**
     *
     * @param newExpiryMonth to change expiry month
     */
    public void changeExpiryMonth(int newExpiryMonth){expiryMonth = newExpiryMonth;}

    /**
     *
     * @param newExpiryDay to change expiry day
     */
    public void changeExpiryDay(int newExpiryDay){expiryDay = newExpiryDay;}

    /**
     *
     * @param newExpiryYear to change expiry year
     */
    public void changeExpiryYear(int newExpiryYear){expiryYear = newExpiryYear;}

    /**
     *
     * @param newIsDrink to change isDrink
     */
    public void changeIsDrink(boolean newIsDrink){isDrink = newIsDrink;}

    /**
     *
     * @param id to change id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *  calculates the days between the current date and the expiry date
     * learned and adapted how to use DAYS.between, from https://www.baeldung.com/java-date-difference
     * @return long how many to before or for how long item has been expiryd
     */
    public long getDaysTillExpire(){

        LocalDate currentDate = LocalDate.now();
        LocalDate expiryDate;
        long daysTillExpiry;
        expiryDate = LocalDate.of(expiryYear, expiryMonth, expiryDay);
        daysTillExpiry = ChronoUnit.DAYS.between(currentDate, expiryDate);
        return daysTillExpiry;
    }

    /**
     * format a double value so it only has 2 decimal places after the decimal
     * @param pattern the pattern of how the formatted string double should look
     * @param value the double value which is going to be formatted
     * @return the formatted double as a string
     */
    public String decimalFormat(String pattern, double value){

        //learned and adapted how to format decimals from website linked in assignment 3 description file
        //https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
        DecimalFormat deciFormat = new DecimalFormat(pattern);
        String formattedValue = deciFormat.format(value);
        return formattedValue;
    }

    /**
     * override the compreTo method so consumable items can be sorted based on expiry date
     * @param compareItem item we want to compare to calling object
     * @return 0, -1, 1 based on which item expires first
     */
    @Override
    public int compareTo(Consumable compareItem){

        //depending on which items expiry date was longer a different value is returned
        if(this.getDaysTillExpire() == compareItem.getDaysTillExpire()){
            return 0;
        } else if(this.getDaysTillExpire() > compareItem.getDaysTillExpire()){
            return 1;
        } else{
            return -1;
        }
    }

}

