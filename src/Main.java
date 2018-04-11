import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static int max(int a, int b) {
        return a>b ? a : b;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try{
            FileReader fr = new FileReader("/home/rolledm/Data/0.txt");
            Scanner scanner = new Scanner(fr);
            Knapsack ks = new Knapsack(scanner.nextInt());

            //init knapsack
            while (scanner.hasNextInt()) {
                Node node = new Node(scanner.nextInt(), scanner.nextInt());
                //ks.set.add(node);
                ks.arr.add(node);
            }

            fr.close();

            Collections.sort(ks.arr, Node.COMPARE_BY_COUNT);

            System.out.println("max weight: " + ks.maxWeight);
            for (Node it : ks.arr) {
                System.out.println(it);
            }

            int[][] arr = new int[ks.arr.size() + 1][ks.maxWeight + 1];

            for (int i = 0; i < ks.maxWeight + 1; i++) {
                arr[0][i] = 0;
            }
            for (int i = 0; i < ks.arr.size() + 1; i++) {
                arr[i][0] = 0;
            }
            for (int k = 1; k < ks.arr.size(); k++) {
                for (int s = 1; s < ks.maxWeight  + 1; s++) {
                    if (s >= ks.arr.get(k).weight) {
                        arr[k][s] = max(arr[k - 1][s - ks.arr.get(k).weight] + ks.arr.get(k).price, arr[k - 1][s]);
                    } else {
                        arr[k][s] = arr[k - 1][s];
                    }
                }
            }
            System.out.println(arr[ks.arr.size() - 1][ks.maxWeight]);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

class Node {

    public Node(int weight, int price) {
        this.weight = weight;
        this.price = price;
    }

    @Override
    public String toString() {
        return "p: " + price + "\nw: " + weight + "\n";
    }

    public static final Comparator<Node> COMPARE_BY_COUNT = new Comparator<Node>() {
        @Override
        public int compare(Node l, Node r) {
            double t = (double)l.price/l.weight-(double)r.price/r.weight;
            if (t > 0) return 1;
            else if (t < 0) return -1;
            else return 0;
        }
    };

    final int weight;
    final int price;
}

class Knapsack {

    public Knapsack(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    final int maxWeight;
    public ArrayList<Node> arr = new ArrayList<>();
}