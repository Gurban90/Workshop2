package Helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jasper Thielen
 */
public class Converter {

    LocalDateTime localDateTime;

    public LocalDateTime convertDate(String x) {

        String y = x.substring(0, 19);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        localDateTime = LocalDateTime.parse(y, formatter);

        return localDateTime;
    }

    public String convertLocalDateTime(LocalDateTime x) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = x.format(formatter);

        return formatDateTime;

    }

}
