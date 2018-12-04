/*
Joseph Zanini
jzanini@ucsc.edu
cmps 101 fall 2018
PA3
*/
@SuppressWarnings("overrides")

public class List {

   private class Node {
      // Fields
      Object data;
      Node next;
      Node previous;
      
      // Constructor
      Node(Object ent) { 
         data = ent;
      	next = null; 
      	previous = null;
      }
      
      // toString():  overrides Object's toString() method
      public String toString() { 
         return String.valueOf(data); 
      }
      
      // equals(): overrides Object's equals() method
      public boolean equals(Object x){
         boolean eq = false;
         if(x instanceof Node) {
            eq = (this.data.equals(((Node) x).data));
         }
         return eq;
      }
   }

   // Fields
   private Node front;
   private Node back;
   private Node cursor;
   private int index;
   private int length;

   // Constructor
   List() { 
      front = back = cursor = null; 
      length = 0; 
      index = -1;
   }


   // Access Functions --------------------------------------------------------

   // isEmpty()
   // Returns true if this List is empty, false otherwise.
   boolean isEmpty() { 
      return length == 0; 
   }

   // length()
   // Returns length of this List.
   int length() { 
      return length; 
   }
   
   // index()
   // Returns the index of cursor element
   int index() {
		if(cursor != null) {
			return index;
		} else {
			return -1;
		}
   }

   // front() 
   // Returns front element.
   // Pre: length() > 0
   Object front(){
      if(this.isEmpty() ){
         throw new RuntimeException(
            "List Error: front() called on empty List");
      }
      return front.data;
   }
   
   // back() 
   // Returns back element.
   // Pre: length() > 0
   Object back(){
      if(this.isEmpty() ){
         throw new RuntimeException(
            "List Error: back() called on empty List");
      }
      return back.data;
   }
   
   // int get() 
   // Returns cursor element. Pre: length()>0, index()>=0
   Object get() {
   	if(this.isEmpty() ){
         throw new RuntimeException(
            "List Error: get() called on empty List");
      }
      
      if(cursor == null){
         throw new RuntimeException(
            "List Error: get() called on undefined cursor");
      }
      return cursor.data;
   }
   
   //boolean equals(List L) 
   // Overrides Object's equals() method.
   // Returns true if and only if this List and L are the same
   // integer sequence. The states of the cursors in the two Lists
   // are not used in determining equality.
   public boolean equals(Object L) {
   	  boolean eq = false;
   	  Node temp = this.front;
   	  Node hold = ((List) L).front;
   	  int i;
   	  if (this.length == ((List) L).length()) {
   	  	eq = true;
   	  	i = this.length;
   	  	while (eq == true && i > 0) {
   	  		eq = temp.equals(hold);
   	  		temp = temp.next;
   	  		hold = hold.next;
   	  		i--;
   	  	}
   	  }
   	  return eq;
   }

   // Manipulation Procedures -------------------------------------------------
   // void clear() 
   // Resets this List to its original empty state.
   void clear() {
   	this.front = this.back = this.cursor = null; 
      this.length = 0; 
      this.index = -1;
   }
   
   //void moveFront() 
   // If List is non-empty, places the cursor under the front element,
   // otherwise does nothing.
   void moveFront() {
   	  if (this.isEmpty() == false) {
   	  	 if (cursor == null) {
   	  	 	cursor = new Node(front.data);
   	  	 	cursor.next = front.next;
   	  	 	index = 0;
   	  	 } else {
   	  	 	cursor = front;
   	  	 	index = 0;
   	  	 }
   	  }
   }
   
   //void moveBack() 
   // If List is non-empty, places the cursor under the back element,
   // otherwise does nothing.
   void moveBack() {
   	  if (this.isEmpty() == false) {
   	  	 if (cursor == null) {
   	  	 	cursor = new Node(this.back());
   	  	 	cursor.previous = back.previous;
   	  	 	index = (length - 1);
   	  	 } else {
   	  	 	cursor = back;
   	  	 	cursor.previous = back.previous;
   	  	 	index = (length - 1);
   	  	 }
   	  }
   }
   
   //void movePrev() 
   // If cursor is defined and not at front, moves cursor one step toward
   // front of this List, if cursor is defined and at front, cursor become
   // undefined, if cursor is undefined does nothing.
   void movePrev() {
   	  
   	  if (cursor != null && index > 0) { 
   	  	cursor = cursor.previous; 
   	  	index--;
   	  } 
   	  if (cursor != null && index == 0) {
   	  	cursor = null;
   	  	index= -1;
   	  }
   }
   
