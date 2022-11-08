# Scala/Java “XEyes” Application

This is a Java/Scala version of the old “Xeyes” application.
It’s written to work on MacOS systems, but it can probably
be ported to Linux and Windows.

As a warning about the code, I originally wrote it a long time ago.
The last time I remember working on it was in a coffee shop in 
Palmer, Alaska in 2011. As a result of that, and writing most of it 
late at night, a lot of it looks really old, but I was able to revive 
it a bit here in 2019. It also relies on a _com.sun.awt.AWTUtilities_ 
that is deprecated, so that code needs to be replaced. (So, feel
free to clean up and update the code, if so desired.)

I’m very slowly porting this code to Scala.
Like really slowly, just a few lines a year, whenever I feel 
like working on something different, or need to update my
Scala/macOS app build process.


## Building the app

The way I currently build the app is:

- I run *sbt* in one Terminal window, with Java 11
- I have another Terminal window open with Java 14, to run the `jpackage` script

It probably isn’t necessary to have two windows open, but that’s what
I did today.

 
One other note: You can toggle the app to/from full-screen mode with
the `[Command][F]` keystroke.

Alvin Alexander  
https://alvinalexander.com
