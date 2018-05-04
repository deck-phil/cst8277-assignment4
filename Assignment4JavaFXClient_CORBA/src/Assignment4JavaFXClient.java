
import business.FishStickFacadeRemote;
import entity.FishStick;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * javaFX client for the assignment 4
 * JEE Project
 * 
 * Permits clients to 
 *  view the data
 *  insert a fishstick
 * 
 * @author Philip Deck, Mahad Osman, Ivan Zubaryev, Adam Tremblett
 */
public class Assignment4JavaFXClient extends Application{


    //Labels for the attribute names
    private Label recordNumLbl = new Label();
    private Label omegaLbl = new Label();
    private Label lambdaLbl = new Label();
    
    //TextFields for the attributes
    private TextField recordNumTxtField = new TextField();
    private TextField omegaTxtField = new TextField();
    private TextField lambdaTxtField = new TextField();
    private TextArea fishStickTxtArea = new TextArea();
    
    //Two buttons, one to insert and one to view data
    private Button viewButton = new Button();
    private Button addButton = new Button();

    private static FishStickFacadeRemote remoteStuff = null;

    @Override
    public void start(Stage primaryStage) {

        //Set the label text
        recordNumLbl.setText("Record Number");
        omegaLbl.setText("Omega");
        lambdaLbl.setText("Lambda");
        
        //Set the view button text
        viewButton.setText("Show FishSticks");
        //Set the action on pressed
        viewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (remoteStuff != null) {
                        //Clear the text area
                        fishStickTxtArea.clear();
                        //For each fishstick append it in the text area
                        for (FishStick s : remoteStuff.findAll())
                            fishStickTxtArea.appendText(s.toString()+"\n");

                    } else {
                        //If their is no connection
                        showAlert(AlertType.ERROR, "No remote object available");
                    }
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, ex.getMessage());
                }
            }
        });

        //Set the insert button button text
        addButton.setText("Add FishStick");
        //Set the action on pressed
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (remoteStuff != null) {
                        //Create a new fishstick
                        FishStick fishStick = new FishStick();
                        //Set the attributes
                        fishStick.setRecordNumber(Integer.parseInt(recordNumTxtField.getText()));
                        fishStick.setOmega(omegaTxtField.getText());
                        fishStick.setLambda(lambdaTxtField.getText());
                        //Insert it
                        remoteStuff.create(fishStick);
                    } else {
                        showAlert(AlertType.ERROR, "No remote object available");
                    }
                } catch (Exception ex) {
                    showAlert(AlertType.ERROR, ex.getMessage());
                }
            }
        });

        //HBox to contain the buttons
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.setPadding(new Insets(10, 10, 10, 10));
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsHBox.getChildren().addAll(addButton, viewButton);

        //Root Pane for the application
        GridPane root = new GridPane();
        //Adding all components in the root pane
        root.add(recordNumLbl, 1, 1); // column 1, row 1
        root.add(recordNumTxtField, 2, 1); // column 2, row 1
        root.add(omegaLbl, 1, 2); // column 1, row 2
        root.add(omegaTxtField, 2, 2); // column 2, row 2
        root.add(lambdaLbl, 1, 3); // column 1, row 2
        root.add(lambdaTxtField, 2, 3); // column 2, row 2
        root.add(buttonsHBox, 2, 4); // column 2, row 3
        root.add(fishStickTxtArea, 2, 5); // column 2, row 4
        root.setHgap(10);
        root.setVgap(10);
        //As well as the margin
        root.setMargin(fishStickTxtArea, new Insets(0, 10, 10, 0)); // top, right, bottom, left
        root.setMargin(recordNumTxtField, new Insets(0, 10, 0, 0));
        root.setMargin(omegaTxtField, new Insets(0, 10, 0, 0));
        root.setMargin(lambdaTxtField, new Insets(0, 10, 0, 0));

        Scene scene = new Scene(root);

        //Application title
        primaryStage.setTitle("JavaFX Client Demo - CORBA");
        primaryStage.setScene(scene);
        primaryStage.show();
        showAlert(AlertType.INFORMATION, "Trying for a session...");
        //Grab a session
        remoteStuff = getRemoteSession();
        if(remoteStuff != null){
            showAlert(AlertType.INFORMATION, "Got a session :)");
        }
        else{
            showAlert(AlertType.ERROR, "No remote object available");
        }
    }

    //
    /**
     * Helper method to display alerts
     * 
     * @param alertType Type of alert. INFORMATION, ALERT etc.
     * @param message Message to display
     */
    public void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Grabs a session from the assignment 4
     * url.
     * 
     * @return Remote FishStickFacade
     */
    private static FishStickFacadeRemote getRemoteSession() {
        FishStickFacadeRemote session = null;

        // CORBA properties and values and lookup taken after earlier work provided by
        // Todd Kelley (2016) Personal Communication
        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        try {
            InitialContext ic = new InitialContext();
            session = (FishStickFacadeRemote) ic.lookup("java:global/Assignment4/Assignment4-ejb/FishStickFacade");
        } catch (NamingException e) {
            System.out.println("Problem Cause: \n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Problem Cause: \n" + e.getMessage());
        }
        return session;
    }

    public static void main(String[] args) {
        Application.launch(Assignment4JavaFXClient.class);
    }
    
}
