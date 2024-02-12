import java.util.ArrayList;
import java.util.Random;

public class HW6 {

    public static void main(String[] args) {
        // Problem 4.
        int[] arr = {5, 2, 5, 9, 99, 6, 7, 9, 1, 2, 3, 4, 5, 6, 7, 8};
        int k = 4;
        int k_largest = select(arr, k);
        // System.out.println(k_largest);

        // Problem 5.
        Random rand = new Random();
        int[] arr1 = rand.ints(50001, 10, 50001).toArray();
        rand = new Random();
        int[] arr2 = rand.ints(100001, 10, 100001).toArray();
        rand = new Random();
        int[] arr3 = rand.ints(150001, 10, 150001).toArray();
        rand = new Random();
        int[] arr4 = rand.ints(200001, 10, 200001).toArray();
        rand = new Random();
        int[] arr5 = rand.ints(250001, 10, 250001).toArray();
        rand = new Random();
        int[] arr6 = rand.ints(300001, 10, 300001).toArray();
        rand = new Random();
        int[] arr7 = rand.ints(350001, 10, 350001).toArray();
        rand = new Random();
        int[] arr8 = rand.ints(400001, 10, 400001).toArray();
        rand = new Random();
        int[] arr9 = rand.ints(450001, 10, 450001).toArray();
        rand = new Random();
        int[] arr10 = rand.ints(500001, 10, 500001).toArray();

        System.out.println(tester(arr1));
        System.out.println(tester(arr2));
        System.out.println(tester(arr3));
        System.out.println(tester(arr4));
        System.out.println(tester(arr5));
        System.out.println(tester(arr6));
        System.out.println(tester(arr7));
        System.out.println(tester(arr8));
        System.out.println(tester(arr9));
        System.out.println(tester(arr10));

    }

    public static double tester(int[] arr) {
        int n = arr.length;
        int total = 0;
        for (int i = 0; i < 100; i++) {
            total += select(arr, (n-1)/2);
        }
        return (double) total / 100.0;
    }

    public static int select(int[]arr, int k) {
        return select(arr, k, 0);
    }

    private static int select(int[] arr, int k, int count) {
        int n = arr.length;
        int rand = new Random().nextInt(n);
        int pivot = arr[rand];
        ArrayList<Integer> s1 = new ArrayList<Integer>();
        ArrayList<Integer> s2 = new ArrayList<Integer>();
        ArrayList<Integer> s3 = new ArrayList<Integer>();
        for (int curr : arr) {
            if (curr < pivot) {
                s1.add(curr);
                count += 1;
            } else if (curr > pivot) {
                s2.add(curr);
                count += 2;
            } else {
                s3.add(curr);;
            }
        }
        int s2_size = s2.size();
        int s3_size = s3.size();
        if (s2_size >= k) {
            return select(toArray(s2, s2_size), k, count);
        } else if (s2_size + s3_size >= k) {
            return count;
            // return pivot;
        } else {
            return select(toArray(s1, s1.size()), k - s2_size - s3_size, count);
        }
    }

    public static int[] toArray(ArrayList<Integer> list, int size) {
        int i = 0;
        int[] arr = new int[size];
        for (int num : list) {
            arr[i] = num;
            i++;
        }
        return arr;
    }


}
