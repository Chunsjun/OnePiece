
public class MergeSort {

	/**
	 * @param args
	 */
		public static void print(int[] array){
			for(int x:array){
				System.out.print(x+" ");
			}
			System.out.println();
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				int[] array = {2,4,6,1,3,0,3,3,3};
				print(array);
				//select_sort(array);
				mergeSort(array,0,array.length-1);
				print(array);

	}
	/**
	 * @param array
	 * @param i
	 * @param j
	 */
	private static void mergeSort(int[] array, int left, int right) {
		// TODO Auto-generated method stub
		if(left<right){
			int middle = (left+right)/2;
			mergeSort(array, left, middle);
			mergeSort(array, middle+1, right);
			merge(array,left,middle,right);
		}
	}
	/**
	 * @param array
	 * @param i
	 * @param j
	 * @param right 
	 */
	private static void merge(int[] a, int left, int middle, int right) {
		// TODO Auto-generated method stub
		int[] newArray = new int[a.length];
		int mid = middle+1;
		int temp = left;
		int third = left;
		while(left<=middle&&mid<=right){
			if(a[left]<a[mid]){
				newArray[third] = a[left];
				left++;
				third++;
			}else{
				newArray[third] = a[mid];
				third++;
				mid++;
			}
		}
		while(left<=middle){
			newArray[third] = a[left];
			third++;left++;
		}
		while(mid<=right){
			newArray[third] = a[mid];
			third++;mid++;
		}
		while(temp<=right){
			a[temp] = newArray[temp];
			temp++;
		}
		
	}

}
