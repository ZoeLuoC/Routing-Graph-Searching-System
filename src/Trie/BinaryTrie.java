package Trie;

public class BinaryTrie {
    TrieNode root = new TrieNode();

    public void insert(int number, double data) {
        TrieNode current = root;
        for(int i = 31; i >= 0; i--) {
            int bit = (number >> i) & 1;
            if(current.children[bit] == null) {
                current.children[bit] = new TrieNode();
            }
            current = current.children[bit];
        }
        current.data = data;
    }
    public double search(int number) {
        TrieNode current = root;
        for(int i = 31; i >= 0; i--) {
            int bit = (number >> i) & 1;
            if(current.children[bit] == null) {
                return -1;
            }
            current = current.children[bit];
        }
        return current.data;
    }
}
