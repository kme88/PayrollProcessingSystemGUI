package PayrollProcessingSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.lang.NumberFormatException;

/**
 * This class is the controller class for the GUI display.
 * This class handles all GUI controls by taking in the user input and performing the appropriate actions
 * based on what the user enters and what button the user clicks on.
 * @author Kathleen Eife, Isha Vora
 */
public class Controller implements Initializable {

    Company myCompany = new Company();

    private String inputName = "";
    private String inputEmployeeType = "";
    private String inputDepartment = "";
    private Date inputDateHired = null;
    private float inputAnnualSalary = 0f;
    private int inputHoursWorked = 0;
    private float inputPayRate = 0f;
    private String inputManagementType = "";
    private boolean isSuccessful;

    @FXML
    private TextField txtName, txtAnnualSalary, txtHoursWorked, txtRate;

    @FXML
    private ToggleGroup departmentName, employeeType, managementType;

    @FXML
    private RadioButton rbtnCS, rbtnIT, rbtnECE;

    @FXML
    private DatePicker dateDateHired;

    @FXML
    private RadioButton rbtnFulltime, rbtnManagement, rbtnParttime;

    @FXML
    private RadioButton rbtnManager, rbtnDepartmentHead, rbtnDirector;

    @FXML
    private Button btnSetHours;

    @FXML
    private TextArea txtDisplayMain;

