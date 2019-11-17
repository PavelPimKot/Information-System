package MVS;

/**
 * View - console data output;
 */
class View {
    /**
     * This method is used to output data from the library in console;
     *
     * @param obj - выводимы на эран объект;
     */
    static void outputObjects(Object obj) {
        System.out.println();
        System.out.println(obj.toString());
        System.out.println();
    }

    /**
     * This method is used to output Information about library for user in console;
     *
     * @param info - информация для пользователя выводимая на экран;
     */
    static void informationForUser(String info) {
        System.out.println();
        System.out.println(info);
        System.out.println();
    }
}
