package com.rogerkepler.demo.command;

import com.rogerkepler.demo.common.AbstractCommand;
import com.rogerkepler.demo.common.ExecutionStatus;
import com.rogerkepler.demo.context.CalculationContext;

public class NormalizedCreditLimit extends AbstractCommand {

	CalculationContext context;
	
	@Override
	public void validate() {
          System.out.println("........Validating Normalized Credit Limit");
          setExecutionStatus(ExecutionStatus.Validated); 
	}

	@Override
	public void execute() {
        System.out.println("........Executing Normalized Credit Limit");
        context = (CalculationContext)getExecutionContext().getContextItem("calculationContext");
        
        context.setSomeCalculationResult(1200);
	}

}
