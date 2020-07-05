package 树;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Tree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private Map<Integer,Integer> map;
    /**
     * 94. 二叉树的中序遍历
     * 给定一个二叉树，返回它的中序 遍历。
     * <p>
     * 示例:
     * <p>
     * 输入: [1,null,2,3]
     * 1
     * \
     * 2
     * /
     * 3
     * <p>
     * 输出: [1,3,2]
     */
    public List<Integer> inorderTraversal01(TreeNode root) {
        //方法一:递归
        List<Integer> list = new ArrayList<>();
        List<Integer> res = helper(root, list);
        return res;
    }
    private List<Integer> helper(TreeNode root, List<Integer> list) {
        if (root == null) {
            return list;
        }
        if (root.left != null) {
            helper(root.left, list);
        }
        list.add(root.val);
        if (root.right != null) {
            helper(root.right, list);
        }
        return list;
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        //方法二:栈实现
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                list.add(root.val);
                root = root.right;
            }
        }
        return list;
    }
    /**
     * 100. 相同的树
     * 给定两个二叉树，编写一个函数来检验它们是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     * <p>
     * 示例 1:
     * 输入:       1         1
     * / \       / \
     * 2   3     2   3
     * <p>
     * [1,2,3],   [1,2,3]
     * <p>
     * 输出: true
     * <p>
     * 示例 2:
     * 输入:      1          1
     * /           \
     * 2             2
     * <p>
     * [1,2],     [1,null,2]
     * <p>
     * 输出: false
     * <p>
     * 示例 3:
     * 输入:       1         1
     * / \       / \
     * 2   1     1   2
     * <p>
     * [1,2,1],   [1,1,2]
     * <p>
     * 输出: false
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    /**
     * 101. 对称二叉树
     * 给定一个二叉树，检查它是否是镜像对称的。
     * <p>
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     * 1
     * / \
     * 2   2
     * / \ / \
     * 3  4 4  3
     * <p>
     * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
     * 1
     * / \
     * 2   2
     * \   \
     * 3    3
     */
    public boolean isSymmetric1(TreeNode root) {
        return isMirror(root, root);
    }
    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        return t1.val == t2.val &&
                isMirror(t1.left, t2.right) &&
                isMirror(t1.right, t2.left);
    }
    public boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            if (t1 == null && t2 == null) {
                continue;
            }
            if (t1 == null || t2 == null) {
                return false;
            }
            if (t1.val != t2.val) {
                return false;
            }
            queue.add(t1.left);
            queue.add(t2.right);
            queue.add(t1.right);
            queue.add(t2.left);
        }
        return true;
    }
    /**
     * 102. 二叉树的层序遍历
     * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
     * <p>
     * 示例：
     * 二叉树：[3,9,20,null,null,15,7],
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p>
     * 返回其层次遍历结果：
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size > 0) {
                root = queue.poll();
                size--;
                list.add(root.val);
                if (root.left != null) {
                    queue.offer(root.left);
                }
                if (root.right != null) {
                    queue.offer(root.right);
                }
            }
            lists.add(list);
        }
        return lists;
    }
    /**
     * 104. 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p>
     * 返回它的最大深度 3 。
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * 根据一棵树的前序遍历与中序遍历构造二叉树。
     *
     * 注意:
     * 你可以假设树中没有重复的元素。
     *
     * 例如，给出
     *
     * 前序遍历 preorder = [3,9,20,15,7]
     * 中序遍历 inorder = [9,3,15,20,7]
     *
     * 返回如下的二叉树：
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return myBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    private TreeNode myBuildTree(int[] preorder, int preLeft, int preRight, int[] inorder, int inLeft, int inRight) {
        if (preLeft > preRight) {
            return null;
        }
        int pre_root = preLeft;
        int in_root = map.get(preorder[pre_root]);
        int pre_left_len = in_root - inLeft;
        TreeNode root = new TreeNode(inorder[in_root]);
        root.left = myBuildTree(preorder, preLeft + 1, preLeft + pre_left_len, inorder, inLeft, in_root - 1);
        root.right = myBuildTree(preorder, preLeft + pre_left_len + 1, preRight, inorder, in_root + 1, inRight);
        return root;
    }
    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int index = 0;
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = stack.peek();
            if (node.val != inorder[index]) {
                node.left = new TreeNode(preorder[i]);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[index]) {
                    node = stack.pop();
                    index++;
                }
                node.right = new TreeNode(preorder[i]);
                stack.push(node.right);
            }
        }
        return root;
    }
    /**
     * 106. 从中序与后序遍历序列构造二叉树
     * 根据一棵树的中序遍历与后序遍历构造二叉树。
     *
     * 注意:
     * 你可以假设树中没有重复的元素。
     *
     * 例如，给出
     *
     * 中序遍历 inorder = [9,3,15,20,7]
     * 后序遍历 postorder = [9,15,7,20,3]
     * 返回如下的二叉树：
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     */
    public TreeNode buildTree11(int[] inorder, int[] postorder) {
        if (postorder == null || postorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[postorder.length - 1]);
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int index = postorder.length - 1;
        for (int i = postorder.length - 2; i >= 0; i--) {
            TreeNode node = stack.peek();
            if (node.val != inorder[index]) {
                node.right = new TreeNode(postorder[i]);
                stack.push(node.right);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[index]) {
                    node = stack.pop();
                    index--;
                }
                node.left = new TreeNode(postorder[i]);
                stack.push(node.left);
            }
        }
        return root;
    }
    /**
     * 107. 二叉树的层次遍历 II
     * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     * <p>
     * 例如：
     * 给定二叉树 [3,9,20,null,null,15,7],
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 返回其自底向上的层次遍历为：
     * [
     * [15,7],
     * [9,20],
     * [3]
     * ]
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            lists.add(0, list);
        }
        return lists;
    }
    /**
     * 112. 路径总和
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例:
     * 给定如下二叉树，以及目标和 sum = 22，
     * <p>
     * 5
     * / \
     * 4   8
     * /   / \
     * 11  13  4
     * /  \      \
     * 7    2      1
     * <p>
     * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        sum -= root.val;
        if (root.left == null && root.right == null) {
            return sum == 0;
        }
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
    }
    public boolean hasPathSum1(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        LinkedList<TreeNode> node_stack = new LinkedList();
        LinkedList<Integer> sum_stack = new LinkedList();
        node_stack.add(root);
        sum_stack.add(sum - root.val);
        TreeNode node;
        int curr_sum;
        while (!node_stack.isEmpty()) {
            node = node_stack.pollLast();
            curr_sum = sum_stack.pollLast();
            if ((node.right == null) && (node.left == null) && (curr_sum == 0))
                return true;

            if (node.right != null) {
                node_stack.add(node.right);
                sum_stack.add(curr_sum - node.right.val);
            }
            if (node.left != null) {
                node_stack.add(node.left);
                sum_stack.add(curr_sum - node.left.val);
            }
        }
        return false;
    }
    /**
     * 144. 二叉树的前序遍历
     * 给定一个二叉树，返回它的 前序 遍历。
     * <p>
     * 示例:
     * <p>
     * 输入: [1,null,2,3]
     * 1
     * \
     * 2
     * /
     * 3
     * <p>
     * 输出: [1,2,3]
     */
    public List<Integer> preorderTraversal01(TreeNode root) {
        //方法一:递归
        List<Integer> list = new ArrayList<>();
        List<Integer> res = helper1(root, list);
        return res;
    }
    private List<Integer> helper1(TreeNode root, List<Integer> list) {
        if (root != null) {
            list.add(root.val);
            if (root.left != null) {
                helper1(root.left, list);
            }
            if (root.right != null) {
                helper1(root.right, list);
            }
        }
        return list;
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        //方法二:栈
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                list.add(root.val);
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                root = root.right;
            }
        }
        return list;
    }
    /**
     * 145. 二叉树的后序遍历
     * 给定一个二叉树，返回它的 后序 遍历。
     * <p>
     * 示例:
     * 输入: [1,null,2,3]
     * 1
     * \
     * 2
     * /
     * 3
     * <p>
     * 输出: [3,2,1]
     */
    public List<Integer> postorderTraversal02(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        List<Integer> res = helper2(root, list);
        return res;
    }
    private List<Integer> helper2(TreeNode root, List<Integer> list) {
        if (root != null) {
            if (root.left != null) {
                helper2(root.left, list);
            }
            if (root.right != null) {
                helper2(root.right, list);
            }
            list.add(root.val);
        }
        return list;
    }
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) {
            return list;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            list.add(curr.val);
            if (curr.left != null) {
                stack.push(curr.left);
            }
            if (curr.right != null) {
                stack.push(curr.right);
            }
        }
        Collections.reverse(list);
        return list;
    }
    public List<Integer> postorderTraversal002(TreeNode root) {
        //左右中->中右左
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        while (root != null || !stack1.isEmpty()) {
            while (root != null) {
                stack2.push(root);
                stack1.push(root);
                root = root.right;
            }
            if (!stack1.isEmpty()) {
                root = stack1.pop();
                root = root.left;
            }
        }
        while (!stack2.isEmpty()) {
            root = stack2.pop();
            list.add(root.val);
        }
        return list;
    }
    /**
     * 222. 完全二叉树的节点个数
     * 给出一个完全二叉树，求出该树的节点个数。
     * <p>
     * 说明：
     * <p>
     * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。
     * <p>
     * 示例:
     * <p>
     * 输入:
     * 1
     * / \
     * 2   3
     * / \  /
     * 4  5 6
     * <p>
     * 输出: 6
     */
    public int countNodes1(TreeNode root) {
        return root == null ? 0 : countNodes1(root.left) + countNodes1(root.right) + 1;
    }
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int d = computeDepth(root);
        if (d == 0) {
            return 1;
        }
        int left = 1, right = (int) (Math.pow(2, d) - 1);
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (exists(mid, d, root)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return (int) (Math.pow(2, d) - 1 + left);
    }
    private int computeDepth(TreeNode node) {
        int d = 0;
        while (node.left != null) {
            node = node.left;
            d++;
        }
        return d;
    }
    private boolean exists(int idx, int d, TreeNode node) {
        int left = 0, right = (int) Math.pow(2, d) - 1;
        for (int i = 0; i < d; i++) {
            int mid = left + (right - left) / 2;
            if (idx <= mid) {
                right = mid;
                node = node.left;
            } else {
                left = mid + 1;
                node = node.right;
            }
        }
        return node != null;
    }
    /**
     * 226. 翻转二叉树
     * 翻转一棵二叉树。
     *
     * 示例：
     *
     * 输入：
     *
     *      4
     *    /   \
     *   2     7
     *  / \   / \
     * 1   3 6   9
     * 输出：
     *
     *      4
     *    /   \
     *   7     2
     *  / \   / \
     * 9   6 3   1
     * 备注:
     * 这个问题是受到 Max Howell 的 原问题 启发的 ：
     *
     * 谷歌：我们90％的工程师使用您编写的软件(Homebrew)，但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            TreeNode currNode = queue.poll();
            TreeNode tempNode = currNode.left;
            currNode.left = currNode.right;
            currNode.right = tempNode;
            if (currNode.left != null) {
                queue.add(currNode.left);
            }
            if (currNode.right != null) {
                queue.add(currNode.right);
            }
        }
        return root;
    }
    /**
     * 236. 二叉树的最近公共祖先
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     *
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     *
     * 例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4]
     *
     *
     *
     * 示例 1:
     *
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * 输出: 3
     * 解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
     *
     * 示例 2:
     *
     * 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * 输出: 5
     * 解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
     *
     *
     *
     * 说明:
     *
     *     所有节点的值都是唯一的。
     *     p、q 为不同节点且均存在于给定的二叉树中。
     */
    private TreeNode ans;
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return ans;
    }
    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        boolean lson = dfs(root.left, p, q);
        boolean rson = dfs(root.right, p, q);
        if ((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))) {
            ans = root;
        }
        return lson || rson || root.val == p.val || root.val == q.val;
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) {
            return right;
        } else {
            if (right == null) {
                return left;
            } else {
                return root;
            }
        }
    }
    /**
     * 257. 二叉树的所有路径
     * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
     *
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例:
     *
     * 输入:
     *
     *    1
     *  /   \
     * 2     3
     *  \
     *   5
     *
     * 输出: ["1->2->5", "1->3"]
     *
     * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
     */
    public List<String> binaryTreePaths(TreeNode root) {
        LinkedList<String> paths = new LinkedList<>();
        construct_paths(root, "", paths);
        return paths;
    }
    private void construct_paths(TreeNode root, String path, LinkedList<String> paths) {
        if (root != null) {
            path += String.valueOf(root.val);
            if (root.left == null && root.right == null) {
                paths.add(path);
            } else {
                path += "->";
                if (root.left != null) {
                    construct_paths(root.left, path, paths);
                }
                if (root.right != null) {
                    construct_paths(root.right, path, paths);
                }
            }
        }
    }
    public List<String> binaryTreePaths1(TreeNode root) {
        List<String> paths = new LinkedList<>();
        if (root == null) {
            return paths;
        }
        Stack<TreeNode> nodeStack = new Stack<TreeNode>();
        Stack<String> pathStack = new Stack<>();
        nodeStack.add(root);
        pathStack.add(String.valueOf(root.val));
        TreeNode node;
        String path;
        while (nodeStack.size() > 0) {
            node = nodeStack.pop();
            path = pathStack.pop();
            if (node.left == null && node.right == null) {
                paths.add(path);
            }
            if (node.left != null) {
                nodeStack.add(node.left);
                pathStack.add(path + "->" + node.left.val);
            }
            if (node.right != null) {
                nodeStack.add(node.right);
                pathStack.add(path + "->" + node.right.val);
            }
        }
        return paths;
    }
    /**
     * 530. 二叉搜索树的最小绝对差
     * 给你一棵所有节点为非负值的二叉搜索树，请你计算树中任意两节点的差的绝对值的最小值。
     *
     *
     *
     * 示例：
     *
     * 输入：
     *
     *    1
     *     \
     *      3
     *     /
     *    2
     *
     * 输出：
     * 1
     *
     * 解释：
     * 最小绝对差为 1，其中 2 和 1 的差的绝对值为 1（或者 2 和 3）。
     *
     *
     *
     * 提示：
     *
     *     树中至少有 2 个节点。
     *     本题与 783 https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/ 相同
     */
    public int getMinimumDifference(TreeNode root) {
        TreeNode preNode = null;
        int ans = Integer.MAX_VALUE;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                ans = preNode == null ? ans : Math.min(ans, root.val - preNode.val);
                preNode = root;
                root = root.right;
            }
        }
        return ans;
    }
    /**
     * 606. 根据二叉树创建字符串
     * 你需要采用前序遍历的方式，将一个二叉树转换成一个由括号和整数组成的字符串。
     *
     * 空节点则用一对空括号 "()" 表示。而且你需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
     *
     * 示例 1:
     *
     * 输入: 二叉树: [1,2,3,4]
     *        1
     *      /   \
     *     2     3
     *    /
     *   4
     *
     * 输出: "1(2(4))(3)"
     *
     * 解释: 原本将是“1(2(4)())(3())”，
     * 在你省略所有不必要的空括号对之后，
     * 它将是“1(2(4))(3)”。
     *
     * 示例 2:
     *
     * 输入: 二叉树: [1,2,3,null,4]
     *        1
     *      /   \
     *     2     3
     *      \
     *       4
     *
     * 输出: "1(2()(4))(3)"
     *
     * 解释: 和第一个示例相似，
     * 除了我们不能省略第一个对括号来中断输入和输出之间的一对一映射关系。
     */
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        } else if (t.left == null && t.right == null) {
            return t.val + "";
        } else if (t.right == null) {
            return t.val + "(" + tree2str(t.left) + ")";
        } else {
            return t.val + "(" + tree2str(t.left)+")(" + tree2str(t.right) + ")";
        }
    }
    public String tree2str1(TreeNode t) {
        if (t == null) {
            return "";
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Set<TreeNode> set = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        stack.push(t);
        while (!stack.isEmpty()) {
            t = stack.peek();
            if (set.contains(t)) {
                stack.pop();
                builder.append(")");
            } else {
                set.add(t);
                builder.append("(" + t.val);
                if (t.left == null && t.right != null) {
                    builder.append("()");
                }
                if (t.right != null) {
                    stack.add(t.right);
                }
                if (t.left != null) {
                    stack.add(t.left);
                }
            }
        }
        return builder.substring(1, builder.length() - 1);
    }
    /**
     * 637. 二叉树的层平均值
     * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组.
     * <p>
     * 示例 1:
     * 输入:
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 输出: [3, 14.5, 11]
     * 解释:
     * 第0层的平均值是 3,  第1层是 14.5, 第2层是 11. 因此返回 [3, 14.5, 11].
     * <p>
     * 注意：
     * 节点值的范围在32位有符号整数范围内。
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Queue<TreeNode> temp = new LinkedList<>();
            double sum = 0, count = 0;
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                sum += node.val;
                count++;
                if (node.left != null) {
                    temp.add(node.left);
                }
                if (node.right != null) {
                    temp.add(node.right);
                }
            }
            queue = temp;
            list.add(sum * 1.0 / count);
        }
        return list;
    }
    public List<Double> averageOfLevels1(TreeNode root) {
        List<Double> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            double sum = 0;
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                TreeNode node = queue.remove();
                sum += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            list.add(sum / len);
        }
        return list;
    }

}