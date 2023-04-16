package com.epam.rd.autocode.bstprettyprint;

public interface PrintableTree {

    void add(int i);
    String prettyPrint();

    static PrintableTree getInstance() {
        return new PrintableTree() {

            Node root;

            class Node{
                private final int value;
                private Node left;
                private Node right;

                public Node(int value) {
                    this.value = value;
                }
            }
            @Override
            public void add(int i) {
                root = insert(root, i);
            }
            private Node insert(Node root, int i) {
                if (root == null) {
                    root = new Node(i);
                } else if (i < root.value) {
                    root.left = insert(root.left, i);
                } else if (i > root.value) {
                    root.right = insert(root.right, i);
                }
                return root;
            }
            @Override
            public String prettyPrint() {
                StringBuilder result = new StringBuilder();
                String whitespaces = whitespacesByNumber(root.value);
                if (root.left != null) {
                    prettyPrint(result, false, whitespaces, root.left);
                }
                printValue(result, root);
                if (root.right != null) {
                    prettyPrint(result, true, whitespaces, root.right);
                }
                return result.toString();
            }

            private void prettyPrint(StringBuilder result, boolean isRight, String indent, Node current) {
                String whitespaces = whitespacesByNumber(current.value);
                if (current.left != null) {
                    prettyPrint(result, false, indent + (isRight ? "│" : " ") + whitespaces, current.left);
                }
                result.append(indent);
                if (isRight) {
                    result.append("└");
                } else {
                    result.append("┌");
                }
                printValue(result, current);
                if (current.right != null) {
                    prettyPrint(result, true, indent + (isRight ? " " : "│" ) + whitespaces, current.right);
                }
            }

            private void printValue(StringBuilder result, Node current) {
                result.append(current.value);
                if(current.left != null && current.right != null){
                    result.append("┤");
                } else if (current.left != null) {
                    result.append("┘");
                } else if (current.right != null) {
                    result.append("┐");
                }
                result.append("\n");
            }

            private String whitespacesByNumber(int value){
                return " ".repeat(String.valueOf(value).length());
            }
        };
    }
}
