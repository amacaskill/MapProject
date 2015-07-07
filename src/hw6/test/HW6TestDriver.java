package hw6.test;

import hw5.Edge;
import hw5.Graph;
import hw6.MarvelPaths;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and your BFS
 * algorithm.
 **/
public class HW6TestDriver {
  
	 public static void main(String args[]) {
	        try {
	            if (args.length > 1) {
	                printUsage();
	                return;
	            }

	            HW6TestDriver td;

	            if (args.length == 0) {
	                td = new HW6TestDriver(new InputStreamReader(System.in),
	                                       new OutputStreamWriter(System.out));
	            } else {

	                String fileName = args[0];
	                File tests = new File (fileName);

	                if (tests.exists() || tests.canRead()) {
	                    td = new HW6TestDriver(new FileReader(tests),
	                                           new OutputStreamWriter(System.out));
	                } else {
	                    System.err.println("Cannot read from " + tests.toString());
	                    printUsage();
	                    return;
	                }
	            }

	            td.runTests();

	        } catch (IOException e) {
	            System.err.println(e.toString());
	            e.printStackTrace(System.err);
	        }
	    }
	 
	 private static void printUsage() {
	        System.err.println("Usage:");
	        System.err.println("to read from a file: java hw5.test.HW5TestDriver <name of input script>");
	        System.err.println("to read from standard in: java hw5.test.HW5TestDriver");
	  }
	 
	 /** String -> Graph: maps the names of graphs to the actual graph **/
	 private final Map<String, Graph<String, String>> graphs = new HashMap<String, Graph<String, String>>();
	 private final PrintWriter output;
	 private final BufferedReader input;

	 /**
	     * @requires r != null && w != null
	     *
	     * @effects Creates a new HW5TestDriver which reads command from
	     * <tt>r</tt> and writes results to <tt>w</tt>.
	     **/
	 public HW6TestDriver(Reader r, Writer w) {
	     input = new BufferedReader(r);
	     output = new PrintWriter(w);
	 }

	 /**
	     * @effects Executes the commands read from the input and writes results to the output
	     * @throws IOException if the input or output sources encounter an IOException
	     **/
	    public void runTests()
	        throws IOException
	    {
	        String inputLine;
	        while ((inputLine = input.readLine()) != null) {
	            if ((inputLine.trim().length() == 0) ||
	                (inputLine.charAt(0) == '#')) {
	                // echo blank and comment lines
	                output.println(inputLine);
	            }
	            else
	            {
	                // separate the input line on white space
	                StringTokenizer st = new StringTokenizer(inputLine);
	                if (st.hasMoreTokens()) {
	                    String command = st.nextToken();

	                    List<String> arguments = new ArrayList<String>();
	                    while (st.hasMoreTokens()) {
	                        arguments.add(st.nextToken());
	                    }

	                    executeCommand(command, arguments);
	                }
	            }
	            output.flush();
	        }
	    }
	    
	    private void executeCommand(String command, List<String> arguments) {
	        try {
	            if (command.equals("LoadGraph")) {
	                loadGraph(arguments);
	            } else if (command.equals("FindPath")) {
	                findPath(arguments);
	            } else if (command.equals("CreateGraph")) {
	                createGraph(arguments);
	            } else if (command.equals("AddNode")) {
	                addNode(arguments);
	            } else if (command.equals("AddEdge")) {
	                addEdge(arguments);
	            } else if (command.equals("ListNodes")) {
	                listNodes(arguments);
	            } else if (command.equals("ListChildren")) {
	                listChildren(arguments);
	            } else {
	                output.println("Unrecognized command: " + command);
	            }
	        } catch (Exception e) {
	            output.println("Exception: " + e.toString());
	        }
	    }
	    
	    private void loadGraph(List<String> arguments) {
	    	if (arguments.size() != 2) {
	    		throw new CommandException("Bad arguments to loadGraph: " + arguments);
	    	}
	    	String graphName = arguments.get(0);
	    	String fileName = arguments.get(1); 
	    	loadGraph(graphName, fileName);
	    }
	    
	    private void loadGraph(String graphName, String fileName) { 
			Graph<String, String> g = MarvelPaths.buildGraph(fileName);
	    	graphs.put(graphName, g);
	        output.println("loaded graph " + graphName);
	    }
	    
	    private void findPath (List<String> arguments) {
	    	if (arguments.size() != 3) {
	    		throw new CommandException("Bad arguments to findPath: " + arguments);
	    	}
	    	String graphName = arguments.get(0);
	    	String node1 = arguments.get(1);
	    	String node2 = arguments.get(2); 
	    	
	    	findPath(graphName, node1.replaceAll("_", " "), node2.replaceAll("_", " ")); 
	    }
	    
