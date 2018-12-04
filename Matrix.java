/*
Joseph Zanini
jzanini@ucsc.edu
cmps 101 fall 2018
PA3
*/

class Matrix {
  int size;
  int NNZ;
  List[] row;
	private class Entry {
		int col;
		double val;

		Entry(int col, double val) { 
			this.col = col;
      this.val = val; 
    }

		public String toString() { 
      return("(" + col + ", " + val + ") ");
    }

		public boolean equals(Object x){
      boolean eq = false;
      Entry that;
      if(x instanceof Entry){
        that = (Entry) x;
        eq = (this.val==that.val);
      }
      return eq;
    }
	}

  // Constructor
  //Matrix(int n) // Makes a new n x n zero Matrix. pre: n>=1
  Matrix(int n) {

    if(n < 1) {
      throw new RuntimeException(
        "Matrix Error: Matrix() called on n < 1");
    }

    int i;
    size = n;
    NNZ = 0;
    row = new List[n + 1];
    for (i = 1; i < n + 1; i++) {
      row[i] = new List();
    }
  }

  // Access functions

  //int getSize() 
  // Returns n, the number of rows and columns of this Matrix
  int getSize() {
    return(size);
  }

  //int getNNZ() 
  // Returns the number of non-zero entries in this Matrix
  int getNNZ() {
    return(NNZ);
  }

  //public boolean equals(Object x) 
  // overrides Object's equals() method
  public boolean equals(Object x) {
    boolean eq = false;
    Matrix M;
    int i;

    if (x instanceof Matrix) {
      M = (Matrix) x;
      if (this.getSize() != M.getSize()) {
        System.out.println("size");
        return(eq);
      }

      for (i = 1; i < size + 1; i++) {
        eq = this.row[i].equals(M.row[i]);
        if (eq == false) {
          return(eq);
        }
      }
    }
    return(eq);
  }

  // Manipulation procedures
  //void makeZero() 
  // sets this Matrix to the zero state
  void makeZero() {
    int i;
    for (i = 0; i < size; i++) {
      row[i + 1].clear();
    }
  }

