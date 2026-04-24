package commands;

import editor.Calculator;

public class DivideCommand implements Command {
   private final Calculator calculator;
   private final double operand;

   public DivideCommand(Calculator calculator, double operand) {
      this.calculator = calculator;
      this.operand = operand;
   }

   @Override
   public String getName() { return "Dividir por " + operand; }

   @Override
   public void execute() {
      if (operand == 0.0) throw new IllegalArgumentException("No se puede dividir entre cero.");
      double previous = calculator.getCurrentValue();
      double result = previous / operand;
      calculator.setCurrentValue(result);
      calculator.appendStep(String.format("Division: %.4f / %.4f = %.4f", previous, operand, result));
   }
}