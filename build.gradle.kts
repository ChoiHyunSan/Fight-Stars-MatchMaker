plugins {
    id("java")
}

group = "com.ll"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Redis (Lettuce)
    implementation("io.lettuce:lettuce-core:6.3.0.RELEASE")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")

    // WebClient
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // 로그
    implementation("org.slf4j:slf4j-simple:2.0.9")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}

tasks.test {
    useJUnitPlatform()
}
