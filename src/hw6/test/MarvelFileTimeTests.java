package hw6.test;

import hw5.Graph;
import hw6.MarvelPaths;

import org.junit.Test;

public class MarvelFileTimeTests {

	// these are not correctness tests, each test 
	// is meant to test the time it takes for MarvelPaths
	// to build the graph from the marvel.tsv file and to find
	// an arbitrarily chosen path 
	
	@Test
	public void test1() {
		Graph<String, String> g = MarvelPaths.buildGraph("marvel.tsv");
		MarvelPaths.getPath(g,"BLACK WIDOW/NATASHA ", "PSI-BORG/"); 
	}
	
	@Test
	public void test2() {
		Graph<String, String> g = MarvelPaths.buildGraph("marvel.tsv");
		MarvelPaths.getPath(g, "CAPTAIN AMERICA","MR. FANTASTIC/REED R" ); 
	}
	
	@Test
	public void test3() {
		Graph<String, String> g = MarvelPaths.buildGraph("marvel.tsv");
		MarvelPaths.getPath(g,"MACTAGGERT, MOIRA KI", "JAMESON, J. JONAH"); 
	}
	
	@Test
	public void test4() {
		Graph<String, String> g = MarvelPaths.buildGraph("marvel.tsv");
		MarvelPaths.getPath(g, "WATSON-PARKER, MARY ", "DAREDEVIL/MATT MURDO"); 
	}
	
	@Test
	public void test5() {
		Graph<String, String> g = MarvelPaths.buildGraph("marvel.tsv");
		MarvelPaths.getPath(g,"KILLRAVEN/JONATHAN R", "BANNER, BETTY ROSS T"); 
	}

}
