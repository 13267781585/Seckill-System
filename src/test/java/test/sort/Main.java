package test.sort;

import org.junit.platform.commons.util.CollectionUtils;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {6,3,8,2,2,0,0,2,4,2,2,6,-1,-4,0,10,0,-11,90};
        //simpleSort(arr);
        //xierSort(arr);
        //quickSort(arr,0,arr.length-1);
        //heapSort(arr);
        //guibing(arr,0,arr.length-1,new int[arr.length]);
        //maopao(arr);
        //xuanzhe(arr);
        //jishu(arr);
        tongpaixu(arr);
        System.out.println(Arrays.toString(arr));
    }
    //kong k shi n+k 稳定
    public static void tongpaixu(int[] arr)
    {
        int len = arr.length;
        int maxValue = arr[0];
        for(int i=0;i<len;i++)
        {
            maxValue = Math.max(maxValue,arr[i]);
        }
        int arrLen = maxValue / len + 1;
        ArrayList<ArrayList<Integer>> count = new ArrayList<>(arrLen);
        for(int i=0;i<arrLen;i++)
        {
            count.add(new ArrayList<>());
        }
        for(int i=0;i<len;i++){
            int index = arr[i] / len;
            count.get(index).add(arr[i]);
        }
        for(int i=0;i<arrLen;i++){
            Collections.sort(count.get(i));
        }
        int arrIndex = 0,countIndex = 0;
        while(arrIndex<len)
        {
            while(count.get(countIndex).size()==0){
                countIndex++;
            }
            ArrayList<Integer> list = count.get(countIndex);
            for(int temp:list){
                arr[arrIndex++] = temp;
            }
            countIndex++;
        }
    }
    //kong k shi n+k
    public static void jishu(int[] arr)
    {
        int maxValue = arr[0],minValue = arr[0];
        int len = arr.length;
        for(int i=0;i<len;i++)
        {
            maxValue = Math.max(maxValue,arr[i]);
            minValue = Math.min(minValue,arr[i]);
        }
        int arrLen = maxValue - minValue + 1;
        int[] count = new int[arrLen];
        for(int i=0;i<len;i++){
            count[arr[i]-minValue]++;
        }
        int countIndex = 0,arrIndex = 0;
        while(arrIndex<len){
            while(count[countIndex]==0){
                countIndex++;
            }
            arr[arrIndex] = countIndex + minValue;
            count[countIndex]--;
            arrIndex++;
        }
    }

    public static void xuanzhe(int[] arr)
    {
        int len = arr.length;
        for(int i=0;i<len-1;i++){
            int minIndex = i;
            for(int j=i;j<len;j++){
                if(arr[minIndex]>arr[j]){
                    minIndex = j;
                }
            }
            swap(arr,i,minIndex);
        }
    }

    public static void maopao(int[] arr)
    {
        int len = arr.length;
        for(int i=0;i<len;i++){
            boolean flag = true;
            for(int j=0;j<len-i-1;j++){
                if(arr[j]>arr[j+1]){
                    flag = false;
                    swap(arr,j,j+1);
                }
            }
            if(flag){
                break;
            }
        }
    }

    public static void swap(int[] arr,int i,int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
