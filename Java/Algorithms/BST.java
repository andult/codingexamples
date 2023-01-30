/*
 * Abstract: This program has different functions that gather information about a BST.
 * There are ordering methodss that will recursively read the tree and print the 
 * desired order. It also allows for checking the height of the tree along with 
 * checking how many leaves and one child nodes there are in the tree.
 */
import java.util.*;
 
class Main 
{
  public static void main(String[] args) {
    //declare variables---------------------
    Scanner readme = new Scanner(System.in);
    BST t1 = new BST();
    int commands = readme.nextInt();

    //loop to read commands until there are no more---
    while(commands != -1){
      String input = readme.nextLine();
      if(input.contains("add")){
        int add = Integer.parseInt(input.substring(4));
        t1.add(add);
      }
      else if(input.contains("preOrder")){
        t1.preOrder();
      }
      else if(input.contains("postOrder")){
        t1.postOrder();
      }
      else if(input.contains("height")){
        t1.height();
      }
      else if(input.contains("countLeaves")){
        t1.countLeaves();
      }
      else if(input.contains("countOneChildNodes")){
        t1.countOneChildNodes();
      }
      commands--;
    }

    return;
  }
}

//BST PROGRAM==================================================================
class Node {//-----------------------------------------------------------------  
  // Use "public" data to make the program simple (= no getters and setters).
  public int data;
  public Node left;
  public Node right;

  // Constructor
  public Node(int data, Node left, Node right){
    this.data = data;
    this.left = left;
    this.right = right;
  }
} 

// A class for a BST which may include many Nodes.
class BST {//------------------------------------------------------------------
  private Node root;
  ArrayList<Integer> orderList = new ArrayList<>();  //stores order (pre or post)
  
  public Node getRoot(){
    return root;
  }
  
  // Add an item to the BST
  // Internally, it calls "insert()" for actual insertion if root is not null.
  public void add(int item){
    Node newNode = new Node(item, null, null);

    // Empty tree
    if (root == null){
      root = newNode;
    }
    else{
      insert(newNode, root);
    }
  }

  // Insert a new node under the subtree using recursion
  private void insert(Node newNode, Node subTree){
    // Go to the left subtree
    if (newNode.data < subTree.data) {
      if(subTree.left == null){
          subTree.left = newNode;
      }
      else{
          insert(newNode, subTree.left);
      }
    }
    else {// Go to the right subtree
      if(subTree.right == null){
          subTree.right = newNode;
      }
      else{
          insert(newNode, subTree.right);
      }
    }
  }

  //ordering methods-------------------------------------------------
  public void inOrder(){
    inOrder(root);
    System.out.println();
  } 
  
  public void preOrder(){
    orderList.clear();  //clear list 
    preOrder(root);
    String preOrder = "";
    for(int i =0; i < orderList.size(); i++){  //print out arraylist with order
      preOrder += orderList.get(i) + " ";
    }
    System.out.println(preOrder.trim());
  } 
  
  public void postOrder(){
    orderList.clear();  //clear list
    postOrder(root);
    String postOrder = "";
    for(int i =0; i < orderList.size(); i++){  //print out arraylist with order
      postOrder += orderList.get(i) + " ";
    }
    System.out.println(postOrder.trim());
  } 

  private void inOrder(Node subTree){
    if (subTree != null){
      inOrder(subTree.left);
      System.out.print(subTree.data + " ");
      inOrder(subTree.right);
    }
  }

  private void preOrder(Node subTree){
    if (subTree != null){
      orderList.add(subTree.data);  //add to arraylist
      preOrder(subTree.left);
      preOrder(subTree.right);
    }
  }

  private void postOrder(Node subTree){
    if (subTree != null){
      postOrder(subTree.left);
      postOrder(subTree.right);
      orderList.add(subTree.data);  //add to arraylist

    }
  }

  //height and children methods--------------------------------------
  public void height(){
    System.out.println(height(root)); 
  }

  public void countLeaves(){
    System.out.println(countLeaves(root)); 
  }

  public void countOneChildNodes(){
    System.out.println(countOneChildNodes(root)); 
  }
  
  private int height(Node subTree){
    if(subTree != null){
      //recursively visit each node and find the height
      return Math.max(height(subTree.left), height(subTree.right)) + 1;
    }
    return -1;
  }

  private int countLeaves(Node subTree){
    if(subTree == null){
      return 0;
    }
    else if(subTree.left == null && subTree.right == null){  //no child, it itself is a leaf
      return 1;
    }
    else{
      return countLeaves(subTree.left) + countLeaves(subTree.right);  //check each node recursively
    }
  }

  private int countOneChildNodes(Node subTree){  //parents with one child
    if(subTree == null){
      return 0;
    }
    else if(subTree.left == null && subTree.right == null){  //it is a child
      return 0;
    }
    else if(subTree.left == null || subTree.right == null){  //parent with one child node
      return 1;
    }
    else{
      return countOneChildNodes(subTree.left) + countOneChildNodes(subTree.right);  //check each node recursively
    }
  }  
}  // end of class BST=========================================================
