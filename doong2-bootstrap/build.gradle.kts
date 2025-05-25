dependencies {
    implementation(project(":doong2-http"))

    implementation("org.springframework.boot:spring-boot-autoconfigure")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    mainClass.set("wyship.doong2.bootstrap.WonyoungshipApplicationKt")
}
