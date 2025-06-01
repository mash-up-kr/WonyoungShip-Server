import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    application
}

dependencies {
    implementation(project(":doong2-http"))
    implementation(project(":doong2-external"))
    implementation(project(":doong2-persistence"))
    implementation(project(":doong2-core"))
    implementation(project(":doong2-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}

tasks {
    bootJar {
        enabled = true
    }

    named("bootJar") {
        dependsOn("ktlintFormat")
    }

    getByName<BootJar>("bootJar") {
        mainClass.set("wyship.doong2.bootstrap.WonyoungshipApplicationKt")
    }

    getByName<BootBuildImage>("bootBuildImage") {

        imageName.set("wonyoungship/doong2")

        // Build 관련 환경 변수 설정
        environment.set(
            mapOf(
                "BP_JVM_VERSION" to "21",
                "BPE_SPRING_PROFILES_ACTIVE" to "prod",
                "BPE_JAVA_TOOL_OPTIONS" to
                    buildString {
                        // Container JVM
                        append("-XX:+UseContainerSupport ")
                        // Heap 메모리 설정 2G
                        append("-Xms512M -Xmx512M ")
                        // OOM시 Heap Dump
                        append("-XX:+HeapDumpOnOutOfMemoryError ")
                        // OOM시 Heap Dump 로그 생성 경로 (파일 이름 : 발생한 시각)
                        append("-XX:HeapDumpPath=/root/heapDump/%Y%m%d_%H%M%S.hprof ")
                        // 중복 문자열 제거로 메모리 절약
                        append("-XX:+UseStringDeduplication ")
                        // OOM시 애플리케이션 즉시 종료
                        append("-XX:+ExitOnOutOfMemoryError ")
                        // Encoding
                        append("-Dfile.encoding=UTF-8 ")
                    }
            )
        )

        // Docker Registry 로그인 정보
        docker {
            publishRegistry {
                url.set("docker.io")
                username.set(System.getenv("DOCKER_HUB_USERNAME"))
                password.set(System.getenv("DOCKER_HUB_PASSWORD"))
            }
        }

        // 빌드 후 이미지를 레지스트리에 자동 푸시
        publish.set(true)
    }
}

springBoot {
    buildInfo()
}
