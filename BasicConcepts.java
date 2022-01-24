package com.gp.java8.app;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//i.like.irfan
//nafri.elik.i
//irfan.like.i

import com.gp.java8.app.model.Emp;

// Generic Type parameter
interface Generics<T> {
	public T sayHello(T param);
}

@FunctionalInterface
interface IntPredicate {
	boolean check(int i);
}

@FunctionalInterface
interface IntPredicate1 {
	boolean check(int i,int j);
}

@FunctionalInterface
interface EmpPresentPredicate {
	boolean checkEmp(Emp e);
}

class IntNumChecker {

	// List<?> wildcardList = new ArrayList<String>();
	// List<Object> objectList = new ArrayList<String>(); // Compilation error
	final public int num;

	public IntNumChecker(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	// check if num is bigger than the input value n
	boolean isBigger(int n) {
		return num > n;
	}
}

public class BasicConcepts {
	public static void main(String[] args) {
		interfaceConcepts();
		doExecuteLambdaConcepts();
		doForEach();
		methodReferenceToInstanceMethod();
		PredicateFunctionalInterface();
		doFunctionFITest();
		doFunctionFIComposeTest();
		doSupplierFITest();
		methodReferenceExamples();
		processOptionalConcept();
	}

	private static void interfaceConcepts() {
		Test t = new TestImpl();
		t.sayHello("Gaurav");
		int i = Emp.returnType(10);
		String s = Emp.returnType("gaurav");
	}

	private static void PredicateFunctionalInterface() {
		List<Integer> integerList = Arrays.asList(new Integer(1), new Integer(10), new Integer(200), new Integer(101),
				new Integer(-10), new Integer(0));
		Predicate<Integer> positiveIntegerPredicate = (Integer i) -> i > 0;
		List<Integer> filteredList = filterList(integerList, positiveIntegerPredicate);
		// filteredList.forEach((i)->System.out.println(i));
		filteredList.forEach(System.out::println);

	}

	private static List<Integer> filterList(List<Integer> listOfIntegers, Predicate<Integer> predicate) {
		List<Integer> filteredList = new ArrayList<Integer>();
		for (Integer integer : listOfIntegers) {
			if (predicate.test(integer)) {
				filteredList.add(integer);
			}
		}
		return filteredList;
	}

	private static void methodReferenceToInstanceMethod() {
		IntNumChecker checker = new IntNumChecker(10);
		int numToCompare = 9;
		IntPredicate p = checker::isBigger;
		
		IntPredicate1 p1= (e,j)->e+j>0;
		p1.check(2, 3);
		
		boolean result = p.check(numToCompare);
		if (result) {
			System.out.println(checker.num + " is bigger than " + numToCompare);
		} else {
			System.out.println(checker.num + " is smaller or equal than " + numToCompare);
		}
		
		

		Emp gEmp = new Emp("gaurav", 1, 52);
		Predicate<Emp> empExistPred = (e -> (e.getName().equals("gaurav") && e.getId() == 1));
		Predicate<Emp> seniorEmpPred = (e -> e.getAge() > 50);

		// combining two predicate
		boolean result1 = empExistPred.and(seniorEmpPred).test(gEmp);

		//EmpPresentPredicate empPresentPredicate = (e -> (e.getName().equals("gaurav");
		// && e.getId() == 1));

		// boolean result1 = empPresentPredicate.checkEmp(gEmp);
		if (result1) {
			System.out.println(gEmp.getName() + " is senior employee and present");
		} else {
			System.out.println(gEmp.getName() + " not present");
		}
	}

	private static void methodReferenceExamples() {
		// static method call using method reference
		Function<String, Double> doubleConvertor = Double::parseDouble;
		System.out.println(doubleConvertor.apply("101"));
		Consumer<String> s1 = System.out::println;
		// instance method of an object is called using method reference
		s1.accept("Hello gaurav by method reference");
		Consumer<String> s2 = (a) -> System.out.println(a);
		s2.accept("Hello gaurav by lamda");

		getEmpList().forEach(System.out::println);
		List<Integer> intList = Arrays.asList(1, 2, 3, 4);
		BiPredicate<List<Integer>, Integer> biPredByLambda = (List<Integer> list, Integer i) -> list.contains(i);
		System.out.println("Is 2 present in inList--> " + biPredByLambda.test(intList, 2));
	}

	private static void doExecuteLambdaConcepts() {
		Thread t1 = new Thread(() -> System.out.println("Running thread " + Thread.currentThread().getName()));
		t1.start();
	}

	private static void doForEach() {
		List<Integer> numberList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		
		numberList.forEach((a) -> {
			System.out.println("Hello is : " + a);
		});
		numberList.forEach(n->System.out.println("Number is : " + n));
		
		Consumer<Integer> numberFilterConsumer = n -> {
			System.out.println("Number is : " + n);
		};
		numberList.stream().filter(n -> n % 2 == 0).forEach(numberFilterConsumer);

		numberList.forEach(new Consumer<Integer>() {
			public void accept(Integer i) {

			};
		});
	}
	
