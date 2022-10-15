/*
Tyler Myerberg
Created: 10.8.22
Updated: 10.15.22
Integer Stack Calculator

Description: The StackCalculator class is called by a Driver class to appropriately manage String 
parameters from the Driver. They are manipulated using methods to separate letters, numbers, and 
operators. The contents of the ArrayList are then sorted appropriately according to Postfix Notation 
between a Stack and a Queue, both of which are produced using Linked Lists according to 
LinkedListStack.java and Node.java. Once contents have been sorted correctly, the calculation method 
is called step by step to generate the final answer for the requested input. A hash integer array 
and a corresponding boolean array are used to manage the storage and implementation of user-defined 
variables, which can be any single digit letter, lowercase or uppercase. Error-catching in user 
input is implemented, and the specifics of errors from Driver input are highlighted to the user. 
Final answers are displayed and calculations are done using integers exclusively.

*/

//import ArrayList
import java.util.ArrayList;

//begin StackCalculator class
public class StackCalculator {
	
	//global hashArray for storing values of letter variables
	public static int [] hashArray = new int[58];
	
	//global hashBoolean array to verify variables have been given a value
	public static boolean [] hashBoolean = new boolean[58];
	
	//global endAfterSet boolean variable for ending processInput when letter variable is set to value
	public static boolean endAfterSet;

