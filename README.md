#   <u>   PortaOne test task application </u>  

The PortaOne application is a small utility that allows you to operate on an array (list, series) of integers (integers in Java classification). By taking an input file that may contain a series of integers, the application will display the following information:
- Total number of lines in the input file
- Total number of integer values (int) to be processed
- Maximum number from the list
- Minimum number from the list
- Median number of the list (the middle number in the list or the average of the two middle numbers in a sorted ascending list)
- Average value of all int numbers in the list
- Number of values constituting a continuous increasing trend in the list
- All numbers in the series constituting a continuous increasing trend in the list
- Number of values constituting a continuous decreasing trend in the list
- All numbers in the series constituting a continuous decreasing trend in the list

This application is written in Java version 17. For its correct operation, you need to have the JRE environment installed with support for at least Java 8.

Clone this repository, build the project via 
**mvn clean package**
then copy file /target/portaone-0.0.1-SNAPSHOT-jar-with-dependencies.jar rename it to portaone.jar and let's go!

To run the application, place the portaone.jar file in the folder containing the file with the source information for processing. Ensure that the folder and the portaone.jar file have the necessary permissions in the system for execution and for creating folders and files for output information.

The application is launched with the command:
java -jar portaone.jar input.txt
where:
java -jar - command to the JRE to execute the .jar file
portaone.jar - the executable file with the application
input.txt - a text file with input data (preferably correct)

As a result of the execution, if there are no errors or exceptional situations, the results of calculations for the input series of numbers will be displayed on the screen. Details of the calculations, as well as any notes and errors encountered, can be reviewed in the log file created by the application in the /logs/ folder.

P.S. The application is developed in JDK using Intellij IDEA Community Edition.
P.P.S. The code algorithm for counting the number of lines in the file is taken from here: https://stackoverflow.com/a/39194088/17958798
P.P.P.S. All other calculation algorithms are personally developed by me and, of course, are not perfect. Feel free to use them!
P.P.P.P.S. Sometimes, I sought advice from ChatGPT3.5 regarding setting up logging in my Java application.
