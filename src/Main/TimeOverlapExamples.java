package Main;

import java.time.LocalDateTime;

public class TimeOverlapExamples {

    public static void TimeOverLapExamples() {

        // Create dates and times
        LocalDateTime startDT = LocalDateTime.of(2019,10,30, 20, 0);
        LocalDateTime endDT = LocalDateTime.of(2019,10,30, 20, 30);
        LocalDateTime myDT = LocalDateTime.of(2020,10,30, 20, 15);

        // Check time Overlap

        if(myDT.isAfter(startDT) && myDT.isBefore(endDT)) {
            System.out.println(myDT + "is between" + startDT + "and" + endDT);
        } else if(myDT.isEqual(startDT) || myDT.isEqual(endDT)) {
            System.out.println("Your date and time matches " + startDT + " or " + endDT);
        } else {
            System.out.println("Your date and time does not overlap");
        }


    }
}
