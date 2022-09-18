# Android Test Sample App

### This repository contains a detailed sample app that implements MVVM architecture with Jetpack

### API Sample at SpaceX
- SpaceXData Github: https://github.com/r-spacex/SpaceX-API
- SpaceXData Docs: https://docs.spacexdata.com/
- Upcoming API: https://api.spacexdata.com/v4/launches/upcoming

### Unit test with Dagger Hilt, Runner, Mockito
- Class reference `dev.nhonnq.test.MainActivityTest`
- Open `MainActivityTest` and run all test cases to check API, Dependence Injection, Data

### Unit test cases
- Read json fake data from resource file `read sample success json file`
- MainActivity scenario `when MainActivity launched and fetch upcoming data`
- Check API status code success `Fetch upcoming from local file compare response Code 200 returned`
- Check API load upcoming, valid data, duplicated data `fetch upcoming from API and check valid data with important fields`

#### The app has following packages:
1. **api**: Network module
2. **base**: Base activity, adapter ...etc
3. **data**: It contains all the data accessing and manipulating components.
4. **di**: Dependency providing classes using Dagger Hilt.
5. **errors**: Error manager module, mapping error with code
6. **extensions**: Kotlin extension function
7. **listeners**: Callback interface
8. **ui**: View classes along with their corresponding ViewModel.
9. **utils**: Utility classes.

#### Classes have been designed in such a way that it could be inherited and maximize the code reuse.

### Library reference resources:
1. Dagger Hilt: https://developer.android.com/training/dependency-injection/hilt-android
2. Room: https://developer.android.com/topic/libraries/architecture/room.html
3. Retrofit: https://square.github.io/retrofit/
4. Okhttp: https://square.github.io/okhttp/ 
5. Gson: https://github.com/google/gson
6. Coroutines: https://developer.android.com/kotlin/coroutines
7. Glide: https://github.com/bumptech/glide

### Requirements
1. Load data from API and display in the app as list
2. Display upcoming information (Image, Name, Time, Flight Number ...etc)
3. Write unit test to check APIs, data
4. Swipe left to ignore or delete an item from the list, the next time open the app does not show these upcoming were ignored/deleted.
5. Search upcoming by name