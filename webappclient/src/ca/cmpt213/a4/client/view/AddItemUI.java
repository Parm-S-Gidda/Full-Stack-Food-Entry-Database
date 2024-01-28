package ca.cmpt213.a4.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.github.lgooddatepicker.components.DatePicker;

/**
 * The AddItemUI class displays the add item GUI. It also takes in the user's
 * input and returns it the GeneratingItems class. It also checks valid input checks
 * the user's inputs.
 */
public class AddItemUI extends JDialog implements ActionListener {

    private JDialog addItemDialog;
    private JDialog addItemErrorDialog;
    private JLabel weightOrVolumeField;
    private JLabel errorMessageLabel;
    private JComboBox drinkOrFoodComboBox;
    private JTextField itemFieldName;
    private JTextField itemFieldNotes;
    private JTextField itemFieldPrice;
    private JTextField itemFieldVolumeOrWeight;
    private DatePicker itemExpiryDatePicker;
    private static String itemName;
    private String itemNotes;
    private boolean itemIsDrink;
    private String itemExpiryDate;
    private String[] data = new String[1];
    private String[] allItemFields = new String[6];
    private boolean itemCanceled;

    /**
     * Creates and displays the GUI for the add item method as well as stores user input
     * @param trackerFrame main jframe
     */
    public AddItemUI(JFrame trackerFrame){

        String[] drinkOrFoodArray = {"Food Item", "Drink Item"};
        JLabel fieldName;
        JPanel addItemMainBackground;
        JPanel addItemCollumnPanel;
        JPanel addItemRowPanel;
        JButton addItemButton;
        JButton cancelButton;
        addItemCollumnPanel = new JPanel();
        addItemCollumnPanel.setLayout(new BoxLayout(addItemCollumnPanel, BoxLayout.Y_AXIS));
        addItemCollumnPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        addItemMainBackground = new JPanel();
        addItemMainBackground.setLayout(new BoxLayout(addItemMainBackground, BoxLayout.Y_AXIS));
        addItemMainBackground.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Learned and adapted code for creating JDialog as modal so I could return values after user sets them from:
        // Professor Cheung (Office hours) &
        //https://stackoverflow.com/questions/4089311/how-can-i-return-a-value-from-a-jdialog-box-to-the-parent-jframe
        //User: Jonathan and Tamias
        addItemDialog = new JDialog(trackerFrame, "Add Item");
        addItemDialog.setSize(400, 220);
        addItemDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);

        //add combo box and text fields to panel as well as add and cancel button
        for(int i = 0; i < 6; i++){

            addItemRowPanel = new JPanel();
            addItemRowPanel.setLayout(new BoxLayout(addItemRowPanel, BoxLayout.X_AXIS));
            addItemRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            if(i == 0){

                fieldName = new JLabel("Type:        ");

                addItemRowPanel.add(fieldName);

                drinkOrFoodComboBox = new JComboBox(drinkOrFoodArray);
                drinkOrFoodComboBox.setSelectedIndex(-1);
                drinkOrFoodComboBox.setMaximumSize(new Dimension(300, drinkOrFoodComboBox.getPreferredSize().height));
                drinkOrFoodComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
                drinkOrFoodComboBox.addActionListener(this);

                addItemRowPanel.add(drinkOrFoodComboBox);

            }else if(i == 1){

                fieldName = new JLabel("Name:        ");

                addItemRowPanel.add(fieldName);

                itemFieldName = new JTextField();
                itemFieldName.setMaximumSize(new Dimension(300, itemFieldName.getPreferredSize().height));

                addItemRowPanel.add(itemFieldName);

            }else if(i == 2){

                fieldName = new JLabel("Notes:       ");

                addItemRowPanel.add(fieldName);

                itemFieldNotes = new JTextField();
                itemFieldNotes.setMaximumSize(new Dimension(300, itemFieldNotes.getPreferredSize().height));

                addItemRowPanel.add(itemFieldNotes);

            }else if(i == 3){

                fieldName = new JLabel("Price:         ");

                addItemRowPanel.add(fieldName);

                itemFieldPrice = new JTextField();
                itemFieldPrice.setMaximumSize(new Dimension(300, itemFieldPrice.getPreferredSize().height));

                addItemRowPanel.add(itemFieldPrice);

            }else if(i == 4){

                weightOrVolumeField = new JLabel("Weight:      ");

                addItemRowPanel.add(weightOrVolumeField);

                itemFieldVolumeOrWeight = new JTextField();
                itemFieldVolumeOrWeight.setMaximumSize(new Dimension(300, itemFieldVolumeOrWeight.getPreferredSize().height));

                addItemRowPanel.add(itemFieldVolumeOrWeight);

            }else if(i == 5){

                fieldName = new JLabel("Expiry Date: ");

                addItemRowPanel.add(fieldName);

                itemExpiryDatePicker = new DatePicker();
                itemExpiryDatePicker.setMaximumSize(new Dimension(300, itemExpiryDatePicker.getPreferredSize().height));

                addItemRowPanel.add(itemExpiryDatePicker);
            }

            addItemCollumnPanel.add(addItemRowPanel);
        }

