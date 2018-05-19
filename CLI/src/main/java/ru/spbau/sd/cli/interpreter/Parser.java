package ru.spbau.sd.cli.interpreter;

import ru.spbau.sd.cli.interpreter.commands.*;

import java.util.List;

/**
 * Parser takes a pipeline in string form and builds an abstract syntax tree
 * (actually list).
 */
public class Parser {
    private Environment environment;

    Parser(Environment environment) {
        this.environment = environment;
    }

    /**
     * Makes the required Command from its name or textual representation.
     * @param cmd - String representation of the command
     * @return The Command
     */
    private Command chooseCommand(String cmd) {
        if (AssignmentCommand.isAssignment(cmd)) {
            return AssignmentCommand.create(cmd, environment);
        }
        switch (cmd) {
            case "echo":
                return new CmdEcho();
            case "cat":
                return new CmdCat(environment);
            case "exit":
                return new CmdExit();
            case "pwd":
                return new CmdPwd(environment);
            case "wc":
                return new CmdWc(environment);
            case "cd":
                return new CmdCd(environment);
            case "ls":
                return new CmdLs(environment);
            default:
                return new ExternalCommand(cmd);
        }
    }

    /*
    Creates an AST node from a pipeline step represented in the form of a list
    of tokens. The first node is interpreted as command name, the rest - as
    arguments.
     */
    private ASTElement buildCommand(List<String> tokens) {
        Command command = chooseCommand(tokens.get(0));
        return new ASTElement(command, tokens.subList(1, tokens.size()));
    }

    /**
     * Makes an AST from pipeline string.
     * @param line a string with commands
     * @return the last element of the built AST
     */
    public ASTElement parseLine(String line) {
        ASTElement astFinish = null;
        Tokenizer tokenizer = new Tokenizer(line, environment);
        for (List<String> tokens: tokenizer) {
            if (tokens.isEmpty()) {
                continue;
            }
            ASTElement newElem = buildCommand(tokens);
            newElem.setPrevious(astFinish);
            astFinish = newElem;
        }
        return astFinish;
    }
}
