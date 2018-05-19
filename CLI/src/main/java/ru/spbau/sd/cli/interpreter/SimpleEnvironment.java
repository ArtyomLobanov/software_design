package ru.spbau.sd.cli.interpreter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Environment interface based on a hash table.
 */
public class SimpleEnvironment implements Environment {
    private Map<String, String> vars = new HashMap<>();
    private Path path = Paths.get("").toAbsolutePath();

    public void set(String name, String val) {
        vars.put(name, val);
    }

    public String get(String name) {
        return vars.getOrDefault(name, "");
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path) {
        this.path = path.normalize().toAbsolutePath();
    }
}
