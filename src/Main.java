public class Main {
    public static void main(String[] args) {
        //the first algorithm - brute-force
//        BruteForce solution = new BruteForce();
//        solution.GetPoints();
//        solution.CheckPoints();
        //the second algorithm - map on compressed coordinates
        WithMap solution = new WithMap();
        solution.CoordinateCompression();
        solution.BuildMap();
        solution.CheckPoints();
    }
}