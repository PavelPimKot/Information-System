package MVS;

/**
 * View - вывод данных в консоль;
 */
class View {
    /**
     * Метод для вывода на экран данных из базы данных;
     *
     * @param obj - выводимы на эран объект;
     */
    static void outputObjects(Object obj) {
        System.out.println();
        System.out.println(obj.toString());
        System.out.println();
    }

    /**
     * Метод для вывода сообщений для пользователя;
     *
     * @param info - информация для пользователя выводимая на экран;
     */
    static void informationForUser(String info) {
        System.out.println();
        System.out.println(info);
        System.out.println();
    }
}
