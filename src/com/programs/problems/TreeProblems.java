package com.programs.problems;


import com.programs.trees.BinaryTree;
import com.programs.trees.ITree;

import java.util.*;

public class TreeProblems {


    public static void main(String args[]) throws Exception {

      BinaryTree<Integer> node = new BinaryTree<>(8);
      node.add(10);
      node.add(5);
      node.add(1);

      node.getRightTree().setLeftTree(new BinaryTree<>(2));
      iterativeInorder(node);

    }

    /**
     * do iterative inorder tyraversal of binary tree
     * @param root
     * @param <T>
     */
    public static <T extends Comparable> void iterativeInorder(BinaryTree<T> root){

        if(root == null) return;
        Stack<BinaryTree> st = new Stack<>();
        leftTraversal(root, st);
        BinaryTree<T> node = null;
        while(!st.isEmpty()){
            node = st.pop();
            System.out.print(node.getData()+" ");
            if(node.getRightTree()!=null){
                leftTraversal(node.getRightTree(), st);
            }
        }
    }

    /*
     Traverse left of a binary tree and put it in Stack
     */
    private static <T extends Comparable> void leftTraversal(BinaryTree<T> root, Stack<BinaryTree> st) {
        while (root != null) {
            st.push(root);
            root = root.getLeftTree();
        }
    }

    /**
     * Iteratuve post order of Binary tree using two stack
     * @param tree
     * @param <T>
     */
    public static <T extends Comparable> void  iterativePostOrder(BinaryTree<T> tree){

        com.programs.stack.Stack<BinaryTree> stack = new com.programs.stack.Stack<>(10);
        stack.push(tree);
        com.programs.stack.Stack<BinaryTree> stack2 = new com.programs.stack.Stack<>(10);
        BinaryTree<T> temp;
        while(!stack.isEmpty()){
             temp = stack.pop();
             if(temp.getLeftTree()!=null){
                 stack.push(temp.getLeftTree());
             }
             if(temp.getRightTree()!=null){
                 stack.push(temp.getRightTree());
             }
             stack2.push(temp);
        }

        while(!stack2.isEmpty()){
            System.out.print(stack2.pop().getData()+" ");
        }
    }

    /**
     * https://www.geeksforgeeks.org/count-bst-nodes-that-are-in-a-given-range/
     * Given a binary search tree and a range count number
     * of nodes between them as per inorder traversal
     *
     * @param node
     * @param min
     * @param max
     * @return
     */
    static int countNodesBetween(ITree<Integer> node, int min, int max) {
        if (node == null) {
            return 0;
        }
        if (node.getData() >= min && node.getData() <= max) {
            return 1 + countNodesBetween(node.getLeftTree(), min, max);
        } else if (node.getData() < min) {
            return countNodesBetween(node.getRightTree(), min, max);
        } else return countNodesBetween(node.getLeftTree(), min, max);
    }

    /**
     * serialize a binary tree into string
     *
     * @param root
     * @return
     */
    public String serialize(Node root) {
        StringBuilder str = new StringBuilder();
        serialize(root, str);
        return str.toString();

    }

    public void serialize(Node root, StringBuilder str) {
        if (root == null) {
            str.append("n,");
            return;
        }
        str.append(root.data);
        str.append(",");
        serialize(root.left, str);
        serialize(root.right, str);
    }

    /**
     * deserialize a binary tree from given string
     *
     * @param data
     * @return
     */
    public Node deserialize(String data) {
        String[] arr = data.split(",");
        int[] index = new int[1];
        return deserialize(arr, index);
    }

    public Node deserialize(String[] arr, int[] index) {
        if (index[0] > arr.length) {
            return null;
        }

        String str = arr[index[0]];
        index[0]++;
        if (str.equals("n")) {
            return null;
        }

        Node node = new Node(Integer.valueOf(str));
        node.left = deserialize(arr, index);
        node.right = deserialize(arr, index);
        return node;
    }

    /**
     * https://www.geeksforgeeks.org/find-minimum-depth-of-a-binary-tree/
     *
     * @param root
     * @return
     */
    int minDepth(Node root) {
        // Your code here

        Result res = new Result();
        minDepth(root, res, 1);
        return res.val;
    }

