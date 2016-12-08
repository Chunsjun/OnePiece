public class binarySearchMain {
	
	private static int binarySearch(int[] items, int target, int firstIndex, int lastIndex) {
		if (firstIndex > lastIndex) {
			return -1;
		} else {
			int middleIndex = (firstIndex + lastIndex) / 2;
			if (items[middleIndex] == target) {
				return middleIndex;
			} else if (target < items[middleIndex]) {
				return binarySearch(items, target, firstIndex, middleIndex - 1);
			} else {
				return binarySearch(items, target, middleIndex + 1, lastIndex);
			}
		}
		
	}
	
	public static int binarySearch(int[] items, int target) {
		return binarySearch(items, target, 0, items.length - 1);
	}
//test
	public static void main(String[] args) {
		int array1[] = {1, 2, 10, 25, 36, 44, 65, 101, 111, 123, 188, 2589, 5555, 10001};
		System.out.println(binarySearch(array1, 10));
	}
 
}


