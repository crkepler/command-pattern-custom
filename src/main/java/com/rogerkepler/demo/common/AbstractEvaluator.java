package com.rogerkepler.demo.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractEvaluator implements Evaluator {

	private List<Command> executedCommands = new ArrayList<Command>();
	private ExecutionStatus status = ExecutionStatus.NotStarted;
	protected ExecutionContext context = new ExecutionContext();
	
	/**
	 * Returns an iterator of Commands (Command interface). 
	 * The execution sequence of these Command is up to the implementation sub-classes
	 */	
	protected abstract Iterator<Command> getCommandsToExecute();

	
	/**
	 * Main operation to run (execute) each Evaluator.
	 */
	public void execute() {
		
		try {
			System.out.println("....Beginning Evaluator loop...");
			status = ExecutionStatus.InProgress;
			
			for (Iterator<Command> iter = getCommandsToExecute(); iter.hasNext();) {
				
				Command currentCommand = iter.next();
				System.out.println("......Executing command: " + currentCommand.getName());
				
				/**
				 * Update current command with full execution context.
				 * This ensures the context is chained though all the commands.
				 */
				currentCommand.setExecutionContext(context);
				
				/**
				 * Asks each Command to perform a validation, if any:
				 */
				currentCommand.validate();
				
				/**
				 * based on the validation result, the Command will be executed or not
				 */
				if (currentCommand.getExecutionStatus() == ExecutionStatus.Invalid) {
					status = ExecutionStatus.Failed;
					System.err.println("......Validation failed. Name: " + currentCommand.getName());
					System.err.println("......Stopping all execution"); //it's pointless to continue until the end if something failed.
					break;
				}
				
				/**
				 * If it gets here, the Command is valid. Should it be skipped?
				 */
				if (currentCommand.getExecutionStatus() == ExecutionStatus.Skipped) {
					
					System.out.println("......Command skipped. Name: " + currentCommand.getName());
					//write log here to indicate Command was skipped.
					//execution continues normally when an evaluation is skipped.
					
				} else {
					
					currentCommand.execute(); //polymorphism
					System.out.println("......Command execution completed. Name: " + currentCommand.getName());
					executedCommands.add(currentCommand);

				}				
			}
			/**
			 * Only change Evaluator status to successful if it hasn't failed			 * 
			 */
			if (status != ExecutionStatus.Failed) {
				status = ExecutionStatus.Successful;
			}
			
			
		} catch (Exception e) {
			
			status = ExecutionStatus.Failed;
			System.err.println("....Execution failed. Message: " + e.getMessage());
			e.printStackTrace(); //for demo purposes only, in production use logging.
			//write here code to log exception
			
		} finally {
			System.out.println("....Number of commands executed in Evaluator loop: " + executedCommands.size());
			System.out.println("....Evaluator execution completed. Status: " + status);
		
		}
		
	}
	
	/**
	 * Getter / setters to implement the Command interface
	 * ExecutedCommands (getter / setter)
	 * @return
	 */
	protected final List<Command> getExecutedCommands() {
		return executedCommands;
	};
	
	/**
	 * ExecutionContext (getter / setter)
	 */
	public ExecutionContext getExecutionContext() {
		if (context == null) {
			context = new ExecutionContext();
		}
		return context;
	}
	
	public void setExecutionContext(ExecutionContext context) {
		this.context = context;
	}
	
	/**
	 * ExecutionStatus (getter / setter)
	 */
	
	public final ExecutionStatus getExecutionStatus() {
		return status;
	}
	
	public final void setExecutionStatus(ExecutionStatus status) {
	   this.status = status;
	}		
	
	/**
	 * Get class name
	 */
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
}
