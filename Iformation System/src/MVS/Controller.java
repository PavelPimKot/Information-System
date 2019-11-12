package MVS;

import InformationClasses.*;


import java.io.*;

/*
 *Контроллер - отвечает за взаимодействие с пользователем;
 */
public class Controller {


    //Список команд справочника

    private static final String[] Commands = {"Get", "SetBook", "SetBookInst", "AddBook", "AddBookInst", "Delete", "Exit", "AddInfFromFile"};

    //метод для начала работы со справочником

    public static void StartLibrary() throws ClassNotFoundException, IOException, NotACommandException, EndOfProgramm {
        System.out.println(" Справочная система ");
        System.out.println(" Содержание: Книга(авторы,название,год издания,число страниц)," +
                " Экзепляр книги(инвент. номер, книга , выдана/нет)");
        System.out.println();
        getCoommadList();
        Model.updateRuntimeDatabase();
        Reader in = new InputStreamReader(System.in);
        while (true) {
            getCommand(in);
        }
    }

    //вывод списка команл справочника в консоль

    private static void getCoommadList() {
        System.out.println("Cписок команд::");
        System.out.println(Commands[0] + " Получение информации по индексу ");
        System.out.println();
        System.out.println(Commands[1] + " Замена объекта в библиотеке(по индексу) на  книгу ");
        System.out.println();
        System.out.println(Commands[2] + " Замена объекта в библиотеке(по индексу)  Экзепляр книги");
        System.out.println();
        System.out.println(Commands[3] + " Добавление книги в библиотеку ");
        System.out.println();
        System.out.println(Commands[4] + " Добавление Экземпляра книги в библиотеку ");
        System.out.println();
        System.out.println(Commands[5] + " Удаление  информации по индексу ");
        System.out.println();
        System.out.println(Commands[6] + " Завершение текущего сеанса");
    }

    //метод для выполнения команды "Get"

    private static void getInfo(Reader in) throws IOException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        int position = (int) input.nval;
        Object get = Model.getInfFromBase(position);
        View.outputInfo(get);
    }

    //метод для выполнения команды "SetBook"

    private static void setBook(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber;
        int position;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        position = (int) input.nval;
        input.nextToken();
        authors = input.sval;
        input.nextToken();
        title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        Model.setInfInBase(position, new Book(authors, title, publishingYear, pagesNumber));
    }

    //метод для выполнения команды "SetBookInst"

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
        authors = input.sval;
        input.nextToken();
        title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        input.nextToken();
        if (input.sval.equals("true")) {
            issued = true;
        }
        Model.setInfInBase(position, new BookInstance(inventoryNumber,
                new Book(authors, title, publishingYear, pagesNumber), issued));
    }

    //метод для выполнения команды "Delete"

    private static void deleteInfo(Reader in) throws IOException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        int position = (int) input.nval;
        Model.deleteInfFromBase(position);
    }

    //метод для выполнения команды "AddBook"

    private static void addBook(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        authors = input.sval;
        input.nextToken();
        title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        Model.addInfToBase(new Book(authors, title, publishingYear, pagesNumber));

    }

    //метод для выполнения команды "AddBookInst"

    private static void addBookInstance(Reader in) throws IOException {
        String authors, title;
        int publishingYear, pagesNumber, inventoryNumber;
        boolean issued = false;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        inventoryNumber = (int) input.nval;
        input.nextToken();
        authors = input.sval;
        input.nextToken();
        title = input.sval;
        input.nextToken();
        publishingYear = (int) input.nval;
        input.nextToken();
        pagesNumber = (int) input.nval;
        input.nextToken();
        if (input.sval.equals("true")) {
            issued = true;
        }
        Model.addInfToBase(new BookInstance(inventoryNumber,
                new Book(authors, title, publishingYear, pagesNumber), issued));
    }

    //метод добавления информации из файла

    private static void addInfFromFile(Reader in) throws IOException, ClassNotFoundException {
        String directory;
        String filename;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        directory = input.sval;
        input.nextToken();
        filename = input.sval;
        Model.addInformationFromFile(new File(directory, filename));
    }

    //метод для получения команды с клавиатуры

    private static void getCommand(Reader in) throws NotACommandException, IOException, EndOfProgramm, ClassNotFoundException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        String comm = input.sval;
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
            case ("Exit"): {
                Model.updateDatabase();
                throw (new EndOfProgramm("Вы успешно вышли из программы!!!"));
            }
            default: {
                Model.updateDatabase();
                throw (new NotACommandException("Введенный вами символ не является командой справочника"));
            }
        }
    }
}
