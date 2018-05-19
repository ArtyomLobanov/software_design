package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;
import ru.spbau.sd.cli.interpreter.io.OutputStream;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;

import static org.junit.Assert.*;

public class CmdLsTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void runTest() throws IOException {
        folder.newFile("file1");
        folder.newFile("file2");
        SimpleStream outputStream = new SimpleStream();
        Command lsCommand = new CmdLs(new SimpleEnvironment());
        lsCommand.run(Collections.singletonList(folder.getRoot().toPath().toAbsolutePath()
                        .toString()),
                null, outputStream);
        assertEquals("file1 file2", outputStream.read());
    }

}