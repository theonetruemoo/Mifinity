package za.co.mifinity.interview.exception;


public class GenericException extends Exception {
    //normally this would have a more professional error message
    public GenericException() {
        super("How did you do that?");
    }

    public GenericException(String message) {
        super(message);
    }
}
