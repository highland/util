package com.gmail.highland.mark;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class to enable java's 'foreach' loop to accept a range.
 * <p>
 * This allows replacing the C style:
 * <pre><code>
 *      for (int i = 0; i < 10; i++) {...}
 * </code></pre>
 * with:
 * <pre><code>
 *      for (int i : range(10)) {...}
 * </code></pre>
 * Three versions of range() are provided allowing combinations of start, end,
 * and step.
 * @author Mark Thomas
 * @version 1.1
 */
public final class Range implements Iterator<Integer>, Iterable<Integer> {

	private int next;

	private final int from;

	/**
	 * The iteration will stop the step before before reaching or passing
	 * <code>to</code>.
	 */
	private final int to;

	private final int step;

	/**
	 * Flag is set when iterator() is first called.
	 * 
	 * @see iterator()
	 */
	private boolean used = false;

	/**
	 * A Method to be used with the java 'foreach' loop.
	 * <p>
	 * Usage:
	 * <pre><code>
	 * import static highland.mark.Range.range;
	 * ...
	 *      for (int i : range(from, to, step)) {...}
	 * </code></pre>
	 * @param from
	 *            - first value returned.
	 * @param to
	 *            - one more than last value returned.
	 * @param step
	 *            - increment for each iteration (may be negative so long as
	 *            {@code (to < step)}.
	 * @return the Iterable which supplies an Iterator which, on each iteration,
	 *         returns integers from {@code from} to {@code (to - 1)}
	 *         incrementing by {@code step} each time.
	 * @throws IllegalArgumentException
	 *             if {@code step == 0} or {@code step} is the wrong sign.
	 */
	public static Iterable<Integer> range(int from, int to, int step)
			throws IllegalArgumentException {
		return new Range(from, to, step);
	}

	/**
	 * A Method to be used with the java 'foreach' loop.
	 * <p>
	 * Usage:
	 * <pre><code>
	 * import static highland.mark.Range.range;
	 * ...
	 *      for (int i : range(from, to)) {...}
	 * </code></pre>
	 * @param from
	 *            - first value returned.
	 * @param to
	 *            - one more than last value returned.
	 * @return the Iterable which supplies an Iterator which, on each iteration,
	 *         returns integers from {@code from} to {@code (to - 1)}
	 *         incrementing by {@code 1} each time.
	 * @throws IllegalArgumentException
	 *             if {@code to < from}.
	 */
	public static Iterable<Integer> range(int from, int to)
			throws IllegalArgumentException {
		return Range.range(from, to, 1);
	}

	/**
	 * A Method to be used with the java 'foreach' loop.
	 * <p>
	 * Usage:
	 * <pre><code>
	 * import static highland.mark.Range.range;
	 * ...
	 *      for (int i : range(to)) {...}
	 * </code></pre>
	 * @param to
	 *            - one more than last value returned.
	 * @return the Iterable which supplies an Iterator which, on each iteration,
	 *         returns integers from {@code 0} to {@code (to - 1)}
	 *         incrementing by {@code 1} each time.
	 */
	public static Iterable<Integer> range(int to) {
		return Range.range(0, to, 1);
	}

	/*
	 * (non-Javadoc) private constructor only used by the static range()
	 * methods.
	 * 
	 * @throws IllegalArgumentException if step == 0 or step is the wrong sign.
	 */
	private Range(int from, int to, int step) throws IllegalArgumentException {
		if (step == 0) {
			throw new IllegalArgumentException(
					"The step argument cannot be zero");
		}
		if ((to - from) / step < 0) {
			throw new IllegalArgumentException(
					"The step argument has the wrong sign");
		}
		this.next = from;
		this.from = from;
		this.to = to;
		this.step = step;
	}

	/*
	 * (non-Javadoc) private constructor only used by the iterator() method
	 * 
	 * @param range : Range, object to be reset to starting values.
	 */
	private Range(Range range) {
		this.next = range.from;
		this.from = range.from;
		this.to = range.to;
		this.step = range.step;
	}

	@Override
	public Iterator<Integer> iterator() {
		if (!used) {	// subsequent calls to iterator must return a fresh Range.
			used = true;
			return this;
		}
		return new Range(this); 
	}

	@Override
	public boolean hasNext() {
		if (step < 0) {
			return to < next;
		} else {
			return next < to;
		}
	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException("End of range exceeded.");
		int value = this.next;
		this.next += this.step;
		return value;
	}

	@Override
	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"The iterator returned from range() does not support remove()");
	}

}
