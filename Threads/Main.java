import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

// Задание 1: Работа с потоками ввода-вывода
class FileUpperCaseConverter {
    public static void convertFile(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.toUpperCase());
                writer.newLine();
            }
            System.out.println("Файл успешно обработан и записан в " + outputFilePath);

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}

// Задание 2: Реализация паттерна Декоратор
interface TextProcessor {
    String process(String text);
}

class SimpleTextProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text;
    }
}

class UpperCaseDecorator implements TextProcessor {
    private TextProcessor processor;

    public UpperCaseDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text).toUpperCase();
    }
}

class TrimDecorator implements TextProcessor {
    private TextProcessor processor;

    public TrimDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text).trim();
    }
}

class ReplaceDecorator implements TextProcessor {
    private TextProcessor processor;

    public ReplaceDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text).replace(' ', '_');
    }
}

// Задание 3: Сравнение производительности IO и NIO
class IOvsNIOComparison {
    public static void comparePerformance(String inputFilePath, String outputFilePathIO, String outputFilePathNIO) {
        long startTimeIO = System.nanoTime();
        copyFileUsingIO(inputFilePath, outputFilePathIO);
        long endTimeIO = System.nanoTime();
        System.out.println("Время выполнения IO: " + (endTimeIO - startTimeIO) + " наносекунд");

        long startTimeNIO = System.nanoTime();
        copyFileUsingNIO(inputFilePath, outputFilePathNIO);
        long endTimeNIO = System.nanoTime();
        System.out.println("Время выполнения NIO: " + (endTimeNIO - startTimeNIO) + " наносекунд");
    }

    private static void copyFileUsingIO(String inputPath, String outputPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileUsingNIO(String inputPath, String outputPath) {
        try (FileChannel sourceChannel = new FileInputStream(inputPath).getChannel();
             FileChannel destChannel = new FileOutputStream(outputPath).getChannel()) {

            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Задание 4: Программа с использованием Java NIO
class NIOCopy {
    public static void copyFile(String inputFilePath, String outputFilePath) {
        try (FileChannel sourceChannel = new FileInputStream(inputFilePath).getChannel();
             FileChannel destChannel = new FileOutputStream(outputFilePath).getChannel()) {

            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
            System.out.println("Файл успешно скопирован с использованием NIO.");

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }
}

// Главный класс для демонстрации всех заданий
public class Main {
    public static void main(String[] args) {
        // Задание 1
        FileUpperCaseConverter.convertFile("input.txt", "output_upper.txt");

        // Задание 2
        TextProcessor processor = new SimpleTextProcessor();
        processor = new UpperCaseDecorator(processor);
        processor = new TrimDecorator(processor);
        processor = new ReplaceDecorator(processor);

        String text = "  Hello, World!  ";
        String result = processor.process(text);
        System.out.println("Результат декораторов: " + result);

        // Задание 3
        IOvsNIOComparison.comparePerformance("large_input.txt", "output_io.txt", "output_nio.txt");

        // Задание 4
        NIOCopy.copyFile("large_input.txt", "copy_nio.txt");
    }
}
