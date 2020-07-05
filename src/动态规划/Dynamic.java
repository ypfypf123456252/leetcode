package 动态规划;

import java.util.Arrays;

public class Dynamic {
    /**
     * 5. 最长回文子串
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     *
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     *
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     */
    public static String longestPalindrome(String s) {
        int n = s.length();
        boolean dp[][] = new boolean[n][n];
        String ans = "";
        for (int l = 0; l < n; l++) {
            for (int i = 0; i + l < n; i++) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1];
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, j + 1);
                }
            }
        }
        return ans;
    }
    /**
     * 10. 正则表达式匹配
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     *
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     *
     * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
     *
     * 说明:
     *
     *     s 可能为空，且只包含从 a-z 的小写字母。
     *     p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
     *
     * 示例 1:
     *
     * 输入:
     * s = "aa"
     * p = "a"
     * 输出: false
     * 解释: "a" 无法匹配 "aa" 整个字符串。
     *
     * 示例 2:
     *
     * 输入:
     * s = "aa"
     * p = "a*"
     * 输出: true
     * 解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
     *
     * 示例 3:
     *
     * 输入:
     * s = "ab"
     * p = ".*"
     * 输出: true
     * 解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
     *
     * 示例 4:
     *
     * 输入:
     * s = "aab"
     * p = "c*a*b"
     * 输出: true
     * 解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
     *
     * 示例 5:
     *
     * 输入:
     * s = "mississippi"
     * p = "mis*is*p*."
     * 输出: false
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean dp[][] = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2];
                    if (match(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    if (match(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[m][n];
    }
    private boolean match(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
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
        //动态规划
        int ans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if ((i - dp[i - 1] > 0 && s.charAt(i - 1 - dp[i - 1]) == '(')) {
                    dp[i] = dp[i - 1] + (i - dp[i - 1] >= 2 ? dp[i - 2 - dp[i - 1]] : 0) + 2;
                }
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }
    /**
     * 53. 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 示例:
     * <p>
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * <p>
     * 进阶:
     * int pre = 0;
     * int maxnum = nums[0];
     * for (int num : nums) {
     * pre = Math.max(num, pre + num);
     * maxnum = Math.max(maxnum, pre);
     * }
     * return maxnum;
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     */
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int num : nums) {
            pre = Math.max(pre + num, num);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
    /**
     * 62. 不同路径
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 问总共有多少条不同的路径？
     *
     * 例如，上图是一个7 x 3 的网格。有多少可能的路径？
     *
     *
     *
     * 示例 1:
     *
     * 输入: m = 3, n = 2
     * 输出: 3
     * 解释:
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向右 -> 向下
     * 2. 向右 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向右
     *
     * 示例 2:
     *
     * 输入: m = 7, n = 3
     * 输出: 28
     *
     * 提示：
     *     1 <= m, n <= 100
     *     题目数据保证答案小于等于 2 * 10 ^ 9
     */
    public int uniquePaths1(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
    public int uniquePaths2(int m, int n){
        int pre[] = new int[n];
        int cur[] = new int[n];
        Arrays.fill(pre, 1);
        Arrays.fill(cur, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                cur[j] = cur[j - 1] + pre[j];
            }
            pre = cur.clone();
        }
        return pre[n - 1];
    }
    public int uniquePaths3(int m, int n){
        int cur[] = new int[n];
        Arrays.fill(cur, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                cur[j] += cur[j - 1];
            }
        }
        return cur[n - 1];
    }
    /**
     * 63. 不同路径 II
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     *
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。
     *
     * 说明：m 和 n 的值均不超过 100。
     *
     * 示例 1:
     *
     * 输入:
     * [
     *   [0,0,0],
     *   [0,1,0],
     *   [0,0,0]
     * ]
     * 输出: 2
     * 解释:
     * 3x3 网格的正中间有一个障碍物。
     * 从左上角到右下角一共有 2 条不同的路径：
     * 1. 向右 -> 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右 -> 向右
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int R = obstacleGrid.length;
        int C = obstacleGrid[0].length;
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        obstacleGrid[0][0] = 1;
        for (int i = 1; i < R; i++) {
            obstacleGrid[i][0] = (obstacleGrid[i][0] == 0 && obstacleGrid[i - 1][0] == 1) ? 1 : 0;
        }
        for (int i = 1; i < C; i++) {
            obstacleGrid[0][i] = (obstacleGrid[0][i] == 0 && obstacleGrid[0][i - 1] == 1) ? 1 : 0;
        }
        for (int i = 1; i < R; i++) {
            for (int j = 1; j < C; j++) {
                if (obstacleGrid[i][j] == 1) {
                    obstacleGrid[i][j] = 0;
                } else {
                    obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
                }
            }
        }
        return obstacleGrid[R - 1][C - 1];
    }
    /**
     * 64. 最小路径和
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     *
     * 示例:
     *
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     */
    public int minPathSum1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int dp[][] = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j != n - 1) {
                    dp[i][j] = dp[i][j + 1] + grid[i][j];
                } else if (i != m - 1 && j == n - 1) {
                    dp[i][j] = dp[i + 1][j] + grid[i][j];
                } else if (i != m - 1 && j != n - 1) {
                    dp[i][j] = grid[i][j] + Math.min(dp[i][j + 1], dp[i + 1][j]);
                } else {
                    dp[i][j] = grid[i][j];
                }
            }
        }
        return dp[0][0];
    }
    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j != n - 1) {
                    dp[j] = dp[j + 1] + grid[i][j];
                } else if (i != m - 1 && j == n - 1) {
                    dp[j] = dp[j] + grid[i][j];
                } else if (i != m - 1 && j != n - 1) {
                    dp[j] = Math.min(dp[j + 1], dp[j]) + grid[i][j];
                } else {
                    dp[j] = grid[i][j];
                }
            }
        }
        return dp[0];
    }
    public int minPathSum3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j != n - 1) {
                    grid[i][j] = grid[i][j + 1] + grid[i][j];
                } else if (i != m - 1 && j == n - 1) {
                    grid[i][j] = grid[i + 1][j] + grid[i][j];
                } else if (i != m - 1 && j != n - 1) {
                    grid[i][j] = Math.min(grid[i + 1][j], grid[i][j + 1]) + grid[i][j];
                }
            }
        }
        return grid[0][0];
    }
    /**
     * 70. 爬楼梯XXX
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶
     *
     * 示例 2：
     * 输入： 3
     * 输出： 3
     * 解释： 有三种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶 + 1 阶
     * 2.  1 阶 + 2 阶
     * 3.  2 阶 + 1 阶
     */
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int dp[] = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
    /**
     * 91. 解码方法
     * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     *
     * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
     *
     * 示例 1:
     *
     * 输入: "12"
     * 输出: 2
     * 解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
     *
     * 示例 2:
     *
     * 输入: "226"
     * 输出: 3
     * 解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.charAt(0) == '0') {
            return 0;
        }
        int len = s.length();
        int dp[] = new int[len];
        dp[0] = 1;
        for (int i = 1; i < len; i++) {
            if (s.charAt(i) != '0') {
                dp[i] = dp[i - 1];
            }
            int sum = 10 * (s.charAt(i - 1) - '0') + s.charAt(i) - '0';
            if (sum >= 10 && sum <= 26) {
                if (i == 1) {
                    dp[i]++;
                } else {
                    dp[i] += dp[i - 2];
                }
            }
        }
        return dp[len - 1];
    }
    /**
     * 96. 不同的二叉搜索树
     * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
     *
     * 示例:
     *
     * 输入: 3
     * 输出: 5
     * 解释:
     * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
     *
     *    1         3     3      2      1
     *     \       /     /      / \      \
     *      3     2     1      1   3      2
     *     /     /       \                 \
     *    2     1         2                 3
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
     * 152. 乘积最大子数组
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     *
     *
     *
     * 示例 1:
     *
     * 输入: [2,3,-2,4]
     * 输出: 6
     * 解释: 子数组 [2,3] 有最大乘积 6。
     *
     * 示例 2:
     *
     * 输入: [-2,0,-1]
     * 输出: 0
     * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
     */
    public int maxProduct(int[] nums) {
        int maxF = nums[0], minF = nums[0], ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int mx = maxF;
            int mn = minF;
            maxF = Math.max(mx * nums[i], Math.max(mn * nums[i], nums[i]));
            minF = Math.min(mn * nums[i], Math.min(mx * nums[i], nums[i]));
            ans = Math.max(maxF, ans);
        }
        return ans;
    }
    /**
     * 198. 打家劫舍
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * <p>
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * <p>
     * 示例 2：
     * <p>
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     * <p>
     * <p>
     * <p>
     * 提示：
     * <p>
     * 0 <= nums.length <= 100
     * 0 <= nums[i] <= 400
     */
    public int rob1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int dp[] = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[n - 1];
    }
    public int rob2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        return second;
    }
    /**
     * 213. 打家劫舍 II
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
     *
     * 示例 1:
     *
     * 输入: [2,3,2]
     * 输出: 3
     * 解释: 你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     *
     * 示例 2:
     *
     * 输入: [1,2,3,1]
     * 输出: 4
     * 解释: 你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     *      偷窃到的最高金额 = 1 + 3 = 4 。
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        return Math.max(myRob(Arrays.copyOfRange(nums, 0, n - 1)), myRob(Arrays.copyOfRange(nums, 1, n)));
    }
    private int myRob(int[] nums) {
        int pre = 0, cur = 0, temp = 0;
        for (int num : nums) {
            temp = cur;
            cur = Math.max(pre + num, cur);
            pre = temp;
        }
        return cur;
    }
    /**
     * 718. 最长重复子数组
     *
     * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
     *
     * 示例 1:
     *
     * 输入:
     * A: [1,2,3,2,1]
     * B: [3,2,1,4,7]
     * 输出: 3
     * 解释:
     * 长度最长的公共子数组是 [3, 2, 1]。
     *
     * 说明:
     *
     *     1 <= len(A), len(B) <= 1000
     *     0 <= A[i], B[i] < 100
     */
    public int findLength(int[] A, int[] B) {
        int m = A.length, n = B.length;
        int dp[][] = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = A.length - 1; i >= 0; i--) {
            for (int j = B.length - 1; j >= 0; j--) {
                dp[i][j] = A[i] == B[j] ? dp[i + 1][j + 1] + 1 : 0;
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }
    /**
     * 746. 使用最小花费爬楼梯
     * 数组的每个索引作为一个阶梯，第 i个阶梯对应着一个非负数的体力花费值 cost[i](索引从0开始)。
     *
     * 每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。
     *
     * 您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。
     *
     * 示例 1:
     *
     * 输入: cost = [10, 15, 20]
     * 输出: 15
     * 解释: 最低花费是从cost[1]开始，然后走两步即可到阶梯顶，一共花费15。
     *
     *  示例 2:
     *
     * 输入: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
     * 输出: 6
     * 解释: 最低花费方式是从cost[0]开始，逐个经过那些1，跳过cost[3]，一共花费6。
     *
     * 注意：
     *
     *     cost 的长度将会在 [2, 1000]。
     *     每一个 cost[i] 将会是一个Integer类型，范围为 [0, 999]。
     */
    public int minCostClimbingStairs1(int[] cost) {
        int n = cost.length;
        int dp[] = new int[n];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < n; i++) {
            dp[i] = Math.min(cost[i] + dp[i - 2], cost[i] + dp[i - 1]);
        }
        return Math.min(dp[n - 2], dp[n - 1]);
    }
    public int minCostClimbingStairs(int[] cost) {
        int f1 = 0, f2 = 0;
        for (int i = 0; i < cost.length; i++) {
            int f0 = Math.min(cost[i] + f1, cost[i] + f2);
            f2 = f1;
            f1 = f0;
        }
        return Math.min(f1, f2);
    }
    /**
     * 1025. 除数博弈
     * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
     *
     * 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作：
     *
     *     选出任一 x，满足 0 < x < N 且 N % x == 0 。
     *     用 N - x 替换黑板上的数字 N 。
     *
     * 如果玩家无法执行这些操作，就会输掉游戏。
     *
     * 只有在爱丽丝在游戏中取得胜利时才返回 True，否则返回 false。假设两个玩家都以最佳状态参与游戏。
     *
     *
     *
     * 示例 1：
     *
     * 输入：2
     * 输出：true
     * 解释：爱丽丝选择 1，鲍勃无法进行操作。
     *
     * 示例 2：
     *
     * 输入：3
     * 输出：false
     * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
     *
     *
     *
     * 提示：
     *
     *     1 <= N <= 1000
     */
    public boolean divisorGame(int N) {
        if (N == 1) {
            return false;
        }
        boolean dp[] = new boolean[N + 1];
        dp[1] = false;
        dp[2] = true;
        for (int i = 3; i <= N; i++) {
            dp[i] = false;
            for (int j = 1; j < i; j++) {
                if (i % j == 0 && dp[i - j] == false) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[N];
    }

    /**
     * 面试题 08.01. 三步问题
     * 三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模 1000000007。
     *
     * 示例1:
     *
     *  输入：n = 3
     *  输出：4
     *  说明: 有四种走法
     *
     * 示例2:
     *
     *  输入：n = 5
     *  输出：13
     *
     * 提示:
     *
     *     n范围在[1, 1000000]之间
     */
    public int waysToStep1(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int dp[] = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for (int i = 4; i < n + 1; i++) {
            dp[i] = ((dp[i - 1] + dp[i - 2]) % 1000000007 + dp[i - 3]) % 1000000007;
        }
        return dp[n];
    }
    public int waysToStep(int n) {
        if (n < 3) {
            return n;
        }
        int one = 1;
        int two = 1;
        int three = 2;
        int curr = 0;
        for (int i = 3; i < n + 1; i++) {
            curr = ((one + two) % 1000000007 + three) % 1000000007;
            one = two;
            two = three;
            three = curr;
        }
        return curr;
    }
    /**
     * 面试题 16.17. 连续数列
     * 给定一个整数数组，找出总和最大的连续数列，并返回总和。
     *
     * 示例：
     *
     * 输入： [-2,1,-3,4,-1,2,1,-5,4]
     * 输出： 6
     * 解释： 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * 进阶：
     *
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     */
    public int maxSubArray1(int[] nums) {
        int pre = 0;
        int ans = nums[0];
        for (int num : nums) {
            pre = Math.max(pre + num, num);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
    /**
     * 面试题 17.16. 按摩师
     * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
     *
     * 注意：本题相对原题稍作改动
     *
     * 示例 1：
     *
     * 输入： [1,2,3,1]
     * 输出： 4
     * 解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。
     *
     * 示例 2：
     *
     * 输入： [2,7,9,3,1]
     * 输出： 12
     * 解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。
     *
     * 示例 3：
     *
     * 输入： [2,1,4,5,3,1,1,3]
     * 输出： 12
     * 解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。
     */
    public int massage1(int[] nums) {
        //自己完成的
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        if (n == 3) {
            return Math.max(nums[0] + nums[2], nums[1]);
        }
        int dp[] = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        dp[2] = Math.max(nums[0] + nums[2], nums[1]);
        for (int i = 3; i < n; i++) {
            dp[i] = Math.max(dp[i - 3] + nums[i], dp[i - 2] + nums[i]);
        }
        return Math.max(dp[n - 1], dp[n - 2]);
    }
    public int massage(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int dp0 = 0, dp1 = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int tempDp0 = Math.max(dp0, dp1);
            int tempDp1 = dp0 + nums[i];
            dp0 = tempDp0;
            dp1 = tempDp1;
        }
        return Math.max(dp0, dp1);
    }
    /**
     * 面试题42. 连续子数组的最大和
     * 输入一个整型数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
     * <p>
     * 要求时间复杂度为O(n)。
     * <p>
     * <p>
     * <p>
     * 示例1:
     * <p>
     * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * <p>
     * <p>
     * <p>
     * 提示：
     * <p>
     * 1 <= arr.length <= 10^5
     * -100 <= arr[i] <= 100
     * <p>
     * 注意：本题与主站 53 题相同：https://leetcode-cn.com/problems/maximum-subarray/
     */
    public int maxSubArray2(int[] nums) {
        int pre = 0;
        int ans = nums[0];
        for (int num : nums) {
            pre = Math.max(pre + num, num);
            ans = Math.max(ans, pre);
        }
        return ans;
    }
}
