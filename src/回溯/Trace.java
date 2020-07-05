package 回溯;

import java.util.*;

public class Trace {
    /**
     * 17. 电话号码的字母组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     *
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     *
     * 示例:
     *
     * 输入："23"
     * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     *
     * 说明:
     * 尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
     */
    public List<String> letterCombinations(String digits) {
        Map<String, String> map = new HashMap<String, String>() {{
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }};
        List<String> list = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return list;
        }
        helper17(digits, "", map, list);
        return list;
    }
    private void helper17(String digits, String combination, Map<String, String> map, List<String> list) {
        if (digits.length() == 0) {
            list.add(combination);
            return;
        }
        String digit = digits.substring(0, 1);
        String letters = map.get(digit);
        for (int i = 0; i < letters.length(); i++) {
            String letter = letters.substring(i, i + 1);
            helper17(digits.substring(1), combination + letter, map, list);
        }
    }
    /**
     * 22. 括号生成
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     *
     *
     *
     * 示例：
     *
     * 输入：n = 3
     * 输出：[
     *        "((()))",
     *        "(()())",
     *        "(())()",
     *        "()(())",
     *        "()()()"
     *      ]
     */
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (n < 1) {
            return list;
        }
        helper22(0, 0, n, builder, list);
        return list;
    }
    private void helper22(int start, int end, int n, StringBuilder builder, List<String> list) {
        if (builder.length() == 2 * n) {
            list.add(new String(builder));
            return;
        }
        if (start < n) {
            builder.append("(");
            helper22(start + 1, end, n, builder, list);
            builder.deleteCharAt(builder.length() - 1);
        }
        if (end < start) {
            builder.append(")");
            helper22(start, end + 1, n, builder, list);
            builder.deleteCharAt(builder.length() - 1);
        }
    }
    /**
     * 39. 组合总和
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     *     所有数字（包括 target）都是正整数。
     *     解集不能包含重复的组合。
     *
     * 示例 1:
     *
     * 输入: candidates = [2,3,6,7], target = 7,
     * 所求解集为:
     * [
     *   [7],
     *   [2,2,3]
     * ]
     *
     * 示例 2:
     *
     * 输入: candidates = [2,3,5], target = 8,
     * 所求解集为:
     * [
     *   [2,2,2,2],
     *   [2,3,3],
     *   [3,5]
     * ]
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        if (candidates == null || candidates.length == 0 || target < 0) {
            return res;
        }
        helper39(candidates, 0, target, temp, res);
        return res;
    }
    private void helper39(int[] candidates, int start, int target, List<Integer> temp, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(temp));
            return;
        } else {
            for (int i = start; i < candidates.length; i++) {
                temp.add(candidates[i]);
                helper39(candidates, i, target - candidates[i], temp, res);
                temp.remove(temp.size() - 1);
            }
        }
    }
    /**
     * 40. 组合总和 II
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的每个数字在每个组合中只能使用一次。
     *
     * 说明：
     *
     *     所有数字（包括目标数）都是正整数。
     *     解集不能包含重复的组合。
     *
     * 示例 1:
     *
     * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
     * 所求解集为:
     * [
     *   [1, 7],
     *   [1, 2, 5],
     *   [2, 6],
     *   [1, 1, 6]
     * ]
     *
     * 示例 2:
     *
     * 输入: candidates = [2,5,2,1,2], target = 5,
     * 所求解集为:
     * [
     *   [1,2,2],
     *   [5]
     * ]
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        if (candidates == null || candidates.length == 0 || target < 0) {
            return res;
        }
        Arrays.sort(candidates);
        helper40(candidates, 0, target, temp, res);
        return res;
    }
    private void helper40(int[] candidates, int start, int target, List<Integer> temp, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            if (!res.contains(temp)) {
                res.add(new ArrayList<>(temp));
            }
            return;
        } else {
            for (int i = start; i < candidates.length; i++) {
                if ((i > start && candidates[i] == candidates[i-1])) {
                    continue;
                }
                temp.add(candidates[i]);
                helper40(candidates, i+1, target - candidates[i], temp, res);
                temp.remove(temp.size() - 1);
            }
        }
    }
    /**
     * 44. 通配符匹配
     * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
     *
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任意字符串（包括空字符串）。
     *
     * 两个字符串完全匹配才算匹配成功。
     *
     * 说明:
     *
     *     s 可能为空，且只包含从 a-z 的小写字母。
     *     p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
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
     * p = "*"
     * 输出: true
     * 解释: '*' 可以匹配任意字符串。
     *
     * 示例 3:
     *
     * 输入:
     * s = "cb"
     * p = "?a"
     * 输出: false
     * 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
     *
     * 示例 4:
     *
     * 输入:
     * s = "adceb"
     * p = "*a*b"
     * 输出: true
     * 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
     *
     * 示例 5:
     *
     * 输入:
     * s = "acdcb"
     * p = "a*c?b"
     * 输出: false
     */
    public boolean isMatch(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIndex = 0, pIndex = 0;
        int sTemp = -1, pTemp = -1;
        while (sIndex < sLen) {
            if (pIndex < pLen && (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '?')) {
                pIndex++;
                sIndex++;
            } else if (pIndex < pLen && p.charAt(pIndex) == '*') {
                sTemp = sIndex;
                pTemp = pIndex;
                pIndex++;
            } else if (pTemp == -1) {
                return false;
            } else {
                pIndex = pTemp + 1;
                sIndex = sTemp + 1;
                sTemp = sIndex;
            }
        }
        for (int i = pIndex; i < pLen; i++) {
            if (p.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }
    /**
     * 46. 全排列
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     *
     * 示例:
     *
     * 输入: [1,2,3]
     * 输出:
     * [
     *   [1,2,3],
     *   [1,3,2],
     *   [2,1,3],
     *   [2,3,1],
     *   [3,1,2],
     *   [3,2,1]
     * ]
     */
    public List<List<Integer>> permute1(int[] nums) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        helper46(nums, temp, res);
        return res;
    }
    private void helper46(int[] nums, List<Integer> temp, List<List<Integer>> res) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (temp.contains(nums[i])) {
                continue;
            }
            temp.add(nums[i]);
            helper46(nums, temp, res);
            temp.remove(temp.size() - 1);
        }
    }
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        for (int num : nums) {
            temp.add(num);
        }
        helper46(nums, temp, res, 0);
        return res;
    }
    private void helper46(int[] nums, List<Integer> temp, List<List<Integer>> res, int first) {
        if (first == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = first; i < nums.length; i++) {
            Collections.swap(temp, i, first);
            helper46(nums, temp, res, first + 1);
            Collections.swap(temp, i, first);
        }
    }
    /**
     * 47. 全排列 II
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
     *
     * 示例:
     *
     * 输入: [1,1,2]
     * 输出:
     * [
     *   [1,1,2],
     *   [1,2,1],
     *   [2,1,1]
     * ]
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        for (int num : nums) {
            temp.add(num);
        }
        helper47(nums, temp, res, 0);
        return res;
    }
    private void helper47(int[] nums, List<Integer> temp, List<List<Integer>> res, int first) {
        if (first == nums.length) {
            if (!res.contains(temp)) {
                res.add(new ArrayList<>(temp));
            }
            return;
        }
        for (int i = first; i < nums.length; i++) {
            Collections.swap(temp, i, first);
            helper47(nums, temp, res, first + 1);
            Collections.swap(temp, i, first);
        }
    }
    /**
     * 78. 子集
     *
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     *
     * 说明：解集不能包含重复的子集。
     *
     * 示例:
     *
     * 输入: nums = [1,2,3]
     * 输出:
     * [
     *   [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   []
     * ]
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            helper78(nums, 0, i, res, new ArrayList<>());
        }
        return res;
    }
    private void helper78(int nums[], int first, int k, List<List<Integer>> res, List<Integer> temp) {
        if (temp.size() == k) {
            res.add(new ArrayList<>(temp));
        }
        for (int i = first; i < nums.length; i++) {
            temp.add(nums[i]);
            helper78(nums, i + 1, k, res, temp);
            temp.remove(temp.size() - 1);
        }
    }
}
