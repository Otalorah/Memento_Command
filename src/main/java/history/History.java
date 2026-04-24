package history;

import commands.Command;
import editor.Calculator;
import editor.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Historial de comandos.
 * Implementa undo/redo guardando el estado antes y después de cada comando.
 */
public class History {
   private final List<Pair> history = new ArrayList<>();
   private final Calculator calculator;
   private int cursor = 0;

   /**
    * Cada entrada del historial guarda:
    * - el comando ejecutado
    * - el estado previo (para undo)
    * - el estado posterior (para redo)
    */
   private static class Pair {
      private final Command command;
      private final Memento before;
      private final Memento after;

      private Pair(Command command, Memento before, Memento after) {
         this.command = command;
         this.before = before;
         this.after = after;
      }
   }

   public History(Calculator calculator) {
      this.calculator = calculator;
   }

   /**
    * Guarda una operación en el historial.
    * Si se había hecho undo y luego se ejecuta un nuevo comando,
    * se elimina la rama futura para mantener una línea de tiempo coherente.
    */
   public void push(Command command, Memento before, Memento after) {
      while (history.size() > cursor) {
         history.remove(history.size() - 1);
      }
      history.add(new Pair(command, before, after));
      cursor = history.size();
   }

   /**
    * Deshace la última operación restaurando el estado previo.
    */
   public boolean undo() {
      if (cursor == 0) return false;

      cursor--;
      Pair pair = history.get(cursor);
      System.out.println("Deshaciendo: " + pair.command.getName());
      calculator.restore(pair.before);
      return true;
   }

   /**
    * Rehace la siguiente operación restaurando el estado posterior.
    * Así no depende de volver a ejecutar el comando.
    */
   public boolean redo() {
      if (cursor == history.size()) return false;

      Pair pair = history.get(cursor);
      System.out.println("Rehaciendo: " + pair.command.getName());
      calculator.restore(pair.after);
      cursor++;
      return true;
   }
}