	//PreCondition: String from driver is in its original state
	//PostCondition: String has been manipulated to perform the appropriate operation for the 
	//the mathematical function included in the input. Result (or error description) is 
	//displayed to user.
	//Method Description: begin processInput method, which is called from driver with String 
	//parameter to be manipulated before performing calculations
	public void processInput(String stringImport) {
		
		//reset endAfterSet to false for each new stringImport
		endAfterSet = false;

		//prints imported String for user verification
		System.out.println("Imported String:\t" + stringImport + "\n");
		
		//create al1 ArrayList
		ArrayList al1 = new ArrayList();

		//for loop to go through the imported String s, character by character
		for(int i = 0; i < stringImport.length(); i++) {
			
			//if the character at the particular i location is not a space and is not a \t
			if(stringImport.charAt(i) != ' ' && stringImport.charAt(i) != '\t') {
				
				//add this character to al1 ArrayList
				al1.add(stringImport.charAt(i));

			//close if statement
			}
			
			//if the character at the particular i location does not satisfy the requirement of not being a space (as in it is indeed a space)
			else{
				
				//manipulate the imported parameter String using substring, with all characters up to the space and all characters after the space
				stringImport = stringImport.substring(0,i) + stringImport.substring(i+1);
				
				//subtract i by 1 given that we have moved all characters after i one character to the left
				i--;

			//close else statement	
			}

		//close for loop
		}
		
		//for loop to go through the newly created ArrayList al1, element by element, to see if the element contains a number
		for(int i = 0; i < al1.size() - 1; i++) {
			
			//if statement, checkNum method is called, returning true if the element at i is a number
			if(checkNum(((al1.get(i)) + "").charAt(0) + "")) {
				
				//if statement calling checkNum again, returning true if the element one past i is a number
				if(checkNum((al1.get(i+1)) + "")) {
					
					//if the element one past is a number, the i element in al1 is adjusted to be the element of i and i+1 concatenated
					al1.set(i,al1.get(i) + "" + al1.get(i+1));
					
					//the i+1 element is removed given that it has been concatenated to the ith element
					al1.remove(i+1);
					
					//i is substracted by 1 given that we have shift all elements one to the left
					i--;
				
				//close if statement
				}
			
			//close if statement	
			}
		
		//close for loop
		}
		
		//for loop to go through the newly created ArrayList al1, element by element, to see if the element contains a star
		for(int i = 0; i < al1.size() - 1; i++) {
			
			//if statement, checkStar method is called, returning true if the element at i is a star
			if(checkStar(((al1.get(i)) + "").charAt(0) + "")) {
				
				//if statement calling checkStar again, returning true if the element one past i is a star
				if(checkStar((al1.get(i+1)) + "")) {
					
					//if the character one past is a star, the i element in al1 is adjusted to be the element of i and i+1 concatenated (two stars together)
					al1.set(i,al1.get(i) + "" + al1.get(i+1));
					
					//the i+1 element is removed given that it has been concatenated to the ith element
					al1.remove(i+1);
					
					//i is subtracted by 1 given that we have shift all elements one to the left
					i--;
				
				//close for loop
				}
				
			//close for loop	
			}
		
		//close for loop
		}
		
		//for loop to go through the newly created ArrayList al1, element by element, to see if the element is a negative sign that needs to be attached to a number
		for(int i = 0; i < al1.size() - 1; i++) {
			
			//if statement, checkOperator method is called, returning true if the element at i is an operator
			if(checkOperator(((al1.get(i)) + "").charAt(0) + "")) {
				
				//if statement calling checkNegative, returning true if the element one past i is a -
				if(checkNegative((al1.get(i+1)) + "")) {
					
					//if statement calling checkNum, to see if the element following the - a number (thereby proving it must be a negative sign given that it comes between an operator and a number)
					if(checkNum(((al1.get(i+2)) + "").charAt(0) + "")) {
					
						//make the element at i+1 a concatenation of i+1 and i+2 (the - and the following number)
						al1.set(i+1,al1.get(i+1) + "" + al1.get(i+2));
					
						//the i+2 element is removed given that it has been concatenated to the i+1 element
						al1.remove(i+2);
					
						//i is substracted by 1 given that we have shift all elements one to the left
						i--;
					
					//close if statement
					}
				
				//close if statement
				}
				
			//close if statement	
			}
		
		//close for loop
		}
		
		//check for ending in operator, therefore nonsensical
		if(al1.size() > 0) {
		
			//if the last digit in the ArrayList is an operator
			if(checkOperator((al1.get(al1.size() - 1) + ""))) {
				
				//display the name of nonsensical input String to user
				System.out.println("Nonsensical Input:\t" + stringImport + "\n");
				
				//continue to next imported String (or end program if there are no more Strings in driver)
				return;
			
			//close if statement
			}
		
		//close if statement
		}
		
		//to handle single number or letter when there are also parentheses
		//numLetterCounter placeholder
		int numLetterCounter = 0;
		
		//numLetterLocation placeholder
		int numLetterLocation = 0;
		
		//numOperatorCounter placeholder
		int numOperatorCounter = 0;
		
		//for loop to go through ArrayList to count quantity of numbers and letters
		for(int i = 0; i < al1.size(); i++) {
			
			///if an element is a number or letter
			if(checkNumber((al1.get(i) + "")) || checkLetter((al1.get(i) + ""))) {
				
				//increase numLetterCounter by 1
				numLetterCounter++;
				
				//set numLetterLocation equal to i
				numLetterLocation = i;
				
			//close if statement
			}
			
			//else if an element is an operator
			else if(checkOperator(al1.get(i) + "")){
				
				//increase numOperatorCounter by 1
				numOperatorCounter++;
			
			//close else if statement
			}
			
		//close for loop
		}
		
		//if numLetterCounter equals 1 and numOperatorCounter equals 0
		if(numLetterCounter == 1 && numOperatorCounter == 0) {
			
			//add + to element one past numLetterLocation
			al1.add(numLetterLocation + 1, "+");
			
			//add 0 to element two past numLetterLocation (this will allow the single number/letter to be added to 0
			al1.add(numLetterLocation + 2, "0");
		
		//close if statement
		}
		
		//if imported String is an empty entry
		if(al1.size() == 0) {
			
			//display to user final answer of 0
			System.out.println("Final Answer: \t\t0\n");
			
			//continue to next imported String (or end program if there are no more Strings in driver)
			return;		
		
		//close if statement
		}		
		
		//fix initial negative characters
		//if al1 is greater than 1 element
		if(al1.size() > 1) {
			
			//check that first element is a - and second element is a number
			if((al1.get(0) + "").charAt(0) == '-' & checkNumber((al1.get(1) + ""))) {
				
				//set first element as concatenation of these two elements
				al1.set(0, (al1.get(0) + "") + (al1.get(1) + ""));
				
				//remove the number element without the -
				al1.remove(1);
				
			//close if statement
			}
		
		//close if statement
		}
		
		//fix initial positive characters
		//if al1 is greater than 1 element
		if(al1.size() > 1) {
			
			//check that first element is a + and second element is a number
			if((al1.get(0) + "").charAt(0) == '+' & checkNumber((al1.get(1) + ""))) {
				
				//set first element as concatenation of these two elements
				al1.set(0, (al1.get(0) + "") + (al1.get(1) + ""));
				
				//remove the number element without the -
				al1.remove(1);
			
			//close if statement
			}
		
		//close if statement
		}
		
		//to handle unary negative
		//if the first element of al1 has an initial character of -
		if((al1.get(0) + "").charAt(0) == '-') {
			
			//for loop to go through al1
			for(int i = 0; i < al1.size(); i++) {
				
				//if al1 element has length 1 and is a - and the next element starts with a -
				if((al1.get(i) + "").length() == 1 && (al1.get(i) + "").charAt(0) == '-' && (al1.get(i + 1) + "").charAt(0) == '-') {
					
					//set al1 as -1
					al1.set(i, "-1"); 
					
					//add a * to i + 1 element (this will multiply by -1)
					al1.add(i + 1, "*");
				
				//close if statement
				}
				
			//close for loop
			}
		
		//close if statement
		}
		
		//if al1 first element does not begin with a -
		if((al1.get(0) + "").charAt(0) != '-') {
			
			//for loop through al1
			for(int i = 0; i < al1.size(); i++) {
				
				//if al1 element has length 1 and is a - and the next element starts with a -
				if((al1.get(i) + "").length() == 1 && (al1.get(i) + "").charAt(0) == '-' && (al1.get(i + 1) + "").charAt(0) == '-') {
					
					//set al1 as -1
					al1.set(i, "-1"); 
					
					//add a * to i + 1 element (this will multiply by -1)
					al1.add(i + 1, "*");
					
					//add ( to i element
					al1.add(i, "(");
					
					//add ) to i + 5 element (this will allow the appropriate terms to be multiplied by -1)
					al1.add(i + 5, ")");
					
				//close if statement
				}
				
			//close for loop
			}
		
		//close if statement
		}

		//to handle unary positive
		//if the first element of al1 has an initial character of +
		if((al1.get(0) + "").charAt(0) == '+') {
				
			//for loop to go through al1
			for(int i = 0; i < al1.size(); i++) {
				
				//if al1 element has length 1 and is a + and the next element starts with a +
				if((al1.get(i) + "").length() == 1 && (al1.get(i) + "").charAt(0) == '+' && (al1.get(i + 1) + "").charAt(0) == '+') {
					
					//remove element i in ArrayList
					al1.remove(i);
					
					//set element i in al1 as 0
					al1.set(i, "0");
				
				//close if statement
				}
				
				//remove positive sign from initial number
				else if((al1.get(i) + "").length() > 1 && (al1.get(i) + "").charAt(0) == '+') {
					
					//sets the contents of al1 at i to everything but the first element
					al1.set(i, (String)(al1.get(i) + "").substring(1)); 
				
				//close if statement
				}
				
			//close for loop
			}
		
		//close if statement
		}
		
		//if al1 first element does not begin with a +
		if((al1.get(0) + "").charAt(0) != '+') {
			
			//for loop through al1
			for(int i = 0; i < al1.size(); i++) {
				
				//if al1 element has length 1 and is a + and the next element starts with a +
				if((al1.get(i) + "").length() == 1 && (al1.get(i) + "").charAt(0) == '+' && (al1.get(i + 1) + "").charAt(0) == '+') {
					
					//set al1 as -1
					al1.set(i, "1"); 
					
					//add a * to i + 1 element (this will multiply by -1)
					al1.add(i + 1, "*");
					
					//add ( to i element
					al1.add(i, "(");
					
					//add ) to i + 5 element (this will allow the appropriate terms to be multiplied by -1)
					al1.add(i + 5, ")");
					
				//close if statement
				}
				
			//close for loop
			}
		
		//close if statement
		}
		
		//to handle single number/letter input
		if(al1.size() == 1) {
			
			//if the input is a number or a letter
			if(checkNumber(al1.get(0) + "") || checkLetter(al1.get(0) + "")) {
				
				//add + to element 1
				al1.add(1, "+");
				
				//add 0 to element 2 (this will allow the single number/letter to be added to 0
				al1.add(2, "0");
			
			//close if statement
			}
			
			//else statement if not number/letter
			else {
				
				//display to user nonsensical input message
				System.out.println("Nonsensical Input:\t" + stringImport + "\n");
				
				//continue to next imported String (or end program if there are no more Strings in driver)
				return;
			
			//close else statement
			}
		
		//close if statement
		}
		
		//for loop to handle multiple letter variable name, starts at i = 1
		for(int i = 1; i < al1.size(); i++) {
			
			//if there are any two letters in a row
			if(checkLetter(al1.get(i - 1) + "") && checkLetter(al1.get(i) + "")) {
				
				//display the name of these two letters to user as invalid
				System.out.println("Invalid Variable Name:\t" + al1.get(i - 1) + al1.get(i) + "\n");
				
				//continue to next imported String (or end program if there are no more Strings in driver)
				return;
			
			//close if statement
			}
		
		//close for loop
		}
		
		//to handle nonsensical parentheses (for instances when ArrayList is 3 or more elements)
		if(al1.size() >= 3) {
			
			//for loop to go through entire ArrayList
			for(int i = 0; i < al1.size() - 2; i++) {
				
				//if the element at i + 1 is length 1 or length 2 (to account for exponent)
				if((al1.get(i + 1) + "").length() <= 2) {	
				
					//if the input is a number or a letter
					if(((al1.get(i) + "").charAt(0) == '[' || (al1.get(i) + "").charAt(0) == ']' ||
					    (al1.get(i) + "").charAt(0) == '(' || (al1.get(i) + "").charAt(0) == ')' ||
					    (al1.get(i) + "").charAt(0) == '{' || (al1.get(i) + "").charAt(0) == '}') &&
					   ((al1.get(i + 1) + "").charAt(0) == '+' || (al1.get(i + 1) + "").charAt(0) == '-' ||
						(al1.get(i + 1) + "").charAt(0) == '*' || (al1.get(i + 1) + "").charAt(0) == '/' ||
						(al1.get(i + 1) + "").charAt(0) == '%' || (al1.get(i + 1) + "").charAt(0) == '=') &&
					   ((al1.get(i + 2) + "").charAt(0) == '[' || (al1.get(i + 2) + "").charAt(0) == ']' ||
					    (al1.get(i + 2) + "").charAt(0) == '(' || (al1.get(i + 2) + "").charAt(0) == ')' ||
					    (al1.get(i + 2) + "").charAt(0) == '{' || (al1.get(i + 2) + "").charAt(0) == '}')) {			
						
						//display to user nonsensical input message
						System.out.println("Nonsensical Input:\t" + stringImport + "\n");
						
						//continue to next imported String (or end program if there are no more Strings in driver)
						return;
					
					//close if statement
					}
				
				//close if statement
				}
			
			//close for loop
			}
			
		//close if statement
		}
		
		//for loop to handle nonsensical input, starting at i = 1
		for(int i = 1; i < al1.size(); i++) {
			
			//if there are any two operators in a row
			if(checkOperator(al1.get(i - 1) + "") && checkOperator(al1.get(i) + "")) {
				
				//display the name of nonsensical input String to user
				System.out.println("Nonsensical Input:\t" + stringImport + "\n");
				
				//continue to next imported String (or end program if there are no more Strings in driver)
				return;
			
			//close if statement
			}
		
		//close for loop
		}
		
			//creates first instance of LinkedListStack class (this will function as Stack)
			LinkedListStack ll = new LinkedListStack();
			
			//creates first instance of LinkedListStack class (this will function as Queue)
			LinkedListStack llQueue = new LinkedListStack();

			//for loop to go through ArrayList containing contents of String
			for(int i = 0; i < al1.size(); i++) {
				
				//converts object in each element of ArrayList to String
				String indexCharacter = ((al1.get(i)) + "");
				
				//creates indexCharacterNode Node to be analyzed for each element (indexCharacter) in ArrayList
				Node indexCharacterNode = new Node(indexCharacter);

				//if the character at the particular i location is a number, push to llQueue
				if(checkNumber(indexCharacter)) {
					
					//add this numerical character to the llQueue Linked List as the indexCharacterNode Node
					llQueue.Push(indexCharacterNode);
				
				//close if statement
				}
				
				//else if negative number
				else if(indexCharacter.charAt(0) == '-' && !checkOperator(indexCharacter)) {
					
					//push negative number to llQueue
					llQueue.Push(indexCharacterNode);
				
				//close else if statement
				}

				//if the character at the particular i location is a letter (variable)
				else if(checkLetter(indexCharacter)) {
					
					//push letter variables to llQueue
					llQueue.Push(indexCharacterNode);
						
				//close else if statement
				}
				
				//if character is equals sign
				else if(indexCharacter.charAt(0) == '=') {
	
					//always push = to ll
					ll.Push(indexCharacterNode);
										
				//close else if
				}	
						
				//else if character is an operator other than =
				else if(checkOperator(indexCharacter) && indexCharacter.charAt(0) != '=') {
					
					//if length is 2 must be a two character exponent
					if(indexCharacter.length() >= 2) {
							
						//if ll is not null
						if(ll.Peek() != null) {
							
							//if first character of ll.Peek is equals sign or a left parentheses
							if(ll.Peek().getName().charAt(0) == '=' || ll.Peek().getName().charAt(0) == '(') {
								
								//push exponent to ll
								ll.Push(indexCharacterNode);

							//close if statement
							}
							
							//else if ll.Peek is not an exponent
							else if(ll.Peek().getName().length() != 2) {
								
								//create pOperator Node of exponent in ll
								Node pOperator = new Node(ll.Peek().getName());
								
								//send ll exponent to llQueue
								llQueue.Push(pOperator);
								
								//pop this exponent off ll
								ll.Pop();
								
								//push the new exponent onto ll
								ll.Push(indexCharacterNode);
							
							//close else if statement
							}
							
							//else ll.Peek must be an exponent
							else {
								
								//push exponent to ll
								ll.Push(indexCharacterNode);
							
							//close else statement
							}
							
						//close if statement
						}
						
						//else ll is empty
						else {
							
							//push exponent to ll
							ll.Push(indexCharacterNode);
						
						//close else statement
						}
						
					//close if statement
					}			
					
					//else if indexCharacter must be a 1 character operator
					else if(indexCharacter.length() == 1) {						
						
						//if a *, /, or %
						if(indexCharacter.charAt(0) == '*' || indexCharacter.charAt(0) == '/' || indexCharacter.charAt(0) == '%') {

							//if ll.Peek is not null
							if(ll.Peek() != null) {
								
								//if ll.Peek is a *, /, or %
								if(ll.Peek().getName().charAt(0) == '*' || ll.Peek().getName().charAt(0) == '/' || ll.Peek().getName().charAt(0) == '%') {

									//create Node called pOperator set to name of ll.Peek
									Node pOperator = new Node(ll.Peek().getName());
									
									//push pOperator to queue
									llQueue.Push(pOperator);
									
									//pop from ll
									ll.Pop();
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close if statement
								}
								
								//else if ll.Peek is a + or -
								else if(ll.Peek().getName().charAt(0) == '+' || ll.Peek().getName().charAt(0) == '-') {
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close else if statement
								}
								
								//else if ll.Peek is a =
								else if(ll.Peek().getName().charAt(0) == '=') {
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close else if statement
								}
								
								//else if ll.Peek if a [, (, or [
								else if(ll.Peek().getName().charAt(0) == '[' || ll.Peek().getName().charAt(0) == '(' || ll.Peek().getName().charAt(0) == '{') {
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close else if statement
								}
							
							//close if statement
							}
							
							//else ll is empty
							else {
								
								//push current indexCharacterNode to ll
								ll.Push(indexCharacterNode);
							
							//close else statement
							}
								
						//close if statement		
						}
						
					//close else if statement		
					}
						
						//if a + or -
						if(indexCharacter.charAt(0) == '+' || indexCharacter.charAt(0) == '-') {

							//if ll in not empty
							if(ll.Peek() != null) {
								
								//if ll.Peek is a **, *, /, %, +, or -
								if(ll.Peek().getName().charAt(0) == '*' || ll.Peek().getName().charAt(0) == '/' || ll.Peek().getName().charAt(0) == '%' || ll.Peek().getName().charAt(0) == '+' || ll.Peek().getName().charAt(0) == '-') {

									//create Node called pOperator set to name of ll.Peek
									Node pOperator = new Node(ll.Peek().getName());
									
									//push pOperator to queue
									llQueue.Push(pOperator);
									
									//pop from ll
									ll.Pop();
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close if statement
								}
								
								//else if ll.Peek is an =
								else if(ll.Peek().getName().charAt(0) == '=') {
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close else if statement
								}
								
								//else if ll.Peek is [, (, or {
								else if(ll.Peek().getName().charAt(0) == '[' || ll.Peek().getName().charAt(0) == '(' || ll.Peek().getName().charAt(0) == '{') {
									
									//push current indexCharacterNode to ll
									ll.Push(indexCharacterNode);
								
								//close else if statement
								}
							
							//close if statement
							}
							
							//else ll is empty
							else {
								
								//push current indexCharacterNode to ll
								ll.Push(indexCharacterNode);
							
							//close else statement
							}
						
						//close if statement
						}		

				//close checkOperator else if
				}

				//else if the character at the particular i location is either a [, (, or {
				else if(indexCharacter.charAt(0) == '[' || indexCharacter.charAt(0) == '(' || indexCharacter.charAt(0) == '{') {

					//add this character to the ll Linked List as the indexCharacterNode Node
					ll.Push(indexCharacterNode);
					
				//close if statement
				}
				
				//if the character at the particular i location is either a ], ), or }
				else if(indexCharacter.charAt(0) == ']' || indexCharacter.charAt(0) == ')' || indexCharacter.charAt(0) == '}') {
					
					//if check is not null
					if(ll.Peek() != null) {
					
						//if indexCharacter is a ]
						if(indexCharacter.charAt(0) == ']') {
							
							//if ll.Peek is an operator
							if(checkOperator(ll.Peek().getName().charAt(0) + "")) {
							
								//create Node called pOperator set to name of ll.Peek
								Node pOperator = new Node(ll.Peek().getName());
								
								//push pOperator to queue
								llQueue.Push(pOperator);
								
								//remove operator
								ll.Pop();
								
								//removes the corresponding Node, which contains [
								if(ll.Peek().getName().charAt(0) == '[') {
									
									//removes the corresponding [
									ll.Pop();
									
								//close if statement
								}
								
								//else unbalanced, mismatched
								else {
									
									//prints unbalanced message to user
									System.out.println("Expected output: Unbalanced Parentheses Error, Mismatched Parentheses\n");
									
									//continue to next imported String (or end program if there are no more Strings in driver)
									return;
								
								//close else statement
								}
							
							//close if statement
							}
							
							//else if ll.Peek is [
							else if(ll.Peek().getName().charAt(0) == '[') {
							
								//remove this [ from ll
								ll.Pop();
							
							//close else if statmenet
							}
							
							//else if ll.Peek is a ( or { it is unbalanced, mismatched
							else if(ll.Peek().getName().charAt(0) == '(' || ll.Peek().getName().charAt(0) == '{') {
								
								//prints unbalanced message to user
								System.out.println("Expected output: Unbalanced Parentheses Error, Mismatched Parentheses\n");
								
								//continue to next imported String (or end program if there are no more Strings in driver)
								return;
							
							//close else if statement
							}
							
						//closes if statement
						}

						//else if indexCharacter is a )
						else if(indexCharacter.charAt(0) == ')') {
							
							//if ll.Peek is an operator
							if(checkOperator(ll.Peek().getName().charAt(0) + "")) {
							
								//create Node called pOperator set to name of ll.Peek
								Node pOperator = new Node(ll.Peek().getName());
								
								//push pOperator to queue
								llQueue.Push(pOperator);
								
								//remove operator
								ll.Pop();
								
								//removes the corresponding Node, which contains (
								if(ll.Peek().getName().charAt(0) == '(') {
									
									//removes the corresponding (
									ll.Pop();
									
								//close if statement
								}
								
								//else unbalanced, mismatched
								else {
									
									//prints unbalanced message to user
									System.out.println("Expected output: Unbalanced Parentheses Error, Mismatched Parentheses\n");
									
									//continue to next imported String (or end program if there are no more Strings in driver)
									return;
								
								//close else statement
								}

							//close if statement
							}
							
							//else if ll.Peek is (
							else if(ll.Peek().getName().charAt(0) == '(') {
							
								//remove this ( from ll
								ll.Pop();
							
							//close else if statement
							}
							
							//else if ll.Peek is a [ or { it is unbalanced, mismatched
							else if(ll.Peek().getName().charAt(0) == '[' || ll.Peek().getName().charAt(0) == '{') {
								
								//prints unbalanced message to user
								System.out.println("Expected output: Unbalanced Parentheses Error, Mismatched Parentheses\n");
								
								//continue to next imported String (or end program if there are no more Strings in driver)
								return;
							
							//close else if statement
							}		
						
						//closes else if statement
						}
						
						//if indexCharacter is a }
						else if(indexCharacter.charAt(0) == '}') {
							
							//if ll.Peek is an operator
							if(checkOperator(ll.Peek().getName().charAt(0) + "")) {
							
								//create Node called pOperator set to name of ll.Peek
								Node pOperator = new Node(ll.Peek().getName());
								
								//push pOperator to queue
								llQueue.Push(pOperator);
								
								//remove operator
								ll.Pop();
								
								//removes the corresponding Node, which contains {
								if(ll.Peek().getName().charAt(0) == '{') {
									
									//removes the corresponding {
									ll.Pop();
									
								//close if statement
								}
								
								//else unbalanced, mismatched
								else {
									
									//prints unbalanced message to user
									System.out.println("Expected output: Unbalanced Parentheses Error, Mismatched Parentheses\n");
									
									//continue to next imported String (or end program if there are no more Strings in driver)
									return;
								
								//close else statement
								}
							
							//close if statement
							}
							
							//else if ll.Peek is {
							else if(ll.Peek().getName().charAt(0) == '{') {
							
								//remove this { from ll
								ll.Pop();
							
							//close else if statement
							}
							
							//else if ll.Peek is a [ or ( it is unbalanced, mismatched
							else if(ll.Peek().getName().charAt(0) == '[' || ll.Peek().getName().charAt(0) == '(') {
								
								//prints unbalanced message to user
								System.out.println("Expected output: Unbalanced Parentheses Error, Mismatched Parentheses\n");
								
								//continue to next imported String (or end program if there are no more Strings in driver)
								return;
							
							//close else if statement
							}

						//closes else if statement
						}
					
					//closes wider else if statement
					}
					
					//check is null and we are trying to add a closing bracket, therefore meaning mismatched
					else {
						
						//prints unbalanced message to user
						System.out.println("Unbalanced Parentheses Error, Mismatched Parentheses\n");
						
						//continue to next imported String (or end program if there are no more Strings in driver)
						return;
					
					//closes else statement
					}
				
				//closes else if statement
				}
				
				//else indexCharacter is invalid and unsupported by the program
				else {
					
					//prints invalid message to user
					System.out.println("Invalid Symbol:\t\t" + indexCharacter.charAt(0) + "\n");
					
					//continue to next imported String (or end program if there are no more Strings in driver)
					return;
				
				//close else statement
				}

			//close for loop
			}
			
			//if there are any remaining brackets/parentheses in the Linked List, we know it unbalanced with too many left parentheses
			if((ll.Peek() != null) && (ll.Peek().getName().charAt(0) == '[' || ll.Peek().getName().charAt(0) == ']' || ll.Peek().getName().charAt(0) == '(' || ll.Peek().getName().charAt(0) == ')' || ll.Peek().getName().charAt(0) == '{' || ll.Peek().getName().charAt(0) == '}')){
				
				//prints unbalanced message to user
				System.out.println("Unbalanced Parentheses Error, Too Many Left Parentheses\n");
				
				//continue to next imported String (or end program if there are no more Strings in driver)
				return;
			
			//closes if statement
			}
			
			//while ll has any remaining contents
			while(ll.Peek() != null) {
				
				//creates node of name of remaining contents
				Node pRemaining = new Node(ll.Peek().getName());	
				
				//pushes pRemaining node to llQueue
				llQueue.Push(pRemaining);
				
				//removes from ll
				ll.Pop();
			
			//close while loop
			}

			//reverse order of queue by sending back to original stack
			while(llQueue.Peek() != null) {
				
				//creates node of name of llQueue contents
				Node pReverse = new Node(llQueue.Peek().getName());	
				
				//pushes pReverse node to ll
				ll.Push(pReverse);
				
				//removes from llQueue
				llQueue.Pop();
			
			//close while loop
			}
			
			//creates currentOperator String placeholder
			String currentOperator = "";
			
			//creates num1 String placeholder
			String num1 = "";
			
			//creates num2 String placeholder
			String num2 = "";
			
			//creates returningValue int placeholder
			int returningValue = 0;

			//go through Postfix queue (currently in ll linked list stack) and perform operations	
			while(ll.Peek() != null) {
				
				//if the current place in the linked list is not an operator or an equals sign
				if(!checkOperator(ll.Peek().getName())) {
					
					//set this current place to a new Node called currentOperand
					Node currentOperand = new Node(ll.Peek().getName());
					
					//push currentOperand to llQueue linked list
					llQueue.Push(currentOperand);
					
					//pop this operand off of ll
					ll.Pop();
				
				//close if statement
				}
				
				//else if the current place in the linked list is an operator
				else if (checkOperator(ll.Peek().getName())){
					
					//sets currentOperator to operator at ll.Peek
					currentOperator = ll.Peek().getName();
					
					//sets num1 to name of getNext of ll.Queue.Peek
					num1 = llQueue.Peek().getNext().getName();
					
					//sets num2 to name of ll.Queue.Peek
					num2 = llQueue.Peek().getName();
		
					//check for undefined variable in num1
					if(ll.Peek().getName().charAt(0) != '=' && checkLetter(num1)) {
						
						//cross-reference hashBoolean array to ensure it is true
						if(hashBoolean[hashFunction(num1) - 17] != true) {
						
							//if false, prints to user undefined message for variable
							System.out.println("Undefined Variable:\t" + num1 + "\n");
						
							//continue to next imported String (or end program if there are no more Strings in driver)
							return;
						
						//close if statement
						}
					
					//close if statement
					}
					
					//check for undefined variable in num2
					if(ll.Peek().getName().charAt(0) != '=' && checkLetter(num2)) {
						
						//cross-reference hashBoolean array to ensure it is true
						if(hashBoolean[hashFunction(num2) - 17] != true) {
						
							//if false, prints to user undefined message for variable
							System.out.println("Undefined Variable:\t" + num2 + "\n");
						
							//continue to next imported String (or end program if there are no more Strings in driver)
							return;
						
						//close if statement
						}
					
					//close if statement
					}
					
					//if num1 is not a letter and currentOperator is an = input is nonsensical
					if(!checkLetter(num1) && currentOperator.charAt(0) == '=') {
						
						//display the name of nonsensical input String to user
						System.out.println("Nonsensical Input:\t" + stringImport + "\n");
						
						//continue to next imported String (or end program if there are no more Strings in driver)
						return;
					
					//close if statement
					}
					
					//performs calculation by calling calculation method
					returningValue = calculation(num1, num2, currentOperator);
					
					//creates Node for returningValue before push
					Node returningValueNode = new Node(Integer.toString(returningValue)); 
					
					//pop currentOperator from ll
					ll.Pop();
					
					//pops 2nd operand from llQueue
					llQueue.Pop();
					
					//pops 1st operand from llQueue
					llQueue.Pop();
					
					//pushes back to ll linked list
					ll.Push(returningValueNode);
				
				//closes else if when current place is an operator
				}
			
			//closes while loop for going through Postfix queue when ll is empty
			}
			
			//if endAfterSet is false, then the processInput method did not set a variable value, so final answer will be printed
			if(!endAfterSet) {
			
				//if llQueue is not empty
				if(llQueue.Peek() != null) {
	
					//prints llQueue.Peek to user as final answer
					System.out.println("Final Answer: \t\t" + llQueue.Peek().getName() + "\n");
				
				//close if statement
				}
				
				//else llQueue is empty
				else {
					
					//therefore display to user default 0 as final answer
					System.out.println("Final Answer: \t\t0\n");	
				
				//close else statement
				}
			
			//close if statement
			}
			
			//endAfterSet is true, so no final answer to display to user
			else {
				
				//continue to next imported String (or end program if there are no more Strings in driver)
				return;
			
			//close else statement
			}
	
	//close processInput method
	}

