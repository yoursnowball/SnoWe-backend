import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
    kotlin("kapt") version "1.5.31"
}

group = "com.snowman"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("com.querydsl:querydsl-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.jsonwebtoken:jjwt-api:0.10.5")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.google.firebase:firebase-admin:6.8.1")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.10.7")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.10.7")
    runtimeOnly("mysql:mysql-connector-java")
    kapt("com.querydsl:querydsl-apt:4.2.2:jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

val querydslDir = "$buildDir/generated/source/kapt/main"

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}

sourceSets["main"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir(querydslDir)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
