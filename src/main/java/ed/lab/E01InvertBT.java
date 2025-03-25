package ed.lab;
//E01InvertBT
public class E01InvertBT {
    public TreeNode<Integer> invertTree(TreeNode<Integer> root) {
        if (root == null) {
            return null;
        }

        // Intercambiar los nodos hijos
        TreeNode<Integer> temp = root.left;
        root.left = root.right;
        root.right = temp;

        // Llamar recursivamente en los subárboles izquierdo y derecho
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }
}