    void minDepth(Node root, Result res, int level) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            res.val = Math.min(res.val, level);
            return;
        }

        minDepth(root.left, res, level + 1);

        minDepth(root.right, res, level + 1);
    }

    class Result {
        int val = Integer.MAX_VALUE;
    }

    /**
     * do a zigzag traversal of a tree and return every level as list
     *
     * @param node
     * @return
     */
    public static ArrayList<ArrayList<Integer>> zigzagLevelOrder(Node node) {
        if (node == null) return null;
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();

        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(node);
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            ArrayList<Integer> li = new ArrayList<>();
            while (!stack1.isEmpty()) {
                Node temp = stack1.pop();
                li.add(temp.data);
                if (temp.left != null) {
                    stack2.push(temp.left);
                }
                if (temp.right != null) {
                    stack2.push(temp.right);
                }

            }

            if (!li.isEmpty()) {
                list.add(li);
            }
            li = new ArrayList<>();

            while (!stack2.isEmpty()) {
                Node temp = stack2.pop();
                li.add(temp.data);
                if (temp.right != null) {
                    stack1.push(temp.right);
                }
                if (temp.left != null) {
                    stack1.push(temp.left);
                }
            }
            if (!li.isEmpty()) {
                list.add(li);
            }


        }

        return list;

    }

    static ArrayList<ArrayList> printSpiral(Node node) {
        if (node == null)
            return null; // NULL check

        ArrayList<ArrayList> list = new ArrayList<>();
        // Create two stacks to store alternate levels
        // For levels to be printed from right to left
        Stack<Node> s1 = new Stack<Node>();
        // For levels to be printed from left to right
        Stack<Node> s2 = new Stack<Node>();

        // Push first level to first stack 's1'
        s1.push(node);

        // Keep printing while any of the stacks has some nodes
        while (!s1.empty() || !s2.empty()) {
            // Print nodes of current level from s1 and push nodes of
            // next level to s2
            ArrayList<Integer> li = new ArrayList<>();
            while (!s1.empty()) {
                Node temp = s1.pop();
                li.add(temp.data);

                // Note that is right is pushed before left
                if (temp.left != null)
                    s2.push(temp.left);

                if (temp.right != null)
                    s2.push(temp.right);
            }

            list.add(li);
            li = new ArrayList<>();
            // Print nodes of current level from s2 and push nodes of
            // next level to s1
            while (!s2.empty()) {
                Node temp = s2.pop();
                li.add(temp.data);

                // Note that is left is pushed before right
                if (temp.right != null)
                    s1.push(temp.right);
                if (temp.left != null)
                    s1.push(temp.left);
            }
            list.add(li);
            char[] arr = new char[2];
            new String(arr);
        }
        return list;


    }

    /**
     * class used to print bottom view of a tree
     */
    static class Pair {
        int data;
        int height;
    }

    /**
     * https://www.geeksforgeeks.org/bottom-view-binary-tree/
     *
     * @param root
     */
    public static void bottomView(Node root) {
        // Your code here
        TreeMap<Integer, Pair> map = new TreeMap<>();
        printBottom(root, map, 0, 0);
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Pair key = map.get(iterator.next());
            System.out.print(key.data + " ");
        }


    }

    private static void printBottom(Node root, TreeMap<Integer, Pair> map, int height, int hDis) {
        if (root == null) return;
        if (!map.containsKey(hDis)) {
            Pair pair = new Pair();
            pair.data = root.data;
            pair.height = height;
            map.put(hDis, pair);
        } else {
            Pair pair = map.get(hDis);
            if (pair.height <= height) {
                pair.height = height;
                pair.data = root.data;
            }

        }

        printBottom(root.left, map, height + 1, hDis - 1);
        printBottom(root.right, map, height + 1, hDis + 1);
    }

    /**
     * https://leetcode.com/problems/check-completeness-of-a-binary-tree/
     *
     * @param root
     * @return
     */
    public static boolean isCompleteTree(Node root) {
        if (root == null) return true;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        boolean flag = false;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left != null) {

                if (flag) {
                    return false;
                }
                queue.add(node.left);
            } else {
                flag = true;
            }

            if (node.right != null) {

                if (flag) {
                    return false;
                }
                queue.add(node.right);
            } else {
                flag = true;
            }

        }

        return true;
    }


    /*
    https://www.geeksforgeeks.org/find-count-of-singly-subtrees/
     */
    static int countUniValue(Node node) {
        Count count = new Count();
        countUniValue(node, count);
        return count.res;
    }

    static class Count {
        int res;
    }

    /*
    https://www.geeksforgeeks.org/find-count-of-singly-subtrees/
     */
    private static boolean countUniValue(Node node, Count count) {
        if (node == null) return true;
        boolean left = countUniValue(node.left, count);
        boolean right = countUniValue(node.right, count);
        if (left && right && (node.left == null || (node.left != null && node.data == node.left.data))
                && (node.right == null || (node.right != null && node.data == node.right.data))) {
            count.res++;
            return true;
        }
        return false;
    }

    /**
     * vertical level traversal of binary tree.
     *
     * @param root
     * @return
     */
    static public List<List<Integer>> verticalTraversal(Node root) {
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        preOrder(root, 0, map);
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> li = entry.getValue();
            Collections.sort(li);
            list.add(li);
        }
        return list;
    }


    static void preOrder(Node root, int dis, TreeMap<Integer, List<Integer>> map) {
        if (root == null) return;
        List<Integer> list = map.get(dis);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(root.data);
        map.put(dis, list);
        preOrder(root.left, dis - 1, map);
        preOrder(root.right, dis + 1, map);
    }

    /**
     * check if two nodes are cousin ie at same level but not same parent
     * https://www.geeksforgeeks.org/check-two-nodes-cousins-binary-tree/
     *
     * @param root
     * @param x
     * @param y
     * @return
     */
    public static boolean ifCousin(Node root, Node x, Node y) {
        //Your Code here.
        if (root == null) return false;
        List<Node> pathx = new ArrayList<>();
        getPath(root, x, pathx);
        List<Node> pathy = new ArrayList<>();
        getPath(root, y, pathy);
        if (pathx.size() != pathy.size()) return false;
        if (pathx.isEmpty() || pathy.isEmpty()) return false;
        return pathx.get(pathx.size() - 1) != pathy.get(pathy.size() - 1);
    }

    /**
     * get path of a node from root
     *
     * @param root
     * @param x
     * @param path
     * @return
     */
    static boolean getPath(Node root, Node x, List<Node> path) {
        if (root == null) {
            return false;
        }
        if (root == x) return true;
        path.add(root);
        if (getPath(root.left, x, path)) {
            return true;
        } else if (getPath(root.right, x, path)) {
            return true;
        }
        if (path.size() > 0)
            path.remove(path.size() - 1);

        return false;
    }

    /*
      check if tree has root to path with given sum
     */
    static void hasPathSum(Node node, int sum) {
        // Your code here
        ArrayList<Integer> list = new ArrayList<>();
        hasPathSum(node, sum, list);

    }

    static void hasPathSum(Node node, int sum, ArrayList<Integer> list) {
        if (node == null) return;
        list.add(node.data);
        if (node.left == null && node.right == null && node.data == sum) {
            for (Integer i : list) System.out.print(i + " ");
            System.out.println();
        }
        hasPathSum(node.right, sum - node.data, list);
        hasPathSum(node.left, sum - node.data, list);
        if (!list.isEmpty())
            list.remove(list.size() - 1);
    }


    public static int[][] levelOrder(Node A) {

        int i = 0;
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        doTraversal(A, lists, 0);
        int arr[][] = new int[lists.size()][];
        for (ArrayList<Integer> list : lists) {
            int[] temp = new int[list.size()];
            int k = 0;
            for (Integer integer : list) {
                temp[k] = integer;
                k++;
            }
            arr[i] = temp;
            i++;
        }

        return arr;
    }

    private static void doTraversal(Node a, ArrayList<ArrayList<Integer>> lists, int depth) {
        if (a == null) return;
        if (depth == lists.size()) {
            ArrayList<Integer> temp = new ArrayList<>();
            lists.add(temp);
        }
        ArrayList<Integer> temp = lists.get(depth);
        temp.add(a.data);
        doTraversal(a.left, lists, depth + 1);
        doTraversal(a.right, lists, depth + 1);
    }

}

class Node {
    int data;
    Node left;
    Node right;

    Node(int x) {
        data = x;
        left = null;
        right = null;
    }
}