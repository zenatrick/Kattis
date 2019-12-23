import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

class FroshWeek {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int numStudents = Integer.parseInt(in.readLine());
        int[] studentNumbers = new int[numStudents];

        for (int i = 0; i < numStudents; i++) {
            studentNumbers[i] = Integer.parseInt(in.readLine());
        }

        out.println(countSwapsMergeSort(studentNumbers, new int[numStudents], 0, numStudents - 1));
        out.close();
    }

    public static long countSwapsMergeSort(int[] arr, int[] temp, int left, int right) {
        int mid;
        long inversions = 0;
        if (right > left) {
            mid = (right + left) / 2;
            inversions += countSwapsMergeSort(arr, temp, left, mid);
            inversions += countSwapsMergeSort(arr, temp, mid + 1, right);
            inversions += merge(arr, temp, left, mid, right);
        }
        return inversions;
    }

    private static long merge(int[] arr, int[] temp, int left, int mid, int right) {
        long inversions = 0;
        int i = left, j = mid + 1, k = left;
        for (; i <= mid && j <= right; k++) {
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
                inversions += mid - i + 1;
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right)
            temp[k++] = arr[j++];

        for (int l = left; l <= right; l++) {
            arr[l] = temp[l];
        }

        return inversions;
    }
}
