package com.gmail.highland.mark;

import java.util.EmptyStackException;

/**
 * A Class to be used in place of the java.util.Stack class, which is considered
 * inefficient. This is because of an early decision to make it a subclass of
 * Vector - which is synchronised and uses an Array as backing store.
 * <p>
 * It turns out that for small Stacks, the push() methods are about the same,
 * and the pop() methods save only about 30%. For larger Stacks (>100 entries)
 * the difference is more pronounced, but the old Stack seldom takes longer than
 * 10x the new one.
 * <p>
 * Hot-spot optimisations can reduce the timings by a factor of 100 over long runs.
 * 
 * @author Mark Thomas
 * @version 1.0
 */
public class Stack<E> implements IStack<E> {
	
	@SuppressWarnings("unchecked")
	private Entry<E> top = Entry.nullEntry;

	@Override
	public E push(E item) {
		top = new Entry<E>(item, top);
		return item;
	}

	@Override
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();
		E value = top.value;
		top = top.next;
		return value;
	}

	@Override
	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return top.value;
	}

	@Override
	public boolean isEmpty() {
		return top == Entry.nullEntry;
	}

	private static class Entry<E> {
		@SuppressWarnings("rawtypes")
		private static final Entry nullEntry = new Entry<>(null, null);

		private final E value;
		private final Entry<E> next;

		private Entry(E value, Entry<E> nextEntry) {
			this.value = value;
			this.next = nextEntry;
		}
	}

}
