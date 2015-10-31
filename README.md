# BrickPiJavaTest

This is a sample BrickPi java project.  The source is copied from 
https://github.com/13eakers/BrickPiJava/blob/master/src/com/ergotech/brickpi/BrickPiTests.java.

###Setup

Clone https://github.com/saxond/BrickPiJava and install the BrickPiJava library
into your local maven repo by executing this command from the BrickPiJava directory:

    ./gradlew install
    
###Building


The build uses the Gradle application plugin to generate a tar file that's ready to 
be untarred and run.  First generate the tar file:

    ./gradlew distTar
    
The application tar file will be generated into the _build/distributions_ directory.
Copy it to your raspberry pi, untar it and execute the shell script in the bin directory.
If you have a motor hooked up on port 1 it should move towards the end of the application
execution.

    ./BrickPiJavaTest/bin/BrickPiJavaTest
