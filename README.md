# README

This is a Java version of the old “Xeyes” application.
It’s written to work on MacOS systems, but it can probably
be ported to Linux and Windows.

As a warning about the code, I originally wrote it a long time ago.
The last time I remember working on it was in a coffee shop in 
Palmer, Alaska in 2011. As a result of that, and writing most of it 
late at night, a lot of it looks really old, but I was able to revive 
it a bit here in 2019. It also relies on a _com.sun.awt.AWTUtilities_ 
that is deprecated, so that code needs to be replaced. (So, feel
free to clean up and update the code, if so desired.)

That being said, it’s a fun little app, and it works on Java 8 on
MacOS 10.14 (Mojave). You should be able to build a native MacOS
application with the two build scripts:

- 1assembly.sh
- 2makeApp.sh
 
One other note: You can toggle the app to/from full-screen mode with
the `[Command][F]` keystroke.

Alvin Alexander  
https://alvinalexander.com
