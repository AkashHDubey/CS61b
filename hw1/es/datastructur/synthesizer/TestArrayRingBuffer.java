package es.datastructur.synthesizer;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<>(10);
        ArrayRingBuffer<Double> arb2 = new ArrayRingBuffer<>(9);

        arb.enqueue(1.1);
        arb.enqueue(1.2);
        arb.enqueue(1.3);

        arb2.enqueue(1.1);
        arb2.enqueue(1.2);
        arb2.enqueue(1.3);

        assertEquals(arb, arb2);


        ArrayRingBuffer<Double> arb3 = new ArrayRingBuffer<>(10);
        ArrayRingBuffer<Double> arb4 = new ArrayRingBuffer<>(10);

        arb3.enqueue(1.1);
        arb3.enqueue(1.2);
        arb3.enqueue(1.3);

        arb4.enqueue(1.1);
        arb4.enqueue(1.2);
        assertNotEquals(arb3,arb4);

        arb4.enqueue(1.3);
        assertEquals(arb3,arb4);


    }
}
