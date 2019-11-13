package MVS;

import InformationClasses.*;

import java.io.*;

/**
 * Контроллер - отвечает за взаимодействие с пользователем;
 */
public class Controller {


    /**
     * Список команд справочной системы::
     * Get - Получение объекта из базы данных;
     * SetBook - Изменение существующего в базе данных объекта на объект типа Book;
     * SetBookInst - Изменение существующего объекта в базе данных на объект типа BookInstance;
     * AddBook - Добавление в базу данных объект типа Book;
     * AddBookInstance - Добавление в базу данных объекта типа BookInstance;
     * Delete - Удаление объекта из базы данных;
     * AddInfFromFile - Добавление объектов из указанного файла в базу данных;
     * Search - поиск в базе данных по указанному шаблонуж
     * Exit - выход из справочника;
     */

    private static final String[] Commands =
            {"Get", "SetBook", "SetBookInst", "AddBook", "AddBookInst", "Delete", "AddInfFromFile", "Search", "Exit"};


    /**
     * Метод служащий для начала работы со справочникомБ единственный public метод в классе;
     * Выводит шаблонный вид типов хранящихся в базе данных и список команд справочной системы;
     *
     * @throws IOException-
     * @throws ClassNotFoundException-
     */

    public static void startLibrary() throws ClassNotFoundException, IOException {
        System.out.println();
        System.out.println(" Справочная система ");
        System.out.println();
        System.out.println(" Содержание: Книга(авторы,название,год издания,число страниц)," +
                " Экзепляр книги(инвент. номер, книга , выдана/нет)");
        System.out.println();
        getCoommadList();
        Model.updateRuntimeDatabase();
        Reader in = new InputStreamReader(System.in);
        while (getCommand(in)) ;
    }


    /**
     * Метод служащий для вывода всех команд справочника их их назначения в консоль;
     */

    private static void getCoommadList() {
        System.out.println("Cписок команд::");
        System.out.println();
        System.out.println(Commands[0] + ": Получение информации по индексу ");
        System.out.println();
        System.out.println(Commands[1] + ": Замена объекта в библиотеке(по индексу) на  книгу ");
        System.out.println();
        System.out.println(Commands[2] + ": Замена объекта в библиотеке(по индексу)  Экзепляр книги");
        System.out.println();
        System.out.println(Commands[3] + ": Добавление книги в библиотеку ");
        System.out.println();
        System.out.println(Commands[4] + ": Добавление Экземпляра книги в библиотеку ");
        System.out.println();
        System.out.println(Commands[5] + ": Удаление  информации по индексу ");
        System.out.println();
        System.out.println(Commands[6] + ": Добавление информации в справочник из файла");
        System.out.println();
        System.out.println(Commands[7] + ": Поиск в справочнике по шаблону ");
        System.out.println();
        System.out.println(Commands[8] + ": Завершение текущего сеанса ");
        System.out.println();
    }


    /**
     * Метод для получения объекта из базы данных, по введенному пользователю индексу;
     * <Если будет введено не целое число , то выводится сообщение об ошибке
     * <и метод модели не вызывается;
     *
     * @param in - поток ввода данных , в данном случае консоль
     * @throws IOException-
     */

