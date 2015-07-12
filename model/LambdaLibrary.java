package model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaLibrary {
	// for getNextAvailableCopyLambda() in Publication 
	public final static Function<List<LendableCopy>, List<LendableCopy>> NEXTAVAILABLECOPY
	   		= (list) -> list.stream()
                     .filter(c -> c.isCheckOut() == false)
                     .collect(Collectors.toList());
	
	// for printEntries(List<CheckoutRecordEntry> entries) in CheckoutRecord
	public final static Consumer<List<CheckoutRecordEntry>> PRINTENTRIES
		   = (list) -> list.stream()
		   				.forEach(c -> System.out.println(c.toString()));

}