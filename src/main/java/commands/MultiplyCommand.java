package commands;

import editor.Calculator;

public class MultiplyCommand implements Command {
   private final Calculator calculator;
   private final double operand;

   public MultiplyCommand(Calculator calculator, double operand) {
      this.calculator = calculator;
      this.operand = operand;
   }

   @Override
   public String getName() { return "Multiplicar por " + operand; }

   @Override
   public void execute() {
      double previous = calculator.getCurrentValue();
      double result = previous * operand;
      calculator.setCurrentValue(result);
      calculator.appendStep(String.format("Multiplicacion: %.4f * %.4f = %.4f", previous, operand, result));
   }
}