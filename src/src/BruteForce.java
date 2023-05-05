package src;

import java.util.Scanner;
import src.Main.Rect;

public class BruteForce{
    private static Scanner sc = new Scanner(System.in);
    private Rect[] array;

    private int n;

    private int m;
    void setM(int m){
        this.m = m;
    }

    void GetPoints(Rect[] rects){
        if (rects != null){ // for tests
            this.n = rects.length;
            this.array = rects;
        } else { // for contest
            this.n = sc.nextInt();
            if (this.n > 0) {
                this.array = new Rect[n];
            }
            for (int i = 0; i < this.n; i++) {
                Rect rectangle = new Rect(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                this.array[i] = rectangle;
            }
            setM(sc.nextInt());
        }
    }

    void CheckPoints(int[] points){
        if (points != null){ // for tests
            setM(points.length / 2);
            for (int i = 0; i < points.length; i += 2) {
                System.out.print(Check(points[i], points[i+1]) + " ");
            }
        } else { // for contest
            for (int i = 0; i < this.m; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                System.out.print(Check(x, y) + " ");
            }
        }
    }

    int Check(int x, int y){
        int count = 0;
        if (this.n > 0) {
            for (Rect rectangle : this.array) {
                if (rectangle.x1 <= x && rectangle.x2 > x && rectangle.y1 <= y && rectangle.y2 > y) {
                    count++;
                }
            }
        }
        return count;
    }
}