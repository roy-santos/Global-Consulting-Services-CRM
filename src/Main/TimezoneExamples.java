package Main;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TimezoneExamples {

    public static void TimezoneExamples() {

        //ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);

        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);

        LocalDate parisDate = LocalDate.of(2019, 10, 26);
        LocalTime parisTime = LocalTime.of(05,00);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

        Instant parisToGMTInstant = parisZDT.toInstant();
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId);

        System.out.println("Local: " + ZonedDateTime.now());
        System.out.println("Paris: " + parisZDT);
        System.out.println("Paris -> GMT: " + parisToGMTInstant);
        System.out.println("GMT -> Local: " + gmtToLocalZDT);
        System.out.println("GMT -> LocalDate: " + gmtToLocalZDT.toLocalDate());
        System.out.println("GMT -> LocalTime: " + gmtToLocalZDT.toLocalTime());

        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
        String dateTime = date + " " + time;
        System.out.println(dateTime);
    }
}
