/*
Joseph Zanini
jzanini@ucsc.edu
cmps 101 fall 2018
PA3
*/

import java.io.*;
import java.util.*;

public class ListTest {

	public static void main(String[] args) throws IOException {
		List a;
		Matrix c = new Matrix(3);
		Matrix d = new Matrix(3);
		
	 	c.changeEntry(1, 1, 1.0);
	 	c.changeEntry(1, 2, 2.0);
	 	c.changeEntry(1, 3, 3.0);

	 	d.changeEntry(1, 1, 4.0);
	 	d.changeEntry(1, 2, 5.0);
	 	d.changeEntry(1, 3, 6.0);

	 	a = c.row[1];
	 	a.append(d.row[1].front());

	 	System.out.println(a);

	 	a.deleteBack();

	 	System.out.println(a);

	 	a.prepend(d.row[1].back());

	 	System.out.println(a);

	 	a.deleteFront();

	 	a.moveFront();
	 	a.moveNext();
	 	a.insertAfter(d.row[1].back());
	 	System.out.println(a);

	 	a.delete();

	 	System.out.println(a);
	}
}