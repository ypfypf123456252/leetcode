package 哈希表;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Hash {
    /**
     * 169. 多数元素
     *
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     *
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     *
     *
     * 示例 1:
     *
     * 输入: [3,2,3]
     * 输出: 3
     *
     * 示例 2:
     *
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     */
    public int majorityElement1(int[] nums) {
        //哈希表
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Map.Entry<Integer, Integer> entry = null;
        for (Map.Entry<Integer, Integer> integerEntry : map.entrySet()) {
            if (entry == null || integerEntry.getValue() > entry.getValue()) {
                entry = integerEntry;
            }
        }
        return entry.getKey();
    }
    public int majorityElement(int[] nums) {
        //排序
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
    /**
     * 202. 快乐数
     * 编写一个算法来判断一个数 n 是不是快乐数。
     * 「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，
     * 也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。
     * 如果 n 是快乐数就返回 True ；不是，则返回 False 。
     * <p>
     * 示例：
     * 输入：19
     * 输出：true
     * 解释：
     * 12 + 92 = 82
     * 82 + 22 = 68
     * 62 + 82 = 100
     * 12 + 02 + 02 = 1
     * 解题思路
     * 判断出现的数之前有没有出现过，出现过就会产生循环，就不是快乐数。可以用集合 set 来记录之前出现的数字。
     */
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while (n != 1 && !set.contains(n)) {
            set.add(n);
            n = nextVal(n);
        }
        return n == 1;
    }
    private int nextVal(int n) {
        int sum = 0;
        while (n > 0) {
            int x = n % 10;
            sum += x * x;
            n = n / 10;
        }
        return sum;
    }
    /**
     * 205. 同构字符串
     * 给定两个字符串 s 和 t，判断它们是否是同构的。
     * <p>
     * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
     * <p>
     * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "egg", t = "add"
     * 输出: true
     * <p>
     * 示例 2:
     * <p>
     * 输入: s = "foo", t = "bar"
     * 输出: false
     * <p>
     * 示例 3:
     * <p>
     * 输入: s = "paper", t = "title"
     * 输出: true
     * <p>
     * 说明:
     * 你可以假设 s 和 t 具有相同的长度。
     */
    public boolean isIsomorphic(String s, String t) {
        return isIsomorphicHelper(s, t) && isIsomorphicHelper(t, s);
    }
    public boolean isIsomorphicHelper(String s, String t) {
        int n = s.length();
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if (map.containsKey(c1)) {
                if (map.get(c1) != c2) {
                    return false;
                }
            } else {
                map.put(c1, c2);
            }
        }
        return true;
    }
    /**
     * 217. 存在重复元素
     * 给定一个整数数组，判断是否存在重复元素。
     * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     * <p>
     * 示例 1:
     * 输入: [1,2,3,1]
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: [1,2,3,4]
     * 输出: false
     * <p>
     * 示例 3:
     * 输入: [1,1,1,3,3,4,3,2,4,2]
     * 输出: true
     * <p>
     * 解题思路
     * 使用 set 集合存储前面遍历过的元素，对于要每个元素 num[i]，判断是否在前面的 recode 中，如果存在，返回 True。
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
        }
        return false;
    }
    /**
     * 219. 存在重复元素 II
     * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值 至多为 k。
     * <p>
     * 示例 1:
     * 输入: nums = [1,2,3,1], k = 3
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: nums = [1,0,1,1], k = 1
     * 输出: true
     * <p>
     * 示例 3:
     * 输入: nums = [1,2,3,1,2,3], k = 2
     * 输出: false
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }
    /**
     * 242. 有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * <p>
     * 示例 2:
     * <p>
     * 输入: s = "rat", t = "car"
     * 输出: false
     * <p>
     * 说明:
     * 你可以假设字符串只包含小写字母。
     * <p>
     * 进阶:
     * 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
     * <p>
     * 解题思路
     * 只要 t 中和 s 中字符都一样且数目都一样，那么就可以了
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int arr[] = new int[26];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a']++;
            arr[t.charAt(i) - 'a']--;
        }
        for (int temp : arr) {
            if (temp != 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * 349. 两个数组的交集
     * 给定两个数组，编写一个函数来计算它们的交集。
     * <p>
     * 示例 1:
     * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出: [2]
     * <p>
     * 示例 2:
     * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出: [9,4]
     * <p>
     * 说明:
     * 输出结果中的每个元素一定是唯一的。
     * 我们可以不考虑输出结果的顺序。
     */
    public int[] intersection1(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set1.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            set2.add(nums2[i]);
        }
        int[] arr;
        int left = 0;
        if (set1.size() < set2.size()) {
            arr = new int[set1.size()];
            for (Integer temp : set2) {
                if (set1.contains(temp)) {
                    arr[left++] = temp;
                }
            }
        } else {
            arr = new int[set2.size()];
            for (Integer temp : set1) {
                if (set2.contains(temp)) {
                    arr[left++] = temp;
                }
            }
        }
        return Arrays.copyOf(arr, left);
    }
    public int[] intersection2(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int i : nums1) {
            set1.add(i);
        }
        for (int i : nums2) {
            set2.add(i);
        }
        set1.retainAll(set2);
        int[] num = new int[set1.size()];
        int i = 0;
        for (Integer temp : set1) {
            num[i++] = temp;
        }
        return num;
    }
    /**
     * 350. 两个数组的交集 II
     * 给定两个数组，编写一个函数来计算它们的交集。
     * <p>
     * 示例 1:
     * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出: [2,2]
     * <p>
     * 示例 2:
     * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出: [4,9]
     * <p>
     * 说明：
     * <p>
     * 输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     * 我们可以不考虑输出结果的顺序。
     * 进阶:
     * 如果给定的数组已经排好序呢？你将如何优化你的算法？
     * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
     * 如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     * 多加了记录元素个数的功能，我们可以使用字典 dict 实现它。
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int n : nums1) {
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }
    /**
     * 387. 字符串中的第一个唯一字符
     * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     * <p>
     * 案例:
     * s = "leetcode"
     * 返回 0.
     * s = "loveleetcode",
     * 返回 2.
     * <p>
     * 注意事项：您可以假定该字符串只包含小写字母。
     */
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(c) == null) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 1207. 独一无二的出现次数
     * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
     * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
     * <p>
     * 示例 1：
     * 输入：arr = [1,2,2,1,1,3]
     * 输出：true
     * 解释：在该数组中，1 出现了 3 次，2 出现了 2 次，3 只出现了 1 次。没有两个数的出现次数相同。
     * <p>
     * 示例 2：
     * 输入：arr = [1,2]
     * 输出：false
     * <p>
     * 示例 3：
     * 输入：arr = [-3,0,1,-3,1,1,1,-3,10,0]
     * 输出：true
     * <p>
     * 提示：
     * 1 <= arr.length <= 1000
     * -1000 <= arr[i] <= 1000
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        for (int data : arr) {
            if (map.get(data) == null) {
                map.put(data, 1);
            } else {
                map.put(data, map.get(data) + 1);
            }
        }
        for (Integer value : map.values()) {
            if (!set.add(value)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 1365. 有多少小于当前数字的数字
     * 给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中比它小的所有数字的数目。
     * 换而言之，对于每个 nums[i] 你必须计算出有效的 j 的数量，其中 j 满足 j != i 且 nums[j] < nums[i] 。
     * 以数组形式返回答案。
     * <p>
     * 示例 1：
     * 输入：nums = [8,1,2,2,3]
     * 输出：[4,0,1,1,3]
     * 解释：
     * 对于 nums[0]=8 存在四个比它小的数字：（1，2，2 和 3）。
     * 对于 nums[1]=1 不存在比它小的数字。
     * 对于 nums[2]=2 存在一个比它小的数字：（1）。
     * 对于 nums[3]=2 存在一个比它小的数字：（1）。
     * 对于 nums[4]=3 存在三个比它小的数字：（1，2 和 2）。
     * <p>
     * 示例 2：
     * 输入：nums = [6,5,4,8]
     * 输出：[2,1,0,3]
     * <p>
     * 示例 3：
     * 输入：nums = [7,7,7,7]
     * 输出：[0,0,0,0]
     * <p>
     * 提示：
     * 2 <= nums.length <= 500
     * 0 <= nums[i] <= 100
     */
    public int[] smallerNumbersThanCurrent(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int temp[] = Arrays.copyOf(nums, n);
        Arrays.sort(temp);
        map.put(temp[0], 0);
        for (int i = 1; i < n; i++) {
            if (temp[i] > temp[i - 1]) {
                map.put(temp[i], i);
            }
        }
        for (int i = 0; i < n; i++) {
            temp[i] = map.get(nums[i]);
        }
        return temp;
    }
    /**
     * 面试题 17.10. 主要元素
     *
     * 数组中占比超过一半的元素称之为主要元素。给定一个整数数组，找到它的主要元素。若没有，返回-1。
     *
     * 示例 1：
     *
     * 输入：[1,2,5,9,5,9,5,5,5]
     * 输出：5
     *
     *
     *
     * 示例 2：
     *
     * 输入：[3,2]
     * 输出：-1
     *
     *
     *
     * 示例 3：
     *
     * 输入：[2,2,1,1,1,2,2]
     * 输出：2
     *
     *
     *
     * 说明：
     * 你有办法在时间复杂度为 O(N)，空间复杂度为 O(1) 内完成吗？
     */
    public int majorityElement11(int[] nums) {
        //哈希表
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Map.Entry<Integer, Integer> entry = null;
        for (Map.Entry<Integer, Integer> integerEntry : map.entrySet()) {
            if (entry == null || integerEntry.getValue() > entry.getValue()) {
                entry = integerEntry;
            }
        }
        if (entry.getValue() > nums.length / 2) {
            return entry.getKey();
        } else {
            return -1;
        }
    }
}
