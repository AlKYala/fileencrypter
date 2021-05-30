package de.yalama.fileencrypter.EncrypterStream;

import java.io.IOException;
import java.io.OutputStream;

public class EncrypterStream extends OutputStream {

    private StringBuilder stringBuilder;

    public EncrypterStream() {
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void write(int i) throws IOException {
        this.stringBuilder.append((char) i);
    }
}