    private static final int MANAGER_CODE = 1;
    private static final int DEPT_HEAD_CODE = 2;
    private static final int DIRECTOR_CODE = 3;
    private static final int MIN_HOURS = 0;
    private static final int MAX_HOURS = 100;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearInputFields();
    }

    /**
     * This method prints the outcome of the user–input based on whether the command was performed successfully or not
     * @param command the command that enables the Payroll Processing System to perform a given action
     * @param isSuccessful a boolean that holds whether the action was performed successfully by the system or not
     */
    private void printOutcome(String command, boolean isSuccessful) {
        if (command.charAt(0) == 'A') {
            if (isSuccessful) {
                if (!(txtHoursWorked.textProperty().get().trim().equals(""))) {
                    txtDisplayMain.appendText("Employee added. (Note: Hours Worked input ignored because not required)"
                                                  + "\n");
                }
                else {
                    txtDisplayMain.appendText("Employee added." + "\n");
                }
            }
            else {
                txtDisplayMain.appendText("Employee is already in the list." + "\n");
            }
        }
        else if (command.equals("R")) {
            if (isSuccessful) {
                txtDisplayMain.appendText("Employee removed." + "\n");
            }
            else {
                txtDisplayMain.appendText("Employee does not exist." + "\n");
            }
        }
        else if (command.equals("S")) {
            if (isSuccessful) {
                if (!(txtRate.textProperty().get().trim().equals(""))) {
                    txtDisplayMain.appendText("Working hours set. (Note: Pay Rate input ignored because not required)" + "\n");
                }
                else {
                    txtDisplayMain.appendText("Working hours set." + "\n");
                }
            }
            else {
                txtDisplayMain.appendText("Employee does not exist." + "\n");
            }
        }
    }

    /**
     * This method disables certain input fields based on the employee type selection made by the user.
     * @param actionEvent employee type ("Fulltime", "Parttime", "Management") radio button selected
     */
    @FXML
    private void processEmployeeType(ActionEvent actionEvent) {
        RadioButton selectedRadioButton = (RadioButton) employeeType.getSelectedToggle();
        inputEmployeeType = selectedRadioButton.getText();
        if (inputEmployeeType.equals("Full Time")) {
            txtAnnualSalary.setDisable(false);

            txtHoursWorked.clear();
            txtRate.clear();
            rbtnManager.setSelected(false);
            rbtnDepartmentHead.setSelected(false);
            rbtnDirector.setSelected(false);

            btnSetHours.setDisable(true);
            txtHoursWorked.setDisable(true);
            txtRate.setDisable(true);
            rbtnManager.setDisable(true);
            rbtnDepartmentHead.setDisable(true);
            rbtnDirector.setDisable(true);
        }
        else if (inputEmployeeType.equals("Part Time")) {
            txtHoursWorked.setDisable(false);
            txtRate.setDisable(false);
            btnSetHours.setDisable(false);

            txtAnnualSalary.clear();
            rbtnManager.setSelected(false);
            rbtnDepartmentHead.setSelected(false);
            rbtnDirector.setSelected(false);

            txtAnnualSalary.setDisable(true);
            rbtnManager.setDisable(true);
            rbtnDepartmentHead.setDisable(true);
            rbtnDirector.setDisable(true);
        }
        else {
            txtAnnualSalary.setDisable(false);
            rbtnManager.setDisable(false);
            rbtnDepartmentHead.setDisable(false);
            rbtnDirector.setDisable(false);

            txtHoursWorked.clear();
            txtRate.clear();

            btnSetHours.setDisable(true);
            txtHoursWorked.setDisable(true);
            txtRate.setDisable(true);
        }
    }

    /**
     * This private helped method checks if relevant user input fields are filled.
     * @return true if all attributes are filled by user, false otherwise
     */
    private boolean processAttributes() {
        inputName = txtName.textProperty().get().trim();
        processDate();
        processDepartment();

        if (inputName.trim().equals("") || dateDateHired.getValue() == null || inputDepartment.equals("")) {
            txtDisplayMain.appendText("Input field(s) missing." + "\n");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * This method performs the actions to try to add an employee to the database based on user input.
     * @param actionEvent Add Employee button is clicked on
     */
    @FXML
    private void addEmployee(ActionEvent actionEvent) {
        if (inputEmployeeType.equals("")) {
            txtDisplayMain.appendText("Input field(s) missing." + "\n");
        }
        else {
            if (processAttributes()) {
                if (!(validateDate())) {
                    txtDisplayMain.appendText("Invalid Date!" + "\n");
                }
                else {
                    if (inputEmployeeType.equals("Full Time")) {
                        if (validateAnnualSalary()) {
                            Fulltime fulltimeEmployee = new Fulltime(inputName, inputDepartment, inputDateHired,
                                    inputAnnualSalary);
                            isSuccessful = myCompany.add(fulltimeEmployee);
                            printOutcome("AF", isSuccessful);
                            clearInputFields();
                        }
                    }
                    else if (inputEmployeeType.equals("Part Time")) {
                        if (validatePayRate()) {
                            Parttime parttimeEmployee = new Parttime(inputName, inputDepartment, inputDateHired,
                                    inputPayRate);
                            isSuccessful = myCompany.add(parttimeEmployee);
                            printOutcome("AP", isSuccessful);
                            clearInputFields();
                        }
                    }
                    else {
                        processManagementType();

                        if (inputManagementType.equals("")) {
                            txtDisplayMain.appendText( "Input field(s) missing." + "\n");
                        }
                        else if (validateAnnualSalary()) {
                            Management managementEmployee;
                            if (inputManagementType.equals("Manager")) {
                                managementEmployee = new Management(inputName, inputDepartment, inputDateHired,
                                        inputAnnualSalary, MANAGER_CODE);
                            }
                            else if (inputManagementType.equals("Department Head")) {
                                managementEmployee = new Management(inputName, inputDepartment, inputDateHired,
                                        inputAnnualSalary, DEPT_HEAD_CODE);
                            }
                            else {
                                managementEmployee = new Management(inputName, inputDepartment, inputDateHired,
                                        inputAnnualSalary, DIRECTOR_CODE);
                            }
                            isSuccessful = myCompany.add(managementEmployee);
                            printOutcome("AM", isSuccessful);
                            clearInputFields();
                        }
                    }
                }
            }
        }
    }

    /**
     * This method performs the actions to try to remove an employee to the database based on user input.
     * @param actionEvent Remove Employee button is clicked on
     */
    @FXML
    private void removeEmployee(ActionEvent actionEvent) {
        if (processAttributes()) {
            if (myCompany.isEmplistEmpty()) {
                txtDisplayMain.appendText("Employee database is empty." + "\n");
            }
            else {
                Employee removeEmployee = new Employee(inputName, inputDepartment, inputDateHired);
                isSuccessful = myCompany.remove(removeEmployee);
                printOutcome("R", isSuccessful);
            }
            clearInputFields();
        }
    }

    /**
     * This method performs the actions to try to set working hours for a Parttime employee in the database
     * based on user input.
     * @param actionEvent Set Hours button is clicked on
     */
    @FXML
    private void processSetHours(ActionEvent actionEvent) {
        if (processAttributes()) {
            if (txtHoursWorked.textProperty().get().trim().equals("")) {
                txtDisplayMain.appendText("Input field(s) missing." + "\n");
            }
            else if (validateHoursWorked()) {
                if (myCompany.isEmplistEmpty()) {
                    txtDisplayMain.appendText("Employee database is empty." + "\n");
                }
                else {
                    Parttime setHoursEmployee = new Parttime(inputName, inputDepartment, inputDateHired,
                                                             inputHoursWorked);
                    isSuccessful = myCompany.setHours(setHoursEmployee);
                    printOutcome("S", isSuccessful);
                }
                clearInputFields();
            }
        }
    }

    /**
     * This method calls the clearInputFields() method to clear the user input fields.
     * @param actionEvent Clear button is clicked on
     */
    @FXML
    private void clearInput(ActionEvent actionEvent) {
        clearInputFields();
    }

    /**
     * This private helper method clears the user input fields.
     */
    private void clearInputFields() {
        txtName.clear();
        txtAnnualSalary.clear();
        txtRate.clear();
        txtHoursWorked.clear();
        rbtnCS.setSelected(false);
        rbtnIT.setSelected(false);
        rbtnECE.setSelected(false);
        rbtnFulltime.setSelected(false);
        rbtnParttime.setSelected(false);
        rbtnManagement.setSelected(false);
        rbtnManager.setSelected(false);
        rbtnDepartmentHead.setSelected(false);
        rbtnDirector.setSelected(false);
        dateDateHired.setValue(null);
        dateDateHired.getEditor().clear();

        txtAnnualSalary.setDisable(true);
        btnSetHours.setDisable(true);
        txtHoursWorked.setDisable(true);
        txtRate.setDisable(true);
        rbtnManager.setDisable(true);
        rbtnDepartmentHead.setDisable(true);
        rbtnDirector.setDisable(true);

        inputName = "";
        inputEmployeeType = "";
        inputDepartment = "";
        inputDateHired = null;
        inputAnnualSalary = 0f;
        inputHoursWorked = 0;
        inputPayRate = 0f;
        inputManagementType = "";
    }

    /**
     * This method checks if the user input for annual salary is valid.
     * @return true if the annual salary is valid, false otherwise
     */
    private boolean validateAnnualSalary() {
        if (txtAnnualSalary.textProperty().get().trim().equals("")) {
            txtDisplayMain.appendText("Input field(s) missing." + "\n");
            return false;
        }

        try {
            inputAnnualSalary = Float.parseFloat(txtAnnualSalary.textProperty().get().trim());
        }
        catch (NumberFormatException notANumber) {
            txtDisplayMain.appendText("Invalid Annual Salary: Not a Number." + "\n");
            return false;
        }
        if (inputAnnualSalary < 0) {
            txtDisplayMain.appendText("Salary cannot be negative." + "\n");
            return false;
        }
        if (inputAnnualSalary == 0) {
            txtDisplayMain.appendText("Salary cannot be zero." + "\n");
            return false;
        }
        return true;
    }

    /**
     * This method checks if the user input for pay rate is valid.
     * @return true if the pay rate is valid, false otherwise
     */
    private boolean validatePayRate() {
        if (txtRate.textProperty().get().trim().equals("")) {
            txtDisplayMain.appendText("Input field(s) missing." + "\n");
            return false;
        }

        try {
            inputPayRate = Float.parseFloat(txtRate.textProperty().get().trim());
        }
        catch (NumberFormatException notANumber) {
            txtDisplayMain.appendText("Invalid Pay Rate: Not a Number." + "\n");
            return false;
        }
        if (inputPayRate < 0) {
            txtDisplayMain.appendText("Pay Rate cannot be negative." + "\n");
            return false;
        }
        if (inputPayRate == 0) {
            txtDisplayMain.appendText("Pay Rate cannot be zero." + "\n");
            return false;
        }
        return true;
    }

    /**
     * This method checks if the user input for hours worked is valid.
     * @return true if the hours worked is valid, false otherwise
     */
    private boolean validateHoursWorked() {
        if (txtHoursWorked.textProperty().get().trim().equals("")) {
            txtDisplayMain.appendText("Input field(s) missing." + "\n");
            return false;
        }

        try {
            inputHoursWorked = Integer.parseInt(txtHoursWorked.textProperty().get().trim());
        }
        catch (NumberFormatException notANumber) {
            txtDisplayMain.appendText("Invalid Hours Worked: Not a Number/Integer." + "\n");
            return false;
        }

        if (inputHoursWorked == MIN_HOURS) {
            txtDisplayMain.appendText("Working hours cannot be zero." + "\n");
            return false;
        }
        if (inputHoursWorked < MIN_HOURS) {
            txtDisplayMain.appendText("Working hours cannot be negative." + "\n");
            return false;
        }
        else if (inputHoursWorked > MAX_HOURS) {
            txtDisplayMain.appendText("Invalid Hours: over 100." + "\n");
            return false;
        }
        return true;
    }

    /**
     * This method processes the date that the user inputted by converting to a Date object.
     */
    private void processDate() {
        if (dateDateHired.getValue() != null) {
            LocalDate date = dateDateHired.getValue();
            String inputLocalDate = date.toString();
            String[] inputParts = inputLocalDate.split("-");
            int year = Integer.parseInt(inputParts[0]);
            int month = Integer.parseInt(inputParts[1]);
            int day = Integer.parseInt(inputParts[2]);
            String dateString = month + "/" + day + "/" + year;
            inputDateHired = new Date(dateString);
        }
    }

    /**
     * This method calls isValid() from the Date class to check if the user input for date hired is valid.
     * @return true if the date is valid, false otherwise
     */
    private boolean validateDate() {
        if (inputDateHired.isValid()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method processes the department type based on the user input.
     */
    private void processDepartment() {
        RadioButton selectedRadioButton = (RadioButton) departmentName.getSelectedToggle();
        if (selectedRadioButton != null) {
            inputDepartment = selectedRadioButton.getText();
        }
    }

    /**
     * This method processes the management type based on the user input.
     */
    private void processManagementType() {
        RadioButton selectedRadioButton = (RadioButton) managementType.getSelectedToggle();
        if (selectedRadioButton != null) {
            inputManagementType = selectedRadioButton.getText();
        }
    }

    /**
     * This method processes the input file and calls the importDatabase() method in the Company class.
     * @param actionEvent Import button is clicked on
     */
    @FXML
    private void processImport(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);
        if (sourceFile == null) {
            txtDisplayMain.appendText("No Import File Selected." + "\n");
        }
        else if (sourceFile.length() == 0) {
            txtDisplayMain.appendText("Import file is empty." + "\n");
        }
        else if (!myCompany.importDatabase(sourceFile, myCompany)) {
            txtDisplayMain.appendText("File Import Failed." + "\n");
        }
        else {
            txtDisplayMain.appendText("File has been imported successfully." + "\n");
        }
    }

    /**
     * This method processes the output for export and calls the exportDatabase() method in the Company class.
     * @param actionEvent Export button is clicked on
     */
    @FXML
    private void processExport(ActionEvent actionEvent) {
        if (myCompany.isEmplistEmpty()) {
            txtDisplayMain.appendText("Employee database is empty." + "\n");
        }
        else {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Target File for the Export");
            chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                    new ExtensionFilter("All Files", "*.*"));
            Stage stage = new Stage();
            File targetFile = chooser.showSaveDialog(stage);
            if (targetFile == null) {
                txtDisplayMain.appendText("No Export File Selected." + "\n");
            }
            else if (!myCompany.exportDatabase(targetFile)) {
                txtDisplayMain.appendText("File Export Failed." + "\n");
            }
            else {
                txtDisplayMain.appendText("File has been exported successfully." + "\n");
            }
        }
    }

    /**
     * This method displays all of the information for Employees in the database using the print method from
     * the Company class.
     * @param actionEvent Print button is clicked on
     */
    @FXML
    private void processPrint(ActionEvent actionEvent) {
        txtDisplayMain.appendText(myCompany.print());
    }

    /**
     * This method displays all of the information for Employees in the database sorted by the dates that they were
     * hired using the printByDate method from the Company class.
     * @param actionEvent Print By Date button is clicked on
     */
    @FXML
    private void processPrintByDate(ActionEvent actionEvent) {
        txtDisplayMain.appendText(myCompany.printByDate());
    }

    /**
     * This method displays all of the information for Employees in the database sorted by the department that they
     * work in using the printByDepartment method from the Company class.
     * @param actionEvent Print By Department button is clicked on
     */
    @FXML
    private void processPrintByDepartment(ActionEvent actionEvent) {
        txtDisplayMain.appendText(myCompany.printByDepartment());
    }

    /**
     * This method processes the payments for all Employees using the processPayment method from the Company class.
     * @param actionEvent Calculate Payment button is clicked on
     */
    @FXML
    private void processCalculatePayment(ActionEvent actionEvent) {
        if (myCompany.isEmplistEmpty()) {
            txtDisplayMain.appendText("Employee database is empty." + "\n");
        }
        else {
            myCompany.processPayments();
            txtDisplayMain.appendText("Calculation of employee payments is done." + "\n");
        }
    }
}