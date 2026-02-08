package com.mycompany.polymorphismfigures.figures;

public class Triangle extends Figure {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        super();
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return (base * height) / 2.0; //area= basexaltura/2
    }

    @Override
    public double calculatePerimeter() {
        double hypotenuse = Math.sqrt(base * base + height * height);//perimetro= raiz(base^2+altura^2)
        return base + height + hypotenuse;
    }
}
