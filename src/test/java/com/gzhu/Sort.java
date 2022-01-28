package com.gzhu;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] arr = {9,4,88,2,6,6};
        int[] temp = new int[arr.length];
//        binggui(arr,0,arr.length-1,temp);
//        kuaipai1(arr,0,arr.length-1);
//        dui(arr);
        heapSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    public static void heapSort(int[] arr){
        buildMaxDui11(arr);
        int len = arr.length-1;
        while(len>=0)
        {
            int temp = arr[len];
            arr[len] = arr[0];
            arr[0] = temp;
            adjustDui11(arr,0,--len);
        }
    }

    public static void buildMaxDui11(int[] arr)
    {
        int len = arr.length-1;
        for(int i=len/2;i>=0;i--){
            adjustDui11(arr,i,len);
        }
    }

    public static void adjustDui11(int[] arr,int index,int len)
    {
        int maxIndex = index;
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        if(left<=len&&arr[left]>arr[maxIndex])
        {
            maxIndex = left;
        }
        if(right<=len&&arr[right]>arr[maxIndex])
        {
            maxIndex = right;
        }
        if(maxIndex!=index)
        {
            int temp = arr[maxIndex];
            arr[maxIndex] = arr[index];
            arr[index] = temp;
            adjustDui11(arr,maxIndex,len);
        }

    }

    public static void headSort1(int[] arr){
        int len = arr.length-1;
        buildMaxDui1(arr);
        while(len>=0)
        {
            int temp = arr[len];
            arr[len] = arr[0];
            arr[0] = temp;
            adjustDui1(arr,0,--len);
        }
    }

    public static void buildMaxDui1(int[] arr)
    {
        int len = arr.length-1;
        for(int i=len/2;i>=0;i--)
        {
            adjustDui1(arr,i,len);
        }
    }

    public static void adjustDui1(int[] arr,int index,int len)
    {
        int maxIndex = index;
        int left = index*2+1;
        int right = index*2+2;
        if(left<=len&&arr[left]>arr[maxIndex])
        {
            maxIndex = left;
        }
        if(right<=len&&arr[right]>arr[maxIndex])
        {
            maxIndex = right;
        }
        if(index!=maxIndex)
        {
            int temp = arr[index];
            arr[index] = arr[maxIndex];
            arr[maxIndex] = temp;
            adjustDui1(arr,maxIndex,len);
        }
    }

    public static void binggui(int[] arr,int left,int right,int[] temp){
        if(left<right)
        {
            int mid = (left + right) / 2;
            binggui(arr,left,mid,temp);
            binggui(arr,mid+1,right,temp);
            merge1(arr,left,mid,right,temp);
        }
    }

    public static void merge1(int[] arr,int left,int mid,int right,int[] temp)
    {
        int l = left;
        int r = mid + 1;
        int res = 0;
        while(l<=mid&&r<=right){
            if(arr[l]<arr[r])
            {
                temp[res++] = arr[l++];
            }else{
                temp[res++] = arr[r++];
            }
        }
        while(l<=mid)
        {
            temp[res++] = arr[l++];
        }
        while(r<=right){
            temp[res++] = arr[r++];
        }
        l = left;
        res = 0;
        while(l<=right){
            arr[l++] = temp[res++];
        }
    }

    public static void headSort(int[] arr){
        buildMaxHead(arr);
        int len = arr.length-1;
        while(len>=0)
        {
            int temp = arr[len];
            arr[len] = arr[0];
            arr[0] = temp;
            adjustHead(arr,0,--len);
        }
    }

    public static void buildMaxHead(int[] arr){
        for(int i=arr.length/2;i>=0;i--){
            adjustDui(arr,i,arr.length-1);
        }
    }

    public static void adjustHead(int[] arr,int index,int len)
    {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int maxCount = index;
        if(left<=len&&arr[left]>arr[maxCount]){
            maxCount = left;
        }
        if(right<=len&&arr[right]>arr[maxCount])
        {
            maxCount = right;
        }
        if(maxCount!=index)
        {
            int temp = arr[index];
            arr[index] = arr[maxCount];
            arr[maxCount] = temp;
            adjustDui(arr,maxCount,len);
        }
    }

    //快排
    public static void kuaipai(int[] arr,int left,int right){
        if(left<right){
            int pos = partition(arr,left,right);
            kuaipai(arr,left,pos-1);
            kuaipai(arr,pos+1,right);
        }
    }

    public static int partition(int[] arr,int left,int right){
        int key = arr[right];
        int l = left;
        int r = right;
        while(l<r){
            while(l<r&&arr[l]<=key){
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


    //归并排序
    public static void guibing(int[] arr,int left,int right,int[] temp){
        if(left<right){
            int mid = (left + right) / 2;
            guibing(arr,left,mid,temp);
            guibing(arr,mid+1,right,temp);
            merge(arr,left,mid,right,temp);
        }
    }

    public static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int l = left;
        int r = mid+1;
        int res = 0;
        while(l<=mid&&r<=right){
            if(arr[l]<arr[r]){
                temp[res++] = arr[l++];
            }else{
                temp[res++] = arr[r++];
            }
        }
        while(l<=mid){
            temp[res++] = arr[l++];
        }
        while(r<=right){
            temp[res++] = arr[r++];
        }
        res = 0;
        l = left;
        while(l<=right){
            arr[l++] = temp[res++];
        }
    }

    public static void dui(int[] arr){
        int len = arr.length-1;
        buildMaxDui(arr);
        while(len>0){
            int temp = arr[len];
            arr[len] = arr[0];
            arr[0] = temp;
            adjustDui(arr,0,--len);
        }
    }

    public static void buildMaxDui(int[] arr){
        int l = arr.length-1;
        for(int i=l/2;i>=0;i--){
            adjustDui(arr,i,l);
        }
    }

    public static void adjustDui(int[] arr,int index,int len){
        int maxIndex = index;
        if(index*2+1<=len&&arr[index*2+1]>arr[maxIndex]){
            maxIndex = index*2+1;
        }
        if(index*2+2<=len&&arr[index*2+2]>arr[maxIndex]){
            maxIndex = index*2+2;
        }
        if(index!=maxIndex){
            int temp = arr[index];
            arr[index] = arr[maxIndex];
            arr[maxIndex] = temp;
            adjustDui(arr,maxIndex,len);
        }
    }
}
