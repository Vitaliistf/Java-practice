package com.epam.rd.autocode.hashtableopen816;

public interface HashtableOpen8to16 {
    void insert(int key, Object value);
    Object search(int key);
    void remove (int key);
    int size();
    int[] keys();

    static HashtableOpen8to16 getInstance(){
        return new HashtableOpen8to16() {
            class Node{
                final int key;
                Object value;
                public Node(int key, Object value) {
                    this.key = key;
                    this.value = value;
                }
            }
            Node[] table = new Node[8];
            int size = 0;
            @Override
            public void insert(int key, Object value) {
                int step = 0;
                int index = hash(key, step);
                while (step < table.length) {
                    if(table[index] != null && table[index].key == key) {
                        table[index].value = value;
                        return;
                    }
                    index = hash(key, ++step);
                }

                if(size >= 16){
                    throw new IllegalStateException();
                } else if(size == table.length) {
                    changeCapacity(2);
                }

                step = 0;
                index = hash(key, step);
                while (table[index] != null) {
                    index = hash(key, ++step);
                }
                table[index] = new Node(key, value);
                size++;
            }

            @Override
            public Object search(int key) {
                int step = 0;
                int index = hash(key, step);
                while (step < table.length) {
                    if(table[index] != null && table[index].key == key) {
                        return table[index].value;
                    }
                    index = hash(key, ++step);
                }
                return null;
            }

            @Override
            public void remove(int key) {
                int step = 0;
                int index = hash(key, step);
                while (step < table.length) {
                    if(table[index] != null && table[index].key == key) {
                        table[index] = null;
                        size--;
                    }
                    index = hash(key, ++step);
                }
                if(table.length > 2 && table.length/4 >= size()) {
                    changeCapacity((double) 1/2);
                }
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public int[] keys() {
                int[] result = new int[table.length];
                for (int i = 0; i < table.length; i++) {
                    if (table[i] != null) {
                        result[i] = table[i].key;
                    } else {
                        result[i] = 0;
                    }
                }
                return result;
            }

            private void changeCapacity(double multiplier) {
                Node[] temp = table;
                table = new Node[(int) (table.length * multiplier)];
                for (Node node : temp) {
                    int step = 0;
                    if(node != null) {
                        while (table[hash(node.key, step)] != null) {
                            step++;
                        }
                        table[hash(node.key, step)] = node;
                    }
                }
            }

            private int hash(int key, int step){
                return (Math.abs(key) + step) % table.length;
            }
        };
    }
}
