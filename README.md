# PathFinderMP

Simple experimental multiplatform project using Kotlin Multiplatform.

The application implements algorithms for shortest path finding in a two-dimensional map (maze).

### Project modules

The project consists of the following submodules:

##### pathfinderlib

Common module that contains multiplatform (common, ios, jvm) implementations of the core application logic and presentation layer for the single screen.

The following pathfinding algorithms are currently implemented:

- Lee algorithm (Wave algorithm)
- Dijkstra's algorithm

##### Platform submodules

The application is implemented for the following platforms:

- **androidApp** - Android implementation of the application. Uses the jvm implementation of **pathfinderlib** submodule.

- **cliApp** - JVM implementation of the application (for testing purposes). Uses the jvm implementation of **pathfinderlib** submodule. Console is used for output.

- **iosApp** - iOS implementation of the application. Uses the ios implementation of **pathfinderlib** submodule.

### Screenshots

Screenshot for android app:

![PathFinderMP androidApp](docs/screenshot_1.png)

Screenshot for iOS app:

![PathFinderMP androidApp](docs/screenshot_2.png)

### Contributors

* [tgrid0](https://github.com/tgrid0)
* [AppoNut](https://github.com/AppoNut/)
* [tetraquark](https://github.com/Tetraquark)