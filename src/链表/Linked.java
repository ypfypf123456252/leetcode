package 链表;

import java.util.*;

public class Linked {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 2.两数相加
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
     public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
         ListNode head = new ListNode(0);
         ListNode current=head;
         int carry=0;
         while (l1 != null || l2 != null) {
             int x = l1 == null ? 0 : l1.val;
             int y = l2 == null ? 0 : l2.val;
             int sum=x+y+carry;
             carry=sum/10;
             current.next = new ListNode(sum % 10);
             current = current.next;
             if (l1 != null) {
                 l1 = l1.next;
             }
             if (l2 != null) {
                 l2 = l2.next;
             }
         }
         if (carry > 0) {
             current.next = new ListNode(carry);
         }
         return head.next;
     }
    /**
     * 19. 删除链表的倒数第N个节点
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     *
     * 示例：
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     *
     * 说明：
     * 给定的 n 保证是有效的。
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }
    /**
     * 21. 合并两个有序链表
     * 将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     *
     * 示例：
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                p.next = l1;
                l1 = l1.next;
                p = p.next;
            } else {
                p.next = l2;
                l2 = l2.next;
                p = p.next;
            }
        }
        if (l1 != null) {
            p.next = l1;
        }
        if (l2 != null) {
            p.next = l2;
        }
        return head.next;
    }
    /**
     * 23. 合并K个排序链表
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     *
     * 示例:
     * 输入:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 输出: 1->1->2->3->4->4->5->6
     */
    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        if (len == 0) {
            return null;
        }
        while (len > 1) {
            for (int i = 0; i < len / 2; i++) {
                lists[i] = mergeTowList(lists[i], lists[len - i - 1]);
            }
            len = (len + 1) / 2;
        }
        return lists[0];
    }
    private ListNode mergeTowList(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        if (l1 != null) {
            p.next = l1;
        }
        if (l2 != null) {
            p.next = l2;
        }
        return head.next;
    }
    /**
     * 25. K 个一组翻转链表
     *
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     *
     * k 是一个正整数，它的值小于或等于链表的长度。
     *
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     *
     *
     *
     * 示例：
     *
     * 给你这个链表：1->2->3->4->5
     *
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     *
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     *
     *
     *
     * 说明：
     *
     *     你的算法只能使用常数的额外空间。
     *     你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;
        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;
            pre = start;
            end = pre;
        }
        return dummy.next;
    }
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
    /**
     * 61. 旋转链表
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     *
     * 示例 1:
     * 输入: 1->2->3->4->5->NULL, k = 2
     * 输出: 4->5->1->2->3->NULL
     * 解释:
     * 向右旋转 1 步: 5->1->2->3->4->NULL
     * 向右旋转 2 步: 4->5->1->2->3->NULL
     *
     * 示例 2:
     * 输入: 0->1->2->NULL, k = 4
     * 输出: 2->0->1->NULL
     * 解释:
     * 向右旋转 1 步: 2->0->1->NULL
     * 向右旋转 2 步: 1->2->0->NULL
     * 向右旋转 3 步: 0->1->2->NULL
     * 向右旋转 4 步: 2->0->1->NULL
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        int n;
        ListNode old_Tall = head;
        for (n = 1; old_Tall.next != null;n++) {
            old_Tall = old_Tall.next;
        }
        old_Tall.next = head;
        ListNode new_Tall = head;
        for (int i = 0; i < n - k % n - 1; i++) {
            new_Tall = new_Tall.next;
        }
        ListNode new_Head = new_Tall.next;
        new_Tall.next = null;
        return new_Head;
    }
    /**
     * 83. 删除排序链表中的重复元素
     * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
     *
     * 示例 1:
     *
     * 输入: 1->1->2
     * 输出: 1->2
     *
     * 示例 2:
     *
     * 输入: 1->1->2->3->3
     * 输出: 1->2->3
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode p = head;
        while (p != null) {
            while (p.next != null && p.val == p.next.val) {
                p.next = p.next.next;
            }
            p = p.next;
        }
        return head;
    }
    /**
     * 141. 环形链表I
     *  给定一个链表，判断链表中是否有环。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     *
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     *
     * 示例 2：
     * 输入：head = [1,2], pos = 0
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     *
     * 示例 3：
     * 输入：head = [1], pos = -1
     * 输出：false
     * 解释：链表中没有环。
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow=head;
        ListNode fast=head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
    /**
     * 142. 环形链表II
     *
     * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     * 说明：不允许修改给定的链表。
     *
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：tail connects to node index 1
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     *
     * 示例 2：
     * 输入：head = [1,2], pos = 0
     * 输出：tail connects to node index 0
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     *
     * 示例 3：
     * 输入：head = [1], pos = -1
     * 输出：no cycle
     * 解释：链表中没有环。
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow=head;
        ListNode fast = getInster(head);
        if (fast == null) {
            return null;
        }
        while (fast != slow) {
            fast=fast.next;
            slow=slow.next;
        }
        return fast;
    }
    private ListNode getInster(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast=head;
        ListNode slow=head;
        while (fast != null && fast.next != null) {
            slow=slow.next;
            fast=fast.next.next;
            if (slow == fast) {
                return fast;
            }
        }
        return null;
    }
    /**
     * 160. 相交链表
     * 编写一个程序，找到两个单链表相交的起始节点。
     * 如下面的两个链表：
     * 在节点 c1 开始相交。
     *
     * 示例 1：
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Reference of the node with value = 8
     * 输入解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，
     * 链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     *
     * 示例 2：
     * 输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
     * 输出：Reference of the node with value = 2
     * 输入解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，
     * 链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
     *
     * 示例 3：
     * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * 输出：null
     *
     * 输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，
     * 所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
     * 解释：这两个链表不相交，因此返回 null。
     *
     * 注意：
     *     如果两个链表没有交点，返回 null.
     *     在返回结果后，两个链表仍须保持原有的结构。
     *     可假定整个链表结构中没有循环。
     *     程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        ListNode aNode = headA;
        ListNode bNode = headB;
        while (aNode != bNode) {
            aNode = aNode == null ? headB : aNode.next;
            bNode = bNode == null ? headA : bNode.next;
        }
        return aNode;
    }
    /**
     * 206 面试题24. 反转链表
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     *
     * 示例:
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     *
     * 限制：
     * 0 <= 节点个数 <= 5000
     */
    public ListNode reverseList01(ListNode head) {
        //方式一,新建一条链表
        ListNode reverseNode = new ListNode(0);
        ListNode p=head;
        ListNode curr;
        while (p != null) {
            curr=p.next;
            p.next=reverseNode.next;
            reverseNode.next=p;
            p=curr;
        }
        return reverseNode.next;
    }
    public ListNode reverseList02(ListNode head) {
        //方式二,原链表是反转
        ListNode pre=null;
        ListNode curr=head;
        while (curr != null) {
            ListNode next=curr.next;
            curr.next=pre;
            pre=curr;
            curr=next;
        }
        return pre;
    }
    /**
     * 234. 回文链表
     * 请判断一个链表是否为回文链表。
     *
     * 示例 1:
     * 输入: 1->2
     * 输出: false
     *
     * 示例 2:
     * 输入: 1->2->2->1
     * 输出: true
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        if (head.next.next == null) {
            return head.val == head.next.val;
        }
        ListNode slow = head;
        ListNode fast = head;
        //寻找中节点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = reverseNode(slow);
        ListNode preNode = head;
        ListNode lastNode = fast;
        while (preNode != lastNode) {
            if (preNode.val != lastNode.val) {
                return false;
            }
            if (preNode.next == lastNode) {
                break;
            }
            preNode = preNode.next;
            lastNode = lastNode.next;
        }
        reverseNode(preNode);
        return true;
    }
    private ListNode reverseNode(ListNode node) {
        //反转链表
        ListNode pre = null;
        ListNode curr = node;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
    /**
     * 237. 删除链表中的节点
     * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
     * 现有一个链表 -- head = [4,5,1,9]，它可以表示为:
     *      4->5->1->9
     *
     * 示例 1:
     * 输入: head = [4,5,1,9], node = 5
     * 输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     *
     * 示例 2:
     * 输入: head = [4,5,1,9], node = 1
     * 输出: [4,5,9]
     * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
     *
     * 说明:
     *     链表至少包含两个节点。
     *     链表中所有节点的值都是唯一的。
     *     给定的节点为非末尾节点并且一定是链表中的一个有效节点。
     *     不要从你的函数中返回任何结果。
     */
    public void deleteNode(ListNode node) {
        node.val=node.next.val;
        node.next=node.next.next;
    }
    /**
     * 328. 奇偶链表
     *
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
     *
     * 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
     *
     * 示例 1:
     *
     * 输入: 1->2->3->4->5->NULL
     * 输出: 1->3->5->2->4->NULL
     *
     * 示例 2:
     *
     * 输入: 2->1->3->5->6->4->7->NULL
     * 输出: 2->3->6->7->1->5->4->NULL
     *
     * 说明:
     *
     *     应当保持奇数节点和偶数节点的相对顺序。
     *     链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
    /**
     * 876. 链表的中间结点
     * 给定一个带有头结点 head 的非空单链表，返回链表的中间结点。
     * 如果有两个中间结点，则返回第二个中间结点。
     *
     * 示例 1：
     * 输入：[1,2,3,4,5]
     * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
     * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
     * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
     * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
     *
     * 示例 2：
     * 输入：[1,2,3,4,5,6]
     * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
     * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
     *
     * 提示：
     *     给定链表的结点数介于 1 和 100 之间。
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    /**
     * 1206. 设计跳表
     * 不使用任何库函数，设计一个跳表。
     *
     * 跳表是在 O(log(n)) 时间内完成增加、删除、搜索操作的数据结构。跳表相比于树堆与红黑树，其功能与性能相当，并且跳表的代码长度相较下更短，其设计思想与链表相似。
     *
     * 例如，一个跳表包含 [30, 40, 50, 60, 70, 90]，然后增加 80、45 到跳表中，以下图的方式操作：
     *
     *
     * Artyom Kalinin [CC BY-SA 3.0], via Wikimedia Commons
     *
     * 跳表中有很多层，每一层是一个短的链表。在第一层的作用下，增加、删除和搜索操作的时间复杂度不超过 O(n)。跳表的每一个操作的平均时间复杂度是 O(log(n))，空间复杂度是 O(n)。
     *
     * 在本题中，你的设计应该要包含这些函数：
     *
     *     bool search(int target) : 返回target是否存在于跳表中。
     *     void add(int num): 插入一个元素到跳表。
     *     bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
     *
     * 了解更多 : https://en.wikipedia.org/wiki/Skip_list
     *
     * 注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
     *
     * 样例:
     *
     * Skiplist skiplist = new Skiplist();
     *
     * skiplist.add(1);
     * skiplist.add(2);
     * skiplist.add(3);
     * skiplist.search(0);   // 返回 false
     * skiplist.add(4);
     * skiplist.search(1);   // 返回 true
     * skiplist.erase(0);    // 返回 false，0 不在跳表中
     * skiplist.erase(1);    // 返回 true
     * skiplist.search(1);   // 返回 false，1 已被擦除
     *
     * 约束条件:
     *
     *     0 <= num, target <= 20000
     *     最多调用 50000 次 search, add, 以及 erase操作。
     */
    class Skiplist {
        private static final float SKIPLIST_P = 0.5f;
        private static final int MAX_LEVEL = 16;
        private int levelCount = 1;
        private Node head = new Node();
        public Skiplist() { }
        public boolean search(int target) {
            Node p = head;
            for (int i = levelCount - 1; i >= 0; i--) {
                while (p.forward[i] != null && p.forward[i].date < target) {
                    p = p.forward[i];
                }
            }
            if (p.forward[0] != null && p.forward[0].date == target) {
                return true;
            } else {
                return false;
            }
        }
        public void add(int num) {
            int level = randomLevel();
            Node newNode = new Node();
            newNode.date = num;
            newNode.maxLevel = level;
            Node update[] = new Node[level];
            for (int i = 0; i < level; i++) {
                update[i] = head;
            }
            Node p = head;
            for (int i = level - 1; i >= 0; i--) {
                while (p.forward[i] != null && p.forward[i].date < num) {
                    p = p.forward[i];
                }
                update[i] = p;
            }
            for (int i = 0; i < level; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
            if (levelCount < level) {
                levelCount = level;
            }
        }
        public boolean erase(int num) {
            Node update[] = new Node[levelCount];
            Node p = head;
            for (int i = levelCount - 1; i >= 0; i--) {
                while (p.forward[i] != null && p.forward[i].date < num) {
                    p = p.forward[i];
                }
                update[i] = p;
            }
            if (p.forward[0] != null && p.forward[0].date == num) {
                for (int i = levelCount - 1; i >= 0; i--) {
                    if (update[i].forward[i] != null && update[i].forward[i].date == num) {
                        update[i].forward[i] = update[i].forward[i].forward[i];
                    }
                }
            } else {
                return false;
            }
            while (levelCount > 1 && head.forward[levelCount] == null) {
                levelCount--;
            }
            return true;
        }
        public int randomLevel() {
            int level = 1;
            while (Math.random() < SKIPLIST_P && level < MAX_LEVEL) {
                level += 1;
            }
            return level;
        }
        public class Node{
            private int date = -1;
            private Node forward[] = new Node[MAX_LEVEL];
            private int maxLevel = 0;
        }
    }
    /**
     * 1290. 二进制链表转整数
     *
     * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
     *
     * 请你返回该链表所表示数字的 十进制值 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：head = [1,0,1]
     * 输出：5
     * 解释：二进制数 (101) 转化为十进制数 (5)
     *
     * 示例 2：
     *
     * 输入：head = [0]
     * 输出：0
     *
     * 示例 3：
     *
     * 输入：head = [1]
     * 输出：1
     *
     * 示例 4：
     *
     * 输入：head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
     * 输出：18880
     *
     * 示例 5：
     *
     * 输入：head = [0,0]
     * 输出：0
     *
     *
     *
     * 提示：
     *
     *     链表不为空。
     *     链表的结点总数不超过 30。
     *     每个结点的值不是 0 就是 1。
     */
    public int getDecimalValue(ListNode head) {
        int num = 0;
        while (head != null) {
            num = num * 2 + head.val;
            head = head.next;
        }
        return num;
    }

    /**
     * 面试题 02.01. 移除重复节点
     *编写代码，移除未排序链表中的重复节点。保留最开始出现的节点。
     *
     * 示例1:
     *
     *  输入：[1, 2, 3, 3, 2, 1]
     *  输出：[1, 2, 3]
     *
     * 示例2:
     *
     *  输入：[1, 1, 1, 1, 2]
     *  输出：[1, 2]
     *
     * 提示：
     *
     *     链表长度在[0, 20000]范围内。
     *     链表元素在[0, 20000]范围内。
     *
     * 进阶：
     *
     * 如果不得使用临时缓冲区，该怎么解决？
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        if (head == null) {
            return null;
        }
        Set<Integer> set = new HashSet<>();
        set.add(head.val);
        ListNode p = head;
        while (p.next != null) {
            ListNode curr = p.next;
            if (set.add(curr.val)) {
                p = p.next;
            } else {
                p.next = p.next.next;
            }
        }
        return head;
    }
    /**
     * 面试题 02.02. 返回倒数第 k 个节点
     *
     * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
     *
     * 注意：本题相对原题稍作改动
     *
     * 示例：
     *
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     *
     * 说明：
     *
     * 给定的 k 保证是有效的。
     */
    public int kthToLast(ListNode head, int k) {
        ListNode slow = head, fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow.val;
    }
    /**
     * 面试题 02.03. 删除中间节点
     *
     * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
     *
     *
     *
     * 示例：
     *
     * 输入：单向链表a->b->c->d->e->f中的节点c
     * 结果：不返回任何数据，但该链表变为a->b->d->e->f
     */
    public void deleteNode2(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
    /**
     * 面试题 02.06. 回文链表
     *
     * 编写一个函数，检查输入的链表是否是回文的。
     *
     *
     *
     * 示例 1：
     *
     * 输入： 1->2
     * 输出： false
     *
     * 示例 2：
     *
     * 输入： 1->2->2->1
     * 输出： true
     *
     *
     *
     * 进阶：
     * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
     */
    public boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        if (head.next.next == null) {
            return head.val == head.next.val;
        }
        ListNode slow = head;
        ListNode fast = head;
        //寻找中节点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = reverseNode(slow);
        ListNode preNode = head;
        ListNode lastNode = fast;
        while (preNode != lastNode) {
            if (preNode.val != lastNode.val) {
                return false;
            }
            if (preNode.next == lastNode) {
                break;
            }
            preNode = preNode.next;
            lastNode = lastNode.next;
        }
        reverseNode(preNode);
        return true;
    }
    private ListNode reverseNode2(ListNode node) {
        //反转链表
        ListNode pre = null;
        ListNode curr = node;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
    /**
     * 面试题 02.07. 链表相交
     *
     * 给定两个（单向）链表，判定它们是否相交并返回交点。请注意相交的定义基于节点的引用，而不是基于节点的值。换句话说，如果一个链表的第k个节点与另一个链表的第j个节点是同一节点（引用完全相同），则这两个链表相交。
     *
     * 示例 1：
     *
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Reference of the node with value = 8
     * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     *
     *
     * 示例 2：
     *
     * 输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
     * 输出：Reference of the node with value = 2
     * 输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
     *
     *
     * 示例 3：
     *
     * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * 输出：null
     * 输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
     * 解释：这两个链表不相交，因此返回 null。
     *
     *
     * 注意：
     *
     *     如果两个链表没有交点，返回 null 。
     *     在返回结果后，两个链表仍须保持原有的结构。
     *     可假定整个链表结构中没有循环。
     *     程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
     */
    public ListNode getIntersectionNode31(ListNode headA, ListNode headB) {
        ListNode aNode = headA;
        ListNode bNode = headB;
        while (aNode != bNode) {
            aNode = aNode == null ? headB : aNode.next;
            bNode = bNode == null ? headA : bNode.next;
        }
        return aNode;
    }
    public ListNode getIntersectionNode32(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                break;
            }
            headB = headB.next;
        }
        return headB;
    }
    /**
     * 面试题06. 从尾到头打印链表
     * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
     *
     * 示例 1：
     * 输入：head = [1,3,2]
     * 输出：[2,3,1]
     *
     * 限制：
     * 0 <= 链表长度 <= 10000
     */
    public int[] reversePrint(ListNode head) {
        ListNode p = head;
        Stack<ListNode> stack = new Stack<>();
        while (p != null) {
            stack.push(p);
            p = p.next;
        }
        int len = stack.size();
        int arr[] = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stack.pop().val;
        }
        return arr;
    }
    /**
     * 面试题22. 链表中倒数第k个节点
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
     *
     * 例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。
     *
     * 示例：
     * 给定一个链表: 1->2->3->4->5, 和 k = 2.
     * 返回链表 4->5.
     */
    public ListNode getKthFromEnd1(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        int size = getLength(head);
        if (k <= 0 || k > size) {
            return null;
        }
        ListNode curr=head;
        for (int i = 0; i < size - k; i++) {
            curr = curr.next;
        }
        return curr;
    }
    public int getLength(ListNode head) {
        //求链表长度
        if (head == null) {
            return 0;
        }
        int len=0;
        ListNode curr=head;
        while (curr != null) {
            len++;
            curr=curr.next;
        }
        return len;
    }
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 0; i < k; i++) {
            first = first.next;
        }
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        return second.next;
    }

    /**
     * 剑指 Offer 18. 删除链表的节点
     * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
     *
     * 返回删除后的链表的头节点。
     *
     * 注意：此题对比原题有改动
     *
     * 示例 1:
     *
     * 输入: head = [4,5,1,9], val = 5
     * 输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     *
     * 示例 2:
     *
     * 输入: head = [4,5,1,9], val = 1
     * 输出: [4,5,9]
     * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
     *
     *
     *
     * 说明：
     *
     *     题目保证链表中节点的值互不相同
     *     若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head.val == val) {
            return head.next;
        }
        ListNode p = head;
        while (p != null && p.next != null) {
            if (p.next.val == val) {
                p.next = p.next.next;
                break;
            }
            p = p.next;
        }
        return head;
    }
    /**
     * 剑指 Offer 52. 两个链表的第一个公共节点
     *
     * 输入两个链表，找出它们的第一个公共节点。
     *
     * 如下面的两个链表：
     *
     * 在节点 c1 开始相交。
     *
     *
     *
     * 示例 1：
     *
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Reference of the node with value = 8
     * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     *
     *
     *
     * 示例 2：
     *
     * 输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
     * 输出：Reference of the node with value = 2
     * 输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
     *
     *
     *
     * 示例 3：
     *
     * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * 输出：null
     * 输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
     * 解释：这两个链表不相交，因此返回 null。
     *
     *
     *
     * 注意：
     *
     *     如果两个链表没有交点，返回 null.
     *     在返回结果后，两个链表仍须保持原有的结构。
     *     可假定整个链表结构中没有循环。
     *     程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
     *     本题与主站 160 题相同：https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
     */
    public ListNode getIntersectionNode21(ListNode headA, ListNode headB) {
        ListNode aNode = headA;
        ListNode bNode = headB;
        while (aNode != bNode) {
            aNode = aNode == null ? headB : aNode.next;
            bNode = bNode == null ? headA : bNode.next;
        }
        return aNode;
    }
    public ListNode getIntersectionNode22(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                break;
            }
            headB = headB.next;
        }
        return headB;
    }
}
