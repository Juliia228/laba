package src;

import java.util.*;
import src.Main.Rect;

public class PersistentSegmentTree{
    public Scanner sc = new Scanner(System.in);
    private final SortedSet<Integer> X = new TreeSet<>();

    private final SortedSet<Integer> Y = new TreeSet<>();

    private int[] intX;

    private int[] intY;

    private Rect[] array;

    private Node[] trees;

    private class Node{
        private final Node left;
        private final Node right;

        private final int value;

        private final int l_index;

        private final int r_index;

        Node(Node left, Node right, int value, int l_index, int r_index){
            this.left = left;
            this.right = right;
            this.value = value;
            this.l_index = l_index;
            this.r_index = r_index;
        }

        Node(Node node){
            this.left = node.left;
            this.right = node.right;
            this.value = node.value;
            this.l_index = node.l_index;
            this.r_index = node.r_index;
        }
    }

    private class Event{
        private final int compressed_x;
        int getX() {
            return compressed_x;
        }

        private final int compressed_y1;

        private final int compressed_y2;

        private final int value;

        Event(int x, int y1, int y2, int value) {
            this.compressed_x = x;
            this.compressed_y1 = y1;
            this.compressed_y2 = y2;
            this.value = value;
        }
    }

    void CoordinateCompression(Rect[] rects){
        int n;
        if (rects != null){ // for tests
            n = rects.length;
            this.array = rects;
            this.trees = new Node[n * 2];
            for (Rect rectangle: array) {
                X.add(rectangle.x1);
                Y.add(rectangle.y1);
                X.add(rectangle.x2);
                Y.add(rectangle.y2);
            }
        } else { // for contest
            n = sc.nextInt();
            this.array = new Rect[n];
            this.trees = new Node[n * 2];
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

        if (n != 0) {
            BuildTree();
        }
    }

    void BuildTree(){
        Event[] arr_events = new Event[array.length * 2];
        int i = 0;
        for (Rect rectangle: array){
            int x1 = BinaryIndexSearch(intX, rectangle.x1);
            int y1 = BinaryIndexSearch(intY, rectangle.y1);
            int x2 = BinaryIndexSearch(intX, rectangle.x2);
            int y2 = BinaryIndexSearch(intY, rectangle.y2);
            arr_events[i++] = new Event(x1, y1, y2, 1);
            arr_events[i++] = new Event(x2, y1, y2, -1);
        }
        Arrays.sort(arr_events, Comparator.comparing(Event::getX));
        int x0 = arr_events[0].compressed_x;
        int ind = 0;
        Node head = CreateStartTree(0, intY.length);
        for (Event event: arr_events){
            if (event.compressed_x != x0){
                trees[ind++] = head;
                x0 = event.compressed_x;
            }
            head = AddNode(head, event.value, event.compressed_y1, event.compressed_y2);
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

    Node CreateStartTree(int start, int end){
        if (start == end - 1){
            return new Node(null, null, 0, start, end);
        }
        Node left_node = CreateStartTree(start, (start + end) / 2);
        Node right_node = CreateStartTree((start + end) / 2, end);
        return new Node(left_node, right_node, left_node.value + right_node.value, left_node.l_index, right_node.r_index);
    }

    Node AddNode(Node previous_node, int value, int start, int end){
        if (previous_node.l_index >= end || previous_node.r_index <= start) {
            return previous_node;
        }
        if (previous_node.l_index >= start && previous_node.r_index <= end) {
            return new Node(previous_node.left, previous_node.right, previous_node.value + value, previous_node.l_index, previous_node.r_index);
        }
        return new Node(AddNode(previous_node.left, value, start, end), AddNode(previous_node.right, value, start, end), previous_node.value, previous_node.l_index, previous_node.r_index);
    }

    void CheckPoints(int[] points){
        if (points != null) { // for tests
            for (int i = 0; i < points.length - 1; i += 2) {
                int x_ = points[i];
                int x = BinaryIndexSearch(intX, x_);
                int y_ = points[i+1];
                int y = BinaryIndexSearch(intY, y_);
                if (array.length == 0) {
                    System.out.print("0 ");
                } else {
                    if (x_ < intX[0] || y_ < intY[0] || x_ > intX[intX.length - 1] || y_ > intY[intY.length - 1]) {
                        System.out.print("0 ");
                    } else {
                        System.out.print(Summa(y, trees[x]) + " ");
                    }
                }
            }
        } else { // for contest
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                int x_ = sc.nextInt();
                int x = BinaryIndexSearch(intX, x_);
                int y_ = sc.nextInt();
                int y = BinaryIndexSearch(intY, y_);
                if (array.length == 0) {
                    System.out.print("0 ");
                } else {
                    if (x_ < intX[0] || y_ < intY[0] || x_ > intX[intX.length - 1] || y_ > intY[intY.length - 1]) {
                        System.out.print("0 ");
                    } else {
                        System.out.print(Summa(y, trees[x]) + " ");
                    }
                }
            }
        }
    }

    int Summa(int target_index, Node current_node){
        if (current_node == null){
            return 0;
        }
        if (target_index < ((current_node.l_index + current_node.r_index) / 2)) {
            return current_node.value + Summa(target_index, current_node.left);
        }
        return current_node.value + Summa(target_index, current_node.right);
    }
}