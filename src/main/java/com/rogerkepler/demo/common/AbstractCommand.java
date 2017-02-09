package com.rogerkepler.demo.common;

public abstract class AbstractCommand implements Command {

	private ExecutionContext context = new ExecutionContext();
	private ExecutionStatus status = ExecutionStatus.NotStarted;
	
	public abstract void validate();
	public abstract void execute();
	
	public ExecutionContext getExecutionContext() {
		return context;
	}
	
	public void setExecutionContext(ExecutionContext context) {
		this.context = context;
	}
	
	public ExecutionStatus getExecutionStatus() {
		return status;
	}
	
	public void setExecutionStatus(ExecutionStatus status) {
		this.status = status;
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
}
