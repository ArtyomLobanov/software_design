package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * If any arguments are passed, writes content of files with that names to the
 * output stream. Otherwise copies input to output stream.
 */
public class CmdCat implements Command {

    private final Environment environment;

    public CmdCat(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        if (arguments.isEmpty()) {
            outputStream.write(inputStream.read());
            return ExecutionResult.OK;
        }
        for (String filename: arguments) {
            Path filepath = Paths.get(filename);
            if (!filepath.isAbsolute()) {
                filepath = environment.getPath().resolve(filepath);
            }
            try {
                Files.lines(filepath)
                        .map(s -> s + '\n')
                        .forEach(outputStream::write);
            } catch (NoSuchFileException e) {
                outputStream.write("File wasn't found: " + filepath);
                return ExecutionResult.Error;
            } catch (IOException e) {
                outputStream.write(e.getMessage());
                return ExecutionResult.Error;
            }
        }
        return ExecutionResult.OK;
    }
}
