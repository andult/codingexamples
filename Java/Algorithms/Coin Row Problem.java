
/*
 * Abstract: This program solves the coin row problem, which coins give us 
 * the max value? This program uses 2 arrays, one to store coins and one for
 * their sums. As we loop through the coins, we check to see which combination
 * has the greater value and compute the sum. Then we reverse loop through the
 * sum array to see which coins changed the sum and select those coins as the
 * best set.
 */
import java.util.*;

class Main 
{
  public static void main(String[] args) {
    //declare variables-----------------------
    Scanner readme = new Scanner(System.in);
    int size = readme.nextInt() + 1;
    int[] coins = new int[size];
    int[] sum = new int[size];

    //call methods----------------------------
    fillArray(coins, readme);
    getSum(coins, sum);
    getBestSet(coins, sum);

    return;
  }

  //METHODS==========================================================
  //FILL ARRAY-------------------------------------------------------
  static void fillArray(int[] arr, Scanner readme){
    for(int i = 1; i < arr.length; i++){
      arr[i] = readme.nextInt();
    }
  }

  //FILL SUM ARRAY---------------------------------------------------
  static void getSum(int[] coins, int[] sum){
    //referencing pseudocode in lecture_31 slide 8
    sum[0] = 0;
    sum[1] = coins[1];

    for(int i = 2; i < coins.length; i++){
      sum[i] = Math.max(sum[i-2] + coins[i], sum[i-1]);
      //gets the max w/ the last coin vs w/o the last coin
    }
  }

  //GET BEST SET-----------------------------------------------------
  static void getBestSet(int[] coins, int[]sum){
    //declare variables
    int maxValue = 0;
    String bestSet = "";
    int size = coins.length;

    //reverse through the sum array
    for(int i = size - 1; i > 0; i--){
      if(sum[i] != sum[i-1]){         //if the sums change, coin was used
        maxValue += coins[i];         //update the max value
        bestSet = i + " " + bestSet;  //save location of coin
        i--;  //skip next coin bc it can't be used
      }
      else{
        continue; //go to next coin
      }
    }

    //output
    System.out.println("Best set:" + bestSet.trim());
    System.out.println("Max value:" + maxValue);
  }
}

