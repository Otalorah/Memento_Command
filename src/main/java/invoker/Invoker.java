package invoker;

import commands.Command;
import history.History;
import editor.Calculator;

public class Invoker {
    private final History history;
    private Command command;

    public Invoker(Calculator calculator) {
        this.history = new History(calculator);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        history.push(command);
        command.execute();
    }

    public boolean undo() { return history.undo(); }
    public boolean redo() { return history.redo(); }
}