import MVS.Controller;
import MVS.EndOfProgramm;
import MVS.NotACommandException;

public class Main {
    public static void main(String[] args) {
        try {
            Controller.StartLibrary();
        }
        catch (Exception | NotACommandException | EndOfProgramm e) {
            System.out.println(e.getCause());
        }

    }
}
