#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void ComSel(int *arr); // 컴퓨터가 3개의 숫자를 선택
int IsValid(int *arr); // 사용자 입력 유효성 검사. 유효하면 1 리턴
void UsrSel(int *arr); // 사용자 3개의 숫자를 입력.
int Compare(int *arr1, int *arr2); // 선택 결과 비교.

int main(void)
{
	
	int comArr[3];		// 컴퓨터에 의한 선택
	int usrArr[3];		// 사용자에 의한 선택
	int result=0;

	printf("Start Game! \n");
	ComSel(comArr);

	while(result!=1)
	{
		UsrSel(usrArr);
		result=Compare(comArr, usrArr);
		printf("\n");
	}

	printf("Game End! \n");
	return 0;
}

void ComSel(int *arr)	// 컴퓨터가 3개의 숫자를 선택
{
	srand((int)time(NULL));

	//selection 1
	arr[0]=rand()%10;

	//selection 2
	do{					
		arr[1]=rand()%10;
	}while(arr[0]==arr[1]);

	//selection 3
	do{					
		arr[2]=rand()&10;
	}while((arr[0]==arr[2])||(arr[1]==arr[2]));
	
}

int IsValid(int *arr)
{
	if(arr[0]==arr[1]||arr[0]==arr[2]||arr[1]==arr[2])
		return 0;
	else
		return 1;	
}

void UsrSel(int *arr)
{
	int valid;

	printf("3개의 숫자 선택 : ");
	while(1)
	{
		scanf("%d %d %d", &arr[0], &arr[1], &arr[2]);
		valid=IsValid(arr);
		if(valid==1)
			break;
		else
			printf("잘못된 입력, 재 입력 : ");
	}
}



int Compare(int *arr1, int *arr2)
{
	static int count=0;
	int strike=0, ball=0;
	int i, j;

	for(i=0; i<3; i++)
	{
		for(j=0; j<3; j++)
		{
			if(arr1[i]==arr2[j])
			{
				i==j? strike++:ball++;
				break;
			}
		}
	}

	count++;
	printf("%d번째 도전 결과 : %dstrike, %dball!!\n", count, strike, ball);

	if(strike==3)
		return 1;
	else
		return 0;
}

		
