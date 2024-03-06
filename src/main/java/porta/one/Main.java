package porta.one;

import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import porta.one.calculator.AdditionalParametersCalculator;
import porta.one.calculator.BasicParametersCalculator;
import porta.one.reader.ContentReader;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final int minLinesInFile = 3;

    public static void main(String[] args) {
        String inputFileName = args.length > 0 ? args[0] : null;
        logger.info("Input file name: {}",
                inputFileName == null ? "not_correct_file_name" : inputFileName);
        ContentReader reader = new ContentReader();
        if (!reader.isFileCorrect(inputFileName)) {
            logger.warn("The file {} is not correct! "
                    + "Please use valid data for input!", System.getProperty("logFileName"));
            System.out.println("Something wrong with input parameters! "
                    + "Please read README.md and output.log file for more details.");
            return;
        }
        long startLinesCountTime = System.currentTimeMillis();
        int linesCount = 0;
        try {
            linesCount = reader.lineCount(inputFileName);
        } catch (IOException exception) {
            logger.error("Something wrong with input data in {} file. "
                    + "Please read README.md for more information about input data. "
                    + "Stacktrace : {}", inputFileName, exception.getMessage());
            System.out.println("Something wrong with input data! "
                    + "Please read log file for more information!");
            exception.printStackTrace();
            System.exit(1);
        }
        long endLinesCountTime = System.currentTimeMillis();
        logger.info("Lines counts process complete. Total time for counting "
                        + "{} lines is {} seconds.", linesCount,
                (endLinesCountTime - startLinesCountTime) / 1000.0);
        System.out.println("Total lines in file " + inputFileName + " is " + linesCount);
        if (linesCount < minLinesInFile) {
            logger.warn("Sorry, your file {} contains less than minimum {} lines. "
                    + "Couldn't process input data.",
                    System.getProperty("logFileName"), minLinesInFile);
            System.out.println("Sorry, your file " + inputFileName + " contains less than minimum "
                    + minLinesInFile + " lines. Couldn't process input data.");
            return;
        }

        BasicParametersCalculator calculator = new BasicParametersCalculator(linesCount);
        long startBasicCalculatesTime = System.currentTimeMillis();
        try {
            calculator = reader.getContentArray(inputFileName, linesCount);
        } catch (IOException exception) {
            logger.warn("An input-output exception occurred during {} file parsing process! "
                    + "Additional information: {}", inputFileName, exception.getMessage());
            exception.printStackTrace();
        }
        long endBasicCalculatesTime = System.currentTimeMillis();

        logger.info("Basic parameters calculating process complete in {} seconds.",
                (endBasicCalculatesTime - startBasicCalculatesTime) / 1000.0);

        if (calculator.getMax() == 0 & calculator.getMin() == 0
                & calculator.getMed() == 0 & calculator.getAverage() == 0) {
            logger.warn("Sorry, looks like your input file contains any valid data!"
                    + " Please check your input data file and try again!");
            System.out.println("Sorry, looks like your input file contains any valid data!"
                    + " See more details in latest .log file in /logs/ folder");
            return;
        }
        System.out.println("Total Lines counted: " + calculator.getLinesCounter());
        System.out.println("Max: " + calculator.getMax());
        System.out.println("Min: " + calculator.getMin());
        System.out.println("Median: " + calculator.getMed());
        System.out.println("Average: " + calculator.getAverage());
        System.out.println("Calculating biggest ascending and descending chains...");
        logger.warn("Total lines counted: {}, max: {}, min: {}, median: {}, average: {}",
                calculator.getLinesCounter(), calculator.getMax(), calculator.getMin(),
                calculator.getMed(), calculator.getAverage());

        long startAdditionalParametersCalculatingTime = System.currentTimeMillis();
        AdditionalParametersCalculator additionalCalculator = new AdditionalParametersCalculator();
        additionalCalculator.calculateChains(calculator.getContentArray());
        long endAdditionalParametersCalculatingTime = System.currentTimeMillis();
        logger.info("Additional parameters calculating process complete in {} seconds.",
                (endAdditionalParametersCalculatingTime
                        - startAdditionalParametersCalculatingTime) / 1000.0);
        List<Integer> ascendingNumbers = additionalCalculator.getAscending();
        logger.info("The largest sequence of increasing numbers has {} elements.",
                ascendingNumbers.size());
        System.out.println("The largest sequence of increasing numbers has "
                + ascendingNumbers.size() + " elements");
        logger.info("There are: {}", ascendingNumbers);
        System.out.println("There are: ");
        ascendingNumbers.stream().forEach(System.out::println);
        List<Integer> descendingNumbers = additionalCalculator.getDescending();
        logger.info("The largest sequence of decreasing numbers has {} elements.",
                descendingNumbers.size());
        System.out.println("The largest sequence of decreasing numbers has "
                + descendingNumbers.size() + " elements");
        logger.info("There are: {}", descendingNumbers);
        System.out.println("There are: ");
        descendingNumbers.stream().forEach(System.out::println);
        System.out.println("Program completed successfully! "
                + "Please read portaone-**date-time**.log "
                + "in the /logs/ folder file for more details.");
    }
}
