package editor;

import commands.AddCommand;
import commands.ClearCommand;
import commands.DivideCommand;
import commands.MultiplyCommand;
import commands.SubtractCommand;

import java.awt.Font;
import java.awt.Color;
import java.util.List;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;

class Canvas {

   private final JFrame frame;
   private final Editor editor;
   private final JLabel display;
   private final JPanel topPanel;
   private final JTextField input;
   private final JTextArea history;

   Canvas(Editor editor) {
      this.editor = editor;
      this.frame = new JFrame("Calculadora con Memento y Command");
      this.display = new JLabel();
      this.topPanel = new JPanel(new BorderLayout(6, 6));
      this.input = new JTextField();
      this.history = new JTextArea(10, 40);
      createFrame();
   }

   private void createFrame() {
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);
      frame.setLayout(new BorderLayout(8, 8));
      frame.setMinimumSize(new Dimension(700, 520));

      display.setFont(new Font("SansSerif", Font.BOLD, 28));
      display.setHorizontalAlignment(JLabel.RIGHT);

      topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
      topPanel.add(new JLabel("Resultado actual:"), BorderLayout.NORTH);
      topPanel.add(display, BorderLayout.CENTER);

      JPanel controls = new JPanel(new GridLayout(2, 4, 6, 16));
      JButton sumar = new JButton("Sumar");
      JButton restar = new JButton("Restar");
      JButton multiplicar = new JButton("Multiplicar");
      JButton dividir = new JButton("Dividir");
      JButton borrar = new JButton("Borrar");
      JButton deshacer = new JButton("Deshacer");
      JButton rehacer = new JButton("Rehacer");

      controls.add(sumar);
      controls.add(restar);
      controls.add(multiplicar);
      controls.add(dividir);
      controls.add(borrar);
      controls.add(deshacer);
      controls.add(rehacer);

      JPanel center = new JPanel(new BorderLayout(6, 6));
      center.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

      JPanel inputPanel = new JPanel(new BorderLayout(6, 60));
      inputPanel.add(new JLabel(""), BorderLayout.CENTER);
      input.setColumns(20);
      input.setText("0");
      input.setEditable(true);
      input.setEnabled(true);
      input.setFocusable(true);
      input.setRequestFocusEnabled(true);
      input.setBackground(new Color(255, 252, 214));
      input.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
      inputPanel.add(input, BorderLayout.CENTER);

      center.add(inputPanel, BorderLayout.CENTER);
      center.add(controls, BorderLayout.SOUTH);

      topPanel.add(center, BorderLayout.SOUTH);
      frame.add(topPanel, BorderLayout.NORTH);

      history.setEditable(false);
      frame.add(new JScrollPane(history), BorderLayout.CENTER);

      sumar.addActionListener(e -> ejecutarSuma());
      restar.addActionListener(e -> ejecutarResta());
      multiplicar.addActionListener(e -> ejecutarMultiplicacion());
      dividir.addActionListener(e -> ejecutarDivision());
      borrar.addActionListener(e -> editor.execute(new ClearCommand(editor)));
      deshacer.addActionListener(e -> editor.undo());
      rehacer.addActionListener(e -> editor.redo());

      frame.addWindowListener(new WindowAdapter() {
         @Override
         public void windowOpened(WindowEvent e) {
            SwingUtilities.invokeLater(() -> input.requestFocusInWindow());
         }
      });

      frame.pack();
      frame.setVisible(true);
      input.requestFocusInWindow();
   }

   private void ejecutarSuma() {
      Double operando = leerOperando();
      if (operando != null) {
         editor.execute(new AddCommand(editor, operando));
      }
   }

   private void ejecutarResta() {
      Double operando = leerOperando();
      if (operando != null) {
         editor.execute(new SubtractCommand(editor, operando));
      }
   }

   private void ejecutarMultiplicacion() {
      Double operando = leerOperando();
      if (operando != null) {
         editor.execute(new MultiplyCommand(editor, operando));
      }
   }

   private void ejecutarDivision() {
      Double operando = leerOperando();
      if (operando == null) {
         return;
      }

      try {
         editor.execute(new DivideCommand(editor, operando));
      } catch (IllegalArgumentException ex) {
         JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   private Double leerOperando() {
      String raw = input.getText();
      if (raw == null) {
         JOptionPane.showMessageDialog(frame, "Ingrese un numero valido.", "Entrada invalida", JOptionPane.WARNING_MESSAGE);
         input.requestFocusInWindow();
         return null;
      }

      String normalized = raw.trim().replace(',', '.');
      if (normalized.isEmpty()) {
         JOptionPane.showMessageDialog(frame, "Ingrese un numero antes de ejecutar la operacion.", "Entrada vacia", JOptionPane.WARNING_MESSAGE);
         input.requestFocusInWindow();
         return null;
      }

      try {
         return Double.parseDouble(normalized);
      } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(frame, "Ingrese un numero valido.", "Entrada invalida", JOptionPane.WARNING_MESSAGE);
         input.requestFocusInWindow();
         return null;
      }
   }

   void refresh() {
      display.setText("Resultado: " + editor.getDisplayValue());

      List<String> steps = editor.getSteps();
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < steps.size(); i++) {
         builder.append(i + 1).append(". ").append(steps.get(i)).append(System.lineSeparator());
      }
      history.setText(builder.toString());
      input.requestFocusInWindow();
   }
}
