import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        Set<String> listOfFiles = new HashSet<>();
        while (true) {
            String[] request = ReceiveRequest();
            if (!CheckInputLength(request))
                continue;

            Boolean checkString = true;
            for (int i = 0; i < 3; i++) {
                if (!CheckDataFormatString(request[i])) {
                    checkString = false;
                    break;
                }
            }
            if (!checkString) {
                continue;
            }

            String[] arrayDate = request[3].split("\\.");
            if (arrayDate.length != 3) {
                System.out.println("Введен неверный формат даты");
                continue;
            }
            if (!isDateValid(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1]),
                    Integer.parseInt(arrayDate[0]))) {
                continue;
            }

            if (request[4].length() != 11) {
                System.out.println("Неверная длина телефонного номера");
                continue;
            }
            if (!CheckPhoneFormat(request[4])) {
                continue;
            }

            if (!request[5].equals("m") && !request[5].equals("f")) {
                System.out.println("Пол указан неверно. Укажите буквой m или f");
                continue;
            }

            WriteFile(String.join(" ", request), request[0], listOfFiles);
        }
    }


    public static Boolean CheckInputLength(String[] request) { // Проверка количества введенных данных
        try {
            if (request.length != 6)
                throw new InvalidInputLengthException();
        } catch (InvalidInputLengthException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static Boolean CheckDataFormatString(String str) { // Проверка формата вводимых ФИО
        try {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isLetter(str.charAt(i))) {
                    throw new InvalidDataFormatStringException(str);
                }
            }
        } catch (InvalidDataFormatStringException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean isDateValid(int year, int month, int day) {  // Проверка формата вводимой даты
        try {
            if (LocalDate.now().isBefore(LocalDate.of(year, month, day)) || year < 1920) {
                throw new DateTimeException("");
            }
        } catch (DateTimeException e) {
            System.out.println("Вы ввели не верную дату рождения");
            return false;
        }
        return true;
    }

    public static Boolean CheckPhoneFormat(String number) { // Проверка правильности ввода телефонного номера
        try {
            if (Integer.parseInt(String.valueOf(number.charAt(0))) != 8
                    && Integer.parseInt(String.valueOf(number.charAt(0))) != 7)
                throw new InvalidPhoneFormat();
            for (Character item : number.toCharArray()) {
                Integer.parseInt(String.valueOf(item));
            }
        } catch (InvalidPhoneFormat e) {
            System.out.println(e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ввода телефонного номера");
            return false;
        }
        return true;
    }

    public static void WriteFile(String str, String fam, Set<String> listOfFiles) { // Метод записи в файл
        try {
            if (listOfFiles.contains(fam)) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fam + ".txt", true));
                bw.append('\n');
                bw.append(str);
                bw.close();
            } else {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fam + ".txt", false));
                bw.append(str);
                listOfFiles.add(fam);
                bw.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String[] ReceiveRequest() { // Метод считывания входных данных и преобразование в масив строк
        Scanner in = new Scanner(System.in);
        System.out.println(
                "Введите строку в формате: Фамилия Имя Отчество датарождения(dd.mm.yyyy) номертелефона пол\n(буквы латинские)");
        String reguest = in.nextLine();
        String[] arrRequest = reguest.split(" ");
        return arrRequest;

    }

}
