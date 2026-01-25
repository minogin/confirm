# confirm
Kotlin Deep Assertion library

## Gradle commands

# Stop all daemons

./gradlew --stop

# Debug build with in-process Kotlin compiler

./gradlew :play:classes -Dorg.gradle.debug=true --rerun-tasks


-Pkotlin.compiler.execution.strategy=in-process \
--no-daemon \
--rerun-tasks \
--no-build-cache

# Build

./gradlew :play:classes --rerun-tasks

-Pkotlin.compiler.execution.strategy=in-process \
--no-daemon \
--rerun-tasks \
--no-build-cache

# Run

./gradlew :play:run
