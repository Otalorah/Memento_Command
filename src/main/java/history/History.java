package history;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class History {
   private final List<Pair> history = new ArrayList<>();
   private int cursor = 0;

   private static class Pair {
      private final Command command;
      private final Memento memento;

      private Pair(Command command, Memento memento) {
         this.command = command;
         this.memento = memento;
      }
   }

   public void push(Command command, Memento memento) {
      while (history.size() > cursor) {
         history.remove(history.size() - 1);
      }

      history.add(new Pair(command, memento));
      cursor = history.size();
   }

   public boolean undo() {
      Pair pair = getUndo();
      if (pair == null) {
         return false;
      }

      System.out.println("Deshaciendo: " + pair.command.getName());
      pair.memento.restore();
      return true;
   }

   public boolean redo() {
      Pair pair = getRedo();
      if (pair == null) {
         return false;
      }

      System.out.println("Rehaciendo: " + pair.command.getName());
      pair.memento.restore();
      pair.command.execute();
      return true;
   }

   private Pair getUndo() {
      if (cursor == 0) {
         return null;
      }

      cursor = Math.max(0, cursor - 1);
      return history.get(cursor);
   }

   private Pair getRedo() {
      if (cursor == history.size()) {
         return null;
      }

      cursor = Math.min(history.size(), cursor + 1);
      return history.get(cursor - 1);
   }
}