	//PreCondition: Imported String has been appropriately manipulated and organized into Postfix 
	//notation. String parameters of num1, num2, and currentOperator are imported for calculation
	//PostCondition: resulting integer after calculation is returned to continue the evaluation of 
	//the entire Postfix queue
	//Method Description: calculates using two numbers and the appropriate operator, returning result
	public int calculation(String num1, String num2, String currentOperator) {
		
			//if num1 is a letter and currentOperator is not an =
			if(checkLetter(num1) && currentOperator.charAt(0) != '=') {
				
				//set num1 as value stored in its associated letter variable location in the hashArray
				num1 = Integer.toString(hashArray[hashFunction(num1) - 17]);
			
			//close if statement
			}
			
			//if num2 is a letter
			if(checkLetter(num2)) {
				
				//set num2 as value stored in its associated letter variable location in the hashArray
				num2 = Integer.toString(hashArray[hashFunction(num2) - 17]);

			//close if statement
			}
			
			//currentOperator is length 2, it is an exponent
			if(currentOperator.length() == 2) {
				
				//num2 is 0, any value of num1 raised to 0 will be 1
				if(Integer.parseInt(num2) == 0) {
					
					//return 1
					return 1;
				
				//close if statement
				}
				
				//else if num2 is 1, any value of num1 raised to 1 will be itself
				else if(Integer.parseInt(num2) == 1) {
					
					//return num1
					return Integer.parseInt(num1);
				
				//close else if statement
				}
				
				//else exponent calculation while loop needs to be performed
				else {
					
					//sets expoCounter at 1 to start
					int expoCounter = 1;

					//sets exponent int to be raised to a power as num1
					int exponent = Integer.parseInt(num1);
					
					//while expoCounter is less than num2
					while(expoCounter < Integer.parseInt(num2)) {
						
						//set exponent to current value of exponent times original num1
						exponent = exponent * Integer.parseInt(num1);
						
						//increase expoCounter by 1
						expoCounter++;
					
					//close while loop
					}
				
					//return final exponent value
					return exponent;
				
				//close else statement
				}
				
			//close if statement for exponent operator	
			}
			
			//else if currentOperator is +
			else if(currentOperator.charAt(0) == '+') {
			
				//sum equals num1 plus num2
				int sum = Integer.parseInt(num1) + Integer.parseInt(num2);
			
				//return sum value
				return sum;
			
			//close else if statement
			}
			
			//else if currentOperator is -
			else if(currentOperator.charAt(0) == '-') {
			
				//diff equals num1 minus num2
				int diff = Integer.parseInt(num1) - Integer.parseInt(num2);
			
				//return diff value
				return diff;
			
			//close else if statement
			}
			
			//else if currentOperator is *
			else if(currentOperator.charAt(0) == '*') {
				
				//product equals num1 times num2
				int product = Integer.parseInt(num1) * Integer.parseInt(num2);
				
				//return product value
				return product;
			
			//close else if statement
			}
			
			//else if currentOperator is /
			else if(currentOperator.charAt(0) == '/') {
		
				//divide equals num1 divided by num2
				int divide = Integer.parseInt(num1) / Integer.parseInt(num2);
				
				//return divide value
				return divide;
			
			//close else if statement	
			}
			
			//else if currentOperator is %
			else if(currentOperator.charAt(0) == '%') {
			
				//modulo equals num1 mod num2
				int modulo = Integer.parseInt(num1) % Integer.parseInt(num2);
				
				//returns modulo value
				return modulo;
			
			//close else if statement
			}
			
			//else if currentOperator is =
			else if(currentOperator.charAt(0) == '=') {	
				
				//sets appropriate location in hashArray to the value of num2
				hashArray[hashFunction(num1) - 17] = Integer.parseInt(num2);
			
				//sets corresponding location in hashBoolean to true
				hashBoolean[hashFunction(num1) - 17] = true;
			
				//displays to user message of variable being set to appropriate value
				System.out.println("\t\t\t" + num1 + " is set to " + num2 + "\n");
				
				//sets endAfterSet to true so final answer will not be printed
				endAfterSet = true;
				
				//returns num2 value (will not be printed due to endAfterSet being true)
				return Integer.parseInt(num2);
			
			//close else if statement
			}
			
			//else currentOperator will return 0, leaving final answer unchanged
			else {
				
				//return 0
				return 0;
			
			//close else statement
			}
	
	//close calculation method
	}
	
