package com.gmail.highland.mark;

import static com.gmail.highland.mark.Range.range;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.testng.annotations.Test;

public class RangeTest {

	private void verifyOutput(final Iterable<Integer> range, final Integer... expectedValues) {
	    final List<Integer> result = new ArrayList<>();
	    for (Integer value: range) {
	        result.add(value);
	    }
	    assertEquals(Arrays.asList(expectedValues), result);
	}

	@Test
	public void fullySpecified() {
		verifyOutput(range(2, 19, 3), 2, 5, 8, 11, 14, 17);
	}

	@Test
	public void backWards() {
		verifyOutput(range(19, 2, -3), 19, 16, 13, 10, 7, 4);
	}

	@Test
	public void defaultStep() {
		verifyOutput(range(2, 9), 2, 3, 4, 5, 6, 7, 8);
	}

	@Test
	public void defaultStepAndStart() {
		verifyOutput(range(7), 0, 1, 2, 3, 4, 5, 6);
	}
	
	@Test
	public void iteratorReuse() {
		// N.B. Not intended usage pattern
		Iterable<Integer> rangeForReuse = range(7);
		String result = "";
		for (int i : rangeForReuse) {
			result += i + " ";
		}
		for (int i : rangeForReuse) {
			result += i + " ";
		}
		assertEquals(result, "0 1 2 3 4 5 6 0 1 2 3 4 5 6 ");
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void whenWrongWay1() {
		range(2, 19, -3);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void whenWrongWay2() {
		range(2, -19, 3);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void whenZeroStep1() {
		 range(2, 19, 0);
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void whenZeroStep2() {
		range(2, -19, 0);
	}
	
	@Test(expectedExceptions=NoSuchElementException.class)
	public void whenNextOutOfRange() {
		// N.B. Not intended usage pattern
		Iterator<Integer> iterator = range(2).iterator();
		iterator.next();	// 0
		iterator.next();	// 1
		iterator.next();	// 2 - out of range
	}
}
