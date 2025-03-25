package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {

    private static class AVLNode<T> {
        T value;
        AVLNode<T> left, right;
        int height;

        AVLNode(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private AVLNode<T> root;
    private final Comparator<T> comparator;
    private int size;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.size = 0;
    }

    // Metodo de inserción con balanceo
    public void insert(T value) {
        root = insert(root, value);
    }

    private AVLNode<T> insert(AVLNode<T> node, T value) {
        if (node == null) {
            size++;
            return new AVLNode<>(value);
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // No permitir valores duplicados
        }

        return balance(node);
    }

    // Metodo de eliminacion con balanceo
    public void delete(T value) {
        root = delete(root, value);
    }

    private AVLNode<T> delete(AVLNode<T> node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            AVLNode<T> min = findMin(node.right);
            node.value = min.value;
            node.right = delete(node.right, min.value);
        }

        return balance(node);
    }

    // Metodo de búsqueda
    public T search(T value) {
        AVLNode<T> node = search(root, value);
        return node != null ? node.value : null;
    }

    private AVLNode<T> search(AVLNode<T> node, T value) {
        if (node == null) return null;
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) return search(node.left, value);
        if (cmp > 0) return search(node.right, value);
        return node;
    }

    // Métodos auxiliares
    public int height() {
        return getHeight(root);
    }

    public int size() {
        return size;
    }

    private int getHeight(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(AVLNode<T> node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    // Rotaciones
    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y;
    }

    // Balanceo del árbol
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node == null) return null;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int balanceFactor = getBalanceFactor(node);

        // Caso Izquierda-Izquierda
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }

        // Caso Izquierda-Derecha
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso Derecha-Derecha
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Caso Derecha-Izquierda
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
