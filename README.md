# Android Sample

[![kotlin](https://img.shields.io/badge/Kotlin-1.4.xx-blue)](https://kotlinlang.org/) [![Dagger](https://img.shields.io/badge/Dagger-Hilt-orange)](https://dagger.dev/hilt)


:construction: Under construction! :construction:


Sample App for use and practise of different libraries.

Includes:
 * Sample feature list of Beers from Punk API.
 * Samples of UI testing and Unit testing.

### UI tests

Under androidTest folder in the app module there are UI tests for the app happy path.
Here is the example of two tests for online and offline cases placed inside `MainActivityTest.kt`

```Kotlin    
    @Test
    fun searchList() {
        robot.turnOnInternetConnections()
        robot.fillEditTextAndApply(R.id.search_et, "vlad")
        robot.doOnView(withText("Hello My Name is Vladimir"), ViewActions.click())
        robot.assertOnView(withId(R.id.beer_cl))
    }


    /**
     * Check that without Internet the Ranking shows previous beers involved in battles
     */
    @Test
    fun offlineSearchList() {
        robot.turnOffInternetConnections()
        robot.fillEditTextAndApply(R.id.search_et, "vlad")
        robot.assertOnView(withText(R.string.dialog_offline))
        robot.turnOnInternetConnections()
    }
```


## Features and Libraries
* Clean Architecture with MVVM
* Single Activity Design
* Jetpack Navigation
* Dagger Hilt
* Kotlin Gradle DSL
* Room
* Lifecycle
* [Punk API](https://punkapi.com/documentation/v2)

## Libraries
*   [Dagger Hilt](https://dagger.dev/hilt)
*   [Hilt Jetpack](https://developer.android.com/training/dependency-injection/hilt-jetpack)
*   [RxJava 3](https://github.com/ReactiveX/RxJava)
*   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
*   [DFM navigation](https://developer.android.com/guide/navigation)
*   [Constraint Layout](https://developer.android.com/training/constraint-layout)
*   [Gradle](https://docs.gradle.org)
*   [Room](https://developer.android.com/topic/libraries/architecture/room)
*   [Retrofit](https://square.github.io/retrofit). A type-safe HTTP client for Android and Java
*   [Timber](https://github.com/JakeWharton/timber)
*   [Glide](https://github.com/bumptech/glide)
*   [Mockito](https://github.com/mockito/mockito)
*   [LeakCanary](https://square.github.io/leakcanary). Memory leak detection library for Android

## Structure
* **Presentation**: Model View View Model pattern from the base sample.
* **Domain**: Holds all business logic. The domain layer starts with classes named *use cases* used by the application presenters. These *use cases* represent all the possible actions a developer can perform from the presentation layer.
* **Repository**: Repository pattern from the base sample.

## Author
[Miguel González Pérez](https://github.com/Miguelos)

## License
	Copyright 2020 Miguel González Pérez

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
