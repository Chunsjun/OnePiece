public class Main {

    public static void main(String[] args) {
  // write your code here
        sorting();
    }

    public static void sorting(){

        int[] paixu = {1,2,6,9,8,1,6,8,4,5,1,6,9,4};

        for (int i = 0; i < paixu.length; i++){

            int min = paixu[i];
            int minb = 0;

            for (int j = i + 1; j < paixu.length; j++){

                if(min > paixu[j]){
                    min = paixu[j];
                    minb = j;
                }

            }

            if (minb != 0){

                int t = paixu[minb];
                paixu[minb] = min;
                min = t;

            }


        }

        for (int i = 0; i < paixu.length; i++)
            System.out.print(paixu[i]+",");
    }
}
