package PayrollProcessingSystem;

/**
 * This class extends the Employee class and includes specific data and operations to a part-time employee.
 * @author Kathleen Eife, Isha Vora
 */
public class Parttime extends Employee {

    private int hoursWorked;
    private float hourlyPayRate;

    private static final int NORMAL_HOURS = 80;
    private static final float OVERTIME_PAY_RATE_MULTIPLE = 1.5f;

    /**
     * This constructor takes in profile and Parttime employee hourlyPayRate attribute and creates a
     * Parttime Employee object.
     * @param name the name of the employee
     * @param department the department the employee works in
     * @param dateHired the date the employee was hired
     * @param hourlyPayRate the hourly pay rate of a Parttime employee
     */
    public Parttime(String name, String department, Date dateHired, float hourlyPayRate) {
        super(name, department, dateHired);
        this.hourlyPayRate = hourlyPayRate;
    }

    /**
     * This constructor takes in profile and Parttime employee hoursWorked attribute and creates a
     * Parttime Employee object.
     * @param name the name of the employee
     * @param department the department the employee works in
     * @param dateHired the date the employee was hired
     * @param hoursWorked the the hours a Parttime employee has worked in a pay period
     */
    public Parttime(String name, String department, Date dateHired, int hoursWorked) {
        super(name, department, dateHired);
        this.hoursWorked = hoursWorked;
    }

    /**
     * This getter method returns the hours a Parttime employee has worked in a pay period.
     * @return hoursWorked the hours a Parttime employee has worked in a pay period
     */
    public int getHoursWorked() {
        return hoursWorked;
    }

    /**
     * This getter method returns the hourly pay rate of a Parttime employee.
     * @return hourlyPayRate the hourly pay rate of a Parttime employee
     */
    public float getHourlyPayRate() {
        return this.hourlyPayRate;
    }

    /**
     * This setter method sets the hours a Parttime employee has worked in a pay period.
     * @param hoursWorked the hours a Parttime employee has worked in a pay period
     */
    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    /**
     * This method creates and returns a string representation of a Parttime employee's attributes.
     * @return string representation of a Parttime employee's attributes
     */
    @Override
    public String toString() {
        return (super.toString() + "::PART TIME::Hourly Rate $" + DECIMAL_FORMATTER.format(hourlyPayRate)
                 + "::Hours worked this period: " + hoursWorked);
    }

    /**
     * This method checks if two objects are equal by comparing their profiles and types.
     * @param obj an object
     * @return true if the two objects are both of type Parttime and have profile, false otherwise
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Parttime) {
            if (super.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculates the payment amount of a Parttime employee by using the hourlyPayRate and hoursWorked.
     * */
    @Override
    public void calculatePayment() {
        float payment;
        if (hoursWorked <= NORMAL_HOURS) {
            payment = hoursWorked * hourlyPayRate;
        }
        else {
            int overtimeHours = hoursWorked - NORMAL_HOURS;
            payment = hourlyPayRate * ((NORMAL_HOURS) + (overtimeHours * OVERTIME_PAY_RATE_MULTIPLE));
        }
        setPayment(payment);
    }
}
