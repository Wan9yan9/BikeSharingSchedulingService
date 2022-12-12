package com.project.schedule.entity.map;

public class Map {
    // 坐标矩阵
    public int[][] maps;

    // 地图宽度
    public int width;

    // 地图高度
    public int height;

    public Map(int[][] maps, int width, int height) {
        this.maps = maps;
        this.width = width;
        this.height = height;
    }

    public Map() {
    }

    public int[][] getMaps() {
        return maps;
    }

    public void setMaps(int[][] maps) {
        this.maps = maps;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
