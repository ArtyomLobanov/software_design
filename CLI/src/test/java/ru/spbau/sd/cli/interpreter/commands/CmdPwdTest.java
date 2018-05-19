package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;

import java.nio.file.Paths;
import java.util.Collections;

public class CmdPwdTest {
    @Test
    public void runTest() {
        Command pwdCommand = new CmdPwd(new SimpleEnvironment());
        String dir = Paths.get(".").toAbsolutePath().normalize().toString();
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream =
                Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        pwdCommand.run(Collections.emptyList(), null, outputStream);
        Mockito.verify(outputStream).write(dir);
    }
}
