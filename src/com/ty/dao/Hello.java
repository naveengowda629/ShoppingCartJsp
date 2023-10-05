package com.ty.dao;

public class Hello {
	public static void main(String[] args) {
		System.out.println("hello this is my first collaboration");
		
		int [] arr={40,30,50,20,10,45};
		int []temp=new int[arr.length];
		for(int i=0;i<arr.length-1;i++) {
			for(int j=1;j<i+1;j++) {
				if(arr[i]!=arr[j]) {
					System.out.println(arr[i]);
				}
			}
		}
	}

}
