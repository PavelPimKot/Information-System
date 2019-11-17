package MVS;

import InformationClasses.*;

import java.io.*;

/**
 * Controller - works with user, read and checks commands
 */
public class Controller {


    /**
     * Library Command List::
     * Get - get information from library ;
     * SetBook - Change info in library by Book from user;
     * SetBookInst - Change info in library by BookInstance from user;
     * AddBook - Add Book to the library;
     * AddBookInstance - Add BookInstance to the library;
     * Delete - Delete info from library;
     * Clear - Delete all info from library;
     * AddInfFromFile - Add Info to the library from another file;
     * Search - template Search in library;
     * Show - View Library to the screen;
     * Exit - Exit from library program;
     */

    private static final String[] Commands =
            {"Get", "SetBook", "SetBookInst", "AddBook",
                    "AddBookInst", "Delete", "Clear", "AddInfFromFile",
                    "Search", "Show", "Exit"};


    /**
     * This method is used to start work with library program, prints Library commands,
     * info about data in library consist of;
     *
     * @throws IOException-
     */

    static void startLibrary() throws IOException {
        try {
            System.out.println();
            System.out.println(" Library :: ");
            System.out.println();
            System.out.println("Contents: Book (authors, title, year of publication, number of pages)," +
                    " Book Instance (inventory number, Book, issued / not) ");
            System.out.println();
            getCommandList();
            Model.updateRuntimeDatabase();
            Reader in = new InputStreamReader(System.in);
            while (getCommand(in)) ;
        }
        catch (Exception e) {
            View.informationForUser(" Invalid program end, data is saved in Database ");
            Model.updateDatabase();
        }
    }


    /**
     * This method is used to print command list;
     */

    private static void getCommandList() {
        System.out.println("Command List ::");
        System.out.println();
        System.out.println(Commands[0] + ": Retrieving Index Information ");
        System.out.println();
        System.out.println(Commands[1] + ": Replacing an object in a library (by index) with a book ");
        System.out.println();
        System.out.println(Commands[2] + ": Replacing an object in a library (by index) Book instance ");
        System.out.println();
        System.out.println(Commands[3] + ": Adding a book to the library ");
        System.out.println();
        System.out.println(Commands[4] + ": Adding a Book Instance to the library ");
        System.out.println();
        System.out.println(Commands[5] + ": Delete information by index ");
        System.out.println();
        System.out.println(Commands[6] + ": Delete all information from library");
        System.out.println();
        System.out.println(Commands[7] + ": Adding information to the library from a file");
        System.out.println();
        System.out.println(Commands[8] + ": Template search ");
        System.out.println();
        System.out.println(Commands[9] + ": Displaying the contents of the library on the screen ");
        System.out.println();
        System.out.println(Commands[10] + ": End current session ");
        System.out.println();
    }


    /**
     * This method is used to get info from library by index from user;
     * <If written by user info is not an integer - library will print;
     * <message about incorrect input;
     *
     * @param in - input stream(Console);
     * @throws IOException-
     */

    private static void getInfo(Reader in) throws IOException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        int position = (int) input.nval;
        if (input.sval != null) {
            View.informationForUser(" The information entered is not an index ");
        } else
            Model.getInfFromBase(position);
    }


    /**
     * This method is used to set object by Book to the library;
     *
     * @param in - input stream(Console);
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
     * This method is used to set object by  BookInstance to the library;
     * <@see 108>
     *
     * @param in - input stream(Console);
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
     * This method is used to  delete info from library;
     *
     * @param in - input stream(Console);
     * @throws IOException-
     * @see 89-90
     */

    private static void deleteInfo(Reader in) throws IOException {
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        int position = (int) input.nval;
        if (input.sval != null) {
            View.informationForUser(" The information entered is not an index ");
        } else
            Model.deleteInfFromBase(position);
    }


    /**
     * This method is used to add Book to the library;
     * <@see 108>
     *
     * @param in - input stream(Console);
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
     * This method is used to add BookInstance to the library;
     * <@see 108>
     *
     * @param in - input stream(Console);
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
     * This method is used to add information to the library from entered filename and path;
     *
     * @param in - input stream(Console);
     * @throws IOException-
     */

    private static void addInfFromFile(Reader in) throws IOException, ClassNotFoundException {
        String filename;
        BufferedReader input = new BufferedReader(in);
        filename = input.readLine();
        Model.addInformationFromFile(new File(filename));
    }


    /**
     * This method is used to search info in library using template from user;
     *
     * @param in - input stream(Console)
     * @throws IOException-
     */

    private static void templateSearch(Reader in) throws IOException {
        String template;
        StreamTokenizer input = new StreamTokenizer(in);
        input.nextToken();
        template = input.sval;
        if (input.sval == null) {
            template = Integer.toString((int) input.nval);
        }
        Model.search(template);
    }


    /**
     * This method is used to get command from user and process information ;
     *
     * @param in - input stream(Console)
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
                case ("Clear"): {
                    Model.clear();
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
                case ("Show"): {
                    Model.showDatabase();
                    break;
                }
                case ("Search"): {
                    templateSearch(in);
                    break;
                }
                case ("Exit"): {
                    Model.updateDatabase();
                    View.informationForUser(" Successfully completing the Library ");
                    return false;
                }
                default: {
                    View.informationForUser(" The entered string is not a reference command ");
                }
            }
        } else {
            View.informationForUser(" The entered string is not a reference command ");
        }
        return true;
    }
}