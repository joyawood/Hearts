import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] ar = { 1, 2, 6, 5, 5, 8, 9,1};
		//System.out.println(solution(0,9,9,0));
		System.out.println(solution2(ar));

	}

	public static int solution2(int[] A) {
		// write your code in Java SE 8
		int start = -1;
		int end = -1;
		// find start
		//{ 1, 2, 6, 5, 5, 8, 9 };

		for (int i = 1; i < A.length; i++) {
			System.out.println("looking at " + (A[i]));

			if (A[i] < A[i - 1]) {
				System.out.println("start " + (A[i-1]));
				start = i - 1;
				end = i - 1;
				break;
			}
		}
		if(start == -1){
			return 0;
		}
		// find end
		for (int i = A.length-1; i > start; i--) {
			System.out.println("looking at " + (A[i]));

			if (A[i] < A[i-1]) {
				System.out.println("end " + A[i]);
				end = i+1;
				break;
			}
		}
	

		return end - start;

	}

	public static String solution(int A, int B, int C, int D) {
		// write your code in Java SE 8

		ArrayList<Integer> values = new ArrayList<Integer>();
		values.add(A);
		values.add(B);
		values.add(C);
		values.add(D);
		int[] results = new int[4];

		// set hour1
		ArrayList<Integer> possibilitiesH1 = new ArrayList<Integer>();
		for (Integer value : values) {
			if (value < 3) {
				possibilitiesH1.add(value);
			}
		}
		if (possibilitiesH1.size() == 0) {
			return "NOT POSSIBLE";
		}
		results[0] = Collections.max(possibilitiesH1);
		values.remove(Collections.max(possibilitiesH1));

		// set hour2
		if (results[0] == 2) {
			// max 0..4
			ArrayList<Integer> possibilitiesH2 = new ArrayList<Integer>();
			for (Integer value : values) {
				if (value < 4) {
					possibilitiesH2.add(value);
				}
			}
			if (possibilitiesH2.size() == 0) {
				return "NOT POSSIBLE";
			}
			results[1] = Collections.max(possibilitiesH2);
			values.remove(Collections.max(possibilitiesH2));
		}

		else {
			// max 0..9
			results[1] = Collections.max(values);
			values.remove(Collections.max(values));
		}

		// set minute1
		ArrayList<Integer> possibilitiesM1 = new ArrayList<Integer>();
		for (Integer value : values) {
			if (value < 6) {
				possibilitiesM1.add(value);
			}
		}
		if (possibilitiesM1.size() == 0) {
			return "NOT POSSIBLE";
		}
		results[2] = Collections.max(possibilitiesM1);
		values.remove(Collections.max(possibilitiesM1));

		// set minute2
		results[3] = values.get(0);

		return results[0] + "" + results[1] + ":" + results[2] + "" + results[3];

	}

}
