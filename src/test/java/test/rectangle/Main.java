package test.rectangle;

import java.io.Externalizable;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Externalizable
        int[][] arr = {
                        {29,54,67,71,85},
                        {98,88,44,11,22},
                        {12,23,34,45,67},
                        {74,99,85,42,14},
                        {84,72,13,64,54}
        };
        //printRecTangle(arr);
        //kuangPring(arr);
        turn(arr);
        for(int[] t:arr)
            System.out.println(Arrays.toString(t));
    }

    public static void turn(int[][] arr){
        int ar = 0;
        int ac = 0;
        int br = arr.length-1;
        int bc = arr[0].length-1;
        while(ar<br&&ac<bc)
        {
            print2(arr,ar++,ac++,br--,bc--);
        }
    }
    public static void print2(int[][] arr,int ar,int ac,int br,int bc)
    {
        for(int i=0;i<bc-ac;i++)
        {
            int temp = arr[ar][ac + i];
            arr[ar][ac + i] = arr[br - i][ac];
            arr[br - i][ac] = arr[br][bc - i];
            arr[br][bc - i] = arr[ar + i][bc];
            arr[ar + i][bc] = temp;
        }
    }

    public static void kuangPring(int[][] arr){
        int ar = 0;
        int ac = 0;
        int br = arr.length-1;
        int bc = arr[0].length-1;
        while(ar<=br&&ac<=bc)
        {
            print1(arr,ar++,ac++,br--,bc--);
        }
    }
    public static void print1(int[][] arr,int ar,int ac,int br,int bc)
    {
        if(ar==br)
        {
            while(ac<=bc)
            {
                System.out.println(arr[ar][ac++]);
            }
        }else if(ac==bc)
        {
            while(ar<=br)
            {
                System.out.println(arr[ar++][ac]);
            }
        }else{
            for(int i=ac;i<bc;i++){
                System.out.println(arr[ar][i]);
            }
            for(int i=ar;i<br;i++){
                System.out.println(arr[i][bc]);
            }
            for(int i=bc;i>ac;i--){
                System.out.println(arr[br][i]);
            }
            for(int i=br;i>ar;i--){
                System.out.println(arr[i][ac]);
            }
        }
    }

    //折线打印
    public static void printRecTangle(int[][] arr)
    {
        int ar = 0;
        int ac = 0;
        int br = 0;
        int bc = 0;
        int endr = arr.length-1;
        int endc = arr[0].length-1;
        boolean leftToRight = true;
        while(ar+ac<=endr+endc)
        {
            printLine(arr,ar,ac,br,bc,leftToRight);
            ar = ac == endc ? ar + 1 : ar;
            ac = ac == endc ? ac : ac + 1;
            bc = br == endr ? bc + 1 : bc;
            br = br == endr ? br : br + 1;
            leftToRight = !leftToRight;
        }

    }

    public static void printLine(int[][] arr, int ar, int ac, int br, int bc, boolean leftToRight)
    {
        if(leftToRight)
        {
            //左下到右上
            while(ar<=br&&ac>=bc)
            {
                System.out.println(arr[br--][bc++] + " ");
            }
        }else{
            //右上到左下
            while(ar<=br&&ac>=bc)
            {
                System.out.println(arr[ar++][ac--] + " ");
            }
        }
    }
}
