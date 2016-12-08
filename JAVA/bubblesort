package strategyPattern.strategy;

import java.util.List;

/**
 * Implementation of bubble sort
 * 
 * http://www.kriblog.com/j2se/util/various-bubble-sort-example-in-java-using-string-array-arraylist-linked-list-recursive.html
 *
 */
public class BubbleSort implements SortAlgorithm {

    @Override
    public void sort(List<Integer> unsorted) {

        for (int i = unsorted.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (unsorted.get(j) > unsorted.get(j + 1)) {
                    int temp = unsorted.get(j);
                    unsorted.set(j, unsorted.get(j + 1));
                    unsorted.set(j + 1, temp);
                }
            }
        }
    }

}
