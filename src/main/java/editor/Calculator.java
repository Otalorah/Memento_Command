package editor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.io.*;

public class Calculator {
    private double currentValue;
    private List<String> steps;

    public Calculator() {
        this.currentValue = 0.0;
        this.steps = new ArrayList<>();
    }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double value) { this.currentValue = value; }
    public void appendStep(String step) { steps.add(step); }
    public List<String> getSteps() { return new ArrayList<>(steps); }
    public String getDisplayValue() { return String.format("%.4f", currentValue); }

    public String backup() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(new CalculatorState(currentValue, steps));
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) { return ""; }
    }

    public Memento save() {
        return new Memento(this);
    }

    public void restore(Memento memento) {
        try {
            byte[] data = Base64.getDecoder().decode(memento.getState());
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            CalculatorState s = (CalculatorState) ois.readObject();
            this.currentValue = s.value;
            this.steps = new ArrayList<>(s.steps);
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.print("Error al restaurar estado.");
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