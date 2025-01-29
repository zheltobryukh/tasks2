import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;
import java.time.DayOfWeek;
import java.time.format.TextStyle;

public class Main {
    public static void main(String[] args) {
        // 1. Основы LocalDate и LocalTime
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Текущие дата и время: " +
                currentDate.atTime(currentTime).format(formatter));

        System.out.println("\nТестирование методов:");

        // 2. Сравнение дат
        LocalDate date1 = LocalDate.of(2025, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 12, 31);
        System.out.println("Сравнение дат: " + compareDates(date1, date2));

        // 3. Дней до Нового года
        System.out.println("Дней до Нового года: " + daysUntilNewYear());

        // 15. Определение дня недели
        System.out.println(getDayOfWeekInRussian(date1));



    }

    // 2. Сравнение дат
    public static String compareDates(LocalDate date1, LocalDate date2) {
        if (date1.isEqual(date2)) {
            return "Даты равны";
        } else if (date1.isBefore(date2)) {
            return "Первая дата раньше второй";
        } else {
            return "Первая дата позже второй";
        }
    }

    // 3. Сколько дней до Нового года
    public static long daysUntilNewYear() {
        LocalDate today = LocalDate.now();
        LocalDate newYear = LocalDate.of(today.getYear() + 1, 1, 1);
        return ChronoUnit.DAYS.between(today, newYear);
    }

    // 4. Проверка високосного года
    public static boolean isLeapYear(int year) {
        return Year.isLeap(year);
    }

    // 5. Подсчет выходных за месяц
    public static int getWeekendDaysInMonth(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        int weekendDays = 0;
        while (date.getMonthValue() == month) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendDays++;
            }
            date = date.plusDays(1);
        }
        return weekendDays;
    }

    // 6. Расчет времени выполнения метода
    public static long measureExecutionTime(Runnable method) {
        long startTime = System.nanoTime();
        method.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000; // в миллисекундах
    }

    // 7. Форматирование и парсинг даты
    public static String formatAndAddDays(String dateStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(dateStr, inputFormatter);
        return date.plusDays(10).format(outputFormatter);
    }

    // 8. Конвертация между часовыми поясами
    public static ZonedDateTime convertTimeZone(LocalDateTime dateTime,
                                                String targetZone) {
        ZonedDateTime utcTime = dateTime.atZone(ZoneId.of("UTC"));
        return utcTime.withZoneSameInstant(ZoneId.of(targetZone));
    }

    // 9. Вычисление возраста
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // 10. Календарь на месяц
    public static void printMonthCalendar(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        while (date.getMonthValue() == month) {
            String dayType = (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    date.getDayOfWeek() == DayOfWeek.SUNDAY)
                    ? "выходной" : "рабочий";
            System.out.printf("%s - %s%n",
                    date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), dayType);
            date = date.plusDays(1);
        }
    }

    // 11. Генерация случайной даты
    public static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = startEpochDay +
                new Random().nextLong(endEpochDay - startEpochDay + 1);
        return LocalDate.ofEpochDay(randomDay);
    }

    // 12. Расчет времени до события
    public static String timeUntilEvent(LocalDateTime eventTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, eventTime);
        return String.format("Осталось: %d часов, %d минут, %d секунд",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart());
    }

    // 13. Вычисление рабочих часов
    public static double calculateWorkingHours(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toMinutes() / 60.0;
    }

    // 14. Конвертация даты с учетом локали
    public static String formatDateWithLocale(LocalDate date, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d MMMM yyyy", locale);
        return date.format(formatter);
    }

    // 15. Определение дня недели
    public static String getDayOfWeekInRussian(LocalDate date) {
        return date.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("ru"));
    }
}
