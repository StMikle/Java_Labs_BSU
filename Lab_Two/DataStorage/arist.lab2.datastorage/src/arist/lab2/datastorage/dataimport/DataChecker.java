package arist.lab2.datastorage.dataimport;

public class DataChecker {

    public static void checkDataItem(String line) throws Exception {
        String[] parts = line.split("&");
        if (parts.length == 1) {
            throw new Exception("Ошибка в строке: " + line + " - нет разделителя(должен быть хотя бы один!)");
        }
        if (parts.length > 2) {
            throw new Exception("Ошибка в строке: " + line + " - несколько разделителей(должен быть один!)");
        }

        if(!(parts[0] != null && !parts[0].trim().isEmpty())) {
            throw new Exception("Ошибка в строке: " + line + " - до разделителя ничего нет!");
        }

        if(!(parts[1] != null && !parts[1].trim().isEmpty())) {
            throw new Exception("Ошибка в строке: " + line + " - после разделителя ничего нет!");
        }

        for (int i = 0; i < parts[1].length(); i++) {
            if (!Character.isAlphabetic(parts[1].charAt(0))) {
                throw new Exception("Ошибка в строке: " + line + " - после разделителя первый символ должен быть только буква!");
            } else if (!Character.isUpperCase(parts[1].charAt(0))) {
                throw new Exception("Ошибка в строке: " + line + " - после разделителя первый символ должен быть только заглавная буква!");
            }
        }
        for (int i = 0; i < parts[0].length(); i++) {
            if (!(Character.isAlphabetic(parts[0].charAt(i)))) {
                throw new Exception("Ошибка в строке: " + line + " - до разделителя должны быть только буквы!");
            }
        }
    }
}

