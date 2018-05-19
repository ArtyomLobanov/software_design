package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;

public class CmdCatTest {
    private final String content = "test content";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void runTest() throws IOException {
        File file = folder.newFile();
        Writer writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream =
                Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        Command catCommand = new CmdCat(new SimpleEnvironment());
        catCommand.run(Collections.singletonList(file.toPath().toAbsolutePath()
                        .toString()),
                null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq(content + '\n'));
    }

}
