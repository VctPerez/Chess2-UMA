package exceptions;

public class SettingsException extends RuntimeException{
    public SettingsException(String error){
        super(error);
    }
}
