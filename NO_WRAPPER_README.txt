This project intentionally does NOT include a Gradle wrapper.

- GitHub Actions builds using the "gradle" command via gradle/actions/setup-gradle.
- If you want to build locally, install Java 17 and Gradle, then run:

  gradle clean build

The built jar will be in build/libs/
