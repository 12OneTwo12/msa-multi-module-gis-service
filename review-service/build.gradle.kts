plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jetbrains.kotlin.kapt") version "1.9.25"
    id("org.jooq.jooq-codegen-gradle") version "3.20.1"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val springCloudVersion: String by lazy {
    rootProject.properties.getOrElse("springCloudVersion") { "2024.0.0" } as String
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // JOOQ 기본 런타임 라이브러리
    implementation("org.jooq:jooq:3.20.1")

    implementation("org.springframework.boot:spring-boot-starter-jooq") // jooq, Spring과 통합하는 경우 추가

    // Spring 트랜잭션 관리
    implementation("org.springframework:spring-tx")

    // JOOQ 코드 생성 관련 라이브러리
    jooqCodegen("org.jooq:jooq-meta:3.20.1")
    jooqCodegen("org.jooq:jooq-codegen:3.20.1")
    // DDLDatabase 지원 라이브러리 추가
    jooqCodegen("org.jooq:jooq-meta-extensions:3.20.1")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    configuration {
        generator {
            name = "org.jooq.codegen.KotlinGenerator"

            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase" // DDL 파일 사용 설정
                properties {
                    property {
                        key = "scripts"
                        value = "src/main/resources/sql/create-table-sql.sql" // DDL 파일 경로
                    }
                    property {
                        key = "sort"
                        value = "semantic" // 테이블 참조 관계 정렬
                    }
                    property {
                        key = "unqualifiedSchema"
                        value = "true" // 스키마 이름 제거
                    }
                }
            }
            generate {
                isPojos = true  // Kotlin 데이터 클래스로 생성
                isDaos = true   // DAO 클래스 생성
            }
            target {
                packageName = "com.onetwo.reviewservice.jooq.generated" // JOOQ 코드 저장 경로
                directory = "src/main/generated"
            }
        }
    }
}

sourceSets {
    main {
        kotlin {
            srcDirs("src/main/generated") // JOOQ 코드가 있는 폴더 소스 코드로 추가
        }
    }
}
