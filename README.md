
# Managing Users POC
Example application build on https://gorest.co.in/ public API as an architecture test case.

The purpose of this application is to show usage of some basic libraries and architecture concepts:
* Clean architecture
* MVVM
* Composer
* coroutines, flows
* GraphQL
* Lottie

## Screen shots
![Splash screen](/screens/screen1.png)
![Splash screen with network error and retry action](/screens/screen1_error.png)
![List of users](/screens/screen2.png)
![Adding users](/screens/screen3.png)
![Adding users - field validation](screens/screen4.png)
![Adding users - server validation](screens/screen5.png)
![Removing users](screens/screen6.png)
![Filtering user list](screens/screen7.png)
![Filtering user list - landscape version](screens/screen8.png)

Further development should be focused on:
* androidTests - Testing fragments and emitting ViewEvents
* Support edge cases of Activity <--> System interaction
* Support for animations and transitions
* Improve dialog place in architecture. 
* Testing business flows. 
* Move snackBars/toasts? to composer.


