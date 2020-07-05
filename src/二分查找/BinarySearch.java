package 二分查找;


import java.util.PriorityQueue;

public class BinarySearch {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
     *
     * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     *
     * 你可以假设 nums1 和 nums2 不会同时为空。
     *
     * 示例 1:
     * nums1 = [1, 3]
     * nums2 = [2]
     * 则中位数是 2.0
     *
     * 示例 2:
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * 则中位数是 (2 + 3)/2 = 2.5
     *
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        int n = n1 + n2;
        if (n % 2 == 1) {
            return getKthElement(nums1, nums2, n / 2 + 1);
        } else {
            return (getKthElement(nums1, nums2, n / 2) + getKthElement(nums1, nums2, n / 2 + 1)) / 2.0;
        }
    }
    private int getKthElement(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length, len2 = nums2.length;
        int index1 = 0, index2 = 0;
        while (true) {
            if (index1 == len1) {
                return nums2[index2 + k - 1];
            } else if (index2 == len2) {
                return nums1[index1 + k - 1];
            } else if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }
            int half = k / 2;
            int newindex1 = Math.min(index1 + half, len1) - 1;
            int newindex2 = Math.min(index2 + half, len2) - 1;
            int profit1 = nums1[newindex1], profit2 = nums2[newindex2];
            if (profit1 <= profit2) {
                k -= newindex1 - index1 + 1;
                index1 = newindex1 + 1;
            } else {
                k -= newindex2 - index2 + 1;
                index2 = newindex2 + 1;
            }
        }
    }
    /**
     * 29. 两数相除
     * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
     * 返回被除数 dividend 除以除数 divisor 得到的商。
     * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
     *
     * 示例 1:
     * 输入: dividend = 10, divisor = 3
     * 输出: 3
     * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
     *
     * 示例 2:
     * 输入: dividend = 7, divisor = -3
     * 输出: -2
     * 解释: 7/-3 = truncate(-2.33333..) = -2
     *
     * 提示：
     * 被除数和除数均为 32 位有符号整数。
     * 除数不为 0。
     * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。本题中，如果除法结果溢出，则返回 231 − 1。
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        boolean k = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0);
        dividend = -Math.abs(dividend);
        divisor = -Math.abs(divisor);
        int result = 0;
        while (dividend <= divisor) {
            int temp = divisor;
            int c = 1;
            while (dividend - temp <= temp) {
                temp = temp << 1;
                c = c << 1;
            }
            result += c;
            dividend -= temp;
        }
        return k ? result : -result;
    }
    /**
     * 33. 搜索旋转排序数组
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
     * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     * 你可以假设数组中不存在重复的元素。
     * 你的算法时间复杂度必须是 O(log n) 级别。
     *
     * 示例 1:
     * 输入: nums = [4,5,6,7,0,1,2], target = 0
     * 输出: 4
     *
     * 示例 2:
     * 输入: nums = [4,5,6,7,0,1,2], target = 3
     * 输出: -1
     */
    public int search1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                if (target >= nums[0] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[n - 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
    public int search12(int[] nums, int target) {
        //优于上面一种
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     * 你的算法时间复杂度必须是 O(log n) 级别。
     * 如果数组中不存在目标值，返回 [-1, -1]。
     *
     * 示例 1:
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: [3,4]
     *
     * 示例 2:
     * 输入: nums = [5,7,7,8,8,10], target = 6
     * 输出: [-1,-1]
     */
    public int[] searchRange(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] <= target) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        int right = i;
        if (j < 0 || nums[j] != target) {
            return new int[]{-1, -1};
        }
        i=0;j=nums.length-1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] < target) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        int left = j;
        return new int[]{left + 1, right - 1};
    }
    /**
     * 69. x 的平方根
     * 实现 int sqrt(int x) 函数。
     * 计算并返回 x 的平方根，其中 x 是非负整数。
     * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     *
     * 示例 1:
     * 输入: 4
     * 输出: 2
     *
     * 示例 2:
     * 输入: 8
     * 输出: 2
     * 说明: 8 的平方根是 2.82842...,
     *      由于返回类型是整数，小数部分将被舍去。
     */
    public int mySqrt(int x) {
        int l = 0, r = x, ans = 0;
        while (l <= r) {
            int mid = l + ((r-l) >> 1);
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }
    /**
     * 74. 搜索二维矩阵
     * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
     *
     * 每行中的整数从左到右按升序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * 示例 1:
     *
     * 输入:
     * matrix = [
     *   [1,   3,  5,  7],
     *   [10, 11, 16, 20],
     *   [23, 30, 34, 50]
     * ]
     * target = 3
     * 输出: true
     * 示例 2:
     *
     * 输入:
     * matrix = [
     *   [1,   3,  5,  7],
     *   [10, 11, 16, 20],
     *   [23, 30, 34, 50]
     * ]
     * target = 13
     * 输出: false
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) {
            return false;
        }
        int n = matrix[0].length;
        int left = 0, right = m * n - 1;
        int mid, midEle;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            midEle = matrix[mid / n][mid % n];
            if (midEle == target) {
                return true;
            } else if (midEle < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
    /**
     * 81. 搜索旋转排序数组 II
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     *
     * ( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。
     *
     * 编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。
     *
     * 示例 1:
     *
     * 输入: nums = [2,5,6,0,0,1,2], target = 0
     * 输出: true
     * 示例 2:
     *
     * 输入: nums = [2,5,6,0,0,1,2], target = 3
     * 输出: false
     * 进阶:
     *
     * 这是 搜索旋转排序数组 的延伸题目，本题中的 nums  可能包含重复元素。
     * 这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？
     */
    public boolean search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        if (n == 1) {
            return nums[0] == target;
        }
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[left] == nums[mid]) {
                left++;
                continue;
            }
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }
    /**
     * 153. 寻找旋转排序数组中的最小值
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     *
     * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
     *
     * 请找出其中最小的元素。
     *
     * 你可以假设数组中不存在重复元素。
     *
     * 示例 1:
     *
     * 输入: [3,4,5,1,2]
     * 输出: 1
     *
     * 示例 2:
     *
     * 输入: [4,5,6,7,0,1,2]
     * 输出: 0
     */
    public int findMin(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int left = 0, right = n - 1;
        if (nums[right] > nums[0]) {
            return nums[0];
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            if (nums[mid - 1] > nums[mid]) {
                return nums[mid];
            }
            if (nums[mid] > nums[0]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    /**
     * 162. 寻找峰值
     * 峰值元素是指其值大于左右相邻值的元素。
     *
     * 给定一个输入数组 nums，其中 nums[i] ≠ nums[i+1]，找到峰值元素并返回其索引。
     *
     * 数组可能包含多个峰值，在这种情况下，返回任何一个峰值所在位置即可。
     *
     * 你可以假设 nums[-1] = nums[n] = -∞。
     *
     * 示例 1:
     *
     * 输入: nums = [1,2,3,1]
     * 输出: 2
     * 解释: 3 是峰值元素，你的函数应该返回其索引 2。
     *
     * 示例 2:
     *
     * 输入: nums = [1,2,1,3,5,6,4]
     * 输出: 1 或 5
     * 解释: 你的函数可以返回索引 1，其峰值元素为 2；
     *      或者返回索引 5， 其峰值元素为 6。
     *
     * 说明:
     *
     * 你的解法应该是 O(logN) 时间复杂度的。
     */
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    /**
     * 209. 长度最小的子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组，并返回其长度。如果不存在符合条件的连续子数组，返回 0。
     *
     * 示例:
     *
     * 输入: s = 7, nums = [2,3,1,2,4,3]
     * 输出: 2
     * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
     *
     * 进阶:
     *
     * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
     */
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int minLen = 0, maxLen = n;
        int midLen = 0;
        int min = 0;
        while (minLen <= maxLen) {
            midLen = minLen + (maxLen - minLen) / 2;
            if (getMaxSum(midLen,nums) >= s) {
                maxLen = midLen - 1;
                min = midLen;
            } else {
                minLen = midLen + 1;
            }
        }
        return min;
    }
    public int getMaxSum(int len, int[] nums) {
        int sum = 0;
        int maxSum = 0;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
        }
        maxSum = sum;
        for (int i = len; i < nums.length; i++) {
            sum += nums[i];
            sum -= nums[i - len];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
    /**
     * 222. 完全二叉树的节点个数
     * 给出一个完全二叉树，求出该树的节点个数。
     *
     * 说明：
     *
     * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。
     *
     * 示例:
     *
     * 输入:
     *     1
     *    / \
     *   2   3
     *  / \  /
     * 4  5 6
     *
     * 输出: 6
     */
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
     * 240. 搜索二维矩阵 II
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
     *
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     * 示例:
     *
     * 现有矩阵 matrix 如下：
     *
     * [
     *   [1,   4,  7, 11, 15],
     *   [2,   5,  8, 12, 19],
     *   [3,   6,  9, 16, 22],
     *   [10, 13, 14, 17, 24],
     *   [18, 21, 23, 26, 30]
     * ]
     */
    public boolean searchMatrix1(int[][] matrix, int target) {
        int row = matrix.length-1;
        int col = 0;
        while (row >= 0 && col <= matrix[0].length-1) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                row--;
            } else {
                col++;
            }
        }
        return false;
    }
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int minShort = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < minShort; i++) {
            boolean row = binarySearch(matrix, target, i, true);
            boolean col = binarySearch(matrix, target, i, false);
            if (row || col) {
                return true;
            }
        }
        return false;
    }
    private boolean binarySearch(int[][] matrix, int target, int start, boolean isRow) {
        int left=0;
        int right = isRow ? matrix.length - 1 : matrix[0].length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (isRow) {
                if (matrix[mid][start] == target) {
                    return true;
                } else if (matrix[mid][start] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                if (matrix[start][mid] == target) {
                    return true;
                } else if (matrix[start][mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }
    /**
     * 278. 第一个错误的版本
     * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。
     * 由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。
     * 实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
     *
     * 示例:
     * 给定 n = 5，并且 version = 4 是第一个错误的版本。
     * 调用 isBadVersion(3) -> false
     * 调用 isBadVersion(5) -> true
     * 调用 isBadVersion(4) -> true
     * 所以，4 是第一个错误的版本。 
     */
    class VersionControl{
        boolean isBadVersion(int value) {
            return true;
        }
    }
    public class Solution1 extends VersionControl {
        public int firstBadVersion(int n) {
            int left = 1, right = n;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (isBadVersion(mid)) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }
    /**
     * 367. 有效的完全平方数
     * 给定一个正整数 num，编写一个函数，如果 num 是一个完全平方数，则返回 True，否则返回 False。
     * 说明：不要使用任何内置的库函数，如  sqrt。
     * 示例 1：
     * 输入：16
     * 输出：True
     *
     * 示例 2：
     * 输入：14
     * 输出：False
     */
    public boolean isPerfectSquare(int num) {
        int left = 0, right = num;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid == num) {
                return true;
            } else if ((long) mid * mid < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
    /**
     *374. 猜数字大小
     * 我们正在玩一个猜数字游戏。 游戏规则如下：
     * 我从 1 到 n 选择一个数字。 你需要猜我选择了哪个数字。
     * 每次你猜错了，我会告诉你这个数字是大了还是小了。
     * 你调用一个预先定义好的接口 guess(int num)，它会返回 3 个可能的结果（-1，1 或 0）：
     * -1 : 我的数字比较小
     *  1 : 我的数字比较大
     *  0 : 恭喜！你猜对了！
     * 示例 :
     * 输入: n = 10, pick = 6
     * 输出: 6
     */
    class GuessGame{
        int guess(int num){
            return -1;
        }
    }
    public class Solution extends GuessGame {
        public int guessNumber(int n) {
            int left=1, right = n;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = guess(mid);
                if (res == 0) {
                    return mid;
                } else if (res < 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return -1;
        }
    }
    /**
     * 378. 有序矩阵中第K小的元素
     * 给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * 请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素。
     * <p>
     * 示例：
     * <p>
     * matrix = [
     * [ 1,  5,  9],
     * [10, 11, 13],
     * [12, 13, 15]
     * ],
     * k = 8,
     * <p>
     * 返回 13。
     * <p>
     * 提示：
     * 你可以假设 k 的值永远是有效的，1 ≤ k ≤ n2 。
     */
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> stack = new PriorityQueue<>((n1, n2)->{
            return n2 - n1;
        });
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (stack.size() == k && matrix[i][j] > stack.peek()) {
                    break;
                }
                stack.add(matrix[i][j]);
                if (stack.size() > k) {
                    stack.poll();
                }
            }
        }
        return stack.peek();
    }

    /**
     * 441. 排列硬币
     * 你总共有 n 枚硬币，你需要将它们摆成一个阶梯形状，第 k 行就必须正好有 k 枚硬币。
     * 给定一个数字 n，找出可形成完整阶梯行的总行数。
     * n 是一个非负整数，并且在32位有符号整型的范围内。
     *
     * 示例 1:
     * n = 5
     * 硬币可排列成以下几行:
     * ¤
     * ¤ ¤
     * ¤ ¤
     *
     * 因为第三行不完整，所以返回2.
     * 示例 2:
     * n = 8
     * 硬币可排列成以下几行:
     * ¤
     * ¤ ¤
     * ¤ ¤ ¤
     * ¤ ¤
     * 因为第四行不完整，所以返回3.
     */
    public int arrangeCoins1(int n) {
        if (n == 0) {
            return 0;
        }
        int left = 0, right = n;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            long midNeed = ((long)(mid + 1) * mid) >> 1;
            if (midNeed < n) {
                left = mid + 1;
            } else if (midNeed > n) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return right;
    }
    /**
     * 704. 二分查找
     * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
     *
     *
     * 示例 1:
     *
     * 输入: nums = [-1,0,3,5,9,12], target = 9
     * 输出: 4
     * 解释: 9 出现在 nums 中并且下标为 4
     *
     * 示例 2:
     *
     * 输入: nums = [-1,0,3,5,9,12], target = 2
     * 输出: -1
     * 解释: 2 不存在 nums 中因此返回 -1
     *
     *
     *
     * 提示：
     *
     *     你可以假设 nums 中的所有元素是不重复的。
     *     n 将在 [1, 10000]之间。
     *     nums 的每个元素都将在 [-9999, 9999]之间。
     */
    public int search11(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    /**
     * 744. 寻找比目标字母大的最小字母
     * 给你一个排序后的字符列表 letters ，列表中只包含小写英文字母。另给出一个目标字母 target，请你寻找在这一有序列表里比目标字母大的最小字母。
     * 在比较时，字母是依序循环出现的。举个例子：
     * 如果目标字母 target = 'z' 并且字符列表为 letters = ['a', 'b']，则答案返回 'a'
     * 示例：
     * 输入:
     * letters = ["c", "f", "j"]
     * target = "a"
     * 输出: "c"
     *
     * 输入:
     * letters = ["c", "f", "j"]
     * target = "c"
     * 输出: "f"
     *
     * 输入:
     * letters = ["c", "f", "j"]
     * target = "d"
     * 输出: "f"
     *
     * 输入:
     * letters = ["c", "f", "j"]
     * target = "g"
     * 输出: "j"
     *
     * 输入:
     * letters = ["c", "f", "j"]
     * target = "j"
     * 输出: "c"
     *
     * 输入:
     * letters = ["c", "f", "j"]
     * target = "k"
     * 输出: "c"
     *
     * 提示：
     * letters长度范围在[2, 10000]区间内。
     * letters 仅由小写字母组成，最少包含两个不同的字母。
     * 目标字母target 是一个小写字母。
     */
    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0, right = letters.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (letters[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return letters[left%letters.length];
    }

    /**
     * 面试题53 - I. 在排序数组中查找数字 I
     * 统计一个数字在排序数组中出现的次数。
     *
     * 示例 1:
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: 2
     *
     * 示例 2:
     * 输入: nums = [5,7,7,8,8,10], target = 6
     * 输出: 0
     *
     * 限制：
     * 0 <= 数组长度 <= 50000
     */
    public int search(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] <= target) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        int right = i;
        if (j < 0 || nums[j] != target) {
            return 0;
        }
        i=0;j=nums.length-1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] < target) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        int left = j;
        return right - left - 1;
    }
}
