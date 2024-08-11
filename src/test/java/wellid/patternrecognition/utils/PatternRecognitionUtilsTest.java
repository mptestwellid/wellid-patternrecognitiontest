package wellid.patternrecognition.utils;

import org.junit.jupiter.api.Test;
import wellid.patternrecognition.model.bo.Point;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatternRecognitionUtilsTest {

    @Test
    void isPointValid(){
        // Test con coordinate valide
        Point validPoint = new Point(1.0f, 2.0f);
        assertTrue(PatternRecognitionUtils.isPointValid(validPoint));

        // Test con coordinate nulle
        Point invalidPointX = new Point(null, 2.0f);
        assertFalse(PatternRecognitionUtils.isPointValid(invalidPointX));

        Point invalidPointY = new Point(1.0f, null);
        assertFalse(PatternRecognitionUtils.isPointValid(invalidPointY));

        Point invalidPointBoth = new Point(null, null);
        assertFalse(PatternRecognitionUtils.isPointValid(invalidPointBoth));
    }
    @Test
    void calculateGradient() {

        // Test con punti con pendenza definito
        Point p1 = new Point(1.0f, 1.0f);
        Point p2 = new Point(3.0f, 3.0f);
        assertTrue(Float.compare(PatternRecognitionUtils.calculateGradient(p1, p2),1.0f) == 0);

        // Test con pendenza infinito (differenza x zero)
        Point p3 = new Point(1.0f, 1.0f);
        Point p4 = new Point(1.0f, 2.0f);
        assertTrue(Float.compare(PatternRecognitionUtils.calculateGradient(p3, p4),0.0f) == 0);

        // Test con pendenza negativo
        Point p5 = new Point(1.0f, 3.0f);
        Point p6 = new Point(2.0f, 1.0f);
        assertTrue(Float.compare(PatternRecognitionUtils.calculateGradient(p5, p6),-2.0f) == 0);

        // Test con punti identici (pendenza non definito)
        Point p7 = new Point(1.0f, 1.0f);
        Point p8 = new Point(1.0f, 1.0f);
        assertTrue(Float.compare(PatternRecognitionUtils.calculateGradient(p7, p8),0.0f) == 0);
    }

    @Test
    void isPointsAligned() {
        // Test con punti allineati
        Point p1 = new Point(1.0f, 1.0f);
        Point p2 = new Point(2.0f, 2.0f);
        Point p3 = new Point(3.0f, 3.0f);
        assertTrue(PatternRecognitionUtils.isPointsAligned(p1, p2, p3));

        // Test con punti non allineati
        Point p4 = new Point(1.0f, 1.0f);
        Point p5 = new Point(2.0f, 4.0f);
        Point p6 = new Point(3.0f, 5.0f);
        assertFalse(PatternRecognitionUtils.isPointsAligned(p4, p5, p6));

        // Test con punti parzialmente allineati
        Point p7 = new Point(1.0f, 1.0f);
        Point p8 = new Point(2.0f, 2.0f);
        Point p9 = new Point(3.0f, 4.0f);
        assertFalse(PatternRecognitionUtils.isPointsAligned(p7, p8, p9));

        // Test con punti allineati su una linea orizzontale
        Point p10 = new Point(1.0f, 1.0f);
        Point p11 = new Point(1.0f, 2.0f);
        Point p12 = new Point(1.0f, 3.0f);
        assertTrue(PatternRecognitionUtils.isPointsAligned(p10, p11, p12));

        // Test con punti allineati su una linea verticale
        Point p13 = new Point(1.0f, 1.0f);
        Point p14 = new Point(1.0f, 2.0f);
        Point p15 = new Point(1.0f, 3.0f);
        assertTrue(PatternRecognitionUtils.isPointsAligned(p13, p14, p15));
    }
}