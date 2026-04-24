package commands;

import editor.Calculator;

public class ClearCommand implements Command {
   private final Calculator calculator;

   public ClearCommand(Calculator calculator) {
      this.calculator = calculator;
   }

   @Override
   public String getName() { return "Borrar resultado"; }

   @Override
   public void execute() {
      calculator.setCurrentValue(0.0);
      calculator.appendStep("Borrado: resultado reiniciado a 0.0000");
   }
}