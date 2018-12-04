/*
Joseph Zanini
jzanini@ucsc.edu
cmps 101 fall 2018
PA3
*/

import java.io.*;
import java.util.*;

public class MatrixTest {

	public static void main(String[] args) throws IOException {
		Matrix a = new Matrix(3);
		Matrix b = new Matrix(3);
		Matrix c;
		
	 	a.changeEntry(1, 1, 1.0);
	 	a.changeEntry(1, 2, 2.0);
	 	a.changeEntry(1, 3, 3.0);
	 	a.changeEntry(2, 1, 4.0);
	 	a.changeEntry(2, 2, 5.0);
	 	a.changeEntry(2, 3, 6.0);
	 	a.changeEntry(3, 1, 7.0);
	 	a.changeEntry(3, 2, 8.0);
	 	a.changeEntry(3, 3, 9.0);

	 	b.changeEntry(1, 1, 1.0);
	 	b.changeEntry(1, 2, 2.0);
	 	b.changeEntry(1, 3, 3.0);
	 	b.changeEntry(2, 1, 4.0);
	 	b.changeEntry(2, 2, 5.0);
	 	b.changeEntry(2, 3, 6.0);
	 	b.changeEntry(3, 1, 7.0);
	 	b.changeEntry(3, 2, 8.0);
	 	b.changeEntry(3, 3, 9.0);


	 	System.out.println("ORIGINALS");
	 	System.out.println(a);
	 	System.out.println(b);

	 	System.out.println(a.equals(b));

	 	System.out.println("SCALARMULT()");
	 	System.out.println(a.scalarMult(2.0));

	 	c = a.copy();

	 	a.makeZero();

	 	System.out.println("makeZero");
	 	System.out.println(a);
	 	System.out.println("MADEZERO");

	 	a.changeEntry(1, 1, 1.0);
	 	a.changeEntry(1, 2, 2.0);
	 	a.changeEntry(1, 3, 3.0);
	 	a.changeEntry(2, 1, 0.0);
	 	a.changeEntry(2, 2, 5.0);
	 	a.changeEntry(2, 3, 6.0);
	 	a.changeEntry(3, 1, 7.0);
	 	a.changeEntry(3, 2, 8.0);
	 	a.changeEntry(3, 3, 9.0);

	 	System.out.println("new A");
	 	System.out.println(a);

	 	System.out.println("ORIGINAL C");
	 	System.out.println(c);

	 	c = a.add(b);
	 	System.out.println("A + B = C");
	 	System.out.println(c);

	 	c = a.sub(b);
	 	System.out.println("A - B = C");
	 	System.out.println(c);

	 	c = a.transpose();
	 	System.out.println("A.transpose() = C");
	 	System.out.println(c);

	 	System.out.println("A");
	 	System.out.println(a);

	 	System.out.println("B");
	 	System.out.println(b);

	 	System.out.println("B.transpose");
	 	System.out.println(b.transpose());

	 	c = a.mult(b);
	 	System.out.println("A * B = C");
	 	System.out.println(c);

	}
}