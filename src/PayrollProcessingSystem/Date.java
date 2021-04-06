package PayrollProcessingSystem;
import java.util.Calendar;

/**
 * This class defines the properties of a Date object.
 * The class includes two date constructors, various private helper methods along with the isValid() method to
 * check whether the date is valid, and getters to access instance variables.
 * @author Kathleen Eife, Isha Vora
 */
public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;

    public static final int START_YEAR = 1900;
    public static final int FIRST_MONTH = 1;
    public static final int LAST_MONTH = 12;
    public static final int FIRST_DAY = 1;
    public static final int LAST_DAY_FEB_NON_LEAP_YEAR = 28;
    public static final int LAST_DAY_FEB_LEAP_YEAR = 29;
    public static final int LAST_DAY_SHORT_MONTH = 30;
    public static final int LAST_DAY_LONG_MONTH = 31;

    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    /**
     * This constructor takes in a date string and creates a date object.
     * @param date a string in the form of mm/dd/yyyy
     */
    public Date(String date) {   //taking mm/dd/yyyy and create a Date object
        String [] dateParts = date.split("/");
        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * This constructor creates a date object with today's date.
     */
    public Date() {   //Creates an object with today's date
        int currMonth = (Calendar.getInstance().get(Calendar.MONTH)) + 1; //one added to get numeric value of the month
        int currDay = Calendar.getInstance().get(Calendar.DATE);
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        this.month = currMonth;
        this.day = currDay;
        this.year = currYear;
    }

    /**
     * This method compares two date objects.
     * @param date a Date object
     * @return 1 if this date is greater than the passed date, -1 if this date is less than the passed date,
     * and 0 if the two dates are equal
     */
    @Override
    public int compareTo(Date date) { //return 1, 0, or -1
        if (this.year > date.year) {
            return 1;
        }
        else if (this.year < date.year) {
            return -1;
        }
        else {
            if (this.month > date.month) {
                return 1;
            }
            else if (this.month < date.month) {
                return -1;
            }
            else {
                if (this.day > date.day) {
                    return 1;
                }
                else if (this.day < date.day) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
    }

    /**
     * This method checks if the year is a leap year.
     * @return true if year is a leap year, false otherwise
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUARTERCENTENNIAL == 0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * This method checks if the date entered by the user past the current date.
     * @return true if date entered by the user past the current date, false otherwise
     */
    private boolean isPastCurrDate() {
        Date today = new Date();
        String stringDateInput = month + "/" + day + "/" + year;
        Date dateInput = new Date(stringDateInput);
        int result = dateInput.compareTo(today);

        if (result == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method checks if the date entered by the user is valid.
     * @return true if date is valid, false otherwise
     */
    public boolean isValid() {
        if (year < START_YEAR) {
            return false;
        }

        if (isPastCurrDate()) {
            return false;
        }

        if (month < FIRST_MONTH || month > LAST_MONTH) {
            return false;
        }

        if (day < FIRST_DAY || day > LAST_DAY_LONG_MONTH) {
            return false;
        }

        if (month == JANUARY || month == MARCH || month == MAY || month == JULY || month == AUGUST
                || month == OCTOBER || month == DECEMBER) {
            return true;
        }
        else if (month == APRIL || month == JUNE || month == SEPTEMBER || month == NOVEMBER) {
            if (day > LAST_DAY_SHORT_MONTH) {
                return false;
            }
            else {
                return true;
            }
        }
        else if (month == FEBRUARY) {
            if (isLeapYear()) {
                if (day > LAST_DAY_FEB_LEAP_YEAR) {
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                if (day > LAST_DAY_FEB_NON_LEAP_YEAR) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This getter method returns the string form of the date.
     * @return string form of the date
     */
    public String getDate() {
        return (month + "/" + day + "/" + year);
    }

    /**
     * This getter method returns the month that the book was published.
     * @return month the month that the book was published
     */
    public int getMonth() {
        return month;
    }

    /**
     * This getter method returns the year that the book was published.
     * @return year the year that the book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * This getter method returns the day that the book was published.
     * @return day the day that the book was published
     */
    public int getDay() {
        return day;
    }
}
