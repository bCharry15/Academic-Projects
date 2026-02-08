package com.mycompany.polymorphismfigures.figures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TriangleTest {

    @Test
    void testCalculateArea() {
        Triangle triangle = new Triangle(3.0, 4.0);
        assertEquals(6.0, triangle.calculateArea(), 0.0001);
    }

    @Test
    void testCalculatePerimeter() {
        Triangle triangle = new Triangle(3.0, 4.0);
        assertEquals(12.0, triangle.calculatePerimeter(), 0.0001);
    }
}