   //void moveNext() 
   // If cursor is defined and not at back, moves cursor one step toward
   // back of this List, if cursor is defined and at back, cursor become
   // undefined, if cursor is undefined does nothing.
   void moveNext()  {
   	  if (cursor != null && index != (length - 1)) {
   	  	cursor = cursor.next;
   	  	index++;
   	  } else if (index == (length - 1)) {
   	  	cursor = null;
   	  	index = -1;
   	  }
   }
   
   //void prepend(int data)
   // Insert new element into this List. If List is non-empty,
   // insertion takes place before front element.
   void prepend(Object data) {
   	  Node N = new Node(data);
   	  if (this.isEmpty()) {
   	  	 front = back = N;
   	  } else {
   	  	 front.previous = N;
   	  	 N.next = front;
   	  	 front = N;
   	  }
   	  length++;
   }
   
   
   //void append(int data) 
   // Insert new element into this List. If List is non-empty,
   // insertion takes place after back element.
   void append(Object data){
   	  Node N = new Node(data);
      if ( this.isEmpty() ) { 
         front = back = N;
      } else { 
         back.next = N; 
         N.previous = back;
         back = N;
      }
      length++;
   }
   
   //void insertBefore(int data) 
   // Insert new element before cursor.
   // Pre: length()>0, index()>=0
   void insertBefore(Object data) {
      if(this.isEmpty()) {
         throw new RuntimeException(
            "List Error: insertBefore() called on empty List");
      }
      
      if(cursor == null) {
         throw new RuntimeException(
            "List Error: inserBefore() called on undefined cursor");
      }
      if (cursor.previous == null) {
      	this.prepend(data);
      } else {
      	Node N = new Node(data);
      	N.previous = cursor.previous;
      	cursor.previous.next = N;
      	cursor.previous = N;
      	N.next = cursor;
      	length++;
      }
   }
   
   //void insertAfter(int data) 
   // Inserts new element after cursor.
   // Pre: length()>0, index()>=0
   void insertAfter(Object data) {
   	  if(this.isEmpty()) {
         throw new RuntimeException(
            "List Error: insertBefore() called on empty List");
      }
      
      if(cursor == null) {
         throw new RuntimeException(
            "List Error: inserBefore() called on undefined cursor");
      }
      
      if (cursor.next == null) {
      	this.append(data);
      } else {
      	Node N = new Node(data);
      	N.next = cursor.next;
      	cursor.next.previous = N;
      	N.previous = cursor;
      	cursor.next = N;
      }
      length++;
   }

   //void deleteFront() 
   // Deletes the front element. Pre: length()>0
   void deleteFront(){
      if (this.isEmpty()) {
         throw new RuntimeException(
            "List Error: deleteFront() called on empty List");
      }
      if (this.length>1) {
      	 if (cursor != null && front.next.equals(cursor.next)){
      	 	cursor = null;
      	 	index = -1;
      	 }
         front = front.next;
         front.previous = null;
      }else{
         front = back = cursor = null;
         index = -1;
      }
      length--;
   }
   
   //void deleteBack() 
   // Deletes the front element. Pre: length()>0
   void deleteBack(){
      if (this.isEmpty()) {
         throw new RuntimeException(
            "List Error: deleteBack() called on empty List");
      }
      if (this.length>1) {
      	 if (cursor != null && back.previous.equals(cursor.previous)){
      	 	cursor = null;
      	 	index = -1;
      	 }
         back = back.previous;
         back.next = null;
      }else{
         front = back = cursor = null;
         index = -1;
      }
      length--;
   }
   
   //void delete() 
   // Deletes cursor element, making cursor undefined.
   // Pre: length()>0, index()>=0
   
   void delete() {
   	  if (this.isEmpty()) {
         throw new RuntimeException(
            "List Error: deleteBack() called on empty List");
      }
      
      
      if (cursor == null) {
      	throw new RuntimeException(
            "List Error: delete() called on undefined cursor");
      }
      
      if (cursor.next == null) {
      	this.deleteBack();
      } else if (cursor.previous == null) {
      	this.deleteFront();
      } else {
      	cursor.next.previous = cursor.previous;
      	cursor.previous.next = cursor.next;
      	cursor = null;
      	index = -1;
      	length--;
      }
      
      
   }


   // Other Methods ---------------------------------------------------------

   //public String toString() 
   // Overrides Object's toString method. Returns a String
   // representation of this List consisting of a space
   // separated sequence of integers, with front on left.
   public String toString(){
      StringBuffer sb = new StringBuffer();
      Node N = front;
      while(N!=null){
         sb.append(" ");
         sb.append(N.toString());
         N = N.next;
      }
      return new String(sb);
   }
}