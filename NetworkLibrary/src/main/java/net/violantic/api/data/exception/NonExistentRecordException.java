package net.violantic.api.data.exception;

public class NonExistentRecordException extends NullPointerException {

    /**
     * Exception is thrown when data is queried but it does not exist in database
     * @param message Exception message
     */
    public NonExistentRecordException(String message) {
        super(message);
    }

}
