package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Change execution folder. Show current path list of arguments is empty
 */
public class CmdCd implements Command {

    private final Environment environment;

    public CmdCd(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream, OutputStream outputStream) {
        if (arguments.isEmpty()) {
            outputStream.write(environment.getPath().toString());
            return ExecutionResult.OK;
        }
        if (arguments.size() != 1) {
            outputStream.write("Wrong arguments format: one argument expected!");
            return ExecutionResult.Error;
        }
        final Path diff = Paths.get(arguments.get(0));
        final Path newPath = diff.isAbsolute() ? diff : environment.getPath().resolve(diff);
        final File folder = newPath.toFile();
        if (!folder.exists() || !folder.isDirectory()) {
            outputStream.write("Directory wasn't found!");
            return ExecutionResult.Error;
        }
        environment.setPath(newPath);
        return ExecutionResult.OK;
    }
}
