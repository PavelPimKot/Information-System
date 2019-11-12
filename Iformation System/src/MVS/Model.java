package MVS;

import InformationClasses.*;
import java.io.*;
import java.util.ArrayList;


/*
 *Модель - оперирует данными, осуществяет запись\чтение\удаление\изменение данных в контейнер\контейнере
 */
class Model {


    private static final File DIRECTORY = new File("C://Users//HP//Documents//GitHub//Information-System//Iformation System//src");
    private static final File DATABASE = new File(DIRECTORY, "Database.txt");
    private static ArrayList<Object> RUNTIME_DATABASE = new ArrayList<Object>();


    //Добавление данных из указанного файла
    static void addInformationFromFile(File infBase) throws IOException, ClassNotFoundException {
        if (infBase.length() != 0) {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(infBase));
            //RUNTIME_DATABASE = (ArrayList<Object>) input.readObject();
            while(input.available()!=0) {
                Object inf = input.readObject();
                if(inf instanceof Book || inf instanceof BookInstance){
                    addInfToBase(inf);
                }
            }
            input.close();
        }
    }


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
        if (!RUNTIME_DATABASE.contains(book)) {
            RUNTIME_DATABASE.add(book);
        }
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
        if (!RUNTIME_DATABASE.contains(newInf)) {
            RUNTIME_DATABASE.set(index, newInf);
        }
    }

    //Запись данных из контейнера в тестовый файл перед завершение программы

    static void updateDatabase() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATABASE));
        output.writeObject(RUNTIME_DATABASE);
        output.close();
    }


}
