import java.util.ArrayList;
import java.util.List;

public class KMP {
	public static void main(String[] args) {
		String str = "ababacabacaabacaaba";
		String pat = "abacaaba";
		int[] table = getTable(pat);
		
		System.out.println("#### 테이블 ####");
		for(int num : table)
			System.out.print(num+" ");
		System.out.println();
		
		List<Integer> indexList = KMP(str, pat);
		System.out.println("#### 매칭 인덱스 위치 ####");
		for(int num : indexList)
			System.out.print(num+" ");

	}

	static int[] getTable(String str) {
		int strLen = str.length();
		int[] table = new int[strLen];
		int j = 0;
		for (int i = 1; i < strLen; i++) {
			while (j > 0 && str.charAt(i) != str.charAt(j)) {
				j = table[j - 1];
			}
			if (str.charAt(i) == str.charAt(j))
				j++;
			table[i] = j;
		}

		return table;
	}

	static List<Integer> KMP(String str, String pat) {
		List<Integer> indexList = new ArrayList();
		int[] table = getTable(pat);
		int strLen = str.length();
		int patLen = pat.length();

		int j = 0;
		for (int i = 0; i < strLen; i++) {
			while (j > 0 && str.charAt(i) != pat.charAt(j)) {
				j = table[j - 1];
			}
			
			if (str.charAt(i) == pat.charAt(j)) {
				if (j == patLen - 1) {
					indexList.add(i - (patLen-1));
					j = table[j];
				}else {
					j++;
				}
			}
		}
		return indexList;
	}
}
