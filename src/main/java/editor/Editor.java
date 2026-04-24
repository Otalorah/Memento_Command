package editor;

import commands.Command;
import invoker.Invoker;

/**
 * Fachada/controlador de la interfaz.
 * Orquesta la ejecución de comandos y las acciones de undo/redo.
 */
public class Editor {
   private final Canvas canvas;
   private final Calculator calculator;
   private final Invoker invoker;

   public Editor() {
      this.calculator = new Calculator();
      this.invoker = new Invoker(calculator);
      this.canvas = new Canvas(this);
      canvas.refresh();
   }

   public void execute(Command command) {
      invoker.setCommand(command);
      invoker.executeCommand();
      canvas.refresh();
   }

   public void undo() {
      if (invoker.undo()) canvas.refresh();
   }

   public void redo() {
      if (invoker.redo()) canvas.refresh();
   }

   public Calculator getCalculator() {
      return calculator;
   }
}