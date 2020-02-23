package Utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

    public static final DateFormat twelveHourFormat = new SimpleDateFormat("hh:mm aa");
    public static final DateFormat twentyFourHourFormat = new SimpleDateFormat("HH:mm:ss");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    public static LocalDateTime apptTimeFormatter(LocalDate date, String time) throws ParseException {
        String output = twentyFourHourFormat.format(twelveHourFormat.parse(time));
        String stringDateTime = date + " " + output;
        LocalDateTime formattedDateTime = LocalDateTime.parse(stringDateTime, formatter);
        return formattedDateTime;
    }

    public static LocalDateTime UTCtoLocaltime(Timestamp time) {
        LocalDateTime localDateTime = time.toLocalDateTime();
        ZonedDateTime zonedLocalDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedUTC = zonedLocalDateTime.withZoneSameInstant(ZoneId.systemDefault());
        String stringTime = zonedUTC.format(formatter);
        LocalDateTime formatDateTime = LocalDateTime.parse(stringTime, formatter);
        return formatDateTime;
    }

    public static Timestamp localTimetoUTC(LocalDateTime dateTime) {
        ZonedDateTime ldtZoned = dateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return Timestamp.valueOf(utcZoned.toLocalDateTime());
    }

    public static String timeFormatter(LocalDateTime localDateTime) {
        String formatted = localDateTime.format(timeFormatter);
        return formatted;
    }

    public static LocalDateTime ldtTimeFormatter (LocalDateTime localDateTime) {
        String formatDateTime = localDateTime.format(formatter2);
        LocalDateTime ldtFormatDateTime = LocalDateTime.parse(formatDateTime, formatter2);
        return ldtFormatDateTime;
    }

}
