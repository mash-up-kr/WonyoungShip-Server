import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    application
}

dependencies {
    implementation(project(":doong2-http"))
    implementation(project(":doong2-external"))
    implementation(project(":doong2-persistence"))
    implementation(project(":doong2-core"))
    implementation(project(":doong2-common"))
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
                "BPL_JVM_HEAD_ROOM" to "0", // HEAD_ROOM 제거
                "BP_JVM_THREAD_COUNT" to "100", // 스레드 수 제한
                "BPE_SPRING_PROFILES_ACTIVE" to "prod",
                "BPE_JAVA_TOOL_OPTIONS" to
                    buildString {
                        append("-XX:+UseContainerSupport ")
                        append("-Xss256k ")
                        append("-XX:ReservedCodeCacheSize=64M ")
                        append("-XX:MaxMetaspaceSize=64M ")
                        append("-XX:MaxDirectMemorySize=10M ")
                        append("-XX:+HeapDumpOnOutOfMemoryError ")
                        append("-XX:HeapDumpPath=/root/heapDump/%Y%m%d_%H%M%S.hprof ")
                        append("-XX:+UseStringDeduplication ")
                        append("-XX:+ExitOnOutOfMemoryError ")
                        append("-Dfile.encoding=UTF-8 ")
                    },
            ),
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
