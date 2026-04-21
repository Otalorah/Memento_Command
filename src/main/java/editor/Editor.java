package editor;

import commands.Command;
import history.History;
import history.Memento;

import java.util.List;
import java.util.Base64;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Editor {
   private final Canvas canvas;
   private final History history;
   private double currentValue;
   private List<String> steps;

   public Editor() {
      this.history = new History();
      this.currentValue = 0.0;
      this.steps = new ArrayList<>();
      this.canvas = new Canvas(this);
      canvas.refresh();
   }

   public void execute(Command command) {
      history.push(command, new Memento(this));
      command.execute();
      canvas.refresh();
   }

   public void undo() {
      if (history.undo()) {
         canvas.refresh();
      }
   }

   public void redo() {
      if (history.redo()) {
         canvas.refresh();
      }
   }

   public double getCurrentValue() {
      return currentValue;
   }

   public void setCurrentValue(double value) {
      currentValue = value;
   }

   public void appendStep(String step) {
      steps.add(step);
   }

   public List<String> getSteps() {
      return new ArrayList<>(steps);
   }

   public String getDisplayValue() {
      return String.format("%.4f", currentValue);
   }

   public String backup() {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(new CalculatorState(currentValue, steps));
         oos.close();
         return Base64.getEncoder().encodeToString(baos.toByteArray());
      } catch (IOException e) {
         return "";
      }
   }

   public void restore(String state) {
      try {
         byte[] data = Base64.getDecoder().decode(state);
         ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
         CalculatorState calculatorState = (CalculatorState) ois.readObject();
         this.currentValue = calculatorState.value;
         this.steps = new ArrayList<>(calculatorState.steps);
         ois.close();
      } catch (IOException e) {
         System.out.print("IOException while restoring state.");
      } catch (ClassNotFoundException e) {
         System.out.print("ClassNotFoundException while restoring state.");
      }
   }

   private static class CalculatorState implements Serializable {
      private static final long serialVersionUID = 1L;

      private final double value;
      private final List<String> steps;

      private CalculatorState(double value, List<String> steps) {
         this.value = value;
         this.steps = new ArrayList<>(steps);
      }
   }
}
