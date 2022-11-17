/**
 * A Time Manager class for managing time checks and conversions.
 *
 * @author Kevin Miller
 */

package utilities;

import java.sql.Timestamp;
import java.time.*;

/**
 * A Time Manager class for managing time checks and conversions.
 */
public class TimeManager {

    /**
     * A static Local Time reference to the business open hours.
     */
    private static LocalTime businessOpen = LocalTime.of(8,0);

    /**
     * A static Local Time reference to the business close hours.
     */
    private static LocalTime businessClose = LocalTime.of(22,0);


    /**
     * Logic to convert UTC timestamps to Local timestamps
     *
     * @param utcTS UTC Timestamp to be converted.
     * @return Local Timestamp from UTC Timestamp.
     */
    public static Timestamp fromUTCtoLocalTS(Timestamp utcTS){
        // Convert to LocalDateTime
        LocalDateTime inputUTCLDT = utcTS.toLocalDateTime();

        // Convert to zoned LDT for UTC
        ZonedDateTime inputUTCZonedLDT = ZonedDateTime.of(inputUTCLDT, ZoneId.of("UTC"));

        // Convert to zoned LDT for System default
        ZonedDateTime outputDefaultZonedLDT = inputUTCZonedLDT.withZoneSameInstant(ZoneId.systemDefault());

        // Convert to LocalDateTime of system default zone ID.
        LocalDateTime outputDefaultLDT = outputDefaultZonedLDT.toLocalDateTime();

        // Convert back to Timestamp for the system default values.
        Timestamp outputTS = Timestamp.valueOf(outputDefaultLDT);

        return outputTS;
    }


    /**
     * Logic to convert Local timestamps to UTC timestamps
     *
     * @param localTS Local Timestamp to be converted.
     * @return UTC Timestamp from local Timestamp.
     */
    public static Timestamp fromLocalToUTCTS(Timestamp localTS){
        // Convert to LocalDateTime
        LocalDateTime inputDefaultLDT = localTS.toLocalDateTime();

        // Convert to Zoned LDT for System Default
        ZonedDateTime inputDefaultZonedLDT = ZonedDateTime.of(inputDefaultLDT, ZoneId.systemDefault());

        // Convert to Zoned LDT from System Default to UTC
        ZonedDateTime outputUTCZonedLDT = inputDefaultZonedLDT.withZoneSameInstant(ZoneId.of("UTC"));

        // Convert to LDT with System Default data
        LocalDateTime outputUTCLDT = outputUTCZonedLDT.toLocalDateTime();

        // Convert to Timestamp for the System Default data.
        Timestamp outputTS = Timestamp.valueOf(outputUTCLDT);

        return outputTS;
    }

    /**
     * Logic to convert Local timestamps to EST timestamps
     *
     * @param localTS Local Timestamp to convert to EST Timestamp.
     * @return EST Timestamp converted from Local Timestamp.
     */
    public static Timestamp fromLocalToEST(Timestamp localTS){
        // Convert to LocalDateTime
        LocalDateTime inputDefaultLDT = localTS.toLocalDateTime();

        // Convert to Zoned LDT for the Local data
        ZonedDateTime inputDefaultZonedLDT = ZonedDateTime.of(inputDefaultLDT, ZoneId.systemDefault());

        // Convert to Zoned LDT for EST
        ZonedDateTime outputESTZonedLDT = inputDefaultZonedLDT.withZoneSameInstant(ZoneId.of("America/New_York"));

        // Convert to LDT with EST data
        LocalDateTime outputESTLDT = outputESTZonedLDT.toLocalDateTime();

        // Convert to Timestamp for the EST
        Timestamp outputTS = Timestamp.valueOf(outputESTLDT);

        return outputTS;
    }

    /**
     * Logic to check that Start Timestamps come before End Timestamps
     *
     * @param start of appointment.
     * @param end of appointment.
     * @return true if appointment times make logical sense.
     */
    public static boolean checkStartBeforeEnd(Timestamp start, Timestamp end){
        if(start.before(end)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Logic to check if start and end timestamps occur within business hours.
     *
     * @param start Timestamp of appointment.
     * @param end Timestamp of appointment.
     * @return Boolean representing if appointment is within business hours or not.
     */
    public static boolean checkWithinBusinessHours(Timestamp start, Timestamp end){
        // Convert Timestamps to EST
        Timestamp startEST = TimeManager.fromLocalToEST(start);
        Timestamp endEST = TimeManager.fromLocalToEST(end);

        // Extract the Date
        LocalDate startDay = startEST.toLocalDateTime().toLocalDate();
        LocalTime startTime = startEST.toLocalDateTime().toLocalTime();

        // Extract the Time.
        LocalDate endDay = endEST.toLocalDateTime().toLocalDate();
        LocalTime endTime = endEST.toLocalDateTime().toLocalTime();

        // Check that the days are the same.
        if(startDay.equals(endDay)){
            // Check that the start time and end time are within the open and close hours.
            if(!startTime.isBefore(businessOpen) && !endTime.isAfter(businessClose)){
                return true;
            }
        }

        return false;
    }
}
