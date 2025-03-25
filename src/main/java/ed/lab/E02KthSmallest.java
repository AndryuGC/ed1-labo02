package ed.lab;

import java.util.Stack;

public class E02KthSmallest {
    public int kthSmallest(TreeNode<Integer> root, int k) {
        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> current = root;
        int count = 0;

        while (current != null || !stack.isEmpty()) {
            // Ir lo más a la izquierda posible
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Obtener el nodo más a la izquierda
            current = stack.pop();
            count++;

            // Si hemos llegado al k-ésimo elemento, lo retornamos
            if (count == k) {
                return current.value;
            }

            // Pasamos al subárbol derecho
            current = current.right;
        }

        return -1; // Si k es inválido (fuera del rango)
    }
}
