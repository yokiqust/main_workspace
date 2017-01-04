package test;

public class WhatEver {
	public static void main(String[] args) {
		for (int i = 0; i < 9; i++) {
			int row = i / 3;
			int col = i % 3;
			System.out.println("row = " + row + ";col = " + col);
		}
	}
}
