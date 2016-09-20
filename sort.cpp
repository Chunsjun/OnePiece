#include <algorithm>
#include <cassert>
#include <forward_list>
#include <functional>
#include <iterator>
#include <vector>
#include <cpp-sort/adapters.h>
#include <cpp-sort/sort.h>
#include <cpp-sort/sorters.h>

int main()
{
    struct wrapper { int value; }

    std::forward_list<wrapper> li = { {5}, {8}, {3}, {2}, {9} };
    std::vector<wrapper> vec = { {5}, {8}, {3}, {2}, {9} };

    // When used, this sorter will use a pattern-defeating quicksort
    // to sort random-access collections, and a mergesort otherwise
    using sorter = cppsort::hybrid_adapter<
        cppsort::pdq_sorter,
        cppsort::merge_sorter
    >;

    // Sort li and vec in reverse order using their value member
    cppsort::sort(sorter{}, li, std::greater<>{}, &wrapper::value);
    cppsort::sort(sorter{}, vec, std::greater<>{}, &wrapper::value);

    assert(std::equal(
        std::begin(li), std::end(li),
        std::begin(vec), std::end(vec)
    ));
}
