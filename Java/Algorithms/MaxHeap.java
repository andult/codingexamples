/*
 * Abstract: This program builds and edits a MaxHeap. Using an array, 
 * the input numbers are checked to see if they are a heap. If there 
 * aren't, the array is heapify. This programs can insert new numbers
 * into the heap, delete the max as well as a specific number, and display
 * the max number or the entire heap.
 */

//References: https://www.geeksforgeeks.org/building-heap-from-array/
//https://www.geeksforgeeks.org/insertion-and-deletion-in-heaps/
import java.util.*;
 
class Main 
{
    public static void main(String[] args) {
      //declare variables----------------------------------
      Scanner readme = new Scanner(System.in);
      int size = readme.nextInt();
      int[] heap_arr = new int[size];
      FillArray(heap_arr, readme);
      int commands = readme.nextInt();
      checkHeapSort(heap_arr);  //check if array is a maxheap

      //loop to read through commands----------------------
      while(commands >= 0){
        String input = readme.next();
        if(input.equals("insert")){
          int insert = readme.nextInt();
          heap_arr = insert(heap_arr, heap_arr.length, insert);
        }
        else if(input.equals("deleteMax")){
          heap_arr = deleteMax(heap_arr, heap_arr.length);
        }
        else if(input.equals("delete")){
          int delete = readme.nextInt();
          heap_arr = delete(heap_arr, heap_arr.length, delete);
        }
        else if(input.equals("displayMax")){
          displayMax(heap_arr);
        }
        else if(input.equals("display")){
          display(heap_arr);
        }
        commands--;
      }

    return;
  }

  //METHODS====================================================================
  //FILLS THE ARRAY------------------------------------------------------------
  static void FillArray(int arr[], Scanner readme) {
    // read inpu and store in an array----
    for (int i = 0; i < arr.length; i++) {
      arr[i] = readme.nextInt();
    }
  }

  //CHECK IF HEAPSORT----------------------------------------------------------
  static void checkHeapSort(int[] arr){
    int max = arr[0];  //set max as first index---
    for(int i = 1; i < arr.length; i++){  //find actual max---
      if (arr[i] > max){
        max = arr[i];
      }
    }

    if(max == arr[0]){  //max = 1st index---
      System.out.println("This is a heap.");
    }
    else  //max is not equal to 1st index---
      System.out.println("This is NOT a heap.");
      buildHeap(arr, arr.length);  //turn array into heap---
  }

  //INSERT INTO THE ARRAY------------------------------------------------------
  static int[] insert(int[] arr, int N, int value){
    N++;
    int[] temp = new int[N];  //create new array with increased size---

    //loop through arr and copy values over---
    for(int i = 0; i < N-1; i++){
      temp[i] = arr[i];  //assign values
    }
    temp[N-1] = value;  //set last index to insert value

    arr = temp;  //point original arr to new arr
    heapifyBU(arr, N, N-1);  //heapify up

    return arr;
  }
  
  //DELETE FROM ARRAY----------------------------------------------------------
  static int[] delete(int[] arr, int N, int value){
    int index = 0;
    for(int i = 0; i < N; i++){  //loop through arr to find location of value
      if(arr[i] == value){
        index =i;
        break;
      }
    }

    arr[index] = arr[N-1];  //overwrite old value with the last element in arr

    N--;  //decrease arr size
    int[] temp = new int[N];  //create new array
    for(int i = 0; i < N; i++){
      temp[i] = arr[i];  //copy values over
    }
    arr = temp;  //point original arr to  new one

    //check if we need to heapify up or down--------
    int parent = (index - 1) / 2;
    if(arr[parent] > arr[index]){  //parent is bigger than current, heapify down
      heapify(arr, N, index);
    }
    else                           //parent is smaller than current, heapify up
      heapifyBU(arr, N, index);

    return arr;
  }
  
  //DELETE THE MAX-------------------------------------------------------------
  static int[] deleteMax(int[] arr, int N){
    //replace root with last node
    arr[0] = arr[N-1];

    N--;  //decrease arr size
    int[] temp = new int[N];  //create new arr
    for(int i = 0; i < N; i++){
      temp[i] = arr[i]; //copy values over
    }
    arr = temp;  //point old arr to new array
    
    heapify(arr, N, 0);  //heapify down
    return arr;
  }
  
  //DISPLAY THE ARRAY----------------------------------------------------------
  static void display(int[] arr){
    String display = "";
    for(int i = 0; i < arr.length; i++){  //loop through arr and build output string
      display += arr[i] + " ";
    }
    System.out.println(display.trim());
  }
  
  //DISPLAY THE MAX------------------------------------------------------------
  static void displayMax(int[] arr){
    System.out.println(arr[0]);
  }

  //BUILD HEAP-----------------------------------------------------------------
  static void buildHeap(int arr[], int N){
       // Index of last non-leaf node, aka last parent
        int startIdx = (N / 2) - 1;
 
        //move upwards from last parent to root
        for (int i = startIdx; i >= 0; i--) {
            heapify(arr, N, i);
        }
    }

  //HEAPIFY BOTTOM-UP----------------------------------------------------------
  static void heapifyBU(int arr[], int n, int i){
     // Find parent
    int parent = (i - 1) / 2;

    //make sure parent isnt 0
    if (arr[parent] > 0) {      //make sure parent isnt 0
      if (arr[i] > arr[parent]) {  //current child is greater than parent, swap
         
        // swap arr[i] and arr[parent]
        int temp = arr[i];
        arr[i] = arr[parent];
        arr[parent] = temp;
       
        // Recursively heapify the parent node
        heapifyBU(arr, n, parent);
      }
    }
  }
  
  //HEAPIFY--------------------------------------------------------------------
  static void heapify(int[] arr, int N, int i){
    int largest = i; // Initialize largest as root
    int l = 2 * i + 1; // left = 2*i + 1
    int r = 2 * i + 2; // right = 2*i + 2

    // If left child is larger than root
    if (l < N && arr[l] > arr[largest])
        largest = l;

    // If right child is larger than largest so far
    if (r < N && arr[r] > arr[largest])
        largest = r;

    // If largest is not root
    if (largest != i) {
        int swap = arr[i];
        arr[i] = arr[largest];
        arr[largest] = swap;

      // Recursively heapify the affected sub-tree
      heapify(arr, N, largest);
    }
  }
}

