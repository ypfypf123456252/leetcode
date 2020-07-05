package 排序;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] arr = {1, 6, 8, 4, 8, 9, 8, 4};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 堆排序
     */
    private static void heapSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        //建堆
        buildHeap(arr);
        //排序
        int k = arr.length - 1;
        while (k > 0) {
            //栈顶元素和数组最后一个元素交换位置
            swap(arr, k, 0);
            //将剩下的元素继续堆化,依次堆化直到根节点
            heapify(arr, --k, 0);
        }
    }
    private static void buildHeap(int[] arr) {
        for (int i = (arr.length) / 2; i >= 0; i--) {
            heapify(arr, arr.length - 1, i);
        }
    }
    private static void heapify(int[] arr, int n, int i) {
        while (true) {
            //最大值位置
            int maxPos = i;
            if (2 * i + 1 <= n && arr[i] < arr[2 * i + 1]) {
                maxPos = 2 * i + 1;
            }
            if (2 * i + 2 <= n && arr[maxPos] < arr[2 * i + 2]) {
                maxPos = 2 * i + 2;
            }
            if (maxPos == i) {
                break;
            }
            swap(arr, i, maxPos);
            i = maxPos;
        }
    }
    private static void swap(int[] arr, int i, int maxNum) {
        int temp = arr[i];
        arr[i] = arr[maxNum];
        arr[maxNum] = temp;
    }

}
