package editor;

/**
 * Memento: almacena una instantánea inmutable del estado de la calculadora.
 * No conoce cómo restaurar; solo guarda el estado.
 */
public class Memento {
   private final String state;

   public Memento(String state) {
      this.state = state;
   }

   public String getState() {
      return state;
   }
}