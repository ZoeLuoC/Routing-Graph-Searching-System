package FibonacciHeapImplementation;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap {
    public FibonacciHeapNode minNode;
    public int nodeCount;

    public void insert(FibonacciHeapNode node) {
        if(minNode == null) {
            minNode = node;
        } else {
            addNode(node, minNode);
            if(minNode.value > node.value) {
                minNode = node;
            }
        }
        nodeCount++;
    }
    public FibonacciHeapNode removeMin() {
        FibonacciHeapNode min = minNode;
        if(min != null) {
            if(min.child != null) {
                FibonacciHeapNode chi = minNode.child;
                do {
                    FibonacciHeapNode next = chi.right;
                    addNode(chi, min);
                    chi.parent = null;
                    chi = next;
                } while(chi != min.child);
            }
            removeNode(min);
            if(min == min.right) {
                minNode = null;
            } else {
                minNode = min.right;
                consolidate();
            }
        }
        return min;
    }
    public void decreaseKey(FibonacciHeapNode node, double newValue) {
        if(node == null || newValue > node.value) {
            return;
        }
        node.value = newValue;
        FibonacciHeapNode pa = node.parent;
        if(pa != null && pa.value > node.value) {
            cut(node, pa);
            cascadingCut(pa);
        }
        if(minNode == null || node.value < minNode.value) {
            minNode = node;
        }
    }
    public void cut(FibonacciHeapNode node, FibonacciHeapNode parent) {
        if(parent.child == node) {
            parent.child = (node.right == node) ? null : node.right;
        }
        parent.degree--;
        removeNode(node);
        addNode(node, minNode);
        node.parent = null;
        node.marked = false;
    }
    public void cascadingCut(FibonacciHeapNode node) {
        FibonacciHeapNode parent = node.parent;
        if(parent != null) {
            if(!node.marked) {
                node.marked = true;
            } else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }
    }

    public void consolidate() {//待补充
        int arraySize = ((int) Math.ceil(Math.log(nodeCount) / Math.log(2))) + 1;
        FibonacciHeapNode[] rootArray = new FibonacciHeapNode[arraySize];

        List<FibonacciHeapNode> rootNode = new ArrayList<>();
        FibonacciHeapNode node = minNode;
        if(node != null) {
            do {
                FibonacciHeapNode next = node.right;
                rootNode.add(node);
                node = next;
            } while(node != minNode);
        }

        for(FibonacciHeapNode root : rootNode) {
            int degree = root.degree;
            while(rootArray[degree] != null) {
                FibonacciHeapNode other = rootArray[degree];
                if(root.value > other.value) {
                    FibonacciHeapNode temp = root;
                    root = other;
                    other = temp;
                }
                linkNodes(other, root);
                rootArray[degree] = null;
                degree++;
            }
            rootArray[degree] = root;
        }
        minNode = null;
        for(FibonacciHeapNode f : rootArray) {
            if(f != null) {
                if(minNode == null) {
                    minNode = f;
                } else {
                    addNode(f, minNode);
                    if(f.value < minNode.value) {
                        minNode = f;
                    }
                }

            }
        }
    }

    public void linkNodes(FibonacciHeapNode child, FibonacciHeapNode parent) {
        removeNode(child);
        child.left = child.right = child;
        parent.child = mergeLists(parent.child, child);
        child.parent = parent;
        parent.degree++;
        child.marked = false;
    }

    public FibonacciHeapNode mergeLists(FibonacciHeapNode a, FibonacciHeapNode b) {
        if(a == null) {
            return b;
        }
        if(b == null) {
            return a;
        }
        FibonacciHeapNode aNext = a.right;
        FibonacciHeapNode bPrev = b.left;

        a.right = b;
        b.left = a;
        aNext.left = bPrev;
        bPrev.right = aNext;

        return a.value < b.value ? a : b;
    }

    public void addNode(FibonacciHeapNode node, FibonacciHeapNode root) {
        if(root == null) {
            node.left = node.right = node;
        } else {
            FibonacciHeapNode temp = root.left;
            node.left = temp;
            node.right = root;
            temp.right = node;
            root.left = node;
        }

    }
    public void removeNode(FibonacciHeapNode node) {
        FibonacciHeapNode leftNode = node.left;
        FibonacciHeapNode rightNode = node.right;
        leftNode.right = rightNode;
        rightNode.left = leftNode;
        node.left = node.right = node;
    }
}
