package src;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //  for tests:
        WriteToFile("prep_time.xlsx", "n,Brute Force,Map,Persistent Tree\n");
        WriteToFile("get_answer_time.xlsx", "n,BruteForce,Map,PersistentTree\n");
        WriteToFile("algo_time.xlsx", "n,Brute Force,Map,Persistent Tree\n");
        int n = 1;
        for (int i = 1; i <= 13; i++){
            Rect[] rects = TestData.CreateRectanglesArray(n);
            int[] points = TestData.CreatePointsArray(n, 10000);

            // the first algorithm - brute-force
            BruteForce solution1 = new BruteForce();
            solution1.GetPoints(rects);
            WriteToFile("prep_time.xlsx", i + ",0,");
            long begin_time = System.nanoTime();
            solution1.CheckPoints(points);
            long end_time = System.nanoTime();
            WriteToFile("get_answer_time.xlsx", i + "," + Long.toString(end_time - begin_time)+  ",");
            WriteToFile("algo_time.xlsx", i + "," + Long.toString(end_time - begin_time) +  ",");

            // the second algorithm - map on compressed coordinates
            WithMap solution2 = new WithMap();
            solution2.CoordinateCompression(rects);
            begin_time = System.nanoTime();
            solution2.BuildMap();
            end_time = System.nanoTime();
            long prep_time = end_time - begin_time;
            WriteToFile("prep_time.xlsx", Long.toString(prep_time) + ",");
            begin_time = System.nanoTime();
            solution2.CheckPoints(points);
            end_time = System.nanoTime();
            WriteToFile("get_answer_time.xlsx", Long.toString(end_time - begin_time) + ",");
            WriteToFile("algo_time.xlsx", Long.toString(prep_time + end_time - begin_time) + ",");

            // the third algorithm - persistent segment tree on compressed coordinates
            PersistentSegmentTree solution3 = new PersistentSegmentTree();
            begin_time = System.nanoTime();
            solution3.CoordinateCompression(rects);
            end_time = System.nanoTime();
            prep_time = end_time - begin_time;
            WriteToFile("prep_time.xlsx", Long.toString(prep_time) + "\n");
            begin_time = System.nanoTime();
            solution3.CheckPoints(points);
            end_time = System.nanoTime();
            WriteToFile("get_answer_time.xlsx", Long.toString(end_time - begin_time) + "\n");
            WriteToFile("algo_time.xlsx", Long.toString(prep_time + end_time - begin_time) + "\n");

            n *= 2;
        }

        //  for contest:
        // the first algorithm - brute-force
//        BruteForce solution = new BruteForce();
//        solution.GetPoints(null);
//        solution.CheckPoints(null);
        // the second algorithm - map on compressed coordinates
//        WithMap solution = new WithMap();
//        solution.CoordinateCompression(null);
//        solution.BuildMap();
//        solution.CheckPoints(null);
        // the third algorithm - persistent segment tree on compressed coordinates
//        PersistentSegmentTree solution = new PersistentSegmentTree();
//        solution.CoordinateCompression(null);
//        solution.CheckPoints(null);
    }

    public static class Rect{
        int x1;
        int y1;
        int x2;
        int y2;

        Rect(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static void WriteToFile(String filename, String data) {
        try (FileWriter writer = new FileWriter("C://Users//miss-//IdeaProjects//laba//src//tests//" + filename, true)) {
            writer.write(data);
            writer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}