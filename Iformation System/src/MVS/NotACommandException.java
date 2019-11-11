package MVS;

public class NotACommandException extends Throwable {
    public NotACommandException(String cause){
        super(cause);
    }
}