	//PreCondition: a particular element from the ArrayList created to hold the manipulated String is sent to be tested
	//PostCondition: a true or false value is returned depending upon whether the element is a number or not
	//Method Description: checkNum method with String parameter called from processInput
	public boolean checkNum(String stringImport) {
		
		//create numbers String containing all numerical digits for comparison
		String numbers = "0123456789";
		
		//for loop to go through numbers string, 0 through 9
		for(int i = 0; i < numbers.length(); i++) {
			
			//if the number at i equals the imported String parameter
			if((numbers.charAt(i) + "").equals(stringImport)) {
		
					//method returns true, meaning element is a number
					return true;
			
			//close if statement
			}
		
		//close for loop	
		}
		
		//return false if imported String is not a number
		return false;
		
	//end checkNum method
	}
	
	//PreCondition: a particular element from the ArrayList created to hold the manipulated String is sent to be tested
	//PostCondition: a true or false value is returned depending upon whether the element is a * or not
	//Method Description: checkStar method with String parameter called from processInput
	public boolean checkStar(String stringImport) {
		
		//if * equals the imported String parameter
		if("*".equals(stringImport)) {
			
			//method returns true, meaning element is a *
			return true;
		
		//close if statement
		}
		
		//return false if imported String is not a *
		return false;
	
	//close checkStar method
	}
	