	private static void doFunctionFITest() {
		List<String> empNameList = new ArrayList<String>();
		// Function<T,R> T is input type, R is output type
		Function<Emp, String> empNamesExtractor = (Emp e) -> e.getName();
		Function<String, String> empNamesInitalLetterExtractor = (String name) -> name.substring(0, 1);
		for (Emp e : getEmpList()) {
			// empNameList.add(empNamesExtractor.apply(e));
			empNameList.add(empNamesExtractor.andThen(empNamesInitalLetterExtractor).apply(e));
		}
		empNameList.forEach(System.out::print);
	}

	private static void doFunctionFIComposeTest() {
		List<String> empNameList = new ArrayList<String>();
		List<String> empNameList1 = new ArrayList<String>();
		// Function<T,R> T is input type, R is output type
		Function<Emp, Emp> empFirstNames = (Emp e) -> {  
			System.out.println("empFirstName function..");
			e.setName(e.getName().substring(0, e.getName().indexOf(" ")));
			return e;
		};

		Function<Emp, String> empNamesInitalLetterExtractor = (Emp emp) -> {
			System.out.println("empNamesInitalLetterExtractor function..");
			char[] nameArr = emp.getName().toCharArray();
			StringBuilder sb = new StringBuilder();
			for (int i = nameArr.length - 1; i >= 0; i--) {
				sb.append(nameArr[i]);
			}
			return sb.toString();
		};

		System.out.println();

		for (Emp e : getEmpList()) {
			empNameList.add(empNamesInitalLetterExtractor.compose(empFirstNames).apply(e));
		}
		System.out.println("-------Starting-------------");
		empNameList.forEach(System.out::println);
		System.out.println("----------END-------------");
		
		for (Emp e : getEmpList()) {
			empNameList1.add(empFirstNames.andThen(empNamesInitalLetterExtractor).apply(e));
		}
		System.out.println("-------Starting1-------------");
		empNameList1.forEach(System.out::println);
		System.out.println("----------END1-------------");

		// getEmpList().forEach(null);
	}

	private static void doSupplierFITest() {
		// Supplier can be used in all contexts where there is no input but an output is
		// expected.

		Supplier<Date> utilDateSupplier = () -> {
			return new Date();
		};

		Date d = utilDateSupplier.get();
		System.out.println("current date- " + d);

		Emp e = null;

		Optional<Emp> isEmpNull = Optional.ofNullable(e);
		isEmpNull.ifPresent(e1 -> System.out.println(e1));
		Optional.ofNullable(e).ifPresent(null);

	}

	private static List<Emp> getEmpList() {
		List<Emp> employeeList = Arrays.asList(new Emp("Tom Jones", 1, 45), new Emp("Harry Major", 2, 25),
				new Emp("Ethan Hardy", 3, 65), new Emp("Nancy Smith", 4, 15), new Emp("Deborah Sprightly", 5, 29));
		return employeeList;
	}

	private static void processOptionalConcept() {
		System.out.println("=======Optional concepts in JAVA ========");
		Address add = new Address("testStreet", null);
		Person person = new Person(add);

		Optional<String> optionalCountry = Optional.ofNullable(add.getCountry());
		System.out.println("@@@ country- " + optionalCountry);

		Function<Person, Address> adddressFunction = per -> per.getAddress();
		Function<Address, String> countryFunction = addrs -> addrs.getCountry();
		Optional<String> countryOptional = Optional.of(person).map(adddressFunction).map(countryFunction);
		if (countryOptional.isPresent()) {
			System.out.println("Country is : " + countryOptional.get());
		} else {
			System.out.println("Country not found");
		}

		System.out.println(countryOptional.orElse("Default Country"));
		System.out.println(countryOptional.orElseGet(() -> {
			return "Returing default country from orElseGet";
		}));

		List<Emp> lclEmpList = getEmpList();
		System.out.println(lclEmpList);

		Optional.ofNullable(lclEmpList).ifPresent(l_empList -> {
			List<Emp> updatedList = l_empList.stream().filter(em -> em.getAge() > 25).collect(Collectors.toList());
			updatedList.forEach(item -> item.setName(item.getName().toUpperCase()));
			System.out.println(updatedList);
		});
		System.out.println("===========END==============");
	}
	
}



interface Test {

	default Date getCurrentTime() {
		System.out.println("Message: " + getMessage());
		return new Date();

	}

	static String getMessage() {
		return "I am returing an String from interface static method";
	}

	void sayHello(String userName);
}

interface Test1 {
	/*
	 * implementing default methods directly. This is basically done in java8 to
	 * support new concepts forEach,StreamsApi. Each Collection Classes is now can
	 * directly use forEach as its implemented as default method in Collection
	 * interface
	 */

	default Date getCurrentTime() {
		return new Date();

	}
}

class TestImpl implements Test {
	// no need to implement default methods of interface.
	@Override
	public void sayHello(String userName) {
		System.out.println("Hello " + userName + ", Current DateTime is : " + getCurrentTime());

	}

}

class DiamondProblem implements Test, Test1 {

	@Override
	public Date getCurrentTime() {
		return Test.super.getCurrentTime();
	}

	@Override
	public void sayHello(String userName) {
		// TODO Auto-generated method stub

	}

}

class Address {
	private String street;
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Address(String street, String country) {
		this.street = street;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}

class Person {
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person(Address add) {
		this.address = add;
	}

}
