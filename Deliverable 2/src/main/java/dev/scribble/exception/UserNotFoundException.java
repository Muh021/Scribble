package dev.scribble.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String data) {
        super(String.format("User not found by identifier: '%s'", data));
    }

    public UserNotFoundException(long userId) {
        super(String.format("User not found for id: '%s'", userId));
    }
}
