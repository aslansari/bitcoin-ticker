![Tests and Lint](https://github.com/aslansari/bitcoin-ticker/actions/workflows/test_lint.yml/badge.svg)

# Bitcoin Ticker

Bitcoin Ticker is an application where you can view and search crypto currencies and add them to favourites

## API

All coin data is fetched from [Coin Gecko](https://www.coingecko.com/en/api)

## Features

### Coin Search

You can search coins in coin list page. It will filter the coins on the fly without having to press search.

### Coin Details

By clicking a currency in the list you can view details page about that coin.  
Details page contain an image of currency, current coin value, percentage change of the coin value in last 24 hours, hashing algorithm and a description.
Note that, description and hashing algorithm data might not be available for all coins.

You can also add coin to favourites in this page by clicking the icon in the toolbar.

### Favourites

After logging in to an account, user can add coins to favourites. Added coins can be viewed in Favourites page.
Current prices of coins can also be viewed in Favourites page.

### User Login

You can register and login with your email. User login is required for adding coins to favourites.

### Background Tracking

If user is logged in and added coins to favourites, these coins are tracked in the background for price change.  
Last prices are recorded and if any of the coin's price is changed, related notification is sent to user, letting them know about the change and the current price.

## Technology Stack & Details

- [Kotlin](https://kotlinlang.org/), [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [MVVM Architecture](https://developer.android.com/topic/architecture)
- [Jetpack Navigation Component](https://developer.android.com/guide/navigation) used with [Safe Args](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args)
- [View binding](https://developer.android.com/topic/libraries/view-binding)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Firebase Could Firestore](https://firebase.google.com/docs/firestore) for storing favourite coins for user
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager/basics) for background tracking of favourite coins

## Documentation

### Continuous Integration
Project has a test and lint checker workflow with GitHub Actions.  
This workflow will run whenever a change is made in the repository. This way it is easier to keep the main branch clean and always working, especially with branching strategies like gitflow.  
You can check in the badge above to see the status of that workflow.

### Test Environment
In order to make development and test easier, I've used Firebase emulators.  
By using emulators, mistakes on production environment can be avoided and you can make changes more swiftly.

If you set up firebase emulators and deploy it, you need to tell the firebase in client to use emulated server.
You can achieve this by adding these lines in TickerApp.kt next line after initializing Firebase
```kotlin
// Change host address and port with emulator's address and ports, tip: you can define which ports to use in firebase.json
FirebaseAuth.getInstance().useEmulator("hostAddress", hostPort)
FirebaseFirestore.getInstance().useEmulator("hostAddress", hostPort)
```
Firebase Emulators and the client communicates in clear text, which is not permitted in android by default.
For only testing you can use a custom network config. [see for related issue on so](https://stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted)

### Could Firestore Security

For security, firestore is configured to permit read and write actions to only users that are authenticated.  
Also users can only modify their own lists.  
This is the rules of the firestore;
```yml
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
      match /users/{userId}/favourites/{favCoin} {
        allow write: if (request.auth.uid == userId);
        allow read: if (request.auth.uid == userId);
      }
  }
}
```

## Improvements

Possible improvement can ve adding more tests. Writing tests is taken into consideration during the development of this project. Domain layer classes (mostly UseCases) are free from platform dependent classes.  

By implementing Actor pattern and using the interfaces in the domain layer, these classes can be abstracted. Then it would be suitable to write fake classes for certain test conditions.
