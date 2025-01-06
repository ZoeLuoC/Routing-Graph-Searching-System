package Trie;

public class TrieNode {
    TrieNode[] children = new TrieNode[2];
    double data;

    public TrieNode() {
        this.data = -1;
    }
}
