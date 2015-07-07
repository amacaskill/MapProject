package hw7.test;

import org.junit.Test;

import java.util.NoSuchElementException;

import hw5.Graph;
import hw7.LeastCostPath;
import hw7.MarvelPaths2;

public class FindPathTests {

	@Test(expected = IllegalArgumentException.class) 
	public void testNullInput() {
		Graph<String, Double> g = null; 
		LeastCostPath.getPath(g, "a", "b"); 
	}
	
	@Test(expected = NoSuchElementException.class) 
	public void testInputNotInGraph() {
		Graph<String, Double> g = new Graph<String, Double>(); 
		LeastCostPath.getPath(g, "a", "b"); 
	}
	
	@Test
	public void testTiming1() {
		Graph<String, Double> g = MarvelPaths2.buildGraph("marvel.tsv"); 
		LeastCostPath.getPath(g,"BLACK WIDOW/NATASHA ", "PSI-BORG/");
	}
	
	@Test
	public void testTiming2() {
		Graph<String, Double> g = MarvelPaths2.buildGraph("marvel.tsv"); 
		LeastCostPath.getPath(g, "CAPTAIN AMERICA","MR. FANTASTIC/REED R" );
	}
	
	@Test
	public void testTiming3() {
		Graph<String, Double> g = MarvelPaths2.buildGraph("marvel.tsv"); 
		LeastCostPath.getPath(g, "MACTAGGERT, MOIRA KI", "JAMESON, J. JONAH");
	}
	
	@Test
	public void testTiming4() {
		Graph<String, Double> g = MarvelPaths2.buildGraph("marvel.tsv"); 
		LeastCostPath.getPath(g,"WATSON-PARKER, MARY ", "DAREDEVIL/MATT MURDO" );
	}
	
	@Test
	public void testTiming5() {
		Graph<String, Double> g = MarvelPaths2.buildGraph("marvel.tsv"); 
		LeastCostPath.getPath(g, "KILLRAVEN/JONATHAN R", "BANNER, BETTY ROSS T");
	}
	

}
