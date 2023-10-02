package com.castlabs.fileanalyser.provider;

public interface MediaFileProvider {
    byte[] fetchFileContent(String url);
}
