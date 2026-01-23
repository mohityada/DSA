
 import java.util.*;

 public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
 }
 
class Solution {
    public TreeNode buildTreeHelper(int[] preorder, int[] inorder, int sp, int ep, int si, int ei, HashMap<Integer, Integer> inorderMap){
        if(sp > ep || si > ei) return null;
        int val = preorder[sp];
        TreeNode root = new TreeNode(val);
        int leftSP = sp + 1;
        int indexOfRootInorder = inorderMap.get(val);
        int elementsInLeftSubTree = indexOfRootInorder - si;
        int leftEP = leftSP + elementsInLeftSubTree - 1;
        int rightSP = leftEP + 1;
        int rightEP = ep;
        int leftSI = si;
        int leftEI = leftSI + elementsInLeftSubTree - 1;
        int rightSI = indexOfRootInorder + 1;
        int rightEI = ei;

        TreeNode leftSubTree = buildTreeHelper(preorder, inorder, leftSP, leftEP, leftSI, leftEI, inorderMap); 
        TreeNode rightSubTree = buildTreeHelper(preorder, inorder, rightSP, rightEP, rightSI, rightEI, inorderMap);

        root.left = leftSubTree;
        root.right = rightSubTree;
        
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        HashMap<Integer, Integer> inorderMap = new HashMap<>();
        for(int i = 0; i < n; i++){
            inorderMap.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, inorder, 0, n-1, 0, n-1, inorderMap);
    }
}