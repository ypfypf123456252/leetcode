package 贪心算法;

import 数组.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class Greed {
    /**
     * 55. 跳跃游戏
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个位置。
     *
     * 示例 1:
     * 输入: [2,3,1,1,4]
     * 输出: true
     * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
     *
     * 示例 2:
     * 输入: [3,2,1,0,4]
     * 输出: false
     * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
     */
    public boolean canJump(int[] nums) {
        int maxRight = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i <= maxRight) {
                maxRight = Math.max(maxRight, i + nums[i]);
                if (maxRight >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 121. 买卖股票的最佳时机
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 注意：你不能在买入股票前卖出股票。
     *
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     *
     * 示例 2:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    public int maxProfit1(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
    /**
     * 122. 买卖股票的最佳时机 II
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     *
     * 示例 2:
     * 输入: [1,2,3,4,5]
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     *
     * 示例 3:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     *
     * 提示：
     *     1 <= prices.length <= 3 * 10 ^ 4
     *     0 <= prices[i] <= 10 ^ 4
     */
    public int maxProfit2(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            int temp = prices[i] - prices[i - 1];
            if (temp > 0) {
                profit += temp;
            }
        }
        return profit;
    }
    /**
     * 406. 根据身高重建队列
     * 假设有打乱顺序的一群人站成一个队列。 每个人由一个整数对(h, k)表示，其中h是这个人的身高，k是排在这个人前面且身高大于或等于h的人数。 编写一个算法来重建这个队列。
     * 注意：
     * 总人数少于1100人。
     *
     * 示例
     * 输入:
     * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * 输出:
     * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     */
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
            }
        });
        List<int[]> list = new ArrayList<>();
        for (int[] temp : people) {
            list.add(temp[1], temp);
        }
        return list.toArray(new int[list.size()][2]);
    }
    /**
     * 409. 最长回文串
     * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
     * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
     *
     * 注意:
     * 假设字符串的长度不会超过 1010。
     *
     * 示例 1:
     * 输入:
     * "abccccdd"
     * 输出:
     * 7
     *
     * 解释:
     * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
     */
    public int longestPalindrome(String s) {
        int count[] = new int[128];
        for (char c : s.toCharArray()) {
            count[c]++;
        }
        int res = 0;
        for (int temp : count) {
            res += temp / 2 * 2;
            if (temp % 2 == 1 && res % 2 == 0) {
                res += 1;
            }
        }
        return res;
    }
    /**
     * 455. 分发饼干
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。对每个孩子 i ，都有一个胃口值 gi ，
     * 这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j ，都有一个尺寸 sj 。
     * 如果 sj >= gi ，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。
     * 你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     *
     * 注意：
     * 你可以假设胃口值为正。
     * 一个小朋友最多只能拥有一块饼干。
     *
     * 示例 1:
     * 输入: [1,2,3], [1,1]
     * 输出: 1
     * 解释:
     * 你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
     * 虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
     * 所以你应该输出1。
     *
     * 示例 2:
     * 输入: [1,2], [1,2,3]
     * 输出: 2
     * 解释:
     * 你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。
     * 你拥有的饼干数量和尺寸都足以让所有孩子满足。
     * 所以你应该输出2.
     */
    public int findContentChildren(int[] g, int[] s) {
        if (g == null || s == null) {
            return 0;
        }
        Arrays.sort(g);
        Arrays.sort(s);
        int gi = 0;
        int si = 0;
        while (gi < g.length && si < s.length) {
            if (g[gi] <= s[si]) {
                gi++;
            }
            si++;
        }
        return gi;
    }
    /**
     * 860. 柠檬水找零
     * 在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
     * 顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     * 注意，一开始你手头没有任何零钱。
     * 如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     *
     * 示例 1：
     * 输入：[5,5,5,10,20]
     * 输出：true
     * 解释：
     * 前 3 位顾客那里，我们按顺序收取 3 张 5 美元的钞票。
     * 第 4 位顾客那里，我们收取一张 10 美元的钞票，并返还 5 美元。
     * 第 5 位顾客那里，我们找还一张 10 美元的钞票和一张 5 美元的钞票。
     * 由于所有客户都得到了正确的找零，所以我们输出 true。
     *
     * 示例 2：
     * 输入：[5,5,10]
     * 输出：true
     *
     * 示例 3：
     * 输入：[10,10]
     * 输出：false
     *
     * 示例 4：
     * 输入：[5,5,10,10,20]
     * 输出：false
     * 解释：
     * 前 2 位顾客那里，我们按顺序收取 2 张 5 美元的钞票。
     * 对于接下来的 2 位顾客，我们收取一张 10 美元的钞票，然后返还 5 美元。
     * 对于最后一位顾客，我们无法退回 15 美元，因为我们现在只有两张 10 美元的钞票。
     * 由于不是每位顾客都得到了正确的找零，所以答案是 false。
     *
     * 提示：
     *     0 <= bills.length <= 10000
     *     bills[i] 不是 5 就是 10 或是 20
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) {
                    return false;
                }
                ten++;
                five--;
            } else {
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five > 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * 1005. K 次取反后最大化的数组和
     * 给定一个整数数组 A，我们只能用以下方法修改该数组：我们选择某个个索引 i 并将 A[i] 替换为 -A[i]，然后总共重复这个过程 K 次。（我们可以多次选择同一个索引 i。）
     * 以这种方式修改数组后，返回数组可能的最大和。
     *
     * 示例 1：
     * 输入：A = [4,2,3], K = 1
     * 输出：5
     * 解释：选择索引 (1,) ，然后 A 变为 [4,-2,3]。
     *
     * 示例 2：
     * 输入：A = [3,-1,0,2], K = 3
     * 输出：6
     * 解释：选择索引 (1, 2, 2) ，然后 A 变为 [3,1,0,2]。
     *
     * 示例 3：
     * 输入：A = [2,-3,-1,5,-4], K = 2
     * 输出：13
     * 解释：选择索引 (1, 4) ，然后 A 变为 [2,3,-1,5,4]。
     *
     * 提示：
     *     1 <= A.length <= 10000
     *     1 <= K <= 10000
     *     -100 <= A[i] <= 100
     */
    public int largestSumAfterKNegations(int[] A, int K) {
        Arrays.sort(A);
        for (int i = 0; i < A.length; i++) {
            if (K <= 0) {
                break;
            }
            if (A[i] < 0) {
                A[i] = -A[i];
                K--;
            }
        }
        Arrays.sort(A);
        int sum = 0;
        for (int a : A) {
            sum += a;
        }
        sum = K % 2 == 0 ? sum : sum - 2 * A[0];
        return sum;
    }
    /**
     * 1029. 两地调度
     * 公司计划面试 2N 人。第 i 人飞往 A 市的费用为 costs[i][0]，飞往 B 市的费用为 costs[i][1]。
     * 返回将每个人都飞到某座城市的最低费用，要求每个城市都有 N 人抵达。
     *
     * 示例：
     * 输入：[[10,20],[30,200],[400,50],[30,20]]
     * 输出：110
     * 解释：
     * 第一个人去 A 市，费用为 10。
     * 第二个人去 A 市，费用为 30。
     * 第三个人去 B 市，费用为 50。
     * 第四个人去 B 市，费用为 20。
     * 最低总费用为 10 + 30 + 50 + 20 = 110，每个城市都有一半的人在面试。
     *
     * 提示：
     *     1 <= costs.length <= 100
     *     costs.length 为偶数
     *     1 <= costs[i][0], costs[i][1] <= 1000
     */
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o1[1]-(o2[0]-o2[1]);
            }
        });
        int sum = 0;
        int n = costs.length / 2;
        for (int i = 0; i < n; i++) {
            sum += costs[i][0] + costs[n + i][1];
        }
        return sum;
    }
    /**
     * 1046. 最后一块石头的重量
     * 有一堆石头，每块石头的重量都是正整数。
     *
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
        int n = stones.length;
        if (n == 1) {
            return stones[0];
        }
        int index = n - 1;
        Arrays.sort(stones);
        while (stones[index - 1] != 0) {
            stones[index] -= stones[index - 1];
            stones[index - 1] = 0;
            Arrays.sort(stones);
        }
        return stones[index];
    }
    /**
     * 1217. 玩筹码
     * 数轴上放置了一些筹码，每个筹码的位置存在数组 chips 当中。
     *
     * 你可以对 任何筹码 执行下面两种操作之一（不限操作次数，0 次也可以）：
     *     将第 i 个筹码向左或者右移动 2 个单位，代价为 0。
     *     将第 i 个筹码向左或者右移动 1 个单位，代价为 1。
     * 最开始的时候，同一位置上也可能放着两个或者更多的筹码。
     * 返回将所有筹码移动到同一位置（任意位置）上所需要的最小代价。
     *
     * 示例 1：
     * 输入：chips = [1,2,3]
     * 输出：1
     * 解释：第二个筹码移动到位置三的代价是 1，第一个筹码移动到位置三的代价是 0，总代价为 1。
     *
     * 示例 2：
     * 输入：chips = [2,2,2,3,3]
     * 输出：2
     * 解释：第四和第五个筹码移动到位置二的代价都是 1，所以最小总代价为 2。
     *
     * 提示：
     *     1 <= chips.length <= 100
     *     1 <= chips[i] <= 10^9
     */
    public int minCostToMoveChips(int[] chips) {
        int odd = 0;
        int even = 0;
        for (int chip : chips) {
            if (chip % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }
        return Math.min(odd, even);
    }


}
