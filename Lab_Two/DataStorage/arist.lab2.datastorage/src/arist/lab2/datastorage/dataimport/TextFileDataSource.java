package arist.lab2.datastorage.dataimport;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
public class TextFileDataSource {
    public void LoadData(String filename, List<String> data) {
        try (BufferedReader src = new BufferedReader(new FileReader(filename, Charset.forName("windows-1251")))) {
            String line;
            while((line = src.readLine()) != null) {
                try {
                    DataChecker.checkDataItem(line);
                    data.add(line);
                    System.out.println(" '" + line + "' -> загружен");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл '" + filename + "' не найден.");
        } catch (IOException e) {//для исключений метода readline и при закрытии ресурса
            System.out.println("Ошибка при работе с файлом '" + filename + "'.");
        }
    }
}
