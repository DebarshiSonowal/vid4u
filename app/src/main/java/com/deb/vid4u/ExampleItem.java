package com.deb.vid4u;

import android.view.LayoutInflater;

public class ExampleItem {
    private String name;
    private Integer download,upload;

    public ExampleItem(String name, Integer download, Integer upload) {
        this.name = name;
        this.download = download;
        this.upload = upload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }
}
