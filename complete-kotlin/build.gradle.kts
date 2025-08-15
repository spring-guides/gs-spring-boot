plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}
dependencies {
    // tag::actuator[]
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // end::actuator[]
    implementation("org.springframework.boot:spring-boot-starter-web")
    // tag::tests[]
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // end::tests[]
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
