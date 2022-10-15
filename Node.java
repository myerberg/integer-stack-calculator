/*
Tyler Myerberg
Created: 9/23/22
Updated: 10/15/22
Integer Stack Calculator

Description: Node class defines Node objects to be used in StackCalculator and LinkedListStack. 
These Node objects constitute the stack and queue which serve as the main components of the 
integer calculator.
*/

//begin Node class
public class Node {

	//identifies the String Node
	public String name;
	
	//points to the next Node
	public Node next;
	
	//constructor for Node
	public Node(String name) {
		
		//sets name to name from imported parameter
		this.name = name;
		
		//sets next to null
		next = null;
	
	//closes constructor
	}
	
	//getName for Node
	public String getName() {
		
		//returns name of Node
		return name;
		
	//closes getName
	}
	
	//setName for Node
	public void setName(String name) {
		
		//sets name of Node to imported name parameter
		this.name = name;
	
	//closes setName
	}

	//getNext for Node
	public Node getNext() {
		
		//returns next of Node
		return next;
		
	//closes getNext
	}
	
	//setNext for Node
	public void setNext(Node next) {
		
		//sets next of Node to imported next parameter
		this.next = next;
	
	//closes setNext
	}
	
	//toString for testing
	public String toString(){
		
		//returns String containing Node name
		return "Node[name = " + name + "]";
		
	//closes toString
	}

//closes Node class
}