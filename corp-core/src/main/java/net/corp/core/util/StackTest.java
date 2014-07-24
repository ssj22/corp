package net.corp.core.util;

import java.util.ArrayList;
import java.util.List;

public class StackTest {
	public static void main(String... s) {
		MyStack stack = new MyStack();

		stack.push(10);
		stack.push(20);
		stack.push(30);
		stack.push(40);
		stack.push(50);
		stack.push(60);
		
		try {
			//Thrown expection by getStackElement should be cought here.
			System.out.println(getStackElement(stack, 0)==10);
			System.out.println(getStackElement(stack, 2)==30);
			System.out.println(getStackElement(stack, 5)==60);
			System.out.println(getStackElement(stack, 0)==10);
			
			getStackElement(stack, 6);
			assert false;
		} catch(Exception e) {
			System.out.println(e);
			assert true;
		}
		
	}

	public static int getStackElement(MyStack stack, int index)throws Exception{
		// 1) ONLY FILL IN YOUR CODE IN THIS METHOD
		// 2) DO NOT MODIFY ANYTHING ELSE
		// 3) USE OF new KEYWORD IS NOT ALLOWED
		// 4) DO NOT USE REFLECTION
		// 5) DO NOT USE STRING CONCATENATION
		
		
		
		
//		int counter = 0;
//		long val = 0l;
//		int returnVal = 0;
//		while (!stack.isEmpty()) {
//			int i = stack.pop();
//			val += i * Math.pow(10, 2 * counter);
//			//System.out.println(val);
//			counter++;
//		}
//		
//		int index1 = 0;
//		for (int i=counter-1;i>=0;i--) {
//			double divisor = Math.pow(10, (2 * i));
//			long div = Math.round(divisor);
//			long entry = val / div;
//			System.out.println("for i = " + i + ", index1 = " + index1 + ", and index is " + index);
//			if (index1 == index) {
//				returnVal = (int)entry;
//				//System.out.println(returnVal);
//			}
//			stack.push((int)entry);
//			val = val % div;
//			index1++;
//		}
	
		return 0;
	}
}

class MyStack {
	private List<Integer> items;

	public MyStack() {
		items = new ArrayList<Integer>();
	}

	public int pop() {
		if(isEmpty()) throw new RuntimeException("Stack is empty!");
		return items.remove(items.size()-1);
	}

	public void push(Integer i) {
		items.add(i);
	}

	public int peek() {
		if(isEmpty()) throw new RuntimeException("Stack is empty!");
		return items.get(items.size()-1);
	}

	public boolean isEmpty() {
		return items.size()==0;
	}
	
}
