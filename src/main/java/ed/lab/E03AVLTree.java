package ed.lab;
import java.util.Comparator;

public class E03AVLTree<T> {
    private class AVLNode {
        T value;
        AVLNode left, right;
        int height;

        AVLNode(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private AVLNode root;
    private final Comparator<T> comparator;
    private int size;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.size = 0;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private AVLNode insert(AVLNode node, T value) {
        if (node == null) {
            size++;
            return new AVLNode(value);
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // Valor ya existe, no se inserta
        }

        return balance(node);
    }

    public void delete(T value) {
        if (search(value) != null) { // Verifica si el nodo existe antes de eliminar
            root = delete(root, value);
            size--; // Disminuir size solo si el nodo fue encontrado y eliminado
        }
    }

    private AVLNode delete(AVLNode node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            // Nodo encontrado
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Buscar el mínimo del subárbol derecho
            AVLNode minLargerNode = getMin(node.right);
            node.value = minLargerNode.value;
            node.right = delete(node.right, minLargerNode.value);
        }

        return balance(node);
    }


    public T search(T value) {
        AVLNode node = search(root, value);
        return (node != null) ? node.value : null;
    }

    private AVLNode search(AVLNode node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) return search(node.left, value);
        if (cmp > 0) return search(node.right, value);
        return node;
    }

    public int height() {
        return getHeight(root);
    }

    private int getHeight(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    public int size() {
        return size;
    }

    private AVLNode balance(AVLNode node) {
        if (node == null) return null;

        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getBalanceFactor(AVLNode node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }


    private AVLNode getMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}

