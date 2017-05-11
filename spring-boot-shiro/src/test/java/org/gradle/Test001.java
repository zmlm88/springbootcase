package org.gradle;

public class Test001 {
	private static Test001 singleTon = new Test001();
	public static int count1;
	public static int count2 = 0;

	static{
		System.out.println("static");
	}
	
	private Test001() {
		System.out.println("const");
		count1++;
		count2++;
	}

	public static Test001 getInstance() {
		return singleTon;
	}
	
}
