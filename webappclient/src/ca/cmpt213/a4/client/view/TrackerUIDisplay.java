package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.control.ManageConsumables;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.awt.*;

/**
 * The TrackerUIDisplay class displays the main GUI to the user as well as displays
 * the consumables items in certain formats depending on which button the user clicks
 */
public class TrackerUIDisplay  extends javax.swing.JDialog implements ActionListener, ItemListener, WindowListener {

    private JButton allButton;
    private JButton expiredButton;
    private JButton notExpiredButton;
    private JButton expireIn7Button;
    private JFrame trackerFrame;
    private JPanel scrollPaneMainBackgroundPanel;
    private ArrayList<String> formattedConsumables;
    private ManageConsumables allItems;

    /**
     * Displays main GUI which displays buttons and lists consumables
     * @throws IOException throws exception and ends program
     */
    public TrackerUIDisplay() throws IOException, InterruptedException {

        allItems = new ManageConsumables();
        JButton addItemButton;
        JPanel mainBackGroundPanel;
        JPanel topButtonRowPanel;
        JScrollPane viewItemsScrollPane;
        JPanel centerButtonPanel;

        trackerFrame = new JFrame("My Consumable Tracker");
        trackerFrame.setSize(500, 600);
        trackerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        trackerFrame.addWindowListener(this);

        mainBackGroundPanel = new JPanel();
        mainBackGroundPanel.setLayout(new BoxLayout(mainBackGroundPanel, BoxLayout.Y_AXIS));
        mainBackGroundPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        topButtonRowPanel = new JPanel();
        topButtonRowPanel.setLayout(new BoxLayout(topButtonRowPanel, BoxLayout.X_AXIS));
        topButtonRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerButtonPanel = new JPanel();
        centerButtonPanel.setLayout(new BoxLayout(centerButtonPanel, BoxLayout.X_AXIS));
        centerButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        allButton = new JButton("All");
        allButton.addActionListener(this);

        expiredButton = new JButton("Expired");
        expiredButton.addActionListener(this);

        notExpiredButton = new JButton("Not Expired");
        notExpiredButton.addActionListener(this);

        expireIn7Button = new JButton("Expires Within 7 Days");
        expireIn7Button.addActionListener(this);

        topButtonRowPanel.add(allButton);
        topButtonRowPanel.add(expiredButton);
        topButtonRowPanel.add(notExpiredButton);
        topButtonRowPanel.add(expireIn7Button);

        mainBackGroundPanel.add(topButtonRowPanel);

        scrollPaneMainBackgroundPanel = new JPanel();
        scrollPaneMainBackgroundPanel.setLayout(new BoxLayout(scrollPaneMainBackgroundPanel, BoxLayout.Y_AXIS));
        scrollPaneMainBackgroundPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        viewItemsScrollPane = new JScrollPane(scrollPaneMainBackgroundPanel);
        viewItemsScrollPane.getViewport().setPreferredSize(new Dimension(10, 500));

        mainBackGroundPanel.add(viewItemsScrollPane);

        viewAllItems(1);

        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(this);

        centerButtonPanel.add(addItemButton);

        mainBackGroundPanel.add(centerButtonPanel);

        trackerFrame.add(mainBackGroundPanel);
        trackerFrame.setVisible(true);
    }

    /**
     * Changes the button colours depending on which button is pressed
     * @param viewNumber number is determines if user wants to view all consumables
     *                   , expired consumables, non expired consumables, or
     *                   items expiring within 7 days
     */
    public void setButtonColour(int viewNumber){

        //Learned and adapted how to change button text colour from:
        //https://stackoverflow.com/questions/15393385/how-to-change-text-color-of-a-jbutton
        //User: Mike G.
        if(viewNumber == 1){
            expiredButton.setForeground(Color.BLACK);
            allButton.setForeground(Color.RED);
            notExpiredButton.setForeground(Color.BLACK);
            expireIn7Button.setForeground(Color.BLACK);

        }else if(viewNumber == 2){
            expiredButton.setForeground(Color.RED);
            allButton.setForeground(Color.BLACK);
            notExpiredButton.setForeground(Color.BLACK);
            expireIn7Button.setForeground(Color.BLACK);

        }else if(viewNumber == 3){
            expiredButton.setForeground(Color.BLACK);
            allButton.setForeground(Color.BLACK);
            notExpiredButton.setForeground(Color.BLACK);
            expireIn7Button.setForeground(Color.RED);

        }else if(viewNumber == 4){

            expiredButton.setForeground(Color.BLACK);
            allButton.setForeground(Color.BLACK);
            notExpiredButton.setForeground(Color.RED);
            expireIn7Button.setForeground(Color.BLACK);

        }

    }

