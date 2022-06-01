import java.util.LinkedList;

public class test {
    public static void main(String[] args) {
        int[][] array = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9},
                {1, 5, 9},
                {3, 5, 7}
        };

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            list.add(i);
        }

        System.out.println(list.get(0));

    }


}
