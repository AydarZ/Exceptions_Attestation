
public class MyExceptions {

}

class InvalidInputLengthException extends RuntimeException {
    public InvalidInputLengthException() {
        super("Вы ввели больше или меньше необходимых параметров");
    }
}

class InvalidDataFormatStringException extends IllegalArgumentException {
    public InvalidDataFormatStringException(String str) {
        super("Неверный формат ввода в значении: " + str);
    }
}

class InvalidPhoneFormat extends RuntimeException {
    public InvalidPhoneFormat() {
        super("Неверный формат ввода телефонного номера: код страны должен начинаться с цифры 7 или 8");
    }
}
