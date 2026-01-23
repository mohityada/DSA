
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
    private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int sp, int ep, int si, int ei, HashMap<Integer, Integer> inorderMap){
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

    private void dfsToCalculateParent(TreeNode root, HashMap<TreeNode, TreeNode> parent){
        if(root == null) return;

        if(root.left != null) {
            parent.put(root.left, root);
            dfsToCalculateParent(root.left, parent);
        }
        if(root.right != null){
            parent.put(root.right, root);
            dfsToCalculateParent(root.right, parent);
        }
        return;
    }

    private Set<TreeNode> pathFromNodeToRoot(HashMap<TreeNode, TreeNode> parent, TreeNode node){
        Set<TreeNode> pathFromNodeToRoot = new HashSet<>();
        pathFromNodeToRoot.add(node);
        while(parent.get(node) != null){
            pathFromNodeToRoot.add(parent.get(node));
            node = parent.get(node);
        }
        return pathFromNodeToRoot;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        HashMap<Integer, Integer> inorderMap = new HashMap<>();
        for(int i = 0; i < n; i++){
            inorderMap.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, inorder, 0, n-1, 0, n-1, inorderMap);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        HashMap<TreeNode, TreeNode> parent = new HashMap<>();
        parent.put(root, null);
        dfsToCalculateParent(root, parent);
        
        Set<TreeNode> pathToRootFromP = pathFromNodeToRoot(parent, p);
        
        TreeNode temp = q;
        while(!pathToRootFromP.contains(temp)){
            temp = parent.get(temp);
        }
        
        return temp;
    }

    private int maxSum = Integer.MIN_VALUE;
    
    private int maxPathSumHelper(TreeNode root){
        if(root == null) return Integer.MIN_VALUE/4;

        int L = maxPathSumHelper(root.left);
        int R = maxPathSumHelper(root.right);
        
        maxSum = Math.max(maxSum, Math.max(L, R));
        
        if(L < 0  && R < 0) {
            maxSum = Math.max(maxSum, root.val);
            return root.val;
        }

        else if(L < 0){
            maxSum = Math.max(maxSum, root.val + R);
            return root.val + R;
        } else if(R < 0){
            maxSum = Math.max(maxSum, root.val + L);
            return root.val + L;
        }
        
        maxSum = Math.max(maxSum, root.val + L + R);
        return  Math.max(L, R) + root.val;
    }

    public int maxPathSum(TreeNode root) {
        maxPathSumHelper(root);
        return maxSum;   
    }

}