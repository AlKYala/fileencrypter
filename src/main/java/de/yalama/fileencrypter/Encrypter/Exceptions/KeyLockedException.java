package de.yalama.fileencrypter.Encrypter.Exceptions;

public class KeyLockedException extends Exception {
    public KeyLockedException(String message) {
        super(message);
    }

    public KeyLockedException() {
        super("Exception: Unlock this file first before you can use it");
    }
}
