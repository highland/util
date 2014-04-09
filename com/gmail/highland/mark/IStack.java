package com.gmail.highland.mark;

/**
 * Basic Stack operations
 * @author Mark Thomas
 * @version 1.0
 */
public interface IStack<E> {
	
	E push(E item);

	E pop();

	E peek();
	
	boolean isEmpty();
}
