plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.60"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    // Maven publish
    `maven-publish`

    // Bintray
    id("com.jfrog.bintray") version "1.8.4"
}

val kotlinVersion = "1.3.60"
val libraryVersion = "0.1.0"

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Kotest
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")

    // SLF4J
    implementation("org.slf4j:slf4j-simple:1.7.30")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("kimage") {
            from(components["java"])
            groupId = "io.mattmoore"
            artifactId = "kimage"
            version = libraryVersion
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("kimage")
    pkg.apply {
        repo = "kimage"
        name = "kimage"
        setLicenses("MIT")
        vcsUrl = "https://github.com/mattmoore/kimage.git"
        version.apply {
            name = libraryVersion
        }
    }
}
