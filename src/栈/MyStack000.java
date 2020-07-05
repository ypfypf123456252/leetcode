package 栈;

import 数组.Array;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyStack000 {
    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     * <p>
     * 示例 1:
     * 输入: "()"
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: "()[]{}"
     * 输出: true
     * <p>
     * 示例 3:
     * 输入: "(]"
     * 输出: false
     * <p>
     * 示例 4:
     * 输入: "([)]"
     * 输出: false
     * <p>
     * 示例 5:
     * 输入: "{[]}"
     * 输出: true
     */
    class Solution {
        private HashMap<Character, Character> hashMap;
        private Stack<Character> stack = new Stack<>();

        public Solution() {
            hashMap = new HashMap<>();
            hashMap.put(')', '(');
            hashMap.put(']', '[');
            hashMap.put('}', '{');
        }

        public boolean isValid(String s) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (hashMap.containsKey(c)) {
                    char topElement = stack.isEmpty() ? '#' : stack.pop();
                    if (topElement != this.hashMap.get(c)) {
                        return false;
                    }
                } else {
                    stack.push(c);
                }
            }
            return stack.isEmpty();
        }
    }
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        Stack<Character> stack = new Stack<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                char topElement = stack.isEmpty() ? '#' : stack.pop();
                if (topElement != map.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
    /**
     * 32. 最长有效括号
     *
     * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
     *
     * 示例 1:
     *
     * 输入: "(()"
     * 输出: 2
     * 解释: 最长有效括号子串为 "()"
     *
     * 示例 2:
     *
     * 输入: ")()())"
     * 输出: 4
     * 解释: 最长有效括号子串为 "()()"
     */
    public int longestValidParentheses(String s) {
        //栈
        int ans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    ans = Math.max(ans, i - stack.peek());
                }
            }
        }
        return ans;
    }
    /**
     * 42. 接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     *
     * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
     *
     * 示例:
     * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出: 6
     */
    public int trap1(int[] height) {
        //暴力法,效率低,非栈
        int ans = 0;
        int len = height.length;
        for (int i = 1; i < len - 1; i++) {
            int max_left = 0;
            int max_right = 0;
            for (int j = i; j >= 0; j--) {
                max_left = Math.max(max_left, height[j]);
            }
            for (int j = i; j < len; j++) {
                max_right = Math.max(max_right, height[j]);
            }
            ans += Math.min(max_left, max_right) - height[i];
        }
        return ans;
    }
    public int trap(int[] height) {
        int ans = 0;
        int current = 0;
        Deque<Integer> stack = new LinkedList<>();
        while (current < height.length) {
            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                int top = stack.poll();
                if (stack.isEmpty()) {
                    break;
                }
                int len = current - stack.peek() - 1;
                int hei = Math.min(height[current], height[stack.peek()]) - height[top];
                ans += len * hei;
            }
            stack.push(current++);
        }
        return ans;
    }
    /**
     * 84. 柱状图中最大的矩形
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     *
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     *
     * 以上是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 [2,1,5,6,2,3]。
     *
     * 图中阴影部分为所能勾勒出的最大矩形面积，其面积为 10 个单位。
     *
     * 示例:
     * 输入: [2,1,5,6,2,3]
     * 输出: 10
     */
    public int largestRectangleArea1(int[] heights) {
        int n = heights.length;
        int left[] = new int[n];
        int right[] = new int[n];
        Arrays.fill(right, n);
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                right[stack.poll()] = i;
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int left[] = new int[n];
        int right[] = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.poll();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.poll();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }
    /**
     * 155. 最小栈
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     * push(x) —— 将元素 x 推入栈中。
     * pop() —— 删除栈顶的元素。
     * top() —— 获取栈顶元素。
     * getMin() —— 检索栈中的最小元素。
     * <p>
     * 示例:
     * 输入：
     * ["MinStack","push","push","push","getMin","pop","top","getMin"]
     * [[],[-2],[0],[-3],[],[],[],[]]
     * 输出：
     * [null,null,null,null,-3,null,0,-2]
     * 解释：
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();      --> 返回 0.
     * minStack.getMin();   --> 返回 -2.
     * <p>
     * 提示：
     * pop、top 和 getMin 操作总是在 非空栈 上调用。
     */
    class MinStack {
        private Stack<Integer> data;//数据栈
        private Stack<Integer> helper;//辅助栈

        public MinStack() {
            data = new Stack<>();
            helper = new Stack<>();
        }

        public void push(int x) {
            data.push(x);
            if (helper.isEmpty() || x < helper.peek()) {
                helper.push(x);
            } else {
                helper.push(helper.peek());
            }
        }

        public void pop() {
            if (!data.isEmpty()) {
                data.pop();
                helper.pop();
            }
        }

        public int top() {
            if (!data.isEmpty()) {
                return data.peek();
            }
            throw new RuntimeException("栈元素为空,操作非法");
        }

        public int getMin() {
            if (!helper.isEmpty()) {
                return helper.peek();
            }
            throw new RuntimeException("栈元素为空,操作非法");
        }

    }
    /**
     * 225. 用队列实现栈
     * 使用队列实现栈的下列操作：
     * push(x) -- 元素 x 入栈
     * pop() -- 移除栈顶元素
     * top() -- 获取栈顶元素
     * empty() -- 返回栈是否为空
     * 注意:
     * 你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
     * 你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
     * 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
     */
    class MyStack1 {
        //方式一: 一个队列实现
        private Queue<Integer> queue1;
        private Queue<Integer> queue2;
        private int top;

        public MyStack1() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        public void push(int x) {
            queue1.add(x);
            top = x;
        }

        public int pop() {
            int res;
            while (queue1.size() > 1) {
                top = queue1.remove();
                queue2.add(top);
            }
            res = queue1.remove();
            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = temp;
            return res;
        }

        public int top() {
            return top;
        }

        public boolean empty() {
            return queue1.isEmpty();
        }
    }
    class MyStack {
        private Queue<Integer> queue;

        public MyStack() {
            queue = new LinkedList<>();
        }

        public void push(int x) {
            queue.add(x);
            int size = queue.size();
            while (size > 1) {
                int temp = queue.remove();
                queue.add(temp);
                size--;
            }
        }

        public int pop() {
            return queue.remove();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }
    /**
     * 232. 用栈实现队列
     * 使用栈实现队列的下列操作：
     * push(x) -- 将一个元素放入队列的尾部。
     * pop() -- 从队列首部移除元素。
     * peek() -- 返回队列首部的元素。
     * empty() -- 返回队列是否为空。
     * 示例:
     * MyQueue queue = new MyQueue();
     * queue.push(1);
     * queue.push(2);
     * queue.peek();  // 返回 1
     * queue.pop();   // 返回 1
     * queue.empty(); // 返回 false
     * <p>
     * 说明:
     * 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
     * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
     * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
     */
    class MyQueue {
        private Stack<Integer> stack1;
        private Stack<Integer> stack2;
        private int front;

        public MyQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void push(int x) {
            if (stack1.isEmpty()) {
                front = x;
            }
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            stack2.push(x);
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }

        public int pop() {
            int temp = stack1.pop();
            if (!stack1.isEmpty()) {
                front = stack1.peek();
            }
            return temp;
        }

        public int peek() {
            return front;
        }

        public boolean empty() {
            return stack1.isEmpty();
        }
    }
    /**
     * 496. 下一个更大元素 I
     * 给定两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
     * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。
     * <p>
     * 示例 1:
     * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
     * 输出: [-1,3,-1]
     * 解释:
     * 对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
     * 对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
     * 对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。
     * <p>
     * 示例 2:
     * 输入: nums1 = [2,4], nums2 = [1,2,3,4].
     * 输出: [3,-1]
     * 解释:
     * 对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
     * 对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
     * <p>
     * 提示：
     * nums1和nums2中所有元素是唯一的。
     * nums1和nums2 的数组大小都不超过1000。
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        int res[] = new int[nums1.length];
        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && nums2[i] > stack.peek()) {
                map.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]);
        }
        while (!stack.isEmpty()) {
            map.put(stack.pop(), -1);
        }
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }
    /**
     * 739. 每日温度
     * 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
     * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
     * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
     */
    public int[] dailyTemperatures(int[] T) {
        Deque<Integer> stack = new LinkedList<>();
        int len = T.length;
        int ans[] = new int[len];
        for (int i = 0; i < T.length; i++) {
            int temp = T[i];
            while (!stack.isEmpty() && temp > T[stack.peek()]) {
                Integer popTemp = stack.pop();
                ans[popTemp] = i - popTemp;
            }
            stack.push(i);
        }
        return ans;
    }
    /**
     * 1021. 删除最外层的括号
     * 有效括号字符串为空 ("")、"(" + A + ")" 或 A + B，其中 A 和 B 都是有效的括号字符串，+ 代表字符串的连接。例如，""，"()"，"(())()" 和 "(()(()))" 都是有效的括号字符串。
     * 如果有效字符串 S 非空，且不存在将其拆分为 S = A+B 的方法，我们称其为原语（primitive），其中 A 和 B 都是非空有效括号字符串。
     * 给出一个非空有效字符串 S，考虑将其进行原语化分解，使得：S = P_1 + P_2 + ... + P_k，其中 P_i 是有效括号字符串原语。
     * 对 S 进行原语化分解，删除分解中每个原语字符串的最外层括号，返回 S 。
     * <p>
     * 示例 1：
     * 输入："(()())(())"
     * 输出："()()()"
     * 解释：
     * 输入字符串为 "(()())(())"，原语化分解得到 "(()())" + "(())"，
     * 删除每个部分中的最外层括号后得到 "()()" + "()" = "()()()"。
     * <p>
     * 示例 2：
     * 输入："(()())(())(()(()))"
     * 输出："()()()()(())"
     * 解释：
     * 输入字符串为 "(()())(())(()(()))"，原语化分解得到 "(()())" + "(())" + "(()(()))"，
     * 删除每个部分中的最外层括号后得到 "()()" + "()" + "()(())" = "()()()()(())"。
     * <p>
     * 示例 3：
     * 输入："()()"
     * 输出：""
     * 解释：
     * 输入字符串为 "()()"，原语化分解得到 "()" + "()"，
     * 删除每个部分中的最外层括号后得到 "" + "" = ""。
     * <p>
     * 提示：
     * S.length <= 10000
     * S[i] 为 "(" 或 ")"
     * S 是一个有效括号字符串
     */
    public String removeOuterParentheses(String S) {
        StringBuilder builder = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int left = 0;
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            builder.append(c);
            if (c == ')') {
                if (stack.peek() == '(') {
                    stack.pop();
                    if (stack.isEmpty()) {
                        builder.deleteCharAt(left);
                        builder.deleteCharAt(builder.length() - 1);
                        left = builder.length();
                    }
                }
            } else {
                stack.push(c);
            }
        }
        return builder.toString();
    }
    /**
     * 1441. 用栈操作构建数组
     * 给你一个目标数组 target 和一个整数 n。每次迭代，需要从  list = {1,2,3..., n} 中依序读取一个数字。
     * 请使用下述操作来构建目标数组 target ：
     * Push：从 list 中读取一个新元素， 并将其推入数组中。
     * Pop：删除数组中的最后一个元素。
     * 如果目标数组构建完成，就停止读取更多元素。
     * 题目数据保证目标数组严格递增，并且只包含 1 到 n 之间的数字。
     * 请返回构建目标数组所用的操作序列。
     * 题目数据保证答案是唯一的。
     * <p>
     * 示例 1：
     * 输入：target = [1,3], n = 3
     * 输出：["Push","Push","Pop","Push"]
     * 解释：
     * 读取 1 并自动推入数组 -> [1]
     * 读取 2 并自动推入数组，然后删除它 -> [1]
     * 读取 3 并自动推入数组 -> [1,3]
     * <p>
     * 示例 2：
     * 输入：target = [1,2,3], n = 3
     * 输出：["Push","Push","Push"]
     * <p>
     * 示例 3：
     * 输入：target = [1,2], n = 4
     * 输出：["Push","Push"]
     * 解释：只需要读取前 2 个数字就可以停止。
     * <p>
     * 示例 4：
     * 输入：target = [2,3,4], n = 4
     * 输出：["Push","Pop","Push","Push","Push"]
     * <p>
     * 提示：
     * 1 <= target.length <= 100
     * 1 <= target[i] <= 100
     * 1 <= n <= 100
     * target 是严格递增的
     */
    public List<String> buildArray(int[] target, int n) {
        List<String> list = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < target.length; i++) {
            while (target[i] > j) {
                list.add("Push");
                list.add("Pop");
                j++;
            }
            list.add("Push");
            j++;
        }
        return list;
    }
    /**
     * 1047. 删除字符串中的所有相邻重复项
     * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
     * 在 S 上反复执行重复项删除操作，直到无法继续删除。
     * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
     * <p>
     * 示例：
     * 输入："abbaca"
     * 输出："ca"
     * 解释：
     * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
     * <p>
     * 提示：
     * 1 <= S.length <= 20000
     * S 仅由小写英文字母组成。
     */
    public String removeDuplicates(String S) {
        StringBuilder builder = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int left = -1;
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (!stack.isEmpty() && c == stack.peek()) {
                stack.pop();
                builder.deleteCharAt(left);
                left--;
            } else {
                stack.push(c);
                builder.append(c);
                left++;
            }
        }
        return builder.toString();
    }
    /**
     * 146. LRU缓存机制
     * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
     * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
     * 写入数据 put(key, value) - 如果密钥已经存在，则变更其数据值；如果密钥不存在，则插入该组「密钥/数据值」。
     * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     *
     * 进阶:
     * 你是否可以在 O(1) 时间复杂度内完成这两种操作？
     */
    class LRUCache {
        class Node{
            int key;
            int value;
            Node pre;
            Node next;
        }

        private void addNode(Node node) {
            node.pre = head;
            node.next = head.next;

            head.next.pre = node;
            head.next = node;
        }

        private void removeNode(Node node) {
            Node pre = node.pre;
            Node next = node.next;

            pre.next = next;
            next.pre = pre;
        }

        private void moveToHead(Node node) {
            removeNode(node);
            addNode(node);
        }

        private Node popTailNode() {
            Node res = tail.pre;
            removeNode(res);
            return res;
        }

        private Hashtable<Integer, Node> map = new Hashtable<>();
        private int capacity;
        private int size;
        private Node head;
        private Node tail;
        public LRUCache(int capacity) {
            this.size = 0;
            this.capacity = capacity;
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
        }

        public int get(int key) {
            Node node = map.get(key);
            if (node == null) {
                return -1;
            }
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            Node node = map.get(key);
            if (node == null) {
                Node newNode = new Node();
                newNode.key = key;
                newNode.value = value;

                map.put(key, newNode);
                addNode(newNode);

                ++size;

                if (size > capacity) {
                    Node tail = popTailNode();
                    map.remove(tail.key);
                    --size;
                }
            } else {
                node.value = value;
                moveToHead(node);
            }
        }
    }

    public static void main(String[] args) {
        int cap =7;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n+1);
        System.out.println(Integer.highestOneBit(7));
//        HashMap
//        ConcurrentHashMap
    }
}