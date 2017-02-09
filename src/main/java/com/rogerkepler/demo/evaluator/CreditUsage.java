package com.rogerkepler.demo.evaluator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rogerkepler.demo.command.NormalizedCreditLimit;
import com.rogerkepler.demo.common.AbstractEvaluator;
import com.rogerkepler.demo.common.Command;
import com.rogerkepler.demo.common.ExecutionStatus;

public class CreditUsage extends AbstractEvaluator {

	@Override
	protected Iterator<Command> getCommandsToExecute() {
		List<Command> commandsToExecute = new ArrayList<Command>();
		
		commandsToExecute.add(new NormalizedCreditLimit());	// GCM-xxx		
		
		return commandsToExecute.iterator();
	}

	public void validate() {
		setExecutionStatus(ExecutionStatus.Validated);
	}

	

}
