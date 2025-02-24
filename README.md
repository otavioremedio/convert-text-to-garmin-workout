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
  7 X (1 min @ 75% (75w) 35-45 rpm with 2 min @ 40% (40w) rest )<br>
  1 min @ 75% (75w) 35-45 rpm<br>
  2 min @ 50% (50w) 50-100 rpm<br>
  5 min @ 65% (65w) 92-102 rpm<br>
  2 min @ 71% (71w)<br>
  5 min @ 60% (60w) 92-102 rpm<br>
  2 min @ 70% (70w)<br>
  5 min @ 69% (69w) 92-102 rpm<br>
  3 min @ 68% (68w)<br>
  5 min @ 60% (60w) 92-102 rpm<br>
  1 min @ 55% (55w)<br>
  3 min @ 50% (50w) 92-102 rpm<br>
  
* Enter your LHTR and press enter
* PS: Conversions will be done based on the approximate parameters below,
  for now it is not possible to configure other values, make the correction in the activity if necessary
![image](https://github.com/user-attachments/assets/8a457955-91a5-4889-937a-03025e3ff513)
* Follow the execution through Chrome and save the activity.

Enjoy!



