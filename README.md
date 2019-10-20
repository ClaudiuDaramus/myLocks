# myLocks

The "myLocks" application was developed as a feature to smart homes which eases the process of locking and unlocking doors.

The app uses Bluetooth technology to pair the phone with multiple locks, allowing them to be locked and unlocked by the app. 

# Technologies

- Java on the Android app;

- C++ for Arduino;

- mySQL Database;

- HTML/CSS/JS & Spring Boot/Java on the Web app.

# How the application will be designed to work

When the phone is in the signal radius of the Bluetooth lock, the user can use the app to lock it, unlock it and change it's sequrity PIN. Another feature of the app is the automatic unlock, meaning that a Bluetooth lock will be automatically unlocked when it has Bluetooth signal from the phone and the feature is turned on. Saved locks will appear in the locks list from the main screen. The locks which are not close enough to be paired to the phone will be gray, having the status "too far away". The paired locks can be green with the status "unlocked", or red with the status "locked".

For a Bluetooth lock to be saved in the app, the user must tap the add button from the main screen, which will swhitch to the lock search activity. In the search activity the user fill find the nearby Bluetooth locks only if the Bluetooth is turned on from the phone. To save a lock in the app, the user must pair to it using the lock's sequrity PIN. On the search activity there is also a back button which will switch to the main screen. By tapping on a lock from the main screen, it will switch to the lock's activity. On the screen will be the name of the lock, it's status represented by a suggestive icon, a lock/unlock button (with two possible suggestive icons), a switch to turn automatic unlock on or off, a button to change the lock's name, a button to change the lock's sequrity PIN, a button to delete the lock from the app and a back button, which will whitch to the main screen. To change the PIN, it's required the old PIN and a new one consisting of exactly four digits. To change and delete the lock, it's required the PIN.
