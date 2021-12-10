import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    jacoco
}

group = "io.chibana"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("junit:junit:4.13.2")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.6.1")

    developmentOnly("org.springframework.boot:spring-boot-devtools:2.6.1")
    runtimeOnly("com.h2database:h2:2.0.202")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.1")
    testImplementation("io.cucumber:cucumber-junit:7.1.0")
    testImplementation("io.cucumber:cucumber-java8:7.1.0")
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations {}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

task("cucumber") {
    dependsOn("assemble", "compileTestKotlin")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            args = listOf(
                "--plugin", "pretty",
                "--plugin", "html:$buildDir/results/jacoco/cucumber-report.html",
                "--glue", "io.chibana.user.steps",
                "src/test/resources/features")

            val jacocoAgent = zipTree(configurations.jacocoAgent.get().singleFile)
                .filter { it.name == "jacocoagent.jar" }
                .singleFile
            jvmArgs = listOf("-javaagent:$jacocoAgent=destfile=$buildDir/results/jacoco/cucumber.exec,append=false")

        }
    }
}


tasks.jacocoTestReport {
    // Give jacoco the file generated with the cucumber tests for the coverage.
    executionData(files("$buildDir/jacoco/test.exec", "$buildDir/results/jacoco/cucumber.exec"))
    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}