package editor;

public class Memento {
   private final String backup;
   private final Calculator calculator;

   public Memento(Calculator calculator) {
      this.calculator = calculator;
      this.backup = calculator.backup();
   }

   public String getState() {
      return backup;
   }

   public void restore() {
      calculator.restore(this);
   }
}