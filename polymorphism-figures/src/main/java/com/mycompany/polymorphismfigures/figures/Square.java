package com.mycompany.polymorphismfigures.figures;

public class Square extends Figure {
    private final double side;

    public Square(double side) {
        super();
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side; //area= ladoxlado
    }

    @Override
    public double calculatePerimeter() {
        return 4 * side; //perimetro= lxlxlxl
    }
}
