package com.hmj.demo.coolweather.event;

public class TitleEvent {
    private int level;
    private String title;

    public TitleEvent(int level, String title) {
        this.level = level;
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }
}
