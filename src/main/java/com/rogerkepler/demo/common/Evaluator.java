package com.rogerkepler.demo.common;

public interface Evaluator {

	void execute();
	void validate();
	
	ExecutionStatus getExecutionStatus();
	void setExecutionStatus(ExecutionStatus status);

	ExecutionContext getExecutionContext();
	void setExecutionContext(ExecutionContext context);
	
	
	public String getName();
		
}
