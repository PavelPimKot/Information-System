package MVS;

import java.io.*;
import java.util.ArrayList;


/*
 *Модель - оперирует данными, осуществяет запись\чтение\удаление\изменение данных в контейнер\контейнере
*/
class Model {


    private static final File DIRECTORY = new File("C://Users//HP//Documents//GitHub//CracrerTasks//Iformation System//src");
    private static final File DATABASE = new File(DIRECTORY, "Database.txt");
    private static ArrayList<Object> RUNTIME_DATABASE = new ArrayList<Object>();



    //Обновление текущего контейнера данными из прошлых запусков Справочника

    static void updateRuntimeDatabase() throws IOException, ClassNotFoundException {
        if (DATABASE.length() != 0) {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(DATABASE));
            RUNTIME_DATABASE = (ArrayList<Object>) input.readObject();
            input.close();
        }
    }

    //Добавление информации в контейнер

    static void addInfToBase(Object book) {
        RUNTIME_DATABASE.add(book);
    }

    //Получение информации из контейнера по индексу

    static Object getInfFromBase(int index) {
        return RUNTIME_DATABASE.get(index);
    }

    //Удаление информации из контейнера по индексу
    static void deleteInfFromBase(int index) {
        RUNTIME_DATABASE.remove(index);
    }

    //Изменнение информации в контенера по укзананному индексу

    static void setInfInBase(int index, Object newInf) {
        RUNTIME_DATABASE.set(index, newInf);
    }

    //Запись данных из контейнера в тестовый файл перед завершение программы

    static void updateDatabase() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATABASE));
        output.writeObject(RUNTIME_DATABASE);
        output.close();
    }


}
