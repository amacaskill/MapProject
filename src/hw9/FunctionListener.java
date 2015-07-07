package hw9;

import java.util.EventListener;

/**
 * 
 * Interface for the FunctionListener used by the FunctionsPanel to respond
 * to events. 
 *
 */
public interface FunctionListener extends EventListener {
	public void functionEventOccurred(FunctionEvent event);
}
