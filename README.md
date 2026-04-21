# Calculadora De Escritorio Con Memento Y Command

Este proyecto implementa una calculadora de escritorio en Java Swing con operaciones basicas y trazabilidad completa de pasos usando los patrones Command y Memento.

## Requirements

- Java 17+
- Maven 3.8+

## Run

```bash
mvn compile
mvn exec:java
```

## Controls

- Escribe un operando numerico en el campo de entrada.
- Usa los botones Sumar, Restar, Multiplicar, Dividir y Borrar.
- Usa Deshacer y Rehacer para navegar el historial sin limite explicito durante la sesion.
- El panel inferior muestra la trazabilidad de cada paso ejecutado.
