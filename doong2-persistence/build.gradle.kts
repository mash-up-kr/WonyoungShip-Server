plugins {
    kotlin("plugin.jpa") version "1.9.25"
}

dependencies {
    implementation(project(":doong2-core"))
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
