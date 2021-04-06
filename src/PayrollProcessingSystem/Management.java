package PayrollProcessingSystem;

/**
 * This class extends the Fulltime class and includes specific data and operations to a full-time
 * employee with a management role.
 * @author Kathleen Eife, Isha Vora
 */
public class Management extends Fulltime {

    private int managementCode;
    private float managementExtra;

    private static final float MANAGER_ADDITIONAL_COMPENSATION = 5000f;
    private static final float DEPT_HEAD_ADDITIONAL_COMPENSATION = 9500f;
    private static final float DIRECTOR_ADDITIONAL_COMPENSATION = 12000f;
    private static final int MANAGER_NUM = 1;
    private static final int DEPT_HEAD_NUM = 2;
    private static final int DIRECTOR_NUM = 3;

    /**
     * This constructor takes in Profile, Fulltime, and Management employee attributes and creates a Management Employee.
     * @param name the name of the employee
     * @param department the department the employee works in
     * @param dateHired the date the employee was hired
     * @param salary the annual salary of a Fulltime employee
     * @param managementCode the code that determines which management role an employee holds
     */
    public Management(String name, String department, Date dateHired, float salary, int managementCode) {
        super(name, department, dateHired, salary);
        this.managementCode = managementCode;
    }

    /**
     * This getter method returns the management code for a Management Employee.
     * @return managementCode code that determines which management role an employee holds
     */
    public int getManagementCode() {
        return this.managementCode;
    }

    /**
     * This method creates and returns a string representation of a Management employee's attributes.
     * @return string representation of a Management employee's attributes
     */
    @Override
    public String toString() {
        calculateAdditionalComp();
        if (managementCode == MANAGER_NUM) {
            return (super.toString() + "::Manager Compensation $" + DECIMAL_FORMATTER.format(managementExtra));
        }
        else if (managementCode == DEPT_HEAD_NUM) {
            return (super.toString() + "::DepartmentHead Compensation $" + DECIMAL_FORMATTER.format(managementExtra));
        }
        else if (managementCode == DIRECTOR_NUM) {
            return (super.toString() + "::Director Compensation $" + DECIMAL_FORMATTER.format(managementExtra));
        }
        return null;
    }

    /**
     * This method checks if two objects are equal by comparing their profiles and types.
     * @param obj an object
     * @return true if the two objects are both of type Management and have the same profile, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Management) {
            if (super.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculates a Management employee's additional compensation based on their management code.
     */
    private void calculateAdditionalComp() {
        if (managementCode == MANAGER_NUM) {
            managementExtra = MANAGER_ADDITIONAL_COMPENSATION / NUM_PAY_PERIODS;
        }
        else if (managementCode == DEPT_HEAD_NUM) {
            managementExtra = DEPT_HEAD_ADDITIONAL_COMPENSATION / NUM_PAY_PERIODS;
        }
        else if (managementCode == DIRECTOR_NUM) {
            managementExtra = DIRECTOR_ADDITIONAL_COMPENSATION / NUM_PAY_PERIODS;
        }
    }

    /**
     * This method calculates the payment amount of a Management employee using their fulltime salary and additional
     * compensation for their management position.
     */
    @Override
    public void calculatePayment() {
        float payment = getSalary() / NUM_PAY_PERIODS;
        calculateAdditionalComp();
        payment = payment + managementExtra;
        setPayment(payment);
    }
}
