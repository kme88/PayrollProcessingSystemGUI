package PayrollProcessingSystem;

/**
 * This class defines the profile of an employee, which contains the employee name, department, and date of hire.
 * @author Kathleen Eife, Isha Vora
 */
public class Profile {
    private String name; //employee’s name
    private String department; //department code: CS, ECE, IT
    private Date dateHired;

    /**
     * This constructor takes in profile attributes and creates a Profile object.
     * @param name the name of the employee
     * @param department the department the employee works in
     * @param dateHired the date the employee was hired
     */
    public Profile (String name, String department, Date dateHired) {
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
    }

    /**
     * This getter method returns the date an employee was hired.
     * @return dateHired the date the employee was hired
     */
    public Date getDateHired() {
        return dateHired;
    }

    /**
     * This getter method returns the department an employee works in.
     * @return department the employee works in
     */
    public String getDepartment() {
        return department;
    }

    /**
     * This getter method returns the name of an employee.
     * @return name of the employee
     */
    public String getName() {
        return name;
    }


    /**
     * This method creates and returns a string representation of an employee's profile attributes.
     * @return string representation of an employee's profile attributes
     */
    @Override
    public String toString() {
        return (name + "::" + department + "::" + dateHired.getDate());
    }

    /**
     * This method checks if two objects are equal by comparing the name, department and dateHired.
     * @param obj an object
     * @return true if the two objects are both of type Profile and have the same name, department,
     * and dateHired, false otherwise
     * */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile empProfile = (Profile) obj;
            if ((empProfile.name.equals(name)) && (empProfile.department.equals(department))
                    && ((empProfile.dateHired.compareTo(dateHired)) == 0)) {
                return true;
            }
        }
        return false;
    }
}
