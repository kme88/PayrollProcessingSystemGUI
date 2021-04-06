package PayrollProcessingSystem;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is an array-based container class that implements the employee database.
 * This class includes a company constructor to create a bag structure to hold the employee objects, various
 * company services such as grow(), add(), remove(), setHours(), processPayment(), isEmplistEmpty(), print(),
 * printByDate(), printByDepartment(), and various private helper methods.
 * @author Kathleen Eife, Isha Vora
 */
public class Company {
    private Employee[] emplist; //list of employees -> initial capacity = 4
    private int numEmployee;

    private static final int INITIAL_CAPACITY = 4;
    private static final int INCREASE_CAPACITY = 4;
    private static final int NOT_FOUND_IN_COMPANY = -1;
    private static final int EXPORT_FILE_START_INDEX = 50;

    /**
     * This default constructor creates an empty array structure with an initial capacity of 4.
     */
    public Company() {
        emplist = new Employee[INITIAL_CAPACITY];
    }

    /**
     * This method finds an employee in the bag structure.
     * @param employee an employee object
     * @return i an integer that represents the index of the employee object in the Employee[] emplist array,
     * or returns NOT_FOUND_IN_COMPANY if the employee is not in the company
     */
    private int find(Employee employee) {
        for (int i = 0; i < numEmployee; i++) {
            if (employee.equals(emplist[i])) {
                return i;
            }
        }
        return NOT_FOUND_IN_COMPANY;
    }

    /**
     * This method checks if the emplist array is empty.
     * @return true if the emplist array is empty, false otherwise
     */
    public boolean isEmplistEmpty() {
        if (numEmployee == 0) {
            return true;
        }
        return false;
    }

    /**
     * This method increases the array capacity by 4.
     */
    private void grow() {
        Employee[] tempEmpList = new Employee[numEmployee + INCREASE_CAPACITY];
        for (int i = 0; i < numEmployee; i++) {
            tempEmpList[i] = emplist[i];
        }
        emplist = tempEmpList;
    }

