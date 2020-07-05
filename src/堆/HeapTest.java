package 堆;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class HeapTest {
    /**
     * 215. 数组中的第K个最大元素
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     *
     * 示例 1:
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     *
     * 示例 2:
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     *
     * 说明:
     * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
     */
    public int findKthLargest(int[] nums, int k) {
        //前k个元素建立小顶堆
        buildHeap(nums, k);
        for (int i = k; i < nums.length; i++) {
            if (nums[i] < nums[0]) {
                continue;
            }
            swap(nums, i, 0);
            heapify(nums, k, 0);
        }
        return nums[0];
    }
    private void buildHeap(int[] nums, int k) {
        for (int i = k / 2 - 1; i >= 0; i--) {
            heapify(nums, k, i);
        }
    }
    private void heapify(int[] nums, int n, int i) {
        int minPos = i;
        while (true) {
            if (2 * i + 1 < n && nums[i] > nums[2 * i + 1]) {
                minPos = 2 * i + 1;
            }
            if (2 * i + 2 < n && nums[minPos] > nums[2 * i + 2]) {
                minPos = 2 * i + 2;
            }
            if (minPos == i) {
                break;
            }
            swap(nums, minPos, i);
            i = minPos;
        }
    }
    private void swap(int[] arr, int m, int n) {
        int temp = arr[m];
        arr[m] = arr[n];
        arr[n] = temp;
    }
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> {
            return n1 - n2;
        });
        for (int num : nums) {
            heap.add(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.poll();
    }
    /**
     * 347. 前 K 个高频元素
     * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
     *
     * 示例 1:
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     *
     * 示例 2:
     * 输入: nums = [1], k = 1
     * 输出: [1]
     *  
     *
     * 提示：
     * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
     * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
     * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
     * 你可以按任意顺序返回答案。
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o1) - map.get(o2);
            }
        });
        for (Integer temp : map.keySet()) {
            heap.add(temp);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int len = heap.size();
        int res[] = new int[heap.size()];
        while (heap.size() != 0) {
            res[--len] = heap.poll();
        }
        return res;
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
        PriorityQueue<Integer> stack = new PriorityQueue<>((n1,n2)->{
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
     * 451. 根据字符出现频率排序
     * 给定一个字符串，请将字符串里的字符按照出现的频率降序排列。
     *
     * 示例 1:
     * 输入:
     * "tree"
     * 输出:
     * "eert"
     * 解释:
     * 'e'出现两次，'r'和't'都只出现一次。
     * 因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
     *
     * 示例 2:
     * 输入:
     * "cccaaa"
     * 输出:
     * "cccaaa"
     * 解释:
     * 'c'和'a'都出现三次。此外，"aaaccc"也是有效的答案。
     * 注意"cacaca"是不正确的，因为相同的字母必须放在一起。
     *
     * 示例 3:
     * 输入:
     * "Aabb"
     * 输出:
     * "bbAa"
     * 解释:
     * 此外，"bbaA"也是一个有效的答案，但"Aabb"是不正确的。
     * 注意'A'和'a'被认为是两种不同的字符。
     */
    public String frequencySort(String s) {
        int letters[] = new int[128];
        for (char c : s.toCharArray()) {
            letters[c]++;
        }
        PriorityQueue<Character> heap=new PriorityQueue<>((n1,n2)->{
            return Integer.compare(letters[n2], letters[n1]);
        });
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] != 0) {
                heap.add((char) i);
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!heap.isEmpty()) {
            char c = heap.poll();
            while (letters[c]-- > 0) {
                builder.append(c);
            }
        }
        return builder.toString();
    }
    /**
     * 692. 前K个高频单词
     * 给一非空的单词列表，返回前 k 个出现次数最多的单词。
     * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
     *
     * 示例 1：
     * 输入: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
     * 输出: ["i", "love"]
     * 解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。
     *     注意，按字母顺序 "i" 在 "love" 之前。
     *
     * 示例 2：
     * 输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
     * 输出: ["the", "is", "sunny", "day"]
     * 解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，
     *     出现次数依次为 4, 3, 2 和 1 次。
     *
     * 注意：
     *     假定 k 总为有效值， 1 ≤ k ≤ 集合元素数。
     *     输入的单词均由小写字母组成。
     * 扩展练习：
     *     尝试以 O(n log k) 时间复杂度和 O(n) 空间复杂度解决。
     */
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<String> heap=new PriorityQueue<>((w1,w2)->
            map.get(w1).equals(map.get(w2)) ? w2.compareTo(w1) : map.get(w1) - map.get(w2)
        );
        for (String temp : map.keySet()) {
            heap.add(temp);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        List<String> ans = new ArrayList<>();
        while (!heap.isEmpty()) {
            ans.add(heap.poll());
        }
        Collections.reverse(ans);
        return ans;
    }
    /**
     * 703. 数据流中的第K大元素
     * 设计一个找到数据流中第K大元素的类（class）。注意是排序后的第K大元素，不是第K个不同的元素。
     * 你的 KthLargest 类需要一个同时接收整数 k 和整数数组nums 的构造器，它包含数据流中的初始元素。每次调用 KthLargest.add，返回当前数据流中第K大的元素。
     *
     * 示例:
     * int k = 3;
     * int[] arr = [4,5,8,2];
     * KthLargest kthLargest = new KthLargest(3, arr);
     * kthLargest.add(3);   // returns 4
     * kthLargest.add(5);   // returns 5
     * kthLargest.add(10);  // returns 5
     * kthLargest.add(9);   // returns 8
     * kthLargest.add(4);   // returns 8
     *
     * 说明:
     * 你可以假设 nums 的长度≥ k-1 且k ≥ 1。
     */
    class KthLargest {
        private PriorityQueue<Integer> minHeap;
        private Integer limit;

        public KthLargest(int k, int[] nums) {
            this.limit = k;
            minHeap = new PriorityQueue<>(k);
            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            if (minHeap.size() < limit) {
                minHeap.offer(val);
            } else if (val > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(val);
            }
            return minHeap.peek();
        }
    }
    /**
     * 973. 最接近原点的 K 个点
     * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
     * （这里，平面上两点之间的距离是欧几里德距离。）
     * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
     *
     * 示例 1：
     * 输入：points = [[1,3],[-2,2]], K = 1
     * 输出：[[-2,2]]
     * 解释：
     * (1, 3) 和原点之间的距离为 sqrt(10)，
     * (-2, 2) 和原点之间的距离为 sqrt(8)，
     * 由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。
     * 我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
     *
     * 示例 2：
     * 输入：points = [[3,3],[5,-1],[-2,4]], K = 2
     * 输出：[[3,3],[-2,4]]
     * （答案 [[-2,4],[3,3]] 也会被接受。）
     *
     * 提示：
     *     1 <= K <= points.length <= 10000
     *     -10000 < points[i][0] < 10000
     *     -10000 < points[i][1] < 10000
     */
    public int[][] kClosest(int[][] points, int K) {
        int len = points.length;
        int dists[] = new int[len];
        for (int i = 0; i < len; i++) {
            dists[i] = dist(points[i]);
        }
        Arrays.sort(dists);
        int distK = dists[K - 1];
        int[][] ans = new int[K][2];
        int t = 0;
        for (int i = 0; i < len; i++) {
            if (dist(points[i]) <= distK) {
                ans[t++] = points[i];
            }
        }
        return ans;
    }
    private int dist(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
    /**
     * 1046. 最后一块石头的重量
     * 有一堆石头，每块石头的重量都是正整数。
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     *     如果 x == y，那么两块石头都会被完全粉碎；
     *     如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     *
     * 示例：
     * 输入：[2,7,4,1,8,1]
     * 输出：1
     * 解释：
     * 先选出 7 和 8，得到 1，所以数组转换为 [2,4,1,1,1]，
     * 再选出 2 和 4，得到 2，所以数组转换为 [2,1,1,1]，
     * 接着是 2 和 1，得到 1，所以数组转换为 [1,1,1]，
     * 最后选出 1 和 1，得到 0，最终数组转换为 [1]，这就是最后剩下那块石头的重量。
     *
     * 提示：
     *     1 <= stones.length <= 30
     *     1 <= stones[i] <= 1000
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((n1, n2) -> {
            return n2 - n1;
        });
        for (int stone : stones) {
            maxHeap.add(stone);
        }
        while (maxHeap.size() >= 2) {
            Integer poll1 = maxHeap.poll();
            Integer poll2 = maxHeap.poll();
            maxHeap.add(poll1 - poll2);
        }
        return maxHeap.poll();
    }

    /**
     * 面试题 17.09. 第 k 个数
     * 有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。
     * 例如，前几个数按顺序应该是 1，3，5，7，9，15，21。
     *
     * 示例 1:
     * 输入: k = 5
     * 输出: 9
     */
    public int getKthMagicNumber(int k) {
        Set<Long> set = new HashSet<>();
        PriorityQueue<Long> queue = new PriorityQueue<>();
        queue.add(1L);
        while (true) {
            Long value = queue.poll();
            if (!set.contains(value)) {
                set.add(value);
                queue.add(3 * value);
                queue.add(5 * value);
                queue.add(7 * value);
            }
            if (set.size() == k) {
                return value.intValue();
            }
        }
    }
    /**
     * 面试题 17.14. 最小K个数
     * 设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
     *
     * 示例：
     * 输入： arr = [1,3,5,7,2,4,6,8], k = 4
     * 输出： [1,2,3,4]
     *
     * 提示：
     *     0 <= len(arr) <= 100000
     *     0 <= k <= min(100000, len(arr))
     */
    public int[] smallestK(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((n1, n2) -> {
            return n2 - n1;
        });
        for (int num : arr) {
            if (maxHeap.size() < k) {
                maxHeap.add(num);
            } else if (num < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(num);
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = maxHeap.poll();
        }
        return res;
    }
    /**
     * 面试题40. 最小的k个数
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     *
     * 示例 1：
     * 输入：arr = [3,2,1], k = 2
     * 输出：[1,2] 或者 [2,1]
     *
     * 示例 2：
     * 输入：arr = [0,1,2,1], k = 1
     * 输出：[0]
     *
     * 限制：
     *     0 <= k <= arr.length <= 10000
     *     0 <= arr[i] <= 10000
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> {
            return n2 - n1;
        });
        for (int num : arr) {
            if (heap.size() < k) {
                heap.offer(num);
            } else if (num < heap.peek()) {
                heap.poll();
                heap.offer(num);
            }
        }
        int res[] = new int[heap.size()];
        int i = 0;
        while (!heap.isEmpty()) {
            res[i++] = heap.poll();
        }
        return res;
    }


    public static void main(String[] args) {
    }
}
