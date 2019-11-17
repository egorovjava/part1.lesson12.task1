package com.gmail.egorovsonalexey;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private Node<T> root;

    Tree(T rootData) {
        root = new Node<>(rootData);
    }

    public Node<T> getRoot() {
        return root;
    }

    public static class Node<T> {

        Node(T d) {
            data = d;
            children = new ArrayList<>();
        }

        private T data;
        private Node<T> parent;
        private List<Node<T>> children;

        Node<T> addChild(T d) {
            Node<T> newNode = new Node<>(d);
            children.add(newNode);
            return newNode;
        }

        public List<Node<T>> getChildren() {
            return children;
        }

        public T getData() {
            return data;
        }

        public boolean hasChildren() {
            return children.size() > 0;
        }
    }
}
