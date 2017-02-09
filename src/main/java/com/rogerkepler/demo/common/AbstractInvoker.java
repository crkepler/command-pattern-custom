package com.rogerkepler.demo.common;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractInvoker implements Evaluator {

	private List<Evaluator> calledEvaluators = new ArrayList<Evaluator>();
	private ExecutionStatus status = ExecutionStatus.NotStarted;
	private ExecutionContext context = new ExecutionContext();
		
	/**
	 * Returns an iterator of evaluators (Evaluator interface). 
	 * The execution sequence of these evaluators is up to the implementation sub-classes
	 */	
	protected abstract Iterator<Evaluator> getEvaluatorsToCall();

	
	/**
	 * Main entry point to start execution.
	 * This loop calls execute() again in each object in the "getEvaluatorsToCall()" iterator. The order of these
	 * objects in the list is important.
	 * 
	 * An Invoker calls Evaluators, but since Invokes also implement the Evaluator interface, Invokers can then
	 * call other invokers, making the calls configuratoin very flexible.
	 */
	public void execute() {
		
		try {
			System.out.println("..Beginning Invoker loop.");
			status = ExecutionStatus.InProgress;
			
			/**
			 * Iterates over all the evaluators to call. The evaluators to call are defined in the concrete class.
			 */
			for (Iterator<Evaluator> iter = getEvaluatorsToCall(); iter.hasNext();) {
				
				Evaluator currentEvaluator = iter.next();
				/**
				 * Doing instanceof check because one invoker can call another invoker OR an evaluator.
				 */
				String classType = currentEvaluator instanceof AbstractInvoker ? "invoker" : "evaluator";  
				System.out.println("..Executing " + classType + ". Name: " + currentEvaluator.getName());
				
				/**
				 * Update current evaluator with full execution context.
				 * This ensures the context gets chained through all execution steps.
				 */
				currentEvaluator.setExecutionContext(context);
				
				/**
				 * Asks each Evaluator to perform a validation, if any:
				 */
				currentEvaluator.validate();
				
				/**
				 * Based on the validation result, the evaluator will be executed or not
				 */
				if (currentEvaluator.getExecutionStatus() == ExecutionStatus.Invalid) {
					status = ExecutionStatus.Failed;
					System.err.println("..Validation failed. Name: " + currentEvaluator.getName());
					System.err.println("..Stopping all execution"); //it's pointless to continue until the end if something failed.
					break;
				}
				
				/**
				 * If it gets here, the evaluator is valid. Should it be skipped?
				 */
				if (currentEvaluator.getExecutionStatus() == ExecutionStatus.Skipped) {
					
					System.out.println("..Execution skipped. Name: " + currentEvaluator.getName());
					//write log here to indicate evaluator was skipped.
					//execution continues normally when an evaluation is skipped.
					
				} else {
					
					currentEvaluator.execute(); //polymorphism magic
					System.out.println("..Execution completed. Name: " + currentEvaluator.getName());
					calledEvaluators.add(currentEvaluator);

				}				
			}
			/**
			 * Only change Invoker status to successful if it hasn't failed			 * 
			 */
			if (status != ExecutionStatus.Failed) {
				status = ExecutionStatus.Successful;
			}
			
			
		} catch (Exception e) {
			
			status = ExecutionStatus.Failed;
			System.err.println("..Execution failed. Message: " + e.getMessage());
			/**
			 * Remove the line below when actually implementing this.
			 */
			e.printStackTrace(); //for demo purposes only, in production use logging.
			//write here code to log exception
			
		} finally {
			System.out.println("..Number of evaluations in Invoker loop: " + calledEvaluators.size());
			System.out.println("..Invoker execution completed. Status: " + status);
		
		}
		
	}
	

	/**
	 * Getters / setters to implement Evaluator interface
	 */
	protected final List<Evaluator> getCalledEvaluators() {
		return calledEvaluators;
	};
	
	public ExecutionContext getExecutionContext() {
		if (context == null) {
			context = new ExecutionContext();
		}
		return context;
	}
	
	public void setExecutionContext(ExecutionContext context) {
		this.context = context;
	}	
	
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
