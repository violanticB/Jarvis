package me.violantic.data.matrix;

public class Matrix {

    private int rows;
    private int cols;
    private double[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data[i][j] = 0;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public void randomize() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public void add(Matrix m) {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                data[i][j] += m.data[i][j];
            }
        }
    }

    public static Matrix fromArray(double[][] array) {
        Matrix matrix = new Matrix(array.length, array[0].length);
        matrix.setData(array);

        return matrix;
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix c = new Matrix(a.rows, b.cols);
        for(int i = 0; i < c.rows; i++) {
            for(int j = 0; j < c.cols; j++) {
                c.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }

        return c;
    }

    public static Matrix transpose(Matrix a) {
        Matrix b = new Matrix(a.cols, a.rows);
        for(int i = 0; i < a.rows; i++) {
            for(int j = 0; j < a.cols; j++) {
                b.data[j][i] = a.data[i][j];
            }
        }

        return b;
    }

}
