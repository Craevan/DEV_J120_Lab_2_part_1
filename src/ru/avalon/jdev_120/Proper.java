package ru.avalon.jdev_120;

import java.io.*;
import java.util.Properties;

public class Proper {

    private static final String DEFAULT_PATH = "src/ru/avalon/jdev_120/resources/";

    private final Properties properties = new Properties();
    private File file;

    public Proper() {
        initialize();
    }

    public Proper(File file) {
        if (file == null)
            System.err.println("Аргумент не может быт null");
        else if (file.exists()) {
            this.file = file;
            propertyLoading();
        }
        else
            System.err.println("Файл не существует");
    }

    public Proper(String path) {
        if (path != null) {
            file = new File(path);
            if (!file.exists())
                System.err.println("Файл не существует");
            else
                propertyLoading();
        }
        else System.err.println("Аргумент не может быт null");
    }

    //возвращающий значение свойства с заданными именем
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    //устанавливающий значение свойства с заданными именем
    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

    //удаляющий свойство с заданными именем
    public void remove(String key) {
        properties.remove(key);
    }

    //проверяющий наличие свойства с заданными именем
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(String value) {
        return properties.containsValue(value);
    }

    //сохраняющий набор свойств в файл
    public void saveToFile() {
        try (Writer writer = new FileWriter(file)) {
            properties.store(writer, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //сохраняющий набор свойств в другой файл
    public void saveToFile(File file) throws FileNotFoundException {
        if (file !=null && file.exists()) {
            try (Writer writer = new FileWriter(file)) {
                properties.store(writer, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (file != null && !file.exists()) {
            try (Writer writer = new FileWriter(file)) {
                file.createNewFile();
                properties.store(writer, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            throw new FileNotFoundException("Файл не найден");
        }

        // удаляем файл по-умолчанию, если ничего в него не сохраняли
        if (this.file.length() == 0)
            this.file.delete();
    }

    private void initialize() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                if (emptyFileCreation(i))
                    break;
            } catch (IOException e) {
                System.err.println("I/O exception");
                break;
            }
        }
    }

    private boolean emptyFileCreation(int number) throws IOException {
        File resourcesDir = new File(DEFAULT_PATH);
        resourcesDir.mkdir();
        file = new File(resourcesDir, "default_" + number + ".properties");
        return file.createNewFile();
    }

    private void propertyLoading() {
        if (file != null && file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
