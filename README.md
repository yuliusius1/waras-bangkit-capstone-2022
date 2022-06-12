# Waras App - Your Personal Medical App - Mobile Develompment

## Bangkit Capstone Project 2022

Bangkit Capstone Team ID : C22 - PS046 <br>
Here is our repository for Bangkit 2022 Capstone project - Mobile Development.

## Android Development Schedule
|  Task  |     Week 1     |       Week 2        |            Week 3          |            Week 4          |
| :----: | :------------: | :-----------------: | :------------------------: | :------------------------: |
| Task 1 | UI/UX Design   | Create Main UI      | Add Machine Learning Model | Add Nearby Hospital Location |  
| Task 2 | Create Concept | Authentication Page | Diagnose Page              | User Testing          | 
| Task 3 |                | Integrate with API  | Add Some Features          | Fix Bugs          |

## Android Architecture
- MVVM Architecture
![WarasArchitecture](https://github.com/yuliusius1/waras-bangkit-capstone-2022/blob/main/assets/android_architecture.jpg)

## Our Features 
- OnBoarding Page
- Splash Screen
- Authentication -> Login, Signup, Forgot Password
- Nearby Hospital Location
- Daily Tracker and Progress
- Quotes
- Recommendations
- Articles
- Predict day to heal based on symptoms
- Multi Languages

## Libraries and Dependecies
- Material Design
- Livedata and Lifecycle
- Navigation Component  
- Retrofit 2 and OKHTTP3
- Google Play Services Maps

## Link Download Application
Link Download APK Waras:<br>
[WarasAPK](https://drive.google.com/file/d/1TT83i5cgIjywD-TkX9S5FAONHVOdjDpY/view?usp=sharing)


## Installation
 * ### Prerequisites
    - Android Studio
 * ### Installations
    - Get your Maps API Token [Here](https://console.cloud.google.com/)
    - Clone This Project 
    ```bash
    $ git clone https://github.com/yuliusius1/waras-bangkit-capstone-2022.git
    ``` 
    - Paste your API Token to values/google_maps_api.xml
    ```bash
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">[YOUR API KEY]</string>
    ``` 
    - Run the application

