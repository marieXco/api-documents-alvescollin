package io.swagger.exception;

public class LockedException extends RuntimeException{
    public LockedException (String msg) {
        super(msg);
    }
}
