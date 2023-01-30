/*
 * Abstract: This program implements linear probing. It uses 2 arrays.
 * One stores the values and another stores the status of each index.
 * There are 5 actions the user can take; display staus, get the table
 * size, search for a number, delete a number, and insert a number.
 * When inserting a number, the program checks if the loading factor is 
 * less than 0.5. If it is not, the size is doubled to the next prime number
 * and the values are rehashed and their status updated.
 */
import java.util.*;
 
class Main 
{
  public static void main(String[] args) {
    //declare variables------------------------------------
    Scanner readme = new Scanner(System.in);
    int initial_size = readme.nextInt();
    int commands = readme.nextInt()-1;
    int[] values = new int[initial_size];
    int[] status = new int[initial_size];

     //loop to read through commands----------------------
      while(commands >= 0){
        String input = readme.next();
        if(input.equals("insert")){
          int insert = readme.nextInt();
          values = insert(insert, values, status);
          status = updateStatus(values, status);
        }
        else if(input.equals("displayStatus")){
          int display = readme.nextInt();
          displayStatus(display, values, status);
        }
        else if(input.equals("delete")){
          int remove = readme.nextInt();
          delete(remove, values, status);
        }
        else if(input.equals("tableSize")){
          tableSize(values);
        }
        else if(input.equals("search")){
          int lookup = readme.nextInt();
          search(lookup, values, status);
        }
        commands--;
      }

    return;
  }

  //METHODS==========================================================
  //INSERT A KEY TO TABLE--------------------------------------------
  static int[] insert(int num, int[] values, int[]status){
    //insert new value into array by hashing-----------------------
    int tablesize = values.length;
    int temp_index = num % tablesize;
    int index = temp_index;

    do{
       //0 == empty, 1 == active, 2 == deleted
      if(status[index] == 0 || status[index] == 2){  //check if spot available
        values[index] = num;  //insert value
        status[index] = 1;    //update status
        break;
      }
      if(values[index] == num){
        status[index] = 1;
        break;
      }
      index = (index+1) % tablesize;  //space not available so rehash to the next spot
    } while(index != temp_index);
    
    
    //check if load factor is greater than 0.5---------------------
    float count = 0;
    float size = values.length;
    for(int i = 0; i < values.length; i++){  //get count of values
      if(values[i] != 0)
        count++;
    }

    if((count/size) > 0.5){  //check if >0.5
      size *= 2;
      int i_size = Math.round(size);
      i_size = checkPrime(i_size);  //check if prime number
      int[] temp_values = new int[i_size];  //create new array 

      int new_index = 0;
      for(int i = 0; i < values.length; i++){  //put values in new array
        if(values[i] != 0){
          new_index = values[i] % i_size;    //get new index
          temp_values[new_index] = values[i];//put in new array
        }
      }
      values = temp_values;  //point original array to new one
    }
    return values;
  }

  //CREATE NEW STATUS TABLE------------------------------------------
  static int[] updateStatus(int[] values, int[] status){
    if(values.length == status.length){
      return status;
    }
    else{
      int size = values.length;
      int[] temp_status = new int[size];  //create new array 
      
      for(int i = 0; i < size; i++){  //put values in new array
        if(values[i] != 0){  //index is not empty
          temp_status[i] = 1;//set to active
        }
      }
      status = temp_status;
    }
    
    return status;
  }
  
  //DISPLAY IF EMPTY/DELETED/ACTIVE----------------------------------
  static void displayStatus(int index, int[] values, int[] status){
    if(status[index] == 0){          //0 == empty
      System.out.println("Empty");
    }
    else if(status[index] == 1){     //1 == active
      System.out.println(values[index] + " Active");
    }
    else{                            //2 == deleted
      System.out.println(values[index] + " Deleted");
    }
  }

  //DISPLAY TABLE SIZE-----------------------------------------------
  static void tableSize(int[] values){
    System.out.println(values.length);
  }

  //SEARCH FOR A KEY-------------------------------------------------
  static void search(int num, int[] values, int[] status){
    for(int i = 0; i < values.length; i++){
      if(values[i] == num && status[i] != 2){  //value is found and not deleted
        System.out.println(num + " Found");
        return;  //exit method
      }
    }
    System.out.println(num + " Not found");
  }

  //DELETE A KEY-----------------------------------------------------
  static void delete(int num, int[] values, int[] status){
    for(int i = 0; i < values.length; i++){
      if(values[i] == num){
        status[i] = 2;  //set status to deleted
        return;  //exit method
      }
    }
  }

  //CHECK PRIME NUMBER-----------------------------------------------
  static int checkPrime(int num){
    //check if num is prime number
    boolean prime = true;
    for(int i = 2; i < num; i++){
      if((num % i) == 0){
        prime = false;
      }
    }

    
    if(prime){  //is prime number
      return num;
    }
    else{      //in not prime number
      num++;
      for (int i = 2; i < num; i++) {
        if(num%i == 0) {  //keep increasing num
          num++;
          i=2;
        } 
        else {  //num is prime
          continue;
        }
      }
      return num;
    }
  }
}
