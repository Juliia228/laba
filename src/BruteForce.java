import java.util.Scanner;

public class BruteForce{
    private static Scanner sc = new Scanner(System.in);
    private Rect[] array;

    private final int n;

    private int m;
    void setM(int m){
        this.m = m;
    }

    record Rect(int x1, int y1, int x2, int y2) {
    }

    BruteForce(){
        this.n = sc.nextInt();
        if (n > 0) {
            this.array = new Rect[n];
        }
    }

    void GetPoints(){
        try {
            for (int i = 0; i < this.n; i++) {
                Rect rectangle = new Rect(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                this.array[i] = rectangle;
            }
        } catch (Exception e) {
            System.out.println("Incorrect data");
        }
        setM(sc.nextInt());
    }

    void CheckPoints(){
        try {
            for (int i = 0; i < this.m; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                System.out.print(Check(x, y) + " ");
            }
        } catch (Exception e) {
            System.out.println("Incorrect data");
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