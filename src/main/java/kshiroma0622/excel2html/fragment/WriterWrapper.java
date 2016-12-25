package kshiroma0622.excel2html.fragment;

import java.io.IOException;
import java.io.Writer;

public class WriterWrapper {

    private final Writer w;

    public WriterWrapper(Writer w) {
        this.w = w;
    }

    public void write(String str) {
        try {
            w.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String str) {
        write(str);
        write("\r\n");
    }

    public void flush() {
        try {
            w.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
