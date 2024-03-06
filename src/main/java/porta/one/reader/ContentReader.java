package porta.one.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import porta.one.calculator.BasicParametersCalculator;

public class ContentReader {
    private static final Logger logger = LogManager.getLogger(ContentReader.class);
    private static final int bufferSize = 1024;
    private static final char currentLineSeparator = '\n';

    public boolean isFileCorrect(String fileName) {
        if (fileName == null || !fileName.matches("^[A-Za-z0-9.]{1,255}$")) {
            return false;
        }
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + File.separator + fileName;
        File currentFile = new File(filePath);
        return currentFile.exists() & currentFile.isFile();
    }

    public int lineCount(String fileName) throws IOException {
        int count = 0;
        try (InputStream inputStream = new BufferedInputStream(Files
                .newInputStream(Paths
                        .get(fileName)))) {
            byte[] byteArray = new byte[bufferSize];
            int readChars = inputStream.read(byteArray);
            if (readChars == -1) {
                return 0;
            }
            while (readChars == bufferSize) {
                for (int i = 0; i < bufferSize; ) {
                    if (byteArray[i++] == currentLineSeparator) {
                        ++count;
                    }
                }
                readChars = inputStream.read(byteArray);
            }
            while (readChars != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (byteArray[i] == currentLineSeparator) {
                        ++count;
                    }
                }
                readChars = inputStream.read(byteArray);
            }
        } catch (IOException exception) {
            throw new IOException("An error throws during read " + fileName + " file!", exception);
        }
        return count;
    }

    public BasicParametersCalculator getContentArray(String fileName, int count)
            throws IOException {
        BasicParametersCalculator parametersCalculator = new BasicParametersCalculator(count);
        int[] contentArray = new int[count];
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int innerCounter = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    contentArray[innerCounter] = Integer.parseInt(line);
                } catch (NumberFormatException exception) {
                    logger.warn("An exception occurred during parsing data from {} file. "
                            + "Additional info: line {}, position {}",
                            fileName, line, innerCounter);
                    System.out.println("Wrong number format!");
                }
                parametersCalculator.calculateMinMaxSum(contentArray[innerCounter]);
                innerCounter++;
            }
        }
        parametersCalculator.calculateAverage();
        parametersCalculator
                .setContentArray(Arrays.copyOf(contentArray, count));
        parametersCalculator.calculateMedian(contentArray);
        return parametersCalculator;
    }
}