    /**
     * Creates a panel which contains formatted consumables as well as
     * their respective remove buttons
     * @param viewNumber determines if user wants to view all consumables,
     *                   expired, non expired, or expiring within 7 days
     *                   consumables
     */
    public void viewAllItems(int viewNumber) throws IOException, InterruptedException {
        setButtonColour(viewNumber);
        JButton removeButton;
        JPanel scrollPaneRowPanel;
        JPanel scrollPaneCollumnPanel;
        JLabel itemLabel;

        //clear current scrollpane panel
        scrollPaneMainBackgroundPanel.removeAll();
        scrollPaneMainBackgroundPanel.revalidate();
        scrollPaneMainBackgroundPanel.repaint();

        formattedConsumables = new ArrayList<>();
        formattedConsumables = allItems.formattedAllItems(viewNumber);

        scrollPaneCollumnPanel = new JPanel();
        scrollPaneCollumnPanel.setLayout(new BoxLayout(scrollPaneCollumnPanel, BoxLayout.Y_AXIS));
        scrollPaneCollumnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel tempLabel = new JLabel();

        scrollPaneRowPanel = new JPanel();
        scrollPaneRowPanel.setLayout(new BoxLayout(scrollPaneRowPanel, BoxLayout.X_AXIS));
        scrollPaneRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        expiredButton.setOpaque(true);
        allButton.setOpaque(true);
        notExpiredButton.setOpaque(true);
        expireIn7Button.setOpaque(true);


        //display respective message if there are no items for the following view
        if(viewNumber == 2 && formattedConsumables.size() == 0){
            tempLabel.setText("No Expired Items to Show");
            scrollPaneRowPanel.add(tempLabel);
            scrollPaneCollumnPanel.add(scrollPaneRowPanel);

        }else if(viewNumber == 3 && formattedConsumables.size() == 0){
            tempLabel.setText("No Items Expiring within 7 Days to Show");
            scrollPaneRowPanel.add(tempLabel);
            scrollPaneCollumnPanel.add(scrollPaneRowPanel);


        }else if(viewNumber == 4 && formattedConsumables.size() == 0){
            tempLabel.setText("No Non-Expired Items to Show");
            scrollPaneRowPanel.add(tempLabel);
            scrollPaneCollumnPanel.add(scrollPaneRowPanel);

        }else if(viewNumber == 1 && formattedConsumables.size() == 0){
            tempLabel.setText("No Items to Show");
            scrollPaneRowPanel.add(tempLabel);
            scrollPaneCollumnPanel.add(scrollPaneRowPanel);

        }
        //add items and remove buttons to the panel which will be added to the scrollpane
        if(viewNumber != 1){
            for (int i = 0; i < formattedConsumables.size(); i++) {

                scrollPaneRowPanel = new JPanel();
                scrollPaneRowPanel.setLayout(new BoxLayout(scrollPaneRowPanel, BoxLayout.X_AXIS));
                scrollPaneRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                itemLabel = new JLabel("<html>" + "#" + (i + 1) + formattedConsumables.get(i));
                scrollPaneRowPanel.add(itemLabel);
                scrollPaneRowPanel.setBorder(BorderFactory.createLineBorder(Color.black));


                scrollPaneCollumnPanel.add(scrollPaneRowPanel);
                scrollPaneCollumnPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        }else{
            for (int i = 0; i < formattedConsumables.size(); i++) {
                scrollPaneRowPanel = new JPanel();
                scrollPaneRowPanel.setLayout(new BoxLayout(scrollPaneRowPanel, BoxLayout.X_AXIS));
                scrollPaneRowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                itemLabel = new JLabel("<html>" + "#" + (i + 1) + formattedConsumables.get(i));

                removeButton = new JButton("Remove # " + (i + 1));
                removeButton.addActionListener(this);

                scrollPaneRowPanel.add(itemLabel);

                scrollPaneRowPanel.add(removeButton);
                scrollPaneRowPanel.setBorder(BorderFactory.createLineBorder(Color.black));

                scrollPaneCollumnPanel.add(scrollPaneRowPanel);
                scrollPaneCollumnPanel.setBorder(BorderFactory.createLineBorder(Color.black));

                expiredButton.setForeground(Color.BLACK);
                allButton.setForeground(Color.RED);
                notExpiredButton.setForeground(Color.BLACK);
                expireIn7Button.setForeground(Color.BLACK);
            }
        }
        scrollPaneMainBackgroundPanel.add(scrollPaneCollumnPanel);
        trackerFrame.validate();
    }

    /**
     * Performs action based on which buttons are pressed by the user
     * @param e used to check if button was pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("All")){
            try {
                viewAllItems(1);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        }else if(e.getActionCommand().equals("Expired")){
            try {
                viewAllItems(2);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        }else if(e.getActionCommand().equals("Not Expired")){
            try {
                viewAllItems(4);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        }else if(e.getActionCommand().equals("Expires Within 7 Days")){
            try {
                viewAllItems(3);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        }else if(e.getActionCommand().equals("Add Item")){

            try {
                allItems.addNewItem(trackerFrame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                viewAllItems(1);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        try {
            formattedConsumables = allItems.formattedAllItems(1);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }



        //Check all buttons aligned with total amount of items
        for(int i = 0; i < formattedConsumables.size(); i++){

            if(e.getActionCommand().equals("Remove # " + (i + 1))){
                try {
                    allItems.removeItem(i);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    viewAllItems(1);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void itemStateChanged(ItemEvent e) {}

    /**
     * Empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void windowOpened(WindowEvent e) {}

    /**
     * Initiates the closing method as well as closes the jframe
     * @param e used to check if button is pressed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            allItems.endProgram();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        trackerFrame.dispose();
    }

    /**
     * Empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void windowClosed(WindowEvent e) {}

    /**
     * Empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void windowIconified(WindowEvent e) {}

    /**
     * Empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void windowActivated(WindowEvent e) {}

    /**
     * Performs empty method
     * @param e used to check if button is pressed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
