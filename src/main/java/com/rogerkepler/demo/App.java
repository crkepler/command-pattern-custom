package com.rogerkepler.demo;

import com.rogerkepler.demo.context.CalculationContext;
import com.rogerkepler.demo.invoker.RunGlobalGCMUpdateCalculations;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    
        System.out.println( "Main process started." );
        
        System.out.println("STEP 1. Simulating data retrieval from the DB...");
        
        //this is like the pre-fetching phase of the process
 
        CalculationContext myContext = new CalculationContext();
        myContext.setGcmCurrencyCode("USD");
        System.out.println("..added DB data into context.");
        System.out.println("..currency Code from DB: " + myContext.getGcmCurrencyCode());
        System.out.println("..someCalculationResult (should be 0): " + myContext.getSomeCalculationResult());
        
        System.out.println("STEP 2. Calling calculation engine with context");
        
        RunGlobalGCMUpdateCalculations calcEngine = new RunGlobalGCMUpdateCalculations(myContext);
        calcEngine.execute();
        
        System.out.println("STEP 3. Simulating saving results to the DB");
        CalculationContext context = (CalculationContext) calcEngine.getExecutionContext().getContextItem("calculationContext");
        System.out.println("..currency Code: " + myContext.getGcmCurrencyCode());
        System.out.println("..someCalculationResult: " + myContext.getSomeCalculationResult());
        
    }
}
