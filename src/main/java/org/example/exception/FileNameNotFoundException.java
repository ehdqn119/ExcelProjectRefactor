package org.example.exception;

public class FileNameNotFoundException extends RuntimeException {
    public FileNameNotFoundException(String msg) {
        super(msg);
    }
}
