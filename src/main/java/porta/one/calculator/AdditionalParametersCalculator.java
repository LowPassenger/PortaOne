package porta.one.calculator;

import java.util.LinkedList;
import java.util.List;

public class AdditionalParametersCalculator {
    private List<Integer> ascending;
    private List<Integer> descending;
    private int ascendingMaxCounter = 0;
    private int descendingMaxCounter = 0;

    public AdditionalParametersCalculator() {
    }

    public List<Integer> getAscending() {
        return ascending;
    }

    public List<Integer> getDescending() {
        return descending;
    }

    public void calculateChains(int[] contentArray) {
        List<Integer> currentList = new LinkedList<>();
        Trend trend = Trend.SIDEWARD;

        currentList.add(contentArray[0]);
        if (contentArray[1] > contentArray[0]) {
            trend = Trend.UPWARD;
            currentList.add(contentArray[1]);
            ascendingMaxCounter = currentList.size();
        }
        if (contentArray[1] < contentArray[0]) {
            trend = Trend.DOWNWARD;
            currentList.add(contentArray[1]);
            descendingMaxCounter = currentList.size();
        }

        for (int i = 2; i <= contentArray.length - 1; i++) {
            if (contentArray[i - 1] == contentArray[i]) {
                trend = Trend.SIDEWARD;
                currentList.clear();
                currentList.add(contentArray[i]);
                continue;
            }

            if ((trend == Trend.DOWNWARD | trend == Trend.SIDEWARD)
                    & contentArray[i - 1] < contentArray[i]) {
                trend = Trend.UPWARD;
                if (currentList.size() > descendingMaxCounter) {
                    descending = new LinkedList<>(currentList);
                    descendingMaxCounter = descending.size();
                }
                currentList.clear();
                currentList.add(contentArray[i - 1]);
                currentList.add(contentArray[i]);
                continue;
            }

            if ((trend == Trend.UPWARD | trend == Trend.SIDEWARD)
                    & contentArray[i - 1] > contentArray[i]) {
                trend = Trend.DOWNWARD;
                if (currentList.size() > ascendingMaxCounter) {
                    ascending = new LinkedList<>(currentList);
                    ascendingMaxCounter = ascending.size();
                }
                currentList.clear();
                currentList.add(contentArray[i - 1]);
                currentList.add(contentArray[i]);
                continue;
            }

            if (trend == Trend.UPWARD & contentArray[i - 1] < contentArray[i]) {
                currentList.add(contentArray[i]);
                continue;
            }

            if (trend == Trend.DOWNWARD & contentArray[i - 1] > contentArray[i]) {
                currentList.add(contentArray[i]);
            }
        }
    }
}


