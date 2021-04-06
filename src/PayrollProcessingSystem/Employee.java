package PayrollProcessingSystem;
import java.text.DecimalFormat;

/**
 * This class defines the common data and operations for all employee type.
 * Each employee has a profile that uniquely identifies the employee.
 * @author Kathleen Eife, Isha Vora
 */
public class Employee {

    private Profile employeeProfile;
    private float payment = 0f;

    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("###,##0.00");

    /**
     * This default constructor creates an Employee object.
     */
    public Employee() {
        this.employeeProfile = null;
    }

    /**
     * This constructor takes in profile attributes and creates an Employee object.
     * @param name the name of the employee
     * @param department the department the employee works in
     * @param dateHired the date the employee was hired
     */
    public Employee(String name, String department, Date dateHired) {
        this.employeeProfile = new Profile(name, department, dateHired);
    }

    /**
     * This method sets the payment amount for an employee for a pay period.
     * @param payment the payment amount of an employee for a pay period
     */
    public void setPayment(float payment) {
        this.payment = payment;
    }

    /**
     * This getter method returns an employee's profile.
     * @return employeeProfile the profile of an employee which contains profile attributes
     */
    public Profile getEmployeeProfile() {
        return employeeProfile;
    }

    /**
     * This is an empty method implementation in the superclass that is overridden by the child classes
     * to calculate the payments for a pay period for each type of employee.
     */
    public void calculatePayment() {
    }

    /**
     * This method creates and returns a string representation of an employee's attributes.
     * @return string representation of an employee's attributes
     */
    @Override
    public String toString() {
        return (employeeProfile.toString() + "::Payment $" + DECIMAL_FORMATTER.format(payment));
    }

    /**
     * This method checks if two objects are the same employee.
     * @return true if the two objects are instances of Employee and are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee emp = (Employee) obj;
            if (emp.employeeProfile.equals(employeeProfile)) {
                return true;
            }
        }
        return false;
    }
}
