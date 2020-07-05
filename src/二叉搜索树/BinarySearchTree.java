package 二叉搜索树;

import java.util.*;

public class BinarySearchTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public class Node{
        int val;
        Node left;
        Node right;
        Node next;
    }
    private HashMap<Integer, Integer> map;


    /**
     * 95. 不同的二叉搜索树 II
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
     * 示例：
     * 输入：3
     * 输出：
     * [
     * [1,null,3,2],
     * [3,2,null,1],
     * [3,1,null,null,2],
     * [2,1,3],
     * [1,null,2,null,3]
     * ]
     * 解释：
     * 以上的输出对应以下 5 种不同结构的二叉搜索树：
     * <p>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     * <p>
     * 提示：
     * 0 <= n <= 8
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<>();
        }
        return generate_trees(1, n);
    }
    private List<TreeNode> generate_trees(int start, int end) {
        List<TreeNode> list = new LinkedList<>();
        if (start > end) {
            list.add(null);
            return list;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftNodes = generate_trees(start, i - 1);
            List<TreeNode> rightNodes = generate_trees(i + 1, end);
            for (TreeNode leftNode : leftNodes) {
                for (TreeNode rightNode : rightNodes) {
                    TreeNode node = new TreeNode(i);
                    node.left = leftNode;
                    node.right = rightNode;
                    list.add(node);
                }
            }
        }
        return list;
    }
    /**
     * 96. 不同的二叉搜索树
     * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
     * <p>
     * 示例:
     * <p>
     * 输入: 3
     * 输出: 5
     * 解释:
     * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
     * <p>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     */
    public int numTrees(int n) {
        int g[] = new int[n + 1];
        g[0] = 1;
        g[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                g[i] += g[j - 1] * g[i - j];
            }
        }
        return g[n];
    }
    /**
     * 98. 验证二叉搜索树
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 假设一个二叉搜索树具有如下特征：
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * <p>
     * 示例 1:
     * 输入:
     * 2
     * / \
     * 1   3
     * 输出: true
     * <p>
     * 示例 2:
     * 输入:
     * 5
     * / \
     * 1   4
     * / \
     * 3   6
     * 输出: false
     * 解释: 输入为: [5,1,4,null,null,3,6]。
     * 根节点的值为 5 ，但是其右子节点值为 4 。
     */
    public boolean isValidBST1(TreeNode root) {
        //方式一:中序遍历
        Stack<TreeNode> stack = new Stack<>();
        double inorder = -Double.MAX_VALUE;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }
    public boolean isValidBST(TreeNode root) {
        return helper(root, null, null);
    }
    private boolean helper(TreeNode root, Integer lower, Integer upper) {
        if (root == null) {
            return true;
        }
        int val = root.val;
        if (lower != null && val <= lower) {
            return false;
        }
        if (upper != null && val >= upper) {
            return false;
        }
        if (!helper(root.right, val, upper)) {
            return false;
        }
        if (!helper(root.left, lower, val)) {
            return false;
        }
        return true;
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     * <p>
     * 示例:
     * 给定有序数组: [-10,-3,0,5,9],
     * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
     * 0
     * / \
     * -3   9
     * /   /
     * -10  5
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(0, nums.length - 1, nums);
    }
    private TreeNode helper(int left, int right, int[] nums) {
        if (left > right) {
            return null;
        }
        int p = (left + right) / 2;
        if ((left + right) % 2 == 1) {
            //偶数时,随机选择左右
            p += new Random().nextInt(2);
        }
        TreeNode root = new TreeNode(nums[p]);
        root.left = helper(left, p - 1, nums);
        root.right = helper(p + 1, right, nums);
        return root;
    }
    /**
     * 114. 二叉树展开为链表
     *给定一个二叉树，原地将它展开为一个单链表。
     *
     *  
     *
     * 例如，给定二叉树
     *
     *     1
     *    / \
     *   2   5
     *  / \   \
     * 3   4   6
     * 将其展开为：
     *
     * 1
     *  \
     *   2
     *    \
     *     3
     *      \
     *       4
     *        \
     *         5
     *          \
     *           6
     */
    public void flatten(TreeNode root) {
        while (root != null) {
            if (root.left == null) {
                root = root.right;
            } else {
                TreeNode pre = root.left;
                while (pre.right != null) {
                    pre = pre.right;
                }
                pre.right = root.right;
                root.right = root.left;
                root.left = null;
                root = root.right;
            }
        }
    }
    /**
     * 116. 填充每个节点的下一个右侧节点指针
     * 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     *
     * 初始状态下，所有 next 指针都被设置为 NULL。
     *
     *  
     *
     * 示例：
     *
     *
     *
     * 输入：{"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":{"$id":"6","left":null,"next":null,"right":null,"val":6},"next":null,"right":{"$id":"7","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}
     *
     * 输出：{"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":{"$id":"6","left":null,"next":null,"right":null,"val":7},"right":null,"val":6},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"7","left":{"$ref":"5"},"next":null,"right":{"$ref":"6"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"7"},"val":1}
     *
     * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。
     *  
     *
     * 提示：
     *
     * 你只能使用常量级额外空间。
     * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
     */
    public Node connect1(Node root) {
        //层次遍历
        if (root == null) {
            return root;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Node node = queue.poll();
                if (i < len - 1) {
                    node.next = queue.peek();
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return root;
    }
    public Node connect2(Node root) {
        if (root == null) {
            return root;
        }
        Node p = root;
        while (p.left != null) {
            Node curNode = p;
            while (curNode != null) {
                curNode.left.next = curNode.right;
                if (curNode.next != null) {
                    curNode.right.next = curNode.next.left;
                }
                curNode = curNode.next;
            }
            p = p.left;
        }
        return root;
    }
    /**
     * 117. 填充每个节点的下一个右侧节点指针 II
     * 给定一个二叉树
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     *
     * 初始状态下，所有 next 指针都被设置为 NULL。
     *
     *  
     *
     * 进阶：
     *
     * 你只能使用常量级额外空间。
     * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
     *  
     *
     * 示例：
     *
     *
     *
     * 输入：root = [1,2,3,4,5,null,7]
     * 输出：[1,#,2,3,#,4,5,7,#]
     * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。
     *  
     *
     * 提示：
     *
     * 树中的节点数小于 6000
     * -100 <= node.val <= 100
     */
    public Node connect11(Node root) {
        //层次遍历
        if (root == null) {
            return root;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Node node = queue.poll();
                if (i < len - 1) {
                    node.next = queue.peek();
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return root;
    }
    /**
     * 173. 二叉搜索树迭代器
     * 实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。
     * <p>
     * 调用 next() 将返回二叉搜索树中的下一个最小的数。
     * <p>
     * <p>
     * <p>
     * 示例：
     * <p>
     * BSTIterator iterator = new BSTIterator(root);
     * iterator.next();    // 返回 3
     * iterator.next();    // 返回 7
     * iterator.hasNext(); // 返回 true
     * iterator.next();    // 返回 9
     * iterator.hasNext(); // 返回 true
     * iterator.next();    // 返回 15
     * iterator.hasNext(); // 返回 true
     * iterator.next();    // 返回 20
     * iterator.hasNext(); // 返回 false
     * <p>
     * <p>
     * <p>
     * 提示：
     * <p>
     * next() 和 hasNext() 操作的时间复杂度是 O(1)，并使用 O(h) 内存，其中 h 是树的高度。
     * 你可以假设 next() 调用总是有效的，也就是说，当调用 next() 时，BST 中至少存在一个下一个最小的数。
     */
    class BSTIterator1 {
        private Stack<TreeNode> stack;

        public BSTIterator1(TreeNode root) {
            stack = new Stack<>();
            this.leftinorder(root);
        }

        public void leftinorder(TreeNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        public int next() {
            TreeNode node = this.stack.pop();
            if (node.right != null) {
                this.leftinorder(node.right);
            }
            return node.val;
        }

        public boolean hasNext() {
            return this.stack.size() > 0;
        }
    }
    class BSTIterator {
        private List<Integer> list;
        private int index;

        public BSTIterator(TreeNode root) {
            this.list = new ArrayList<>();
            this.index = -1;
            this.inorder(root);
        }

        private void inorder(TreeNode root) {
            if (root == null) {
                return;
            }
            inorder(root.left);
            list.add(root.val);
            inorder(root.right);
        }

        public int next() {
            return this.list.get(++index);
        }

        public boolean hasNext() {
            return index + 1 < list.size();
        }
    }
    /**
     * 235. 二叉搜索树的最近公共祖先
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
     * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
     * 示例 1:
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * 输出: 6
     * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
     * 示例 2:
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * 输出: 2
     * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
     * 说明:
     *     所有节点的值都是唯一的。
     *     p、q 为不同节点且均存在于给定的二叉搜索树中。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else {
            return root;
        }
    }
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        while (node != null) {
            if (p.val > node.val && q.val > node.val) {
                node = node.right;
            } else if (p.val < node.val && q.val < node.val) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }
    /**
     * 450. 删除二叉搜索树中的节点
     * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
     *
     * 一般来说，删除节点可分为两个步骤：
     *
     *     首先找到需要删除的节点；
     *     如果找到了，删除它。
     *
     * 说明： 要求算法时间复杂度为 O(h)，h 为树的高度。
     *
     * 示例:
     *
     * root = [5,3,6,2,4,null,7]
     * key = 3
     *
     *     5
     *    / \
     *   3   6
     *  / \   \
     * 2   4   7
     *
     * 给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
     *
     * 一个正确的答案是 [5,4,6,2,null,null,7], 如下图所示。
     *
     *     5
     *    / \
     *   4   6
     *  /     \
     * 2       7
     *
     * 另一个正确答案是 [5,2,6,null,4,null,7]。
     *
     *     5
     *    / \
     *   2   6
     *    \   \
     *     4   7
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode node = root.right;
                while (node.left != null) {
                    node = node.left;
                }
                node.left = root.left;
                return root.right;
            }
        }
        return root;
    }
    /**
     * 700. 二叉搜索树中的搜索
     * 给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 NULL。
     * <p>
     * 例如，
     * <p>
     * 给定二叉搜索树:
     * <p>
     * 4
     * / \
     * 2   7
     * / \
     * 1   3
     * <p>
     * 和值: 2
     * <p>
     * 你应该返回如下子树:
     * <p>
     * 2
     * / \
     * 1   3
     * <p>
     * 在上述示例中，如果要找的值是 5，但因为没有节点值为 5，我们应该返回 NULL。
     */
    public TreeNode searchBST(TreeNode root, int val) {
        //递归
        if (root == null || root.val == val) {
            return root;
        }
        return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
    }
    public TreeNode searchBST1(TreeNode root, int val) {
        //迭代
        while (root != null && root.val != val) {
            root = val < root.val ? root.left : root.right;
        }
        return root;
    }
    /**
     * 701. 二叉搜索树中的插入操作
     * 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 保证原始二叉搜索树中不存在新值。
     * <p>
     * 注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回任意有效的结果。
     * <p>
     * 例如,
     * <p>
     * 给定二叉搜索树:
     * <p>
     * 4
     * / \
     * 2   7
     * / \
     * 1   3
     * <p>
     * 和 插入的值: 5
     * <p>
     * 你可以返回这个二叉搜索树:
     * <p>
     * 4
     * /   \
     * 2     7
     * / \   /
     * 1   3 5
     * <p>
     * 或者这个树也是有效的:
     * <p>
     * 5
     * /   \
     * 2     7
     * / \
     * 1   3
     * \
     * 4
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        //递归
        if (root == null) {
            return new TreeNode(val);
        }
        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }
    public TreeNode insertIntoBST1(TreeNode root, int val) {
        TreeNode node = root;
        while (node != null) {
            if (val < node.val) {
                if (node.left == null) {
                    node.left = new TreeNode(val);
                    return root;
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new TreeNode(val);
                    return root;
                }
                node = node.right;
            }
        }
        return new TreeNode(val);
    }

    public static void main(String[] args) {
        new TreeSet<>().ceiling(3);
    }
}
