/ * Abstract: This program tackles the coin collection problem. A 2D array
 * is created one row and column bigger than the original size. After the 
 * values are stored in the array, the array is traversed to find the value
 * of each index. If the index is -1 it is unaccessible and skipped over.
 * After the array is summed up, it is traversed in reverse to find the 
 * most optimal path to the last index.
 */
import java.util.*;
 
class Main 
{
  public static void main(String[] args) {
    //declare variables--------------
    Scanner readme = new Scanner(System.in);
    int cols = readme.nextInt()+1;
    int rows = readme.nextInt()+1;
    int[][] arr = new int[rows][cols];

    //call methods-------------------
    Fill2DArr(arr, readme);
    findAllPaths(arr);
    bestPath(arr, rows, cols);
  
    return;
  }

  //FILLS THE 2D ARR W/ VALUES---------------------------------------
  static void Fill2DArr(int[][] arr, Scanner readme){
    //set top row to -1---
    for(int i = 1; i < arr[0].length; i++){
      arr[0][i] = -1;
    } 

    //fill in array---
    for(int i = 1; i < arr.length; i++){
      for(int j = 1; j < arr[0].length; j++){
        arr[i][j]  = readme.nextInt();
      }
    }
  }

  //CALCULATES THE VALUE OF EACH INDEX-------------------------------
  static void findAllPaths(int[][] arr){
    for(int i = 1; i < arr.length; i++){  //loop through rows
      for(int j = 1; j < arr[0].length; j++){  //loop through columns
        if(arr[i-1][j] == -1 && arr[i][j-1] == -1) arr[i][j] = -1;  //if both are -1, index =-1
        if(arr[i][j] == -1) continue;  //if index is -1, skip
        else arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]) + arr[i][j];  //find the max value
      }
    }
  }

  //FINDS THE BEST PATH----------------------------------------------
  static void bestPath(int[][]arr, int rows, int cols){
    //declare output variables-----------------
    String path_ans = "";
    int max_coins = arr[rows-1][cols-1];
    String max_ans = "Max coins:" + max_coins;

    //backtraces through the 2d to find best path--------------
    for(int i = rows-1; i > 0; i--){  //loop through rows
      for(int j = cols-1; j > 0; j--){  //loop through columns

         //for first column---
        if(j == 1 && arr[i-1][j] != -1){  
          path_ans = "(" + j + "," + i + ")->" + path_ans;
          i--;  //moves up only
        }
        
        //comparing all other indexes---------------------------
        if(arr[i-1][j] > arr[i][j-1]){  //index above > index to the left
          path_ans = "(" + j + "," + i + ")->" + path_ans;
          i--;  //moves to row above
          j++;  //keeps it in the same column
        }
        else if(arr[i][j-1] >= arr[i-1][j]){  //index to the left > index above
          path_ans = "(" + j + "," + i + ")->" + path_ans;
          continue;  //moves to the left
        }
      }
    }
    System.out.println(max_ans);
    System.out.println("Path:" + path_ans.substring(0, path_ans.length()-2));
  }
}
