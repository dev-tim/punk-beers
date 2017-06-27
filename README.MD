## PunkBeer Challenge

Punk beer challenge is a fun app for real beer fans! 
App fetches information about beers from [Punk API](https://punkapi.com/) using 
[Retrofit](https://square.github.io/retrofit/) and [OkHttp](http://square.github.io/okhttp/).

![App sample](https://raw.githubusercontent.com/dev-tim/punk-beers/master/gifs/normal_gif.gif "App sample")

[See app no network sample below](#pookie)

### How to run the app

In order to run unit tests:

```bash
./gradlew clean test
```

In order to run android instrumentation tests (please make sure that you have
device connected or emulator running):

```bash
./gradlew connectedAndroidTest
```

If you want to install app to your device/emulator, run:
```bash
./gradlew installDebug
```


Application follows MVP pattern and was split to following modules:

#### Model 
Here we put the logic where to fetch/store data. API layer is implemented
 with **Retrofit** and **OkHttp** libraries.
 
Data stored locally using [Realm for Java](https://realm.io/docs/java/latest/) as simple, yet 
powerful way to persist data.

Model layer uses DataManager as facade to interact with **Presenters**. 

#### View 

Views are implemented using Android API. For list rendering used ViewHolder 
pattern and RecycleView. Beer icons are found on internet.

In the same package with **Views** we put **Presenters** where we put most of 
logic to pass data from **View** to **Model**.   

Here used some [RxJava2](https://github.com/ReactiveX/RxJava) 
to orchestrate background task execution. 
  

#### DI configuration

In order to keep application component decoupled Dagger 2 was used and in
separate module we configured components, scopes and modules.
 
 
 
#### <a name="pookie">Here is app no network sample (2 gifs at the same time don't look great)</a>
![App No network sample](https://raw.githubusercontent.com/dev-tim/punk-beers/master/gifs/no_network.gif "App no network sample")


