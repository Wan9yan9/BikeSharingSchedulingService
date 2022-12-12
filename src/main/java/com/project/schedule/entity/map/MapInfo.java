package com.project.schedule.entity.map;

public class MapInfo extends Map {
    // 起始结点
    public Node start;

    // 最终结点
    public Node end;

    public MapInfo(int[][] maps, int width, int height, Node start, Node end)
    {
        this.maps = maps;
        this.width = width;
        this.height = height;
        this.start = start;
        this.end = end;
    }

    public MapInfo(Map map, Node start, Node end)
    {
        super(map.getMaps(),map.getWidth(),map.getHeight());
        this.start = start;
        this.end = end;
    }
}
