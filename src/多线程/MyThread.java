package 多线程;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class MyThread {
    /**
     * 1114. 按序打印
     * 我们提供了一个类：
     *
     * public class Foo {
     *   public void one() { print("one"); }
     *   public void two() { print("two"); }
     *   public void three() { print("three"); }
     * }
     * 三个不同的线程将会共用一个 Foo 实例。
     *
     * 线程 A 将会调用 one() 方法
     * 线程 B 将会调用 two() 方法
     * 线程 C 将会调用 three() 方法
     * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。
     *
     *  
     *
     * 示例 1:
     *
     * 输入: [1,2,3]
     * 输出: "onetwothree"
     * 解释:
     * 有三个线程会被异步启动。
     * 输入 [1,2,3] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 two() 方法，线程 C 将会调用 three() 方法。
     * 正确的输出是 "onetwothree"。
     * 示例 2:
     *
     * 输入: [1,3,2]
     * 输出: "onetwothree"
     * 解释:
     * 输入 [1,3,2] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 three() 方法，线程 C 将会调用 two() 方法。
     * 正确的输出是 "onetwothree"。
     *
     * 注意:
     * 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
     * 你看到的输入格式主要是为了确保测试的全面性。
     */
    class Foo {
        private AtomicInteger integer = new AtomicInteger(1);
        public Foo() {}
        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            integer.getAndIncrement();
        }
        public void second(Runnable printSecond) throws InterruptedException {
            while (integer.get() != 2) {}
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            integer.getAndIncrement();
        }
        public void third(Runnable printThird) throws InterruptedException {
            while (integer.get() != 3) {}
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
    /**
     * 1115. 交替打印FooBar
     * 我们提供一个类：
     * class FooBar {
     *   public void foo() {
     *     for (int i = 0; i < n; i++) {
     *       print("foo");
     *     }
     *   }
     *   public void bar() {
     *     for (int i = 0; i < n; i++) {
     *       print("bar");
     *     }
     *   }
     * }
     * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
     * 请设计修改程序，以确保 "foobar" 被输出 n 次。
     *
     * 示例 1:
     *
     * 输入: n = 1
     * 输出: "foobar"
     * 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
     * 示例 2:
     *
     * 输入: n = 2
     * 输出: "foobarfoobar"
     * 解释: "foobar" 将被输出两次。
     */
    class FooBar {
        private int n;
        private AtomicInteger integer = new AtomicInteger();

        private Lock lock = new ReentrantLock();
        Condition fooCondition = lock.newCondition();
        Condition barCondition = lock.newCondition();
        public FooBar(int n) {
            this.n = n;
        }
        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                while (integer.get()!=0){
                    fooCondition.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                integer.getAndIncrement();
                barCondition.signal();
                lock.unlock();
            }
        }
        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                lock.lock();
                while (integer.get()!=1){
                    barCondition.await();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                integer.getAndDecrement();
                fooCondition.signal();
                lock.unlock();
            }
        }
    }
    /**
     * 1116. 打印零与奇偶数
     * 假设有这么一个类：
     * class ZeroEvenOdd {
     *   public ZeroEvenOdd(int n) { ... }      // 构造函数
     *   public void zero(printNumber) { ... }  // 仅打印出 0
     *   public void even(printNumber) { ... }  // 仅打印出 偶数
     *   public void odd(printNumber) { ... }   // 仅打印出 奇数
     * }
     * 相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程：
     * 线程 A 将调用 zero()，它只输出 0 。
     * 线程 B 将调用 even()，它只输出偶数。
     * 线程 C 将调用 odd()，它只输出奇数。
     * 每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。
     *
     * 示例 1：
     * 输入：n = 2
     * 输出："0102"
     * 说明：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
     *
     * 示例 2：
     * 输入：n = 5
     * 输出："0102030405"
     */
    class ZeroEvenOdd {
        private int n;
        private Lock lock = new ReentrantLock();
        Condition zeroCondition = lock.newCondition();
        Condition evenCondition = lock.newCondition();
        Condition oddCondition = lock.newCondition();
        private int x = 0;
        public ZeroEvenOdd(int n) {
            this.n = n;
        }
        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                lock.lock();
                while (x != 0) {
                    zeroCondition.await();
                }
                printNumber.accept(0);
                if (i % 2 == 0) {
                    x = 2;
                    evenCondition.signal();
                } else {
                    x = 1;
                    oddCondition.signal();
                }
                lock.unlock();
            }
        }
        public void even(IntConsumer printNumber) throws InterruptedException {
            for (int i = 2; i <= n; i+=2) {
                lock.lock();
                while (x != 2) {
                    evenCondition.await();
                }
                printNumber.accept(i);
                x = 0;
                zeroCondition.signal();
                lock.unlock();
            }
        }
        public void odd(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i+=2) {
                lock.lock();
                while (x != 1) {
                    oddCondition.await();
                }
                printNumber.accept(i);
                x = 0;
                zeroCondition.signal();
                lock.unlock();
            }
        }
    }
    /**
     * 1117. H2O 生成
     * 现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
     * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
     * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
     * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
     * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
     * 换句话说:
     * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
     * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
     * 书写满足这些限制条件的氢、氧线程同步代码。
     *
     * 示例 1:
     * 输入: "HOH"
     * 输出: "HHO"
     * 解释: "HOH" 和 "OHH" 依然都是有效解。
     *
     * 示例 2:
     * 输入: "OOHHHH"
     * 输出: "HHOHHO"
     * 解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和 "OHHOHH" 依然都是有效解。
     *
     * 提示：
     * 输入字符串的总长将会是 3n, 1 ≤ n ≤ 50；
     * 输入字符串中的 “H” 总数将会是 2n 。
     * 输入字符串中的 “O” 总数将会是 n 。
     */
    class H2O {
        private Semaphore hsemaphore = new Semaphore(2);
        private Semaphore osemaphore = new Semaphore(0);
        public H2O() { }
        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            hsemaphore.acquire(1);
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseHydrogen.run();
            osemaphore.release(1);
        }
        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            osemaphore.acquire(2);
            // releaseOxygen.run() outputs "O". Do not change or remove this line.
            releaseOxygen.run();
            hsemaphore.release(2);
        }
    }
    /**
     * 1195. 交替打印字符串
     * 编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：
     * 如果这个数字可以被 3 整除，输出 "fizz"。
     * 如果这个数字可以被 5 整除，输出 "buzz"。
     * 如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
     * 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。
     * 假设有这么一个类：
     * class FizzBuzz {
     *   public FizzBuzz(int n) { ... }               // constructor
     *   public void fizz(printFizz) { ... }          // only output "fizz"
     *   public void buzz(printBuzz) { ... }          // only output "buzz"
     *   public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
     *   public void number(printNumber) { ... }      // only output the numbers
     * }
     * 请你实现一个有四个线程的多线程版  FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：
     * 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
     * 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
     * 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
     * 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
     */
    class FizzBuzz { }
    /**
     * 1226. 哲学家进餐
     * 5 个沉默寡言的哲学家围坐在圆桌前，每人面前一盘意面。叉子放在哲学家之间的桌面上。（5 个哲学家，5 根叉子）
     * 所有的哲学家都只会在思考和进餐两种行为间交替。哲学家只有同时拿到左边和右边的叉子才能吃到面，
     * 而同一根叉子在同一时间只能被一个哲学家使用。每个哲学家吃完面后都需要把叉子放回桌面以供其他哲学家吃面。
     * 只要条件允许，哲学家可以拿起左边或者右边的叉子，但在没有同时拿到左右叉子时不能进食。
     *
     * 假设面的数量没有限制，哲学家也能随便吃，不需要考虑吃不吃得下。
     *
     * 设计一个进餐规则（并行算法）使得每个哲学家都不会挨饿；也就是说，在没有人知道别人什么时候想吃东西或思考的情况下，每个哲学家都可以在吃饭和思考之间一直交替下去。
     *
     * 哲学家从 0 到 4 按 顺时针 编号。请实现函数 void wantsToEat(philosopher, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork)：
     *
     * philosopher 哲学家的编号。
     * pickLeftFork 和 pickRightFork 表示拿起左边或右边的叉子。
     * eat 表示吃面。
     * putLeftFork 和 putRightFork 表示放下左边或右边的叉子。
     * 由于哲学家不是在吃面就是在想着啥时候吃面，所以思考这个方法没有对应的回调。
     * 给你 5 个线程，每个都代表一个哲学家，请你使用类的同一个对象来模拟这个过程。在最后一次调用结束之前，可能会为同一个哲学家多次调用该函数。
     *
     * 示例：
     * 输入：n = 1
     * 输出：[[4,2,1],[4,1,1],[0,1,1],[2,2,1],[2,1,1],[2,0,3],[2,1,2],[2,2,2],[4,0,3],[4,1,2],
     * [0,2,1],[4,2,2],[3,2,1],[3,1,1],[0,0,3],[0,1,2],[0,2,2],[1,2,1],[1,1,1],[3,0,3],[3,1,2],[3,2,2],[1,0,3],[1,1,2],[1,2,2]]
     * 解释:
     * n 表示每个哲学家需要进餐的次数。
     * 输出数组描述了叉子的控制和进餐的调用，它的格式如下：
     * output[i] = [a, b, c] (3个整数)
     * - a 哲学家编号。
     * - b 指定叉子：{1 : 左边, 2 : 右边}.
     * - c 指定行为：{1 : 拿起, 2 : 放下, 3 : 吃面}。
     * 如 [4,2,1] 表示 4 号哲学家拿起了右边的叉子。
     *
     * 提示：
     * 1 <= n <= 60
     */
    class DiningPhilosophers {
        private Semaphore semaphore = new Semaphore(4);
        private Lock lockList[] = {
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock()
        };
        public DiningPhilosophers() { }

        // call the run() method of any runnable to execute its code
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int leftFork = (philosopher + 1) % 5;
            int rightFork = philosopher;

            semaphore.acquire();
            lockList[leftFork].lock();
            lockList[rightFork].lock();

            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putLeftFork.run();
            putRightFork.run();

            lockList[leftFork].unlock();
            lockList[rightFork].unlock();
            semaphore.release();
        }
    }

}