  //Matrix copy()
  // returns a new Matrix having the same entries as this Matrix
  Matrix copy() {
    int i;
    Matrix newM = new Matrix(size);
    for (i = 1; i < size + 1; i++) {
      if (!row[i].isEmpty()) {
        for (row[i].moveFront(); row[i].index() >= 0; row[i].moveNext()) {
          newM.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).val);
        }
      }
    }
    return(newM);
  }

  //void changeEntry(int i, int j, double x)
  // changes ith row, jth column of this Matrix to x
  // pre: 1<=i<=getSize(), 1<=j<=getSize()
  void changeEntry(int i, int j, double x) {
    if (i < 1 || i > getSize()) {
      throw new RuntimeException("Matrix Error: ChangeEntry() called on out of bounds i");
    }

    if (j < 1 || j > getSize()) {
      throw new RuntimeException("Matrix Error: ChangeEntry() called on out of bounds j");
    }

    Entry newE = new Entry(j, x);
    boolean inserted = false;
    int mid = row[i].length() / 2;

    //adding new entry to existing List Object in Matrix.
    if (row[i].isEmpty()) {
      if (x != 0) {
        row[i].prepend(newE);
        NNZ++;
      }
      return;
    } else {
      for (row[i].moveFront(); row[i].index() >= 0; row[i].moveNext()) {
        if (((Entry) row[i].get()).col == newE.col) {
          //changing value of initialized Matrix position
          if(x == 0) {
            row[i].delete();
            NNZ--;
            inserted = true;
            return;
          }

          if (x != 0) {
            ((Entry) row[i].get()).val = x;
            return;
          }
        }
        //inserting data at a uninitialized Matrix position
        if (((Entry) row[i].get()).col > j && !inserted) {
          row[i].insertBefore(newE);
          inserted = true;
          NNZ++;
          return;
        }
      }
    }

    if (!inserted && x != 0) {
      row[i].append(newE);
      NNZ++;
    }
  }

  //Matrix scalarMult(double x)
  // returns a new Matrix that is the scalar product of this 
  //Matrix with x
  Matrix scalarMult(double x) {
    int i;
    Matrix newM = new Matrix(size);

    for (i = 1; i < size + 1; i++) {
      if (!row[i].isEmpty()) {
        for (row[i].moveFront(); row[i].index() >= 0; row[i].moveNext()) {
          newM.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).val * x);
        }
      }
    }

    return(newM);
  }

  //Matrix add(Matrix M)
  // returns a new Matrix that is the sum of this Matrix with M
  // pre: getSize()==M.getSize()
  Matrix add(Matrix Mat) {
    int i, p, r, j;
    Matrix sum = new Matrix(size);

    if (this.getSize() != Mat.getSize()) {
      throw new RuntimeException("Matrix Error: add() called on 2 matrices with different size");
    }

    if (this.equals(Mat)) {
      Mat = Mat.copy();
    }

    for (i = 1; i < size + 1; i++) {
      int m;
      p = row[i].length();
      r = Mat.row[i].length();

      if (p <= r) {
        m = r;
      } else {
        m = p;
      }

      row[i].moveFront();
      Mat.row[i].moveFront();

      for (j = 0; j < 2 * m; j++) {
        if (row[i].index() == -1 && Mat.row[i].index() == -1) {
          break;
        }

        Entry temp1;
        Entry temp2;

        if (row[i].index() != -1 && Mat.row[i].index() != -1) {
          temp1 = new Entry(((Entry) row[i].get()).col, ((Entry) row[i].get()).val);
          temp2 = new Entry(((Entry) Mat.row[i].get()).col, 
            ((Entry) Mat.row[i].get()).val);

          if (temp2.col == temp1.col) {
            temp1.val += temp2.val;
            sum.changeEntry(i, temp1.col, temp1.val);
            row[i].moveNext();
            Mat.row[i].moveNext();
            continue;
          } else if (temp1.col < temp2.col) {
            sum.changeEntry(i, temp1.col, temp1.val);
            row[i].moveNext();
            continue;
          } else {
            sum.changeEntry(i, temp2.col, temp2.val);
            Mat.row[i].moveNext();
            continue;
          }
        } else if (row[i].index() == -1) {
          temp1 = new Entry(((Entry) Mat.row[i].get()).col, 
            ((Entry) Mat.row[i].get()).val);
          sum.changeEntry(i, temp1.col, temp1.val);
          Mat.row[i].moveNext();
          continue;
        } else if (Mat.row[i].index() == -1) {
          temp1 = new Entry(((Entry) row[i].get()).col, ((Entry) row[i].get()).val);
          sum.changeEntry(i, temp1.col, temp1.val);
          row[i].moveNext();
          continue;
        }
      }
    }
    return(sum);
  }

  //Matrix sub(Matrix M)
  // returns a new Matrix that is the difference of this Matrix with M
  // pre: getSize()==M.getSize()
  Matrix sub(Matrix Mat) {
    int i, p, r, j;
    Matrix diff = new Matrix(size);

    if (this.equals(Mat)) {
      return (diff);
    }

    if (this.getSize() != Mat.getSize()) {
      throw new RuntimeException("Matrix Error: sub() called on 2 matrices with different size");
    }
    for (i = 1; i < size + 1; i++) {
      int m;
      p = row[i].length();
      r = Mat.row[i].length();

      if (p <= r) {
        m = r;
      } else {
        m = p;
      }

      row[i].moveFront();
      Mat.row[i].moveFront();

      for (j = 0; j < 2 * m; j++) {
        if (row[i].index() == -1 && Mat.row[i].index() == -1) {
          break;
        }

        Entry temp1;
        Entry temp2;

        if (row[i].index() != -1 && Mat.row[i].index() != -1) {
          temp1 = new Entry(((Entry) row[i].get()).col, ((Entry) row[i].get()).val);
          temp2 = new Entry(((Entry) Mat.row[i].get()).col, 
            ((Entry) Mat.row[i].get()).val);

          if (temp2.col == temp1.col) {
            temp1.val -= temp2.val;
            if (temp1.val != 0) {
              diff.changeEntry(i, temp1.col, temp1.val);
            }
            row[i].moveNext();
            Mat.row[i].moveNext();
            continue;
          } else if (temp1.col < temp2.col) {
            if (temp1.val != 0) {
              diff.changeEntry(i, temp1.col, temp1.val);
            }
            row[i].moveNext();
            continue;
          } else {
            if (temp2.val != 0) {
              temp2.val = -temp2.val;
              diff.changeEntry(i, temp2.col, temp2.val);
            }
            Mat.row[i].moveNext();
            continue;
          }
        } else if (row[i].index() == -1) {
          temp1 = new Entry(((Entry) Mat.row[i].get()).col, 
            ((Entry) Mat.row[i].get()).val);
          if (temp1.val != 0) {
            temp1.val = -temp1.val;
            diff.changeEntry(i, temp1.col, temp1.val);
          }
          Mat.row[i].moveNext();
          continue;
        } else if (Mat.row[i].index() == -1) {
          temp1 = new Entry(((Entry) row[i].get()).col, ((Entry) row[i].get()).val);
          if (temp1.val != 0) {
            diff.changeEntry(i, temp1.col, temp1.val);
          }
          row[i].moveNext();
          continue;
        }
      }
    }
    return(diff);
  }

  //Matrix transpose()
  // returns a new Matrix that is the transpose of this Matrix
  Matrix transpose() {
    int i;
    Matrix newM = new Matrix(size);
    for (i = 1; i < size + 1; i++) {
      for (row[i].moveFront(); row[i].index() >= 0; row[i].moveNext()) {
        newM.changeEntry(((Entry) row[i].get()).col, i, ((Entry) row[i].get()).val);
      }
    }
    return(newM);
  }

  //Matrix mult(Matrix M)
  // returns a new Matrix that is the product 
  //of this Matrix with M 
  // pre: getSize()==M.getSize()
  Matrix mult(Matrix M) {
    
    if (this.getSize() != M.getSize()) {
      throw new RuntimeException("Matrix Error: mult() called on 2 matrices with different size");
    }

    Matrix newM = new Matrix(size);
    Matrix trans = M.transpose();
    int i, j;
    double dotP;

    for (i = 1; i < size + 1; i++) {
      for(j = 1; j < size + 1; j++) {
        dotP = dot(row[i], trans.row[j]);
        newM.changeEntry(i, j, dotP);
        dotP = 0;
      }
    }
    return(newM);
  }
  
  // Other functions

  //double dot()
  // returns a value that represents 
  // the sum of the products of a 
  // row and its corresponding collumn
  // for Matrix multiplication; 
  double dot(List hor, List vert) {
    double sum = 0;
    int i;
    
    hor.moveFront();
    vert.moveFront();

    if (!hor.isEmpty() && !vert.isEmpty()) {
      while (hor.index() != -1 && vert.index() != -1) {
        if (((Entry) hor.get()).col != ((Entry) vert.get()).col) {
          if (((Entry) hor.get()).col < ((Entry) vert.get()).col) {
            hor.moveNext();
          } else {
            vert.moveNext();
          }
        } else {
          sum += (((Entry) hor.get()).val * ((Entry) vert.get()).val);
          hor.moveNext();
          vert.moveNext();
        }
      }
    } 
    return(sum);
  }

  //public String toString() 
  // overrides Object's toString() method
  public String toString() {
    int i, j;
    StringBuffer sb = new StringBuffer();
    if (this.getNNZ() == 0) {
      return("");
    }

    for (i = 1; i < size + 1; i++) {
      if (!row[i].isEmpty()) {
        sb.append(String.valueOf(i) + ": ");
        sb.append(row[i].toString());
        sb.append("\n");
      }
    }
    return (new String(sb));
  }




}