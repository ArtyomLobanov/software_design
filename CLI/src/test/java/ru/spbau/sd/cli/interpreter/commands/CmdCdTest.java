package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;

public class CmdCdTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void runTest() throws IOException {
        File file = folder.newFolder("tmp");
        Environment environment = new SimpleEnvironment();
        environment.setPath(folder.getRoot().toPath().toAbsolutePath());
        Command cdCommand = new CmdCd(environment);
        cdCommand.run(Collections.singletonList("tmp"), null, null);
        SimpleStream outputStream = new SimpleStream();
        cdCommand.run(Collections.emptyList(),null, outputStream);
        assertEquals(file.getAbsolutePath(), outputStream.read());
    }
}