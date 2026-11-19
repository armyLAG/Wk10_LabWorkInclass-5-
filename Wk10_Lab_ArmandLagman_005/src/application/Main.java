
package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        EmploymentFormDao employmentDao = new EmploymentFormDao();

        primaryStage.setTitle("Employment Application");

        // Create GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Form title
        Text scenetitle = new Text("Employment Application");
        grid.add(scenetitle, 0, 0, 2, 1);

        // Full Name
        Label fullNameLabel = new Label("Full Name:");
        grid.add(fullNameLabel, 0, 1);

        TextField fullNameTextField = new TextField();
        grid.add(fullNameTextField, 1, 1);

        // Current Address
        Label addressLabel = new Label("Current Address:");
        grid.add(addressLabel, 0, 2);

        TextField addressTextField = new TextField();
        grid.add(addressTextField, 1, 2);
        
        // Contact Number
        Label contactLabel = new Label("Contact Number:");
        grid.add(contactLabel, 0, 3);

        TextField contactTextField = new TextField();
        grid.add(contactTextField, 1, 3);

        // Email Address
        Label emailLabel = new Label("Email Address:");
        grid.add(emailLabel, 0, 4);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 4);
        
     // Highest Educational Attainment
        Label educationLabel = new Label("Highest Educational Attainment:");
        grid.add(educationLabel, 0, 5);

        TextField educationTextField = new TextField();
        grid.add(educationTextField, 1, 5);

        // Gender
        Label genderLabel = new Label("Gender:");
        grid.add(genderLabel, 2, 5);

        RadioButton maleRadio = new RadioButton("Male");
        RadioButton femaleRadio = new RadioButton("Female");
        RadioButton otherRadio = new RadioButton("Other");

        ToggleGroup genderGroup = new ToggleGroup();

        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        otherRadio.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(10, maleRadio, femaleRadio, otherRadio);
        grid.add(genderBox, 3, 5);
        
        // Date Available
        Label dateLabel = new Label("Date Available:");
        grid.add(dateLabel, 0, 6);

        DatePicker datePicker = new DatePicker();
        grid.add(datePicker, 1, 6);

        // Desired Position
        Label positionLabel = new Label("Desired Position:");
        grid.add(positionLabel, 2, 6);

        TextField positionTextField = new TextField();
        grid.add(positionTextField, 3, 6);
        
        // Desired Salary
        Label salaryLabel = new Label("Desired Salary:");
        grid.add(salaryLabel, 0, 7);

        TextField salaryTextField = new TextField();
        grid.add(salaryTextField, 1, 7);

        // Authorized to Work
        Label authorizedLabel = new Label("Authorized to Work?");
        grid.add(authorizedLabel, 2, 7);

        RadioButton authorizedYes = new RadioButton("Yes");
        RadioButton authorizedNo = new RadioButton("No");

        ToggleGroup authorizedGroup = new ToggleGroup();

        authorizedYes.setToggleGroup(authorizedGroup);
        authorizedNo.setToggleGroup(authorizedGroup);

        HBox authorizedBox = new HBox(10, authorizedYes, authorizedNo);
        grid.add(authorizedBox, 3, 7);
        
        
        //Relatives Working 
        Label relativesLabel = new Label("Relatives Working?");
        grid.add(relativesLabel, 0, 8);
        
        RadioButton relativesYes = new RadioButton("Yes");
        RadioButton relativesNo = new RadioButton("No");
        
        ToggleGroup relativesGroup = new ToggleGroup();
        
        relativesYes.setToggleGroup(relativesGroup);
        relativesNo.setToggleGroup(relativesGroup);
        
        HBox relativesBox = new HBox(10, relativesYes, relativesNo);
        grid.add(relativesBox, 1, 8);
        
        // Explanation
        Label explanationLabel = new Label("If yes, please explain:");
        grid.add(explanationLabel, 0, 9);

        TextArea explanationTextArea = new TextArea();
        explanationTextArea.setPrefRowCount(2);
        grid.add(explanationTextArea, 1, 9, 3, 1);
        
        //Submit Button 
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
        	
        	EmploymentForm employment = new EmploymentForm();

        	// Get the information entered by the user and store it in the EmploymentForm object
        	employment.setFullName(fullNameTextField.getText());
        	employment.setCurrentAddress(addressTextField.getText());
        	employment.setContactNumber(contactTextField.getText());
        	employment.setEmailAddress(emailTextField.getText());

        	// Get the education entered by the user
        	employment.setEducation(educationTextField.getText());

        	// Get the selected gender
        	if (genderGroup.getSelectedToggle() != null) {
        	    RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
        	    employment.setGender(selectedGender.getText());
        	}

        	// Get the date available and desired position
        	if (datePicker.getValue() != null) {
        	    employment.setDateAvailable(datePicker.getValue().toString());
        	}

        	employment.setDesiredPosition(positionTextField.getText());

        	// Get the desired salary and check if the user is authorized to work
        	employment.setDesiredSalary(salaryTextField.getText());

        	if (authorizedGroup.getSelectedToggle() != null) {
        	    RadioButton selectedAuthorization = (RadioButton) authorizedGroup.getSelectedToggle();
        	    employment.setAuthorizedToWork(selectedAuthorization.getText().equals("Yes"));
        	}

        	// Check if the user has any relatives working at the company
        	if (relativesGroup.getSelectedToggle() != null) {
        	    RadioButton selectedRelative = (RadioButton) relativesGroup.getSelectedToggle();
        	    employment.setRelativesWorking(selectedRelative.getText().equals("Yes"));
        	}

        	// Get the explanation entered by the user
        	employment.setExplanation(explanationTextArea.getText());
        	
        	// Save the employment form to the database
        	try {
        	    int result = employmentDao.saveEmploymentForm(employment);

        	    if (result > 0) {
        	        alert(AlertType.INFORMATION, "Success", "Employment form submitted successfully!");
        	    }

        	} catch (SQLException e) {
        	    alert(AlertType.ERROR, "Database Error", e.getMessage());
        	}

        	});
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().add(submitButton);
        
        grid.add(buttonBox, 3, 10);
        
        
        
     // Display window
        Scene scene = new Scene(grid, 900, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
 // Display an alert message to the user
    private void alert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
