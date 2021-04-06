package PayrollProcessingSystem;

/**
 * This class extends the Employee class and includes specific data and operations to a full-time employee.
 * @author Kathleen Eife, Isha Vora
 */
public class Fulltime extends Employee {

    private float salary;

    public static final float NUM_PAY_PERIODS = 26f;

    /**
     * This constructor takes in Profile and Fulltime employee attributes and creates a Fulltime Employee.
     * @param name the name of the employee
     * @param department the department the employee works in
     * @param dateHired the date the employee was hired
     * @param salary the annual salary of a Fulltime employee
     */
    public Fulltime(String name, String department, Date dateHired, float salary) {
        super(name, department, dateHired);
        this.salary = salary;
    }

    /**
     * This getter method returns the salary of a Fulltime employee.
     * @return salary the salary of a Fulltime employee
     */
    public float getSalary() {
        return salary;
    }

    /**
     * This method creates and returns a string representation of a Fulltime employee's attributes.
     * @return string representation of a Fulltime employee's attributes
     */
    @Override
    public String toString() {
        return (super.toString() + "::FULL TIME::Annual Salary $" + DECIMAL_FORMATTER.format(this.salary));
    }

    /**
     * This method checks if two objects are equal by comparing their profiles and types.
     * @param obj an object
     * @return true if the two objects are both of type Fulltime and have profile, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Fulltime) {
            if (super.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculates the payment amount of a Fulltime employee by using the salary and number of pay periods.
     */
    @Override
    public void calculatePayment() {
        float payment = this.salary / NUM_PAY_PERIODS;
        setPayment(payment);
    }
}
