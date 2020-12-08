package ru.clevertec.gradleplugin


import org.gradle.api.tasks.AbstractCopyTask
import org.gradle.api.tasks.Copy

class UnzipJarTask extends Copy {


    @Override
    AbstractCopyTask from(Object... sourcePath) {
        AnalyticalMetricsExtension extension = getProject().getExtensions().getByType(AnalyticalMetricsExtension.class);
        System.out.println("fromDirPath is " + extension.getFromDirPath());
        System.out.println("toDirPath is " + extension.getToDirPath());
        System.out.println("aspectjVersion is " + extension.getAspectjVersion());
        System.out.println("greeting is " + extension.getGreeting());

        def ant = new AntBuilder()

        ant.unzip(src: extension.getFromDirPath(),
                dest: "build/classes/java/main/ru/clevertec/aop/aspect",
                overwrite: "false")
        return super.from(sourcePath);
    }

    @Override
    AbstractCopyTask into(Object destDir) {
        return super.into(destDir);
    }
}
