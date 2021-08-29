package me.lolico.learning.designpattern.decorator;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UpperCaseInputStreamTest {

    InputStream inputStream;

    @Before
    public void init() {
        inputStream = new UpperCaseInputStream(this.getClass().getResourceAsStream("/a.txt"));
    }


    @Test
    public void read() throws IOException {
        int c;
        while ((c = inputStream.read()) >= 0) {
            System.out.print((char) c);
        }
    }

    @Test
    public void testRead() {
    }
}
