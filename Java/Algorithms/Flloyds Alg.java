/*
 * Abstract: This program implements Floyd's algorithm. Using a 
 * 2D array, the user input is stored and looped through n times.
 * Each time the array is traversed, it updates the shortest path
 * for each index if possible. 
 */
import java.util.*;
 
class Main 
{
  public static void main(String[] args) {
    //declare variables----------------------
    Scanner readme = new Scanner(System.in);
    int size = readme.nextInt();
    int[][] og_arr = new int[size][size];

    //call methods---------------------------
    Fill2DArr(og_arr, readme);
    og_arr = flloyd(og_arr);

    //output---------------------------------
    String ans  = "";
    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        ans += og_arr[i][j] + " ";
      }
      System.out.println(ans.trim());
      ans = "";
    }
  }

  //FILLS THE 2D ARR W/ VALUES---------------------------------------
  public static void Fill2DArr(int[][] arr, Scanner readme){
    for(int i = 0; i < arr.length; i++){
      for(int j = 0; j < arr.length; j++){
        arr[i][j]  = readme.nextInt();
      }
    }
  }

  //FLLOYD'S ALG-----------------------------------------------------
  public static int[][] flloyd(int[][] arr){
    int size = arr.length;

    for(int k = 0; k < size; k++){  //number of times 2d array has to be looped through
      for(int i = 0; i < size; i++){  //loop through rows
        for(int j = 0; j < size; j++){  //loop through columns
          if(arr[i][j] <= -1){  //check if current index is -1
            if(arr[i][k] > -1 && arr[k][j] > -1)  //check if neighboring are > -1
              arr[i][j] = arr[i][k] + arr[k][j];  //if yes then get their sum
          }        
          else if(arr[i][k] <= -1 || arr[k][j] <= -1){  //check if neighboring are -1
            continue;  //if yes, keep current index value
          }
          else {  //none are -1
           arr[i][j] = Math.min(arr[i][j], 
                          arr[i][k] + arr[k][j]);  //find the min
          }
        }
      }
    }

    return arr;
  }
}
