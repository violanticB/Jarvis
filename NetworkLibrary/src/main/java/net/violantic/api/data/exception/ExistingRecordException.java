package net.violantic.api.data.exception;

public class ExistingRecordException extends Exception {

    /**
     * Will be thrown when a data record already exists in the database
     * when trying to insert.
     * @param error
     */
    public ExistingRecordException(String error) {
        super(error);
    }

}
