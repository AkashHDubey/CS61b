package synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {

        this.first = 0;
        this.last = 0 ;
        this.fillCount = 0;
        this.rb = (T []) new Object[capacity];
    }

    @Override
    public int capacity() {
        return this.rb.length;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {

        if(this.fillCount == this.rb.length){
            throw new RuntimeException("Ring Buffer overflow");
        }

        this.rb[last] = x;
        this.last = Math.floorMod(++last,this.capacity());
        this.fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {

        if(this.fillCount == 0){
            throw new RuntimeException("Ring Buffer underflow");
        }

        T item = this.rb[first];
        this.rb[first] = null;
        this.fillCount--;
        this.first = Math.floorMod(++first,this.capacity());
        return item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {

        if(this.fillCount == 0){
            throw new RuntimeException("Ring Buffer underflow");
        }

        return this.rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new QuequeIterator();
    }

    @Override
    public boolean equals(Object o){

        if (o == null) { return false; }
        if (this == o) { return true; } // optimization
        if (this.getClass() != o.getClass()) { return false; }

        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o ;

        if(this.fillCount() != other.fillCount) {
            return false;
        }

        int currIndex = this.last;
        int otherIndex = other.last;

        while (currIndex < this.first && otherIndex < other.first) {

            if(!this.rb[currIndex].equals(other.rb[otherIndex])){
                return false;
            }

            currIndex = Math.floorMod(++currIndex,this.fillCount);
            otherIndex = Math.floorMod(++otherIndex,this.fillCount);
        }

        return true;

    }

    private class QuequeIterator implements Iterator<T>{

        int next = first;
        int count = 1;
        @Override
        public boolean hasNext() {

            return count <= fillCount;

        }

        @Override
        public T next() {

            T item = rb[next];
            this.next = Math.floorMod(this.next++,rb.length);
            count++;
            return item;

        }
    }


}

