package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Counts lines, words and symbols in files which names are passed as arguments.
 * If no arguments provided, reads from input stream.
 */
public class CmdWc implements Command {

    private final Environment environment;

    public CmdWc(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        if (arguments.isEmpty()) {
            WCStat counter = new WCStat(inputStream.read());
            outputStream.write(String.format("%d %d %d",
                    counter.getLinesNumber(), counter.getWordsNumber(),
                    counter.getSymbolsNumber()));
        }
        for (String filename: arguments) {
            Path filepath = Paths.get(filename);
            if (!filepath.isAbsolute()) {
                filepath = environment.getPath().resolve(filepath);
            }
            try {
                WCStat counter = new WCStat();
                Files.lines(filepath)
                        .map(s -> s + '\n')
                        .map(WCStat::new)
                        .forEach(counter::merge);
                outputStream.write(String.format("%d %d %d %s\n",
                        counter.getLinesNumber(), counter.getWordsNumber(),
                        counter.getSymbolsNumber(), filename));
            } catch (IOException e) {
                outputStream.write(e.getMessage());
                return ExecutionResult.Error;
            }
        }
        return ExecutionResult.OK;
    }

    private long countNonEmpty(String[] strs) {
        return Arrays.stream(strs)
                .filter(s -> !s.isEmpty())
                .count();
    }

    class WCStat {
        private long lines = 0;
        private long words = 0;
        private long symbols = 0;

        WCStat(){}

        WCStat(String str) {
            lines = (' ' + str).split("\n").length;
            words = countNonEmpty(str.split("[ \n\t]"));
            symbols = str.length();
        }

        private void merge(WCStat other) {
            lines += other.lines;
            words += other.words;
            symbols += other.symbols;
        }

        private long getLinesNumber() {
            return lines;
        }

        private long getWordsNumber() {
            return words;
        }

        private long getSymbolsNumber() {
            return symbols;
        }
    }
}
