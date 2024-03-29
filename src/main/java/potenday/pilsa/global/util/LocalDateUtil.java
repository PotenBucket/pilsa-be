package potenday.pilsa.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateUtil {
    public static LocalDateTime startLocalDateToTime(LocalDate startDate) {
        return startDate.atStartOfDay();
    }

    public static LocalDateTime endLocalDateToTime(LocalDate endDate) {
        return endDate.atTime(23, 59, 59);
    }

    public static LocalDate localDateTimeToDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

}
