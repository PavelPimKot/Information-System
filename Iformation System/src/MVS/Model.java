package MVS;

import InformationClasses.*;

import java.io.*;
import java.util.ArrayList;


/**
 * Модель - оперирует данными, осуществяет запись\чтение\удаление\изменение данных в контейнер\контейнере
 */
class Model {

    /**
     * Данные хранятся в Сериализованном виде
     * DIRECTORY B И DATABASE - отвечают за хранение данных в тестовом файле
     * runtimeDatabase -Данные считываются с файла во время запуска справочника и записываются туда
     * во время завершения работы
     * <p>
     * В качестве базы данных во время работы массива используется класс из java.util -
     * ArrayList;
     */
    private static final File DIRECTORY = new File("C://Users//HP//Documents//GitHub//Information-System//Iformation System//src");
    private static final File DATABASE = new File(DIRECTORY, "Database.txt");
    private static ArrayList<Object> runtimeDatabase = new ArrayList<>();


    /**
     * Проверка идекса на удовлетворение размерам runtimeDatabase
     *
     * @param index- проверяемый индекс
     * @return - true если удовл. размерам , иначе  false
     */

    private static boolean isIndexInRange(int index) {
        if (index > runtimeDatabase.size()) {
            View.informationForUser("Индекс выходит за границы справочника");
            return false;
        }
        return true;
    }


    /**
     * Добавление объектов из указанного файла
     *
     * @param infBase- файл из которого нужно добавить объекты
     * @throws IOException-
     * @throws ClassNotFoundException-
     */

    static void addInformationFromFile(File infBase) throws IOException, ClassNotFoundException {
        if (infBase.length() != 0) {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(infBase));
            while (input.available() != 0) {
                Object inf = input.readObject();
                if (inf instanceof Book || inf instanceof BookInstance) {
                    addInfToBase(inf);
                }
            }
            input.close();
        }
    }


    /**
     * Поиск по шаблону
     *
     * @param template- шаблон для поиска
     */

    static void search(String template) {
        View.informationForUser(" Результаты поиска:: ");
        for (int i = 0; i < runtimeDatabase.size(); ++i) {
            if (runtimeDatabase.get(i).toString().contains(template)) {
                getInfFromBase(i);
            }
        }
    }


    /**
     * Метод для обновления базы данных(во время запуска справочника)
     *
     * @throws IOException-
     * @throws ClassNotFoundException-
     */

    static void updateRuntimeDatabase() throws IOException, ClassNotFoundException {
        if (DATABASE.length() != 0) {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(DATABASE));
            runtimeDatabase = (ArrayList<Object>) input.readObject();
            input.close();
        }
    }


    /**
     * Метод для добавления объекта в базу данных
     *
     * @param book- объект который нужно добавить в базу данных
     */

    static void addInfToBase(Object book) {
        if (!runtimeDatabase.contains(book)) {
            runtimeDatabase.add(book);
            View.informationForUser(" Данные успешно добавлены ");
        } else {
            View.informationForUser(" Такие данные уже существуют, объект не добавлен");
        }
    }


    /**
     * метод для получения необходимой инофрмации из базы данных
     *
     * @param index- индекс объекта который хотят получить
     */

    static void getInfFromBase(int index) {
        if (isIndexInRange(index))
            View.outputObjects(runtimeDatabase.get(index));
    }


    /**
     * метод для удаления информации из базы данных
     *
     * @param index-- индекс объекта который хотят удалить
     */

    static void deleteInfFromBase(int index) {
        if (isIndexInRange(index)) {
            if (runtimeDatabase.get(index) instanceof BookInstance) {
                BookInstance toDelete = (BookInstance) runtimeDatabase.get(index);
                runtimeDatabase.removeIf(i -> (toDelete.getBook().equals(i)));
                runtimeDatabase.remove(toDelete);
            }
            View.informationForUser(" Данные успешно удалены ");
        }
    }


    /**
     * метод для изменнеия информации в базе данных
     *
     * @param index-  индекс объекта который хотят изменить
     * @param newInf- объект на который нужно заменить указанный индексом
     */

    static void setInfInBase(int index, Object newInf) {
        if (!runtimeDatabase.contains(newInf) && isIndexInRange(index)) {
            runtimeDatabase.set(index, newInf);
            View.informationForUser(" Данные успешно изменены!! ");
        } else {
            View.informationForUser(" Такие данные уже существуют, объект не изменен");
        }
    }


    /**
     * метод для обновления текстовой базы данных , вызывается непосредственно пере завершением программы
     *
     * @throws IOException-
     */

    static void updateDatabase() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATABASE));
        output.writeObject(runtimeDatabase);
        output.close();
    }


}
