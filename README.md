# MoviesList

Android sample app that shows a catalog of movies and lets the user open a details screen for each one. Movie data is fetched from a static `movies.json` file hosted on GitHub Gist — no API key or account is needed to build or run the app.

## Features

- Movies list shown in a grid (3 columns) with poster thumbnails loaded by Picasso
- Paged list loading built on the Android Paging library
- Movie details screen: title, genres, release date, poster, overview, average rating, vote count, original title, popularity, adult flag, language, and trailer playback (BetterVideoPlayer)
- Pull-to-refresh on both the list and the details screen (forces a network reload)
- Loading and error states on both screens
- Offline support: movies are saved to a file cache on disk (with expiration) and served from it while fresh; OkHttp also gets a 10 MB HTTP cache
- Custom SSL trust store built from certificates bundled in the app assets

## Tech stack

- Kotlin 1.3.41, Android Gradle Plugin 3.5.0, Gradle wrapper 5.4.1
- minSdk 19, targetSdk / compileSdk 29, applicationId `ru.sample.movies`
- UI: AndroidX AppCompat 1.1.0, core-ktx 1.1.0, Material 1.0.0, ConstraintLayout 1.1.3
- Presentation pattern: MVP with Moxy 1.4.5
- Dependency injection: Dagger 2 (2.8)
- Networking: Retrofit 2.4.0 with Gson converter and RxJava2 adapter, OkHttp logging interceptor 4.2.1
- Reactive: RxJava 2 / RxAndroid
- Images: Picasso 2.71828
- Paging: `android.arch.paging:runtime` 1.0.1
- Video: BetterVideoPlayer (`kotlin-SNAPSHOT` via JitPack)
- Testing: JUnit 4.12, Mockito (core 1.9.5, mockito-inline 2.8.9, mockito-kotlin 1.6.0), MockWebServer 3.10.0, Robolectric 3.1.1, AssertJ 1.7.1

## Architecture

Clean Architecture with three Gradle modules (`presentation` depends on `data` and `domain`; `data` uses `domain` via `compileOnly`):

| Module | Type | Responsibility |
| --- | --- | --- |
| `presentation` | Android application | Activities, fragments, Moxy presenters and views, Dagger components/modules, navigation |
| `domain` | Android library | Entities (`Movie`, `Genre`, `MoviesPage`), use cases (`GetMoviesPage`, `GetMovieDetails`), repository interface, executor abstractions |
| `data` | Android library | Retrofit `MoviesApi`, `MoviesRepository` implementation, cloud/disk data stores, file cache |

`MoviesRepository` chooses between a cloud data store (Retrofit) and a disk data store (file cache) based on cache freshness; presenters pass a `syncWithHost` flag so pull-to-refresh always goes to the network.

## Tests

The `data` module has unit tests (Mockito, MockWebServer, Robolectric, AssertJ) covering `MoviesApi`, `MoviesRepository` and the data-store classes. The `domain` and `presentation` modules contain only the generated template test stubs.

## Build & run

- Open the project in Android Studio, or build from the command line with the included wrapper: `./gradlew assembleDebug` (Windows: `gradlew.bat assembleDebug`).
- No configuration or API keys are required — the app downloads its data from a public JSON file.

## Status

Personal learning/test project. Last commit: 2019-11-25. The project uses 2019-era libraries (including the now-deprecated `android.arch.paging`) and is not actively maintained.
