package test.sort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {6,3,8,4,2,2,6,};
        //quickSort(arr,0,arr.length-1);
//        HeapSort(arr);
        guibing(arr,0,arr.length-1,new int[arr.length]);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr,int left,int right){
        if(left<right){
            int pos = partition(arr,left,right);
            quickSort(arr,left,pos-1);
            quickSort(arr,pos+1,right);
        }
    }

    public static int partition(int[] arr,int left,int right){
        int key = arr[right];
        int l = left;
        int r = right;
        while(l<r){
            while(l<r&&arr[l]<=key)
            {
                l++;
            }
            while(l<r&&arr[r]>=key){
                r--;
            }
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
        }
        arr[right] = arr[l];
        arr[l] = key;
        return l;
    }

//    public static void headSort(int[] arr)
//    {
//        int len = arr.length-1;
//        buildMaxHead(arr);
//        while(len>0){
//            int temp = arr[0];
//            arr[0] = arr[len];
//            arr[len] = temp;
//            adjustHead(arr,0,--len);
//        }
//    }
//    public static void buildMaxHead(int[] arr){
//        for(int i=arr.length/2;i>=0;i--){
//            adjustHead(arr,i,arr.length-1);
//        }
//    }
//    public static void adjustHead(int[] arr,int index,int len){
//        int left = index * 2 + 1;
//        int right = index * 2 + 2;
//        int maxIndex = index;
//        if(left<=len&&arr[left]>arr[maxIndex]){
//            maxIndex = left;
//        }
//        if(right<=len&&arr[right]>arr[maxIndex])
//        {
//            maxIndex = right;
//        }
//        if(maxIndex!=index)
//        {
//            int temp = arr[maxIndex];
//            arr[maxIndex] = arr[index];
//            arr[index] = temp;
//            adjustHead(arr,maxIndex,len);
//        }
//    }


    public static int[] HeapSort(int[] array) {
        int len = array.length-1;
        if (len == 0) return array;
        //1.构建一个大根堆
        buildMaxHeap(array);
        //2.循环将堆顶（最大值）与堆尾交换，删除堆尾元素，然后重新调整大根堆
        while (len > 0) {
            swap(array, 0, len);
            len--; //原先的堆尾进入有序区，删除堆尾元素
            adjustHeap(array, 0,len); //重新调整大根堆
        }
        return array;
    }

    /**
     * 自顶向下调整以 i 为根的堆为大根堆
     * @param array
     * @param i
     */
    public static void adjustHeap(int[] array, int i,int len) {
        int maxIndex = i;
        //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
        if (2 * i + 1 < len && array[2 * i + 1] > array[maxIndex])
            maxIndex = 2 * i + 1;
        //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
        if (2 * i + 2 < len && array[2 * i + 2] > array[maxIndex])
            maxIndex = 2 * i + 2;
        //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
        if (maxIndex != i) {
            swap(array, maxIndex, i);
            adjustHeap(array, maxIndex,len);
        }
    }

    /**
     * 自底向上构建初始大根堆
     * @param array
     */
    public static void buildMaxHeap(int[] array) {
        //从最后一个非叶子节点开始自底向上构造大根堆
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            adjustHeap(array, i, array.length-1);
        }
    }

    public static void swap(int[] arr,int var1,int var2)
    {
        int temp = arr[var1];
        arr[var1] = arr[var2];
        arr[var2] = temp;
    }

    public static void guibing(int[] arr,int left,int right,int[] temp){
        if(left<right){
            int mid = (left + right) / 2;
            guibing(arr,left,mid,temp);
            guibing(arr,mid+1,right,temp);
            merger(arr,left,right,temp);
        }
    }
    public static void merger(int[] arr,int left,int right,int[] temp)
    {
        int l = left;
        int mid = (left + right) / 2;
        int r = mid + 1;
        int res = 0;
        while(l<=mid&&r<=right)
        {
            if(arr[l]<arr[r]){
                temp[res++] = arr[r++];
            }else{
                temp[res++] = arr[l++];
            }
        }
        while(l<=mid)
        {
            temp[res++] = arr[l++];
        }
        while(r<=right)
        {
            temp[res++] = arr[r++];
        }
        l = left;
        res = 0;
        while (l<=right){
            arr[l++] = temp[res++];
        }
    }
}
