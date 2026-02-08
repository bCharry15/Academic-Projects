package com.mycompany.polymorphismfigures.figures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CircleTest {

    @Test
    void testCalculateArea() {
        Circle circle = new Circle(1.0);
        assertEquals(Math.PI, circle.calculateArea(), 0.0001);
    }

    @Test
    void testCalculatePerimeter() {
        Circle circle = new Circle(1.0);
        assertEquals(2 * Math.PI, circle.calculatePerimeter(), 0.0001);
    }
}
