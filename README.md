# confirm
Kotlin Deep Assertion library

./gradlew --stop

./gradlew :play:classes -Dorg.gradle.debug=true --no-daemon --rerun-tasks --no-build-cache

./gradlew :play:classes \
-Dorg.gradle.debug=true \
-Pkotlin.compiler.execution.strategy=in-process \
--no-daemon \
--rerun-tasks \
--no-build-cache