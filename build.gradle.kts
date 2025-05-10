import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "kr.mashup"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	getByName<BootJar>("bootJar") {
		mainClass.set("kr.mashup.WonyoungshipApplicationKt") //todo. architecture 이후 다시
	}

	getByName<BootBuildImage>("bootBuildImage") {
		// Docker 이미지 이름 설정
		imageName.set("${System.getenv("DOCKER_HUB_REGISTRY")}/doong2") //todo. env 세팅

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
							append("-Dfile.encoding=UTF-8")
						},
			),
		)

		// Docker Registry 로그인 정보
		docker {
			publishRegistry {
				url.set(System.getenv("DOCKER_HUB_REGISTRY"))
				username.set(System.getenv("DOCKER_HUB_USERNAME")) //todo. public 이면 노필요
				password.set(System.getenv("DOCKER_HUB_PASSWORD"))
			}
		}

		// 빌드 후 이미지를 레지스트리에 자동 푸시
		publish.set(true)
	}
}