    private static void getInfo(Reader in) throws IOException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        int position = (int) input.nval;
        if (input.sval != null) {
            View.informationForUser(" Введенная информация не является индексом ");
        } else
            Model.getInfFromBase(position);
    }


    /**
     * Метод для замены объекта по указанному индексу на введенный пользователюж
     * Вводится объект Book;
     * <Т.к. названия книг\авторы не обязательно будут представлены в виде строки -
     * <например : 1703 , даже если введа не строка , все равно приводим к строке и записываем
     * <в поле объекта;
     * <Если год Публикации книги или кол-во страниц меньше 0 или не явл числом
     * <метод модели не вызывается и выводится сообщение пользователю об ошибке
     *
     * @param in - поток ввода данных , в данном случае консоль;
     * @throws IOException-
     */

    private static void setBook(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber;
        int position;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        position = (int) input.nval;
        input.nextToken();
        if (input.sval == null) {
            authors = Double.toString(input.nval);
        } else
            authors = input.sval;
        input.nextToken();
        if (input.sval == null) {
            title = Double.toString(input.nval);
        } else
            title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        try {
            Model.setInfInBase(position, new Book(authors, title, publishingYear, pagesNumber));
        }
        catch (BadFieldsException e) {
            View.informationForUser(e.getMessage());
        }
    }


    /**
     * Метод замены объекта по указанному индексу на введенный пользователю;
     * Вводится объект BookInstance;
     * <@see 108>
     *
     * @param in - поток ввода данных , в данном случае консоль;
     * @throws IOException-
     */

    private static void setBookInstance(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber, inventoryNumber;
        int position;
        boolean issued = false;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        inventoryNumber = (int) input.nval;
        input.nextToken();
        position = (int) input.nval;
        input.nextToken();
        if (input.sval == null) {
            authors = Double.toString(input.nval);
        } else
            authors = input.sval;
        input.nextToken();
        if (input.sval == null) {
            title = Double.toString(input.nval);
        } else
            title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        input.nextToken();
        if (input.sval.equals("true")) {
            issued = true;
        }
        try {
            Model.setInfInBase(position, new BookInstance(inventoryNumber,
                    new Book(authors, title, publishingYear, pagesNumber), issued));
        }
        catch (BadFieldsException e) {
            View.informationForUser(e.getMessage());
        }
    }


    /**
     * Метод для удаления объекта из базы данных по указаному индексу;
     *
     * @param in - поток ввода данных , в данном случае консоль;
     * @throws IOException-
     * @see 89-90
     */

    private static void deleteInfo(Reader in) throws IOException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        int position = (int) input.nval;
        if (input.sval != null) {
            View.informationForUser(" Введенная информация не является индексом ");
        } else
            Model.deleteInfFromBase(position);
    }


    /**
     * Метод добавления объекта по указанному индексу на введенный пользователю;
     * Вводится объект Book;
     * <@see 108>
     *
     * @param in - поток ввода данных , в данном случае консоль;
     * @throws IOException-
     */

    private static void addBook(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        if (input.sval == null) {
            authors = Double.toString(input.nval);
        } else
            authors = input.sval;
        input.nextToken();
        if (input.sval == null) {
            title = Double.toString(input.nval);
        } else
            title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        try {
            Model.addInfToBase(new Book(authors, title, publishingYear, pagesNumber));
        }
        catch (BadFieldsException e) {
            View.informationForUser(e.getMessage());
        }

    }


    /**
     * Метод добавления объекта по указанному индексу на введенный пользователю;
     * Вводится объект BookInstance;
     * <@see 108>
     *
     * @param in - поток ввода данных , в данном случае консоль;
     * @throws IOException-
     */

    private static void addBookInstance(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber, inventoryNumber;
        boolean issued = false;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        inventoryNumber = (int) input.nval;
        input.nextToken();
        if (input.sval == null) {
            authors = Double.toString(input.nval);
        } else
            authors = input.sval;
        input.nextToken();
        if (input.sval == null) {
            title = Double.toString(input.nval);
        } else
            title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        input.nextToken();
        if (input.sval.equals("true")) {
            issued = true;
        }
        try {
            Model.addInfToBase(new BookInstance(inventoryNumber,
                    new Book(authors, title, publishingYear, pagesNumber), issued));
        }
        catch (BadFieldsException e) {
            View.informationForUser(e.getMessage());
        }
    }


    /**
     * Метод для добаления всех объекта из указанного файла;
     * Если вводимые путь и названия файла не являются строками - выводится сообщение об ошибке;
     *
     * @param in - поток ввода данных , в данном случае консоль;
     * @throws IOException-
     */

    private static void addInfFromFile(Reader in) throws IOException, ClassNotFoundException {
        String directory;
        String filename;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        directory = input.sval;
        input.nextToken();
        filename = input.sval;
        if (directory == null || filename == null) {
            View.informationForUser(" Аргумент/Аргументы не являются строками");
        } else
            Model.addInformationFromFile(new File(directory, filename));
    }


    /**
     * Метод для поиска в базе данных по указанному пользователем шаблону
     *
     * @param in-
     * @throws IOException-
     */

    private static void templateSearch(Reader in) throws IOException {
        String template;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        template = input.sval;
        if (input.sval == null) {
            template = Double.toString(input.nval);
        }
        Model.search(template);
    }


    /**
     * Метод для получения команды для справочника(из указанного списка комманд);
     *
     * @param in-
     * @return Возвращает true усли можно продолжать работу со справочником - иначе false
     * @throws IOException-
     * @throws ClassNotFoundException-
     */

    private static boolean getCommand(Reader in) throws IOException, ClassNotFoundException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        String comm = input.sval;
        if (comm != null) {
            switch (comm) {
                case ("Get"): {
                    getInfo(in);
                    break;
                }
                case ("SetBook"): {
                    setBook(in);
                    break;
                }
                case ("SetBookInst"): {
                    setBookInstance(in);
                    break;
                }
                case ("Delete"): {
                    deleteInfo(in);
                    break;
                }
                case ("AddBook"): {
                    addBook(in);
                    break;
                }
                case ("AddBookInst"): {
                    addBookInstance(in);
                    break;
                }
                case ("AddInfFromFile"): {
                    addInfFromFile(in);
                    break;
                }
                case ("Search"): {
                    templateSearch(in);
                    break;
                }
                case ("Exit"): {
                    Model.updateDatabase();
                    View.informationForUser(" Успешное заверщение работы со Справочником ");
                    return false;
                }
                default: {
                    View.informationForUser(" Введенная строка не является командой справочника");
                }
            }
        } else {
            View.informationForUser(" Введенная строка не является командой справочника");
        }
        return true;
    }
}
