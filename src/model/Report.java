/**
 * A model class for the Report
 *
 * @author Kevin Miller
 */


package model;

import utilities.ReportDAO;
import java.sql.SQLException;

/**
 * A model class for the Report
 */
public class Report {

    /**
     * A static String to hold the report.
     */
    private static String report;


    /**
     * Logic to get the customer by month and type report.
     *
     * @return String representation of the report.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static String getCustomerByTypeMonthReport() throws SQLException {
        report = ReportDAO.customersByTypeMonth();
        return report;
    }

    /**
     * Logic to get the contact schedule report.
     *
     * @return String representation of the report.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static String getContactSchedule() throws SQLException {
        report = ReportDAO.contactSchedule();
        return report;
    }

    /**
     * Logic to get the customer total appointment time report.
     *
     * @return String representation of the report.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static String getCustomerTotalAppointmentTime() throws SQLException {
        report = ReportDAO.customerTotalTime();
        return report;
    }
}
