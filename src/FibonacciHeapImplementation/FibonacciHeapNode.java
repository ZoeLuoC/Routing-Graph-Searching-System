package FibonacciHeapImplementation;

public class FibonacciHeapNode {
    public int key;//node address: IPv6
    public double value;//path length
    FibonacciHeapNode parent;
    FibonacciHeapNode child;
    FibonacciHeapNode left;//sibling
    FibonacciHeapNode right;
    boolean marked;
    int degree;

    public FibonacciHeapNode(int key, double value) {
        this.key = key;
        this.value = value;
        this.left = this;
        this.right = this;
    }
}
