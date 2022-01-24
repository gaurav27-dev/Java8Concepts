package com.gp.java8.app;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gp.java8.app.model.Emp;

public class StreamsExamples {

	public static void main(String[] args) {
		doStreamsProcessing();
	}

	private static void doStreamsProcessing() {
		// create a sequential streams. Any collection class
		List<Emp> seniorEmp = getEmpList().stream().filter((Emp e) -> e.getAge() > 50).collect(Collectors.toList());
		System.out.println("Senior Employee list");
		seniorEmp.forEach(System.out::println);

		boolean isHarryPresent = getEmpList().stream().anyMatch((Emp e) -> e.getName().equals("Harry Major"));
		System.out.println("isHarryPresent- >" + isHarryPresent);

		System.out.println("Total Emp in stream- >" + getEmpList().stream().count());

		Comparator<Emp> empAgeComparator = new Comparator<Emp>() {
			@Override
			public int compare(Emp o1, Emp o2) {
				if (o1.getAge() > o2.getAge()) {
					return 1;
				}
				if (o1.getAge() < o2.getAge()) {
					return -1;
				}
				return 0;
			}
		};
		System.out.println("Age comparator ascending by creating comparator object..");
		getEmpList().stream().sorted(empAgeComparator).forEach(System.out::println);
		System.out.println("Age comparator descending by using lambda..");
		getEmpList().stream().sorted((Emp o1, Emp o2) -> {
			if (o1.getAge() > o2.getAge())
				return -1;
			if (o1.getAge() < o2.getAge())
				return 1;
			return 0;
		}).forEach(System.out::println);
	
			System.out.println();
		 Stream.generate(Math::random).limit(3).forEach(System.out::println);
		 
		 List<String> intList=Arrays.asList("110","220","334","4122","523");
		 
		 System.out.println("Mapped String list to double list using Streams-->map");
		 // lets convert intList in doubleList using streams
		 Function<String,Double> doubleValueMapper=(String s)->Double.parseDouble(s);
		 intList.stream().map(doubleValueMapper).collect(Collectors.toList()).forEach(System.out::println);
		 
		 getEmpList().parallelStream();
	
	}

	private static List<Emp> getEmpList() {
		List<Emp> employeeList = Arrays.asList(new Emp("Tom Jones", 1, 45), new Emp("Harry Major", 2, 25),
				new Emp("Ethan Hardy", 3, 65), new Emp("Nancy Smith", 4, 15), new Emp("Deborah Sprightly", 5, 29));
		return employeeList;
	}

}