    /**
     * This method adds an employee to the company.
     * @param employee an employee object
     * @return true if the employee is successfully added to the company, false otherwise
     */
    public boolean add(Employee employee) {

        Profile empProfile = employee.getEmployeeProfile();
        String empName = empProfile.getName();
        String empDept = empProfile.getDepartment();
        Date empDateHired = empProfile.getDateHired();
        Employee empToCheck = new Employee(empName, empDept, empDateHired);
        int indexOfEmp = find(empToCheck);

        if (indexOfEmp == NOT_FOUND_IN_COMPANY) {
            if (emplist.length <= numEmployee) {
                grow();
            }
            emplist[numEmployee] = employee;
            numEmployee++;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method removes an employee from the company.
     * @param employee an employee object
     * @return true if the employee is successfully removed, false otherwise
     */
    public boolean remove(Employee employee) { //maintain the original sequence

        int indexOfEmp = find(employee);
        if (indexOfEmp == NOT_FOUND_IN_COMPANY) {
            return false;
        }
        for (int i = indexOfEmp; i < numEmployee - 1; i++) {
            emplist[i] = emplist[i + 1];
        }
        numEmployee--; //reduce number of employees by one after removing one employee
        emplist[numEmployee] = new Employee();
        return true;
    }

    /**
     * This method sets the working hours of a parttime employee in the company.
     * @param employee an employee object
     * @return true if the hours are successfully set, false otherwise
     */
    public boolean setHours(Employee employee) { //set working hours for a part time
        int indexOfEmp = find(employee);
        if (indexOfEmp == NOT_FOUND_IN_COMPANY) {
            return false;
        }
        else {
            Parttime parttimeEmp = (Parttime)(emplist[indexOfEmp]);
            Parttime inputEmp = (Parttime)(employee);
            parttimeEmp.setHoursWorked(inputEmp.getHoursWorked());
            return true;
        }
    }

    /**
     * This method processes the payments of all the employee in the company by calling the
     * appropriate calculatePayment methods based on the type of each employee to set their
     * payment for the payment period.
     */
    public void processPayments() { //process payments for all employees
        for (int i = 0; i < numEmployee; i++) {
            if (emplist[i] instanceof Management) {
                Management managementEmp = (Management) emplist[i];
                managementEmp.calculatePayment();
            }
            else if (emplist[i] instanceof Fulltime) {
                Fulltime fulltimeEmp = (Fulltime) emplist[i];
                fulltimeEmp.calculatePayment();
            }
            else if (emplist[i] instanceof Parttime) {
                Parttime parttimeEmp = (Parttime) emplist[i];
                parttimeEmp.calculatePayment();
            }
        }
    }

    /**
     * This method returns a string with all of the earning statements for all employees.
     * @return printString a string with all of the earning statements for all employees
     */
    public String print() { //print earning statements for all employees
        String printString = "";
        if (numEmployee == 0) {
            printString = "Employee database is empty." + "\n";
        }
        else {
            printString = "--Printing earning statements for all employees--" + "\n";
            for (int i = 0; i < numEmployee; i++) {
                printString = printString + emplist[i].toString() + "\n" ;
            }
        }
        return printString;
    }

    /**
     * This method returns a string with all of the earning statements for all employees by department.
     * @return printString a string with all of the earning statements for all employees by department
     */
    public String printByDepartment() { //print earning statements by department
        String printString = "";
        if (numEmployee == 0) {
            printString = "Employee database is empty." + "\n";
        }
        else {
            printString = "--Printing earning statements by department--" + "\n";
            sortByDepartment(emplist);
            for (int i = 0; i < numEmployee; i++) {
                printString = printString + emplist[i].toString() + "\n" ;
            }
        }
        return printString;
    }

    /**
     * This method returns a string with all of the earning statements for all employees by date hired.
     * @return printString a string with all of the earning statements for all employees by date hired
     */
    public String printByDate() { //print earning statements by date hired
        String printString = "";
        if (numEmployee == 0) {
            printString = "Employee database is empty." + "\n";
        }
        else {
            printString = "--Printing earning statements by date hired--" + "\n";
            sortByDate(emplist);
            for (int i = 0; i < numEmployee; i++) {
                printString = printString + emplist[i].toString() + "\n" ;
            }
        }
        return printString;
    }

    /**
     * This private helper method sorts the list of employee earning statements by date hired (ascending).
     * @param emplist an array containing all of the employee objects in the company
     */
    private void sortByDate(Employee[] emplist) {
        for (int i = 0; i < numEmployee; i++) {
            int min = i; // min is the index of the smallest element with an index greater or equal to i
            for (int j = i + 1; j < numEmployee; j++) {
                Date currDateHired = emplist[j].getEmployeeProfile().getDateHired();
                Date minBookDate = emplist[min].getEmployeeProfile().getDateHired();
                Calendar currDateHiredCal = Calendar.getInstance();
                Calendar minDateHiredCal = Calendar.getInstance();
                currDateHiredCal.set(currDateHired.getYear(), currDateHired.getMonth() - 1,
                                     currDateHired.getDay(), 0, 0, 0);
                minDateHiredCal.set(minBookDate.getYear(), minBookDate.getMonth() - 1, minBookDate.getDay(),
                          0, 0, 0);
                int compareDates = currDateHiredCal.compareTo(minDateHiredCal);
                if (compareDates < 0) {
                    min = j;
                }
            }
            // Swapping i-th and min-th elements
            Employee swap = emplist[i];
            emplist[i] = emplist[min];
            emplist[min] = swap;
        }
    }

    /**
     * This private helper method sorts the list of employee earning statements by department.
     * @param emplist an array containing all of the employee objects in the company
     */
    private void sortByDepartment(Employee[] emplist) {
        for (int i = 0; i < numEmployee; i++) {
            int min = i; // min is the index of the smallest element with an index greater or equal to i
            for (int j = i + 1; j < numEmployee; j++) {
                String currEmpDepartment = emplist[j].getEmployeeProfile().getDepartment();
                String minEmpDepartment = emplist[min].getEmployeeProfile().getDepartment();
                int compareDates = currEmpDepartment.compareTo(minEmpDepartment);
                if (compareDates < 0) {
                    min = j;
                }
            }
            // Swapping i-th and min-th elements
            Employee swap = emplist[i];
            emplist[i] = emplist[min];
            emplist[min] = swap;
        }
    }

    /**
     * This method handles input from a file and adds employees from the file to the database.
     * @param sourceFile the file from which to derive Employee database information
     * @param myCompany the Company to add Employees in
     * @return true if the file's input is successful, and false otherwise
     */
    public boolean importDatabase(File sourceFile, Company myCompany) {
        Scanner readInputFile;
        try {
            readInputFile = new Scanner(sourceFile);
        }
        catch (FileNotFoundException fileNotFound) {
            return false;
        }
        String userInput;
        while (readInputFile.hasNextLine()) {
            userInput = readInputFile.nextLine();
            String[] inputParts = userInput.split("[,]");
            String employeeType = inputParts[0].trim();
            switch (employeeType) {
                case "P" :
                    Date dateHired = new Date(inputParts[3]);
                    float hourlyPayRate = Float.parseFloat(inputParts[4]);
                    Parttime parttimeEmployee = new Parttime(inputParts[1], inputParts[2], dateHired, hourlyPayRate);
                    myCompany.add(parttimeEmployee);
                    break;
                case "M" :
                    dateHired = new Date(inputParts[3]);
                    float salary = Float.parseFloat(inputParts[4]);
                    int managementCode = Integer.parseInt(inputParts[5]);
                    Management managementEmployee = new Management(inputParts[1], inputParts[2], dateHired, salary,
                            managementCode);
                    myCompany.add(managementEmployee);
                    break;
                case "F" :
                    dateHired = new Date(inputParts[3]);
                    salary = Float.parseFloat(inputParts[4]);
                    Fulltime fulltimeEmployee = new Fulltime(inputParts[1], inputParts[2], dateHired, salary);
                    myCompany.add(fulltimeEmployee);
                    break;
                default :
                    return false;
            }
        }
        return true;
    }

    /**
     * This method writes to the target file with all Employee database information.
     * @param targetFile the file that will be written to with Employee database information
     * @return true if the file's output is successful, and false otherwise
     */
    public boolean exportDatabase(File targetFile) {
        try {
            FileWriter writeToTargetFile = new FileWriter(targetFile);
            String empString = "";
            empString = print().substring(EXPORT_FILE_START_INDEX);
            writeToTargetFile.write(empString);
            writeToTargetFile.close();
        }
        catch (IOException error) {
            return false;
        }
        return true;
    }
}
