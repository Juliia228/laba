package src;

import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import src.Main.Rect;

public class WithMap {
    public Scanner sc = new Scanner(System.in);
    private final SortedSet<Integer> X = new TreeSet<>();

    private final SortedSet<Integer> Y = new TreeSet<>();

    private int[] intX;

    private int[] intY;

    private Rect[] array;

    private int[][] map;

    void CoordinateCompression(Rect[] rects){
        if (rects != null){ // for tests
            this.array = rects;
            for (Rect rectangle: array) {
                X.add(rectangle.x1);
                Y.add(rectangle.y1);
                X.add(rectangle.x2);
                Y.add(rectangle.y2);
            }
        } else { // for contest
            int n = sc.nextInt();
            this.array = new Rect[n];
            for (int i = 0; i < n; i++) {
                int x1 = sc.nextInt();
                int y1 = sc.nextInt();
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                X.add(x1);
                Y.add(y1);
                X.add(x2);
                Y.add(y2);
                Rect rectangle = new Rect(x1, y1, x2, y2);
                this.array[i] = rectangle;
            }
        }

        intX = new int[X.size()];
        int i = 0;
        for (int x: X){
            intX[i++] = x;
        }

        intY = new int[Y.size()];
        i = 0;
        for (int y: Y){
            intY[i++] = y;
        }
    }

    void BuildMap(){
        map = new int[intX.length][intY.length];
        for (Rect rectangle: array){
            int x1 = BinaryIndexSearch(intX, rectangle.x1);
            int y1 = BinaryIndexSearch(intY, rectangle.y1);
            int x2 = BinaryIndexSearch(intX, rectangle.x2);
            int y2 = BinaryIndexSearch(intY, rectangle.y2);
            for (int x = x1; x < x2; x++){
                for (int y = y1; y < y2; y++){
                    map[x][y]++;
                }
            }
        }
    }

    int BinaryIndexSearch(int[] array, int target) {
        int down = 0;
        int up = array.length - 1;
        while (down <= up) {
            int midpoint = (down + up) / 2;
            if (array[midpoint] < target){
                down = midpoint + 1;
            } else if (array[midpoint] > target){
                up = midpoint - 1;
            } else{
                return midpoint;
            }
        }
        return down - 1;
    }

    void CheckPoints(int[] points){
        if (points != null){ // for tests
            for (int i = 0; i < (points.length / 2) - 1; i += 2) {
                int x = BinaryIndexSearch(intX, points[i]);
                int y = BinaryIndexSearch(intY, points[i+1]);
                if (x == -1 || y == -1) {
                    System.out.print("0 ");
                } else {
                    System.out.print(map[x][y] + " ");
                }
            }
        } else { // for contest
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                int x = BinaryIndexSearch(intX, sc.nextInt());
                int y = BinaryIndexSearch(intY, sc.nextInt());
                if (x == -1 || y == -1) {
                    System.out.print("0 ");
                } else {
                    System.out.print(map[x][y] + " ");
                }
            }
        }
    }
}
