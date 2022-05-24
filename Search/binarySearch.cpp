#include <iostream>
using namespace std;

#define NUM 10
int nums[NUM];

void initNums()
{
	for (int i = 0; i < NUM; i++)
	{
		nums[i] = i+1;
	}
}

int binarySearch(int a[], int x)
{
	int lower = 0;
	int upper = NUM - 1;
	int middle = 0;
	int p = 0; 

	while(true)
	{
		if(lower > upper)
		{
			p = -1; 
			break;
		}
		middle = (lower + upper)/2;
		if(a[middle] < x)
		{
			lower = middle + 1;
		}
		else if(a[middle] > x)
		{
			upper = middle - 1;
		}
		else
		{
			p = middle; 
			break;
		}
	}
	return p;
}

void main()
{
	initNums();
	int pos = 0;
	pos = binarySearch(nums,4)+1;
	if (pos == 0)
	{
		cout<<"Not found!! SORRY"<<endl;
	}
	else
	{
		cout<<"Your number is the "<<pos<<"th in the array!"<<endl;
	} 
} 
