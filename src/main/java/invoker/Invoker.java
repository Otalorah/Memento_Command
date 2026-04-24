package invoker;

import commands.Command;
import editor.Calculator;
import editor.Memento;
import history.History;

/**
 * Invoker del patrón Command.
 * Ejecuta comandos y delega el control del historial a History.
 */
public class Invoker {
    private final History history;
    private final Calculator calculator;
    private Command command;

    public Invoker(Calculator calculator) {
        this.calculator = calculator;
        this.history = new History(calculator);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Ejecuta el comando como parte del patrón Command.
     * Antes y después se capturan mementos para permitir undo/redo exactos.
     */
    public void executeCommand() {
        Memento before = calculator.createMemento(); // snapshot previo
        command.execute();                           // ejecución del comando
        Memento after = calculator.createMemento();  // snapshot posterior
        history.push(command, before, after);        // se registra en historial
    }

    public boolean undo() {
        return history.undo();
    }

    public boolean redo() {
        return history.redo();
    }
}