	//PreCondition: a particular element from the ArrayList created to hold the manipulated String is sent to be tested
	//PostCondition: a true or false value is returned depending upon whether the element is an operator or not
	//Method Description: checkOperator method with String parameter called from processInput
	public boolean checkOperator(String stringImport) {
	
		//create operators String containing all operators for comparison
		String operators = "+-*/%=";
		
		//if stringImport length is 2 or greater
		if(stringImport.length() >= 2) {
		
			//if second character is a *
			if(stringImport.charAt(1) == '*') {
			
				//return true because stringImport must be an exponent
				return true;
			
			//close if statement
			}
			
			//else stringImport is not an operator
			else {
				
				//return false
				return false;
			
			//close else statement
			}
		
		//close if statement for length 2 or greater
		}	
		
		//for loop to go through all operators in operators String
		for(int i = 0; i < operators.length(); i++) {
			
			//if the operator at i equals the imported String parameter
			if((operators.charAt(i) + "").equals(stringImport)) {
		
				//method returns true, meaning element is an operator
				return true;
			
			//close if statement
			}
		
		//close for loop
		}
		
		//return false if imported String is not an operator
		return false;
	
	//close checkOperator method
	}
	
	//PreCondition: a particular element from the ArrayList created to hold the manipulated String is sent to be tested
	//PostCondition: a true or false value is returned depending upon whether the element is a - or not
	//Method Description: checkNegative method with String parameter called from processInput
	public boolean checkNegative(String stringImport) {
		
		//if - equals the imported String parameter
		if(("-").equals(stringImport)) {
		
			//method returns true, meaning element is a -
			return true;
			
		//close if statement
		}
		
		//return false if imported String is not a -
		return false;
	
	//close checkNegative method
	}
	
