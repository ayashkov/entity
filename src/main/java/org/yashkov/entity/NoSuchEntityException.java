package org.yashkov.entity;

public class NoSuchEntityException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoSuchEntityException(String message)
    {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
