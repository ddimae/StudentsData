package ntukhpi.semit.dde.studentsdata.utils;

public enum ContactMessages {
    EMPTY(""),
    MESSAGE01("Новий номер телефону був доданий!"),
    MESSAGE02("Помилка додавання! Insert SQL mistake!!!"),
    MESSAGE03("\"Неактивний\" телефон не може бути \"Основним\"!!!"),
    MESSAGE04("Помилка скидання Основного телефону! Update SQL mistake!!!"),
    MESSAGE05("Зробити \"Неактивний\" телефон \"Основним\" неможливо !!!"),
    MESSAGE06("\"Основний\" телефон вилучено!"),
    MESSAGE07("\"Основний\" телефон змінено!"),
    MESSAGE08("Помилка оновлення! Перевірте наявність телефону із статусом \"Основний\"! Update SQL mistake!!!"),
    MESSAGE09("Зробити  \"Основний\" телефон \"Неактивним\" неможливо !!!"),
    MESSAGE10("Статус телефону змінений!"),
    MESSAGE11("Помилка оновлення статусу актуальності! Update SQL mistake!!!"),
    MESSAGE12("Телефон було вилучено!!!"),
    MESSAGE13("Помилка вилучення! Delete SQL mistake!!!");

    private String text;

    ContactMessages(String s) {
        text = s;
    }

    public String getText() {
        return text;
    }
}