	    private void findPath(String graphName, String node1, String node2) {
	    	Graph<String, String> g = graphs.get(graphName); 
	    	if (!g.containsNode(node1) | !g.containsNode(node2)) {
	    		if (!g.containsNode(node1)) {
	    			output.println("unknown character " + node1);
	    		}
	    		if (!g.containsNode(node2)) {
		    		output.println("unknown character " + node2);
	    		}
	    	} else {
	    		output.println("path from " + node1 + " to " + node2 + ":");
	    		ArrayList<Edge<String, String>> path = MarvelPaths.getPath(g, node1, node2);
	    		
	    		if (path == null) {
	    			output.println("no path found");
	    		} else {
	    			for (Edge<String, String> e: path) {
	    				output.println(e.getParent() + " to " + e.getChild() + " via "
	    						           + e.getLabel());
	    			}
	    		}
	    	}
	    }
	    
	    private void createGraph(List<String> arguments) {
	        if (arguments.size() != 1) {
	            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
	        }

	        String graphName = arguments.get(0);
	        createGraph(graphName);
	    }

	    private void createGraph(String graphName) {
	    	Graph<String, String> g = new Graph<String, String>(); 
	        graphs.put(graphName, g);
	        output.println("created graph " + graphName);
	    }
	    
	    private void addNode(List<String> arguments) {
	        if (arguments.size() != 2) {
	            throw new CommandException("Bad arguments to addNode: " + arguments);
	        }

	        String graphName = arguments.get(0);
	        String nodeName = arguments.get(1);

	        addNode(graphName, nodeName);
	    }

	    private void addNode(String graphName, String nodeName) {
	         Graph<String, String> g = graphs.get(graphName);
	         g.insertNode(nodeName); 
	         output.println("added node " + nodeName + " to " + graphName);
	    }

	    private void addEdge(List<String> arguments) {
	        if (arguments.size() != 4) {
	            throw new CommandException("Bad arguments to addEdge: " + arguments);
	        }

	        String graphName = arguments.get(0);
	        String parentName = arguments.get(1);
	        String childName = arguments.get(2);
	        String edgeLabel = arguments.get(3);

	        addEdge(graphName, parentName, childName, edgeLabel);
	    }

	    private void addEdge(String graphName, String parentName, String childName,
	            String edgeLabel) {
	        Graph<String, String> g = graphs.get(graphName);
	        g.insertEdge(parentName, childName, edgeLabel); 
	        output.println("added edge " + edgeLabel + " from " + parentName +
	        		       " to " + childName + " in " + graphName);
	    }

	    private void listNodes(List<String> arguments) {
	        if (arguments.size() != 1) {
	            throw new CommandException("Bad arguments to listNodes: " + arguments);
	        }

	        String graphName = arguments.get(0);
	        listNodes(graphName);
	    }

	    private void listNodes(String graphName) {
	         Graph<String, String> g = graphs.get(graphName);
	         HashSet<String> nodes = g.getNodeSet(); 
	         ArrayList<String> sortedNodes = new ArrayList<String>();
	         sortedNodes.addAll(nodes); 
	         Collections.sort(sortedNodes); 
	         output.print(graphName + " contains:");
	         for (String s: sortedNodes) {
	        	 output.print(" " + s); 
	         }
	         output.println(); 
	    }

	    private void listChildren(List<String> arguments) {
	        if (arguments.size() != 2) {
	            throw new CommandException("Bad arguments to listChildren: " + arguments);
	        }

	        String graphName = arguments.get(0);
	        String parentName = arguments.get(1);
	        listChildren(graphName, parentName);
	    }

	    private void listChildren(String graphName, String parentName) {
	    	 Graph<String, String> g = graphs.get(graphName);
	         Set<Edge<String, String>> children = g.getOutEdgesOf(parentName);
	         ArrayList<String> sortedChildren = new ArrayList<String>();
	         for (Edge<String, String> e: children) {
	        	 String temp = e.getChild() + "(" + e.getLabel() + ")";
	        	 sortedChildren.add(temp); 
	         }
	         Collections.sort(sortedChildren); 
	         output.print("the children of " + parentName + " in " + graphName +
	        		      " are:");
	         for (int i = 0; i < sortedChildren.size(); i++) {
	        	 output.print(" " + sortedChildren.get(i)); 
	         }
	         output.println(); 
	    }

	    /**
	     * This exception results when the input file cannot be parsed properly
	     **/
	    public static class CommandException extends RuntimeException {

	        public CommandException() {
	            super();
	        }
	        public CommandException(String s) {
	            super(s);
	        }

	        public static final long serialVersionUID = 3495;
	    }


}
