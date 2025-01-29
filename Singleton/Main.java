import java.util.ArrayList;
import java.util.List;

// Задача 1. Создание класса базы данных
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Подключение к базе данных создано.");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

// Задача 2. Логирование в системе
class Logger {
    private static Logger instance;
    private List<String> logMessages;

    private Logger() {
        logMessages = new ArrayList<>();
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void addLogMessage(String message) {
        logMessages.add(message);
    }

    public void printLogs() {
        for (String message : logMessages) {
            System.out.println(message);
        }
    }
}

// Задача 3. Реализация статусов заказа
enum OrderStatus {
    NEW, IN_PROGRESS, DELIVERED, CANCELLED
}

class Order {
    private OrderStatus status;

    public Order() {
        this.status = OrderStatus.NEW;
    }

    public void setStatus(OrderStatus newStatus) {
        if (this.status == OrderStatus.DELIVERED && newStatus == OrderStatus.CANCELLED) {
            System.out.println("Нельзя отменить доставленный заказ.");
        } else {
            this.status = newStatus;
            System.out.println("Статус заказа изменен на: " + newStatus);
        }
    }

    public OrderStatus getStatus() {
        return status;
    }
}

// Задача 4. Сезоны года
enum Season {
    WINTER, SPRING, SUMMER, AUTUMN
}

class SeasonTranslator {
    public static String getRussianSeasonName(Season season) {
        switch (season) {
            case WINTER:
                return "Зима";
            case SPRING:
                return "Весна";
            case SUMMER:
                return "Лето";
            case AUTUMN:
                return "Осень";
            default:
                throw new IllegalArgumentException("Неизвестный сезон: " + season);
        }
    }
}

// Основной класс для тестирования
public class Main {
    public static void main(String[] args) {
        // Тестирование задачи 1
        System.out.println("==Тестирование задачи 1==");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("db1 и db2 ссылаются на один и тот же объект? " + (db1 == db2));
        System.out.println();

        // Тестирование задачи 2
        System.out.println("==Тестирование задачи 2==");
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.addLogMessage("Первое сообщение в логе.");
        logger2.addLogMessage("Второе сообщение в логе.");

        System.out.println("Логи из logger1:");
        logger1.printLogs();

        System.out.println("Логи из logger2:");
        logger2.printLogs();

        System.out.println("logger1 и logger2 ссылаются на один и тот же объект? " + (logger1 == logger2));
        System.out.println();

        // Тестирование задачи 3
        System.out.println("==Тестирование задачи 3==");
        Order order = new Order();
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setStatus(OrderStatus.DELIVERED);
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println();

        // Тестирование задачи 4
        System.out.println("==Тестирование задачи 4==");
        Season season = Season.SUMMER;
        System.out.println("Сезон: " + SeasonTranslator.getRussianSeasonName(season));
    }
}
