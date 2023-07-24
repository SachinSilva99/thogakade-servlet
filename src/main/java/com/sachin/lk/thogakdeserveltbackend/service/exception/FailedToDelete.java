package com.sachin.lk.thogakdeserveltbackend.service.exception;

/*
Author : Sachin Silva
*/
public class FailedToDelete extends Exception{
    public FailedToDelete() {
        super();
    }

    public FailedToDelete(String message) {
        super(message);
    }

    public FailedToDelete(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToDelete(Throwable cause) {
        super(cause);
    }
}