        addItemMainBackground.add(addItemCollumnPanel);

        addItemButton = new JButton("Add");
        addItemButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        addItemRowPanel = new JPanel();
        addItemRowPanel.setLayout(new BoxLayout(addItemRowPanel, BoxLayout.X_AXIS));
        addItemRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addItemRowPanel.add(addItemButton);
        addItemRowPanel.add(cancelButton);

        addItemMainBackground.add(addItemRowPanel);

        addItemDialog.add(addItemMainBackground);
    }

    /**
     * Makes the GUI visible and also returns all of the user inputs
     * @return String[] which is all the fields of a given consumable item
     */
    public String[] showAddItemDialog(){
        addItemDialog.setVisible(true);

        //Learned and adapted code for creating JDialog as modal so I could return values after user sets them from:
        // Professor Cheung (Office hours) &
        //https://stackoverflow.com/questions/4089311/how-can-i-return-a-value-from-a-jdialog-box-to-the-parent-jframe
        //User: Jonathan and Tamias
        //if user does not press cancel add all inputted fields to array
        if(!itemCanceled) {
            allItemFields[0] = itemName;
            allItemFields[1] = itemNotes;
            allItemFields[2] = drinkOrFoodComboBox.getSelectedItem().toString();
            allItemFields[3] = itemFieldPrice.getText();
            allItemFields[4] = itemFieldVolumeOrWeight.getText();
            allItemFields[5] = itemExpiryDate;
        }
        return allItemFields;
    }

    /**
     * Display a dialog to the user if their input was invalid or non existent
     * @param whichMessage number determines which warning message to display
     * @param addItemDialog main jdialog
     */
    void errorMessage(int whichMessage, JDialog addItemDialog){

        JPanel errorMessageRowPanel;
        JPanel errorMessagePanel;
        JButton okButton;
        addItemErrorDialog = new JDialog(addItemDialog, "Warning");
        addItemErrorDialog.setSize(450, 80);
        addItemErrorDialog.setVisible(true);

        errorMessagePanel = new JPanel();
        errorMessagePanel.setLayout(new BoxLayout(errorMessagePanel, BoxLayout.Y_AXIS));
        errorMessagePanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        errorMessageRowPanel = new JPanel();
        errorMessageRowPanel.setLayout(new BoxLayout(errorMessageRowPanel, BoxLayout.X_AXIS));
        errorMessageRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //display a certain error message in a dialog depending on which field is incorrect
        if(whichMessage == 1){

            errorMessageLabel = new JLabel("Warning! Please Choose what type of item you would like to add");
        }else if(whichMessage == 2){

            errorMessageLabel = new JLabel("Warning! Please enter an item price");
        }else if(whichMessage == 3){

            errorMessageLabel = new JLabel("Warning! Please enter an item weight");
        }else if(whichMessage == 4){

            errorMessageLabel = new JLabel("Warning! Please enter an item volume");
        }else if(whichMessage == 5){

            errorMessageLabel = new JLabel("Warning! Item Price must be positive");
        }else if(whichMessage == 6){

            errorMessageLabel = new JLabel("Warning! Item volume must be positive");
        }else if(whichMessage == 7){

            errorMessageLabel = new JLabel("Warning! Item weight must be positive");
        }else if(whichMessage == 8){

            errorMessageLabel = new JLabel("Warning! Please choose an item expiry date");
        } else if(whichMessage == 9){

            errorMessageLabel = new JLabel("Warning! Item Name cannot be empty");
        }
        errorMessageRowPanel.add(errorMessageLabel);

        okButton = new JButton("Okay");
        okButton.addActionListener(this);
        errorMessagePanel.add(errorMessageRowPanel);
        errorMessageRowPanel = new JPanel();
        errorMessageRowPanel.add(okButton);
        errorMessagePanel.add(errorMessageRowPanel);
        addItemErrorDialog.add(errorMessagePanel);
    }

    /**
     * check if user inputs are valid and store those value
     * if they are not valid show an error message
     */
    public void userInputBasicCheck(){
        itemCanceled = false;
        String tempItemPrice;
        String tempItemVolumeOrWeight;
        String tempItemIsDrink;
        double itemPrice;
        double itemVolumeOrWeight;

        //check all the inputs and send an error message via dialog if invalid
        try {
            tempItemIsDrink = drinkOrFoodComboBox.getSelectedItem().toString();
        } catch(Exception a){

            errorMessage(1, addItemDialog);
            return;
        }

        if(tempItemIsDrink.equals("Food Item")){
            itemIsDrink = false;

        } else if(tempItemIsDrink.equals("Drink Item")){
            itemIsDrink = true;
        }

        itemName = itemFieldName.getText();
        itemNotes = itemFieldNotes.getText();
        tempItemPrice = itemFieldPrice.getText();

        try {
            itemPrice = Double.parseDouble(tempItemPrice);
        } catch (Exception a){
            errorMessage(2, addItemDialog);
            return;
        }

        tempItemVolumeOrWeight = itemFieldVolumeOrWeight.getText();

        try {
            itemVolumeOrWeight = Double.parseDouble(tempItemVolumeOrWeight);
        } catch(Exception a){

            if(itemIsDrink) {
                errorMessage(4, addItemDialog);
            }else{
                errorMessage(3, addItemDialog);
            }
            return;
        }

        itemExpiryDate = itemExpiryDatePicker.toString();
        if(itemName.isBlank()){
            errorMessage(9, addItemDialog);
            return;
        }
        if(itemPrice < 0){
            errorMessage(5, addItemDialog);
            return;
        }
        if(itemExpiryDate.equals("")){
            errorMessage(8, addItemDialog);
            return;
        }
        if(itemVolumeOrWeight < 0){
            if(itemIsDrink){
                errorMessage(6, addItemDialog);
            }else{
                errorMessage(7, addItemDialog);
            }
            return;
        }
        data[0] = itemName;
        addItemDialog.dispose();

    }

    /**
     * Performs the actions of the button on the GUI
     * as well as stores the user's inputs
     * @param e used to check if button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChanged")){
            if(drinkOrFoodComboBox.getSelectedItem() == "Food Item"){
                weightOrVolumeField.setText("Weight:      ");

            }else{
                weightOrVolumeField.setText("Volume:     ");
            }
        }
        if(e.getActionCommand().equals("Add")) {
            userInputBasicCheck();

        }else if(e.getActionCommand().equals("Cancel")){

            //if user clicks cancel all fields are set to "Cancel" so GenerateItems knows not to fully create this item
            itemCanceled = true;
            allItemFields[0] = "Cancel";
            allItemFields[1] = "Cancel";
            allItemFields[2] = "Cancel";
            allItemFields[3] = "Cancel";
            allItemFields[4] = "Cancel";
            allItemFields[5] = "Cancel";
            addItemDialog.dispose();

        }else if(e.getActionCommand().equals("Okay")){
            addItemErrorDialog.dispose();
        }
    }

}
