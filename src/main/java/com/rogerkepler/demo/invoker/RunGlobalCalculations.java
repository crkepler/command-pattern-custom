package com.rogerkepler.demo.invoker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rogerkepler.demo.common.AbstractInvoker;
import com.rogerkepler.demo.common.Evaluator;
import com.rogerkepler.demo.context.CalculationContext;


public class RunGlobalCalculations extends AbstractInvoker {

	private List<Evaluator> evaluatorList = new ArrayList<Evaluator>();
	
	public RunGlobalCalculations(CalculationContext myContext) {
		getExecutionContext().addContextItem("calculationContext", myContext);	//this is actually calling another Invoker, not an Evaluator
	}
	
	@Override
	protected Iterator<Evaluator> getEvaluatorsToCall() {

		/**
		 * Here we add all evaluator classes we need to call.
		 * Then the evaluators will add the commands to execute
		 */
		evaluatorList.add(new CalculateCreditUsage());
		
		return evaluatorList.iterator();
	}

	public void validate() {	
	}

}
