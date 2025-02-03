import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
	groovy
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.12"
	id("com.diffplug.spotless") version "7.0.2"
	id("com.github.ben-manes.versions") version "0.52.0"
}

group = "net.frey"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("com.mysql:mysql-connector-j")
	implementation("org.flywaydb:flyway-mysql")
	implementation("org.flywaydb:flyway-core")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-core:2.4-M5-groovy-4.0")
	testImplementation("org.spockframework:spock-spring:2.4-M5-groovy-4.0")
	testImplementation("com.h2database:h2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

fun isNonStable(version: String): Boolean {
	val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
	val regex = "^[0-9,.v-]+(-r)?$".toRegex()
	val isStable = stableKeyword || regex.matches(version)
	return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
	rejectVersionIf {
		isNonStable(candidate.version)
	}
}

tasks.wrapper {
	gradleVersion = "8.12.1"
	distributionType = Wrapper.DistributionType.ALL
}

spotless {
	java {
		palantirJavaFormat()
	}
}

tasks.named("compileJava") {
	dependsOn("spotlessApply")
}