#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <vector>

using namespace std;
typedef unsigned int uint;

template <class T>
class BinarySearch{
    private:
        vector<T> sorted_list;

    public:
        BinarySearch(uint size);

        void print_list();
        int search(int left_index, int right_index, T key);
};

template <class T>
BinarySearch<T>::BinarySearch(uint size)
{
    //initialize seed (get different random numbers)
    //srand(time(NULL));

    //initialize first number in list
    sorted_list.push_back(0);

    for(uint i = 0; i < size - 1; i++)
    {
        int random_number = rand() % 10 + sorted_list.back();
        sorted_list.push_back(random_number);
    }
}

template <class T>
void BinarySearch<T>::print_list()
{
    for(uint i = 0; i < sorted_list.size(); i++)
    {
        cout << sorted_list.at(i) << ' ';
    }
    cout << '\n';
}

//assuming ascending order
template <class T>
int BinarySearch<T>::search(int left_index, int right_index, T key)
{
    if((right_index - left_index) <= 1)
    {
        if(sorted_list.at(left_index) == key)
            return left_index;
        else if(sorted_list.at(right_index) == key)
            return right_index;
        else
            return -1;
    }

    int index = (right_index - left_index) / 2 + left_index;
    cout << "index: " << index << endl;

    if(sorted_list.at(index) > key)
    {
        right_index = index - 1;
    }
    else if(sorted_list.at(index) < key)
    {
        left_index = index + 1;    
    }
    else{
        return index;
    }

    cout << "left_index: " << left_index;
    cout << "\tright_index: " << right_index << endl;
    
    return search(left_index, right_index, key);
}

int main(int argc, char** argv)
{
    if(argc != 3)
    {
        cout << "Usage: ./run <list size> <number to search for>" << endl;
        cout << "Example: ./run 100 47" << endl;
        return EXIT_FAILURE;
    }

    uint size = atoi(argv[1]);
    BinarySearch<int> s(size);

    s.print_list();
    cout << s.search(0, size-1, atoi(argv[2])) << endl;

    return EXIT_SUCCESS;
}
 
