package com.rogerkepler.demo.invoker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rogerkepler.demo.common.AbstractInvoker;
import com.rogerkepler.demo.common.Evaluator;
import com.rogerkepler.demo.common.ExecutionContext;
import com.rogerkepler.demo.common.ExecutionStatus;
import com.rogerkepler.demo.evaluator.CreditUsage;

public class CalculateCreditUsage extends AbstractInvoker implements Evaluator {

	private List<Evaluator> evaluatorList = new ArrayList<Evaluator>();
	
	@Override
	protected Iterator<Evaluator> getEvaluatorsToCall() {

		evaluatorList.add(new CreditUsage());
		
		return evaluatorList.iterator();
	}


	public void validate() {
		setExecutionStatus(ExecutionStatus.Invalid); 
		
		if (getExecutionContext().containsKey("calculationContext")) {
			setExecutionStatus(ExecutionStatus.Validated);
		}
	}

}
