package 队列;

import java.util.ArrayDeque;

public class QueueTest {
    /**
     * 239. 滑动窗口最大值
     * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     * <p>
     * 返回滑动窗口中的最大值。
     * <p>
     * 进阶：
     * 你能在线性时间复杂度内解决此题吗？
     * <p>
     * 示例:
     * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
     * 输出: [3,3,5,5,6,7]
     * 解释:
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     * <p>
     * 提示：
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * 1 <= k <= nums.length
     */
    ArrayDeque<Integer> deq = new ArrayDeque();
    int nums[];
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) {
            return new int[0];
        }
        if (k == 1) {
            return nums;
        }
        this.nums = nums;
        int max_idx = 0;
        for (int i = 0; i < k; i++) {
            removeQueue(i, k);
            deq.addLast(i);
            if (nums[i] > nums[max_idx]) {
                max_idx = i;
            }
        }
        int ret[] = new int[n - k + 1];
        ret[0] = nums[max_idx];
        for (int i = k; i < n; i++) {
            removeQueue(i, k);
            deq.addLast(i);
            ret[i - k + 1] = nums[deq.getFirst()];
        }
        return ret;
    }
    private void removeQueue(int i, int k) {
        if (!deq.isEmpty() && deq.getFirst() == i - k) {
            deq.removeFirst();
        }
        while (!deq.isEmpty() && nums[i] > nums[deq.getLast()]) {
            deq.removeLast();
        }
    }
}
