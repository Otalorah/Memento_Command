package commands;

import editor.Editor;

public class SubtractCommand implements Command {
   private final Editor editor;
   private final double operand;

   public SubtractCommand(Editor editor, double operand) {
      this.editor = editor;
      this.operand = operand;
   }

   @Override
   public String getName() {
      return "Restar " + operand;
   }

   @Override
   public void execute() {
      double previous = editor.getCurrentValue();
      double result = previous - operand;
      editor.setCurrentValue(result);
      editor.appendStep(String.format("Resta: %.4f - %.4f = %.4f", previous, operand, result));
   }
}
