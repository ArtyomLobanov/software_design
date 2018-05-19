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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CmdWcTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void runTest() throws IOException {
        Command wcCommand = new CmdWc(new SimpleEnvironment());
        File file = folder.newFile();
        Writer writer = new FileWriter(file);
        String content = "test content";
        writer.write(content + '\n');
        writer.write(content + '\n');
        writer.close();
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        wcCommand.run(Collections.singletonList(file.toPath().toString()),
                null, outputStream);
        List<String> expected = Stream.of(2, 4, 2 * (content.length() + 1),
                file.toPath())
                .map(Object::toString)
                .collect(Collectors.toList());
        Mockito.verify(outputStream).write(Mockito.argThat(string ->
                expected.equals(Arrays.asList(string.split("[ |\\n]")))
        ));
    }

}
