package ru.clevertec.gradleplugin

class AnalyticalMetricsExtension {
    private String aspectjVersion;
    String aspectSrcDir;
    private String fromDirPath;
    private String toDirPath;
    private String greeting;

    String getAspectjVersion() {
        return aspectjVersion
    }

    void setAspectjVersion(String aspectjVersion) {
        this.aspectjVersion = aspectjVersion
    }

    String getAspectSrcDir() {
        return aspectSrcDir
    }

    void setAspectSrcDir(String aspectSrcDir) {
        this.aspectSrcDir = aspectSrcDir
    }

    String getFromDirPath() {
        return fromDirPath
    }

    void setFromDirPath(String fromDirPath) {
        this.fromDirPath = fromDirPath
    }

    String getToDirPath() {
        return toDirPath
    }

    void setToDirPath(String toDirPath) {
        this.toDirPath = toDirPath
    }

    String getGreeting() {
        return greeting
    }

    void setGreeting(String greeting) {
        this.greeting = greeting
    }
}
