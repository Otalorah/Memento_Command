package commands;

import editor.Editor;

public class AddCommand implements Command {
   private final Editor editor;
   private final double operand;

   public AddCommand(Editor editor, double operand) {
      this.editor = editor;
      this.operand = operand;
   }

   @Override
   public String getName() {
      return "Sumar " + operand;
   }

   @Override
   public void execute() {
      double previous = editor.getCurrentValue();
      double result = previous + operand;
      editor.setCurrentValue(result);
      editor.appendStep(String.format("Suma: %.4f + %.4f = %.4f", previous, operand, result));
   }
}
