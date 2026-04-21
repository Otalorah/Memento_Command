package commands;

import editor.Editor;

public class ClearCommand implements Command {
   private final Editor editor;

   public ClearCommand(Editor editor) {
      this.editor = editor;
   }

   @Override
   public String getName() {
      return "Borrar resultado";
   }

   @Override
   public void execute() {
      editor.setCurrentValue(0.0);
      editor.appendStep("Borrado: resultado reiniciado a 0.0000");
   }
}
