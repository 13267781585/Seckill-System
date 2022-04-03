package test.sort;

import org.junit.platform.commons.util.CollectionUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {6,3,8,2,2,0,0,2,4,2,2,6,-1,-4,0,10,0,-11,90};
        simpleSort(arr);
        //xierSort(arr);
        //quickSort(arr,0,arr.length-1);
        heapSort(arr);
        //guibing(arr,0,arr.length-1,new int[arr.length]);
        //maopao(arr);
        //xuanzhe(arr);
        jishu(arr);
        //tongpaixu(arr);
        System.out.println(Arrays.toString(arr));
    }
    //shi n+k kong k wending
    public static void jishu(int[] arr)
    {
        int len = arr.length;
        int min = arr[0],max = arr[0];
        for(int i:arr)
        {
            min = Math.min(min,i);
            max = Math.max(max,i);
        }
        int arrLen = max - min + 1;
        int[] temp = new int[arrLen];
        for(int i:arr){
            temp[i-min]++;
        }
        int index = 0;
        for(int i=0;i<len;){
            if(temp[index]==0){
                index++;
            }else{
                temp[index]--;
                arr[i++] = index + min;
            }
        }
    }

    //shi nlogn kong 1 buwending
    public static void heapSort(int[] arr)
    {
        int len = arr.length - 1;
        buildMaxHeap(arr);
        while(len>0)
        {
            swap(arr,0,len);
            len--;
            adjustHeap(arr,0,len);
        }
    }

    public static void buildMaxHeap(int[] arr)
    {
        int len = arr.length;
        for(int i=len/2;i>=0;i--){
            adjustHeap(arr,i,len-1);
        }
    }

    public static void adjustHeap(int[] arr,int index,int len)
    {
        int maxIndex = index;
        int left = index * 2 + 1,right = index * 2 + 2;
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
            swap(arr,maxIndex,index);
            adjustHeap(arr,maxIndex,len);
        }
    }

    //shi nlogn kong n wending
    public static void guibing(int[] arr,int left,int right,int[] temp)
    {
        if(left<right)
        {
            int mid = left + (right - left) / 2;
            guibing(arr,left,mid,temp);
            guibing(arr,mid+1,right,temp);
            merger(arr,left,mid,right,temp);
        }
    }

    public static void merger(int[] arr,int left,int mid,int right,int[] temp)
    {
        int l = left,r = mid + 1,res = 0;
        while(l<=mid&&r<=right)
        {
            if(arr[l]<=arr[r])
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
        while(r<=right)
        {
            temp[res++] = arr[r++];
        }
        l = left;
        res = 0;
        while(l<=right)
        {
            arr[l++] = temp[res++];
        }
    }

    //shi nlogn kong logn buwending
    public static void quickSort(int[] arr,int left,int right)
    {
        if(left<right){
            int mid = partition(arr,left,right);
            quickSort(arr,left,mid-1);
            quickSort(arr,mid+1,right);
        }
    }

    public static int partition(int[] arr,int left,int right)
    {
        int key = arr[right];
        int l = left,r = right;
        while(l<r)
        {
            while(l<r&&arr[l]<key){
                l++;
            }
            while(l<r&&arr[r]>=key)
            {
                r--;
            }
            swap(arr,l,r);
        }
        swap(arr,right,l);
        return l;
    }

    //shi n^2 kong 1 wending
    public static void simpleSort(int[] arr)
    {
        int len = arr.length;
        for(int i=0;i<len;i++)
        {
            int index = i;
            while(index-1>=0&&arr[index-1]>arr[index])
            {
                swap(arr,index-1,index);
                index--;
            }
        }
    }

    //shi logn kong 1 buwending
    public static void xierSort(int[] arr)
    {
        int len = arr.length;
        int pos = len / 2;
        while(pos>0){
            for(int i=1;i<len;i++){
                int index = i;
                while(index-pos>=0&&arr[index-pos]>arr[index])
                {
                    swap(arr,index,index-pos);
                    index -= pos;
                }
            }
            pos /= 2;
        }
    }


    //shi n^2 kong 1 wending
    public static void maopao(int[] arr)
    {
        int len = arr.length;
        for(int i=0;i<len;i++)
        {
            boolean flag = true;
            for(int j=0;j<len-i-1;j++){
                if(arr[j]<arr[j+1]){
                    swap(arr,j,j+1);
                    flag = false;
                }
            }
            if(flag)
            {
                break;
            }
        }
    }

    //shi n^2 kong 1 buwending
    public static void xuanzhe(int[] arr)
    {
        int len = arr.length;
        for(int i=0;i<len;i++)
        {
            int minIndex = i;
            for(int j=i;j<len;j++){
                if(arr[j]<arr[minIndex]){
                    minIndex = j;
                }
            }
            swap(arr,minIndex,i);
        }
    }

    public static void swap(int[] arr,int i,int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
