package hw6.test;

import java.util.NoSuchElementException;

import hw5.Graph;
import hw6.MarvelPaths;

import org.junit.Test;

public class FindPathsTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void findPathNullArgs() {
		MarvelPaths.getPath(null, "c1", "c2"); 
	}
	
	@Test(expected=NoSuchElementException.class)
	public void findPathNodesNotInGraph() {
		MarvelPaths.getPath(new Graph<String, String>(), "c1", "c1"); 
	}

}
