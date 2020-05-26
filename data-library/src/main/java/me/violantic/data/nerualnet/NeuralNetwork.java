package me.violantic.data.nerualnet;

import me.violantic.data.matrix.Matrix;

public class NeuralNetwork {

    private int inputs;
    private int hidden;
    private int output;

    private Matrix weightsX;
    private Matrix weightsH;


    public NeuralNetwork(int inputs, int hidden, int output) {
        this.inputs = inputs;
        this.hidden = hidden;
        this.output = output;

        this.weightsX = new Matrix(hidden, inputs);
        this.weightsH = new Matrix(output, inputs);

    }

    public int feedForward() {
        int guess;



        return 0;
    }

}
