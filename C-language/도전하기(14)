#include<stdio.h> 

/*
   이 문제와 관한한 다양한 답이 존재합니다.
   아래의 코드는 상대적으로 초보자가 이해하기 쉬운 코드라고 생각하여 답안으로 보여드립니다.
*/

int main(void)
{
    int arr[50][50]; 
    int len, idx, i, j; 
    int s=0, w=-1, inc=1, val=0; 

    printf("숫자를 입력하시오: ");
    scanf("%d", &len); 
    idx=len;

    while(1) 
	{
        for(i=0; i<idx;i++) // 가로 단위 그림 
		{
            val++; 
            w=w+inc; 
            arr[s][w]=val;
        }
        idx=idx-1; 

        if(val==len*len) 
			break; 

        for(i=0; i<idx; i++) // 세로 단위 그림
		{ 
            val++; 
            s=s+inc; 
            arr[s][w]=val; 
        }
        inc=inc*-1;
    }

    for(i=0; i<len; i++)  // 달팽이 배열 출력.  
	{ 
		for(j=0; j<len; j++) 
            printf("%5d", arr[i][j]); 
        printf("\n");
    } 

	return 0;
}

