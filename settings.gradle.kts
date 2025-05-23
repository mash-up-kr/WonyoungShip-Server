plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "wonyoungship"

include("doong2-http")
include("doong2-bootstrap")
include("doong2-external")
include("doong2-persistence")
include("doong2-core")
include("doong2-common")
