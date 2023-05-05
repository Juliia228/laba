package src;

import src.Main.Rect;

public class TestData {
    public static Rect[] CreateRectanglesArray(int n){
        Rect[] array = new Rect[n];
        for (int i = 0; i < n; i++){
            array[i] = new Rect(10 * i, 10 * i, 10 * (2 * n - i), 10 * (2 * n - i));
        }
        return array;
    }

    public static int[] CreatePointsArray(int n){
        int m = 100000;
        int[] array = new int[m * 2];
        for (int i = 0; i < m * 2; i += 2){
            array[i] = (int) Math.pow(2003 * i, 31) % (20 * n); // x
            array[i+1] = (int) Math.pow(3001 * i, 31) % (20 * n); // y
        }
        return array;
    }
}
