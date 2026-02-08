package com.mycompany.polymorphismfigures.figures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    void testCalculateArea() {
        Square square = new Square(2.0);
        assertEquals(4.0, square.calculateArea(), 0.0001);
    }

    @Test
    void testCalculatePerimeter() {
        Square square = new Square(2.0);
        assertEquals(8.0, square.calculatePerimeter(), 0.0001);
    }
}
