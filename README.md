# Pulse Notifier - Yad al Hadofek - DevNation Hackathon 2020
## Background
When a rape victim is under drug influence or is under extreme stress & panic it is known that their heartrate changes drastically. For example, Rohpynol, the most common rape drug
causes the hearrate of the victim to decrease dramatically within 2 minutes of its' consumption.
In addition, many rape / sexual assualt victims 'freeze' whenever they are being assaulted and therefore can't call for help. 

## Our App
Whenever a victim is actually being harrassed / drugged, our app can detect the drastic change in the vitals and start *recoding audio*, *gps location* and will *call for help*.
As we all know there are many factors that can change heartrate and vital signs, therefore before the app starts recording, it'll notify the user.
If the user is unable to respond to the notification, (due to being unconscious, or unable to move) the app will automatically call for help, record audio and gps location.

## Technical Details
The application is based on `Java`, it can access the vital through `Anroid OS` & `Wear OS API's`. To detect anomalies we use a statistical technique from stock analysis called 'Bollinger Bands'. 
