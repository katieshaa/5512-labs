package org.suai.matrix;

import java.awt.*;
import java.util.Random;


public class UsualMatrix {
	private int[][] matrix;
	private int rows;
	private int columns;


	public UsualMatrix(int aRows, int aColumns) {
		rows = aRows;
		columns = aColumns;

		matrix = new int[rows][columns];
	}

	
	public int getRows() {
		return rows;
	}


	public int getColumns() {
		return columns;
	}


	public int getCell(int aRow, int aColumn) {
		return matrix[aRow][aColumn];
	}


	public void setCell(int aRow, int aColumn, int aValue) {
		matrix[aRow][aColumn] = aValue;
	}


	public UsualMatrix product(UsualMatrix aOther) {
		long start1 = System.currentTimeMillis();
		if(columns != aOther.rows) {
			throw new RuntimeException("Cant product! Bad matrix!");
		}

		UsualMatrix result = new UsualMatrix(rows, aOther.columns);

		for(int i = 0; i < result.rows; i++) {
			for(int j = 0; j < result.columns; j++) {
				result.matrix[i][j] = 0;

				for(int k = 0; k < columns; k++) {
					result.matrix[i][j] += (matrix[i][k] * aOther.matrix[k][j]);
				}
			}
		}
		System.out.println((System.currentTimeMillis()-start1) + " ---- usualMatrix" + '\n');
		return result;
	}

	
	public void fillRandom(int module) {
		Random rand = new Random();

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				matrix[i][j] = rand.nextInt(module);
			}
		}
	}


	@Override
	public String toString() {
		String out = "";

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				out += matrix[i][j];
				out += " ";
			}

			out += "\n";
		}

		return out;
	}


	@Override
	public boolean equals(Object aObject) {
		if(this == aObject) {
			return true;
		}

		if(aObject == null) {
			return false;
		}

		if(!(aObject instanceof UsualMatrix)) {
			return false;
		}

		UsualMatrix other = (UsualMatrix)aObject;

		if(rows != other.rows || columns != other.columns) {
			return false;
		}

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(matrix[i][j] != other.matrix[i][j]) {
					return false;
				}
			}
		}

		return true;
	}
}