	//PreCondition: a particular element from the ArrayList created to hold the manipulated String is sent to be tested
	//PostCondition: a true or false value is returned depending upon whether the element is a letter or not
	//Method Description: checkLetter method with String parameter called from processInput
	public boolean checkLetter(String stringImport) {
		
		//if hashFunction returns a value corresponding to a letter
		if((17 <= hashFunction(stringImport) && hashFunction(stringImport) <= 42) || (49 <= hashFunction(stringImport) && hashFunction(stringImport) <= 74)) {
		
			//method returns true, meaning element is a letter
			return true;
			
		//close if statement
		}
		
		//return false if imported String is not a letter
		return false;
	
	//close checkLetter method
	}
	
	//PreCondition: a particular element from the ArrayList created to hold the manipulated String is sent to be tested
	//PostCondition: a true or false value is returned depending upon whether the element is a number or not
	//Method Description: checkNumber method with String parameter called from processInput
	public boolean checkNumber(String stringImport) {
		
		//if hashFunction returns a value corresponding to a number
		if((0 <= hashFunction(stringImport.charAt(0) + "") && hashFunction(stringImport.charAt(0) + "") <= 9)) {
			
			//method returns true, meaning element is a number
			return true;
			
		//close if statement
		}
		
		//return false if imported String is not a number
		return false;
	
	//close checkNumber method
	}
	
	//PreCondition: String stringImport is imported as parameter to return key value for analysis
	//PostCondition: key value returned can be compared to determine type of character based upon ASCII value
	//hashFunction method is used in the process of verifying numbers and letters, in addition to placing 
	//numbers in the appropriate location in the hashArray (and correspondingly adjusting the hashBoolean array)
	public int hashFunction(String stringImport) {
		
		//users charAt of imported stringImport to populate firstLetter variable
		char firstLetter = stringImport.charAt(0);
		
		//converts firstLetter to corresponding ASCII integer
		int ascii = (int)firstLetter;
		
		//hash function to calculate key by subtracting 48 from ASCII value, thereby making key the appropriate index in hash array
		int key = ascii - 48;
		
		//return key (index)
		return key;
	
	//close hashFunction method
	}

//close StackCalculator class
}