package synthesizer;

public abstract class AbstractBoundedQueque<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;
    
    public int capacity(){
        return capacity;
    }
    public int fillCount(){
        return fillCount;
    }
    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);

}
