package com.simec.todolistapi.exception;

public class DataSourceException extends RuntimeException {
    public DataSourceException(Exception e) {
        super(e);
    }
}
