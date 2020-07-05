package 数学;

import java.util.*;

/**
 * 异或:
 *  1. 任何数和 0 做异或运算，结果仍然是原来的数，即 a⊕0=a
 *  2. 任何数和其自身做异或运算，结果是 0，即 a⊕a=0
 *  3. 异或运算满足交换律和结合律，即 a⊕b⊕a = b⊕a⊕a = b⊕(a⊕a) = b⊕0=b
 */
public class MyMath {
    /**
     * 136. 只出现一次的数字
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     *
     * 示例 1:
     * 输入: [2,2,1]
     * 输出: 1

     * 示例 2:
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }
    /**
     * 191. 位1的个数
     * 编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：00000000000000000000000000001011
     * 输出：3
     * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
     * <p>
     * 示例 2：
     * <p>
     * 输入：00000000000000000000000010000000
     * 输出：1
     * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
     * <p>
     * 示例 3：
     * <p>
     * 输入：11111111111111111111111111111101
     * 输出：31
     * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 31 位为 '1'。
     * <p>
     * <p>
     * <p>
     * 提示：
     * <p>
     * 请注意，在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
     * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。因此，在上面的 示例 3 中，输入表示有符号整数 -3。
     * <p>
     * <p>
     * <p>
     * 进阶:
     * 如果多次调用这个函数，你将如何优化你的算法？
     */
    public int hammingWeight1(int n) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & 1) == 1) {
                ans++;
            }
            n = n >> 1;
        }
        return ans;
    }
    public int hammingWeight(int n) {
        int ans = 0;
        while (n != 0) {
            ans++;
            n &= (n - 1);
        }
        return ans;
    }
    /**
     * 204. 计数质数
     * 统计所有小于非负整数 n 的质数的数量。
     *
     * 示例:
     *
     * 输入: 10
     * 输出: 4
     * 解释: 小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
     */
    public int countPrimes1(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }
    private boolean isPrime(int n) {
        for (int i = 2; i*i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        int ans = 0;
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i]) {
                ans++;
            }
        }
        return ans;
    }
    /**
     * 268. 缺失数字
     * 给定一个包含 0, 1, 2, ..., n 中 n 个数的序列，找出 0 .. n 中没有出现在序列中的那个数。
     *
     * 示例 1:
     *
     * 输入: [3,0,1]
     * 输出: 2
     *
     * 示例 2:
     *
     * 输入: [9,6,4,2,3,5,7,0,1]
     * 输出: 8
     *
     * 说明:
     * 你的算法应具有线性时间复杂度。你能否仅使用额外常数空间来实现?
     */
    public int missingNumber(int[] nums) {
        int ans1 = 0;
        int ans2 = 0;
        for (int i = 0; i < nums.length; i++) {
            ans1 ^= nums[i];
            ans2 ^= i + 1;
        }
        return ans1 ^ ans2;
    }
    /**
     * 290. 单词规律
     * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
     *
     * 示例1:
     * 输入: pattern = "abba", str = "dog cat cat dog"
     * 输出: true
     *
     * 示例 2:
     * 输入:pattern = "abba", str = "dog cat cat fish"
     * 输出: false
     *
     * 示例 3:
     * 输入: pattern = "aaaa", str = "dog cat cat dog"
     * 输出: false
     *
     * 示例 4:
     * 输入: pattern = "abba", str = "dog dog dog dog"
     * 输出: false
     *
     * 说明:
     * 你可以假设 pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。
     */
    public boolean wordPattern(String pattern, String str) {
        Map<Character, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        String[] array = str.split(" ");
        if (pattern.length() != array.length) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            char key = pattern.charAt(i);
            if (map.containsKey(key)) {
                if (!map.get(key).equals(array[i])) {
                    return false;
                }
            } else {
                if (set.contains(array[i])) {
                    return false;
                }
                map.put(key, array[i]);
                set.add(array[i]);
            }
        }
        return true;
    }
    /**
     * 326. 3的幂
     * 给定一个整数，写一个函数来判断它是否是 3 的幂次方。
     *
     * 示例 1:
     *
     * 输入: 27
     * 输出: true
     *
     * 示例 2:
     *
     * 输入: 0
     * 输出: false
     *
     * 示例 3:
     *
     * 输入: 9
     * 输出: true
     *
     * 示例 4:
     *
     * 输入: 45
     * 输出: false
     *
     * 进阶：
     * 你能不使用循环或者递归来完成本题吗？
     */
    public boolean isPowerOfThree1(int n) {
        return (Math.log10(n) / Math.log10(3)) % 1 == 0;
    }
    public boolean isPowerOfThree(int n) {
        if (n < 1) {
            return false;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }
    /**
     * 389. 找不同
     * 给定两个字符串 s 和 t，它们只包含小写字母。
     * 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
     * 请找出在 t 中被添加的字母。
     *
     * 示例:
     * 输入：
     * s = "abcd"
     * t = "abcde"
     * 输出：
     * e
     * 解释：
     * 'e' 是那个被添加的字母。
     */
    public char findTheDifference1(String s, String t) {
        char res = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            res ^= s.charAt(i) ^ t.charAt(i);
        }
        res ^= t.charAt(len);
        return res;
    }
    public char findTheDifference(String s, String t) {
        return (char) Math.abs(sumChar(s) - sumChar(t));
    }
    private int sumChar(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += (int) c;
        }
        return sum;
    }
    /**
     * 412. Fizz Buzz
     * 写一个程序，输出从 1 到 n 数字的字符串表示。
     *
     * 1. 如果 n 是3的倍数，输出“Fizz”；
     *
     * 2. 如果 n 是5的倍数，输出“Buzz”；
     *
     * 3.如果 n 同时是3和5的倍数，输出 “FizzBuzz”。
     *
     * 示例：
     *
     * n = 15,
     *
     * 返回:
     * [
     *     "1",
     *     "2",
     *     "Fizz",
     *     "4",
     *     "Buzz",
     *     "Fizz",
     *     "7",
     *     "8",
     *     "Fizz",
     *     "Buzz",
     *     "11",
     *     "Fizz",
     *     "13",
     *     "14",
     *     "FizzBuzz"
     * ]
     */
    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                list.add("Fizz");
            } else if (i % 3 != 0 && i % 5 == 0) {
                list.add("Buzz");
            } else if (i % 3 == 0 && i % 5 == 0) {
                list.add("FizzBuzz");
            } else {
                list.add(i + "");
            }
        }
        return list;
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
        return (int) (Math.sqrt(2) * Math.sqrt(n + 0.125) - 0.5);
    }
    public int arrangeCoins2(int n) {
        int i = 1;
        while (n >= i) {
            n -= i++;
        }
        return i;
    }

    /**
     * 剑指 Offer 64. 求1+2+…+n
     *
     * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     *
     *
     *
     * 示例 1：
     *
     * 输入: n = 3
     * 输出: 6
     *
     * 示例 2：
     *
     * 输入: n = 9
     * 输出: 45
     *
     *
     *
     * 限制：
     *
     *     1 <= n <= 10000
     */
    public int sumNums(int n) {
        return n * (n + 1) / 2;
    }
}
