# This program converts text instructions for a garmin connect web cycling workout (from FTP to HR).

## Pre requisite:
You must have Java installed >= 18.

## How to use:
* Configure your Chrome to run in debug mode, so embeeded Selenium can perform the tasks.
* To do this, add the following to the Chrome shortcut: --remote-debugging-port=9222
![image](https://github.com/user-attachments/assets/cc7a221f-8e82-446f-8872-80a08fa2f06a)
* Open Chrome to the page https://connect.garmin.com/modern/workout/create/cycling
* Run the program with the command: java -jar .\build\libs\GarminConnect-1.0-SNAPSHOT.jar
* Paste the training instructions in like format below, and at the end press enter twice<br>
  15 min @ 100-142 bpm<br>
  2 X (30 min @ 143-161 bpm with 10 min @ 100-142 bpm)<br>
  80 min @ 125-142 bpm<br>
  15 min @ 100-125 bpm<br>
  
* Enter your LHTR and press enter
* PS: Conversions will be done based on the approximate parameters below,
  for now it is not possible to configure other values, make the correction in the activity if necessary
![image](https://github.com/user-attachments/assets/8a457955-91a5-4889-937a-03025e3ff513)
* Follow the execution through Chrome and save the activity.

Enjoy!



