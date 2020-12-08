package ru.clevertec.gradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class AnalyticalMetricsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

            UnzipJarTask unzipTask = project.getTasks().create('unzipJarTask', UnzipJarTask)

        def extension = project.extensions.create("analyticalMetrics", AnalyticalMetricsExtension)
            extension.with {
                aspectjVersion = '1.9.4';
                fromDirPath = "lib/analytics-api-0.0.1-SNAPSHOT.jar";
                toDirPath = 'build/classes/java/main/ru/clevertec/aop/aspect';
                greeting = "Hello, my colleges";
            }

            project.configurations.create("ajc");
            project.configurations.create("runtimeAgent");
            project.dependencies {
                compile "org.aspectj:aspectjrt:$extension.aspectjVersion"
                compile "org.aspectj:aspectjweaver:$extension.aspectjVersion"
                ajc "org.aspectj:aspectjtools:$extension.aspectjVersion"
                runtimeAgent "org.aspectj:aspectjweaver"
            };

            def aspectj = { sourceFileSet, destDir ->
                project.ant.taskdef(
                        resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                        classpath: project.configurations.ajc.asPath
                )
                project.ant.iajc(maxmem: "1024m",
                        fork: "true",
                        Xlint: "warning",
                        classpath: sourceFileSet.runtimeClasspath.asPath,
                        destDir: destDir,
                        source: project.sourceCompatibility,
                        target: project.targetCompatibility) {
                    sourceroots {
                        sourceFileSet.java.srcDirs.each { dir ->
                            if (project.file(dir).exists()) {
                                pathelement(path: dir)
                            }
                        }
                    }
                }
            }

            project.sourceSets {
                aspects {
                    java {
                        srcDir "build/classes/java/main/ru/clevertec/aop/aspect"
                        include '**/*.aj'

                        compileClasspath = project.sourceSets.main.compileClasspath
                        runtimeClasspath = project.sourceSets.main.runtimeClasspath
                    }
                }
            }

            project.tasks.withType(JavaCompile) {
                doLast {
                    unzip(unzipTask, "$extension.fromDirPath", "$extension.toDirPath")
                    aspectj(project.sourceSets.aspects,
                            "$project.buildDir/classes/java/main")
                }
            }
    }

    private unzip(UnzipJarTask unzipTask, String from, String into) {
        unzipTask.from from
        unzipTask.into into
    }
}
