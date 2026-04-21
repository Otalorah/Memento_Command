package commands;

import editor.Editor;

public class DivideCommand implements Command {
   private final Editor editor;
   private final double operand;

   public DivideCommand(Editor editor, double operand) {
      this.editor = editor;
      this.operand = operand;
   }

   @Override
   public String getName() {
      return "Dividir por " + operand;
   }

   @Override
   public void execute() {
      if (operand == 0.0) {
         throw new IllegalArgumentException("No se puede dividir entre cero.");
      }

      double previous = editor.getCurrentValue();
      double result = previous / operand;
      editor.setCurrentValue(result);
      editor.appendStep(String.format("Division: %.4f / %.4f = %.4f", previous, operand, result));
   }
}
