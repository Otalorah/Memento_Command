package commands;

import editor.Calculator;

public class AddCommand implements Command {
   private final Calculator calculator;
   private final double operand;

   public AddCommand(Calculator calculator, double operand) {
      this.calculator = calculator;
      this.operand = operand;
   }

   @Override
   public String getName() { return "Sumar " + operand; }

   @Override
   public void execute() {
      double previous = calculator.getCurrentValue();
      double result = previous + operand;
      calculator.setCurrentValue(result);
      calculator.appendStep(String.format("Suma: %.4f + %.4f = %.4f", previous, operand, result));
   }
}