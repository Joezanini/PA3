/*
Joseph Zanini
jzanini@ucsc.edu
cmps 101 fall 2018
PA3
*/

import java.util.*;
import java.lang.*;
import java.io.*;

public class Sparse {
	public static void main(String[] args) throws IOException {
		Scanner input = null;
		PrintWriter output = null;
		String line;
		String[] token;
		int n = 0;
		boolean flag = false;
		int holdup = 0;
		Matrix A = null;
		Matrix B = null;
		Matrix C = null;

		String math;

		//checks for correct argument 
		if (args.length != 2) { 
			System.err.println("EXIT");
			System.exit(1);
		} 
		
		//scans input/output file
		input = new Scanner(new File(args[0])); 
		output = new PrintWriter(new File(args[1]));

		while (input.hasNextLine()) {
			String a = input.nextLine() + " ";
			//create Matrix using first line of input
			if (n == 0) {
				line = a.substring(0, a.indexOf(" "));
				int size = Integer.parseInt(line);
				A = new Matrix(size);
				B = new Matrix(size);
			}	

			//lets loop know to start making B Matrix
			if (n > 1 && a.equals(" ")) {
				flag = true;
				holdup = n;
			}		

			// create Entry(info[0], info[1], info[2])
			//for A matrix.
			if (n > 1 && flag == false) {
				String[] info = a.split(" "); 
				A.changeEntry(Integer.parseInt(info[0]), Integer.parseInt(info[1]),
					Double.parseDouble(info[2]));
			}
			// create Entry(info[0], info[1], info[2])
			//for B matrix.
			if (n > holdup && flag == true) {
				String[] info = a.split(" "); 
				B.changeEntry(Integer.parseInt(info[0]), Integer.parseInt(info[1]),
					Double.parseDouble(info[2]));
			}	
			n++;
		}

		input.close();
		
		output.println("A has " + A.getNNZ() + " non-zero entries:");
		output.println(A.toString());
		output.println("\nB has " + B.getNNZ() + " non-zero entries");
		output.println(B.toString());
		output.println("\n(1.5)*A =");
		C = A.scalarMult(1.5);
		output.println(C.toString());
		output.println("\nA+B =");
		C = A.add(B);
		output.println(C.toString());
		output.println("\nA+A =");
		C = A.add(A);
		output.println(C.toString());
		output.println("\nB-A =");
		C = B.sub(A);
		output.println(C.toString());
		output.println("\nA-A =");
		C = A.sub(A);
		output.println(C.toString());
		output.println("\nTranspose(A) =");
		C = A.transpose();
		output.println(C.toString());
		output.println("\nA*B =");
		C = A.mult(B);
		output.println(C.toString());
		output.println("\nB*B =");
		C = B.mult(B);
		output.println(C.toString());
		
		output.close();
	}


}