package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Show names of all files in folder
 */
public class CmdLs implements Command {

    private final Environment environment;

    public CmdLs(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream, OutputStream outputStream) {
        if (arguments.size() > 1) {
            outputStream.write("Wrong arguments format: one or zero arguments expected!");
            return ExecutionResult.Error;
        }
        final Path path = environment.getPath();
        final Path target = arguments.isEmpty() ? path : path.resolve(arguments.get(0));
        final File directory = target.toFile();
        final String[] files = directory.list();
        if (files == null) {
            outputStream.write("Strange error occurred :(");
            return ExecutionResult.Error;
        }
        final String result = Arrays.stream(files)
                .sorted()
                .collect(Collectors.joining(" "));
        outputStream.write(result);
        return ExecutionResult.OK;
    }
}
