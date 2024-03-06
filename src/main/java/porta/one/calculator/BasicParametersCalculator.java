package porta.one.calculator;

import java.util.Arrays;

public class BasicParametersCalculator {
    private final int linesCounter;
    private int min;
    private int max;
    private int sum;
    private double med;
    private double average;
    private int[] contentArray;

    public BasicParametersCalculator(int linesCounter) {
        this.linesCounter = linesCounter;
    }

    public int getLinesCounter() {
        return linesCounter;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getMed() {
        return med;
    }

    public double getAverage() {
        return average;
    }

    public int[] getContentArray() {
        return contentArray;
    }

    public void setContentArray(int[] contentArray) {
        this.contentArray = contentArray;
    }

    public void calculateMinMaxSum(int number) {
        sum += number;
        if (number >= max) {
            max = number;
        }
        if (number <= min) {
            min = number;
        }
    }

    public void calculateAverage() {
        if (linesCounter > 0) {
            average = (double) sum / linesCounter;
        }
    }

    public void calculateMedian(int[] array) {
        Arrays.sort(array);
        if (array.length % 2 == 0) {
            med = (double) (array[array.length / 2 - 1] + array[array.length / 2]) / 2;
        } else {
            med = array[array.length / 2 - 1];
        }
    }
}
