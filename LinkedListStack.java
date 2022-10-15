/*
Tyler Myerberg
Created: 9/23/22
Updated: 10/15/22
Integer Stack Calculator

Description: LinkedListStack allows for the creation of Linked Lists in the StackCalculator class.
Two Linked Lists are used in this method in the implementation of a stack and queue for 
the integer calculator.
*/

//begin LinkedListStack class
public class LinkedListStack {
	
	//sets global Node head variable to null
	public Node head = null;
	
	//PreCondition: Node p is sent as parameter
	//PostCondition: p is the new node to be added to the linked list
	//Description: Push method adds an element to the head of the linked list
	public void Push(Node p){
		
		//if head is equal to null (list is empty)
		if (head == null){
			
			//sets imported p Node as head (first in list)
			head = p;
		
		//closes if statement
		}
		
		//list is not empty
		else{
			
			//sets Next of p to head
			p.setNext(head);
			
			//sets head to p
			head = p;
		
		//closes else statement
		}
	
	//closes Push method
	}
	
	//PreCondition: list must not be empty (it must have a first element, method is only used when there is a first element)
	//PostCondition: first element is removed from the linked list
	//Description: Pop method removes the element at the head of the linked list
	public void Pop(){
		
		//creates index Node and sets equal to head
		Node index = head;
		
		//gets next of index and sets it equal to index
		index = index.getNext();
	
		//sets head equal to new index
		head = index;
	
	//closes Pop method
	}
	
	//PreCondition: list can have a first element or be null
	//PostCondition: first element is returned from the linked list
	//Description: returns the node at the first index
	public Node Peek(){
		
		//creates index Node and sets equal to head
		Node index = head;
		
		//if list is not empty
		if(head != null) {
			
			//returns index equal to head
			return index;
		
		//closes if statement
		}
		
		//if list is empty
		return null;
	
	//closes Peek method
	}
	
	//PreCondition: Linked List must have contents to display something when method is run
	//PostCondition: list is printed for user
	//Description: prints out the Linked List, used for testing
	public void PrintList(){
		
		//creates index Node and sets equal to head
		Node index = head;
		
		//while index does not equal null
		while (index != null){
			
			//prints name of current index
			System.out.println("TEST" + index.getName());
			
			//sets index to getNext of index
			index = index.getNext();
		
		//closes while loop
		}
	
	//closes PrintList method
	}
	
//closes class
}