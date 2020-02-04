package club.zstuca.platform.exception;

public class CustomizeException extends RuntimeException {
    public CustomizeException(ICustomizeErrorCode errorCode){
        super(errorCode.getMessage());
    }
    public CustomizeException(String message){
        super(message);
    }
}
