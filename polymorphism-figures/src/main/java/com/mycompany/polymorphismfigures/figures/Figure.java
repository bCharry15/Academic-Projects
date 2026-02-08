package com.mycompany.polymorphismfigures.figures;

public abstract class Figure {  //Recordatorio: Abstract hace que Figure sae la clase padre y JAVA hace que las clases hijas lo implementen
    protected double x;
    protected double y;

    protected Figure(double x, double y) {
        this.x = x;
        this.y = y;
    }
    protected Figure() {
        this(0, 0);
    }

    public abstract double calculateArea();
    public abstract double calculatePerimeter();
}
