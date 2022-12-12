package com.project.schedule.algo;

import com.project.schedule.entity.map.Coord;
import com.project.schedule.entity.map.MapInfo;
import com.project.schedule.entity.map.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathAlgo {
    /**
     * 障碍值
     */
    public final static int BAR = 1;

    /**
     * 路径值
     */
    public final static int PATH = 2;

    /**
     * 横竖移动代价
     */
    public final static int DIRECT_VALUE = 10;

    // 优先队列（升序）
    Queue<Node> openList = new PriorityQueue<Node>();

    // 以关闭节点列表
    List<Node> closeList = new ArrayList<Node>();

    /**
     * 判断结点是否是最终结点
     *
     * @param end 终点坐标
     * @param coord 当前点坐标
     * @return 是/否
     */
    private boolean isEndNode(Coord end, Coord coord)
    {
        return coord != null && end.equals(coord);
    }

    /**
     * 判断结点能否放入Open列表
     *
     * @param mapInfo map信息
     * @param x x坐标
     * @param y y坐标
     * @return 能：是/不能：否
     */
    private boolean canAddNodeToOpen(MapInfo mapInfo, int x, int y)
    {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.height) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.maps[y][x] == BAR) return false;
        // 判断结点是否存在close表
        if (isCoordInClose(x, y)) return false;

        return true;
    }

    /**
     * 判断坐标是否在close表中
     *
     * @param coord 坐标点
     * @return 是/否
     */
    private boolean isCoordInClose(Coord coord)
    {
        return coord!=null&&isCoordInClose(coord.x, coord.y);
    }

    /**
     * 判断坐标是否在close表中
     *
     * @param x x坐标
     * @param y y坐标
     * @return 是/否
     */
    private boolean isCoordInClose(int x, int y)
    {
        if (closeList.isEmpty()) return false;
        for (Node node : closeList)
        {
            if (node.coord.x == x && node.coord.y == y)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算代价估值
     *
     * @param end 终点
     * @param coord 当前坐标点
     * @return 估值H
     */
    private int calcH(Coord end,Coord coord)
    {
        return Math.abs(end.x - coord.x) + Math.abs(end.y - coord.y);
    }

    /**
     * 域open列表中检索节点
     *
     * @param coord 坐标
     * @return 节点
     */
    private Node findNodeInOpen(Coord coord)
    {
        if (coord == null || openList.isEmpty()) return null;
        for (Node node : openList)
        {
            if (node.coord.equals(coord))
            {
                return node;
            }
        }
        return null;
    }

    /**
     * 添加所有邻结点到open表
     *
     * @param mapInfo map信息
     * @param current 当前节点
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
    {
        int x = current.coord.x;
        int y = current.coord.y;
        // 左
        addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
        // 上
        addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
        // 右
        addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
        // 下
        addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
    }

    /**
     * 添加一个邻结点到open表
     *
     * @param mapInfo 地图信息
     * @param current 当前节点
     * @param x 邻结点x
     * @param y 邻结点y
     * @param value 代价估值
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value)
    {
        if (canAddNodeToOpen(mapInfo,x, y))
        {
            Node end=mapInfo.end;
            Coord coord = new Coord(x, y);
            int G = current.G + value; // 计算邻结点的G值
            Node child = findNodeInOpen(coord);
            if (child == null)
            {
                int H=calcH(end.coord,coord); // 计算H值
                if(isEndNode(end.coord,coord))
                {
                    child=end;
                    child.parent=current;
                    child.G=G;
                    child.H=H;
                }
                else
                {
                    child = new Node(coord, current, G, H);
                }
                openList.add(child);
            }
            else if (child.G > G)
            {
                child.G = G;
                child.parent = current;
                // 重新调整堆
                openList.add(child);
            }
        }
    }

    /**
     * 绘制路径并返回路径长度
     *
     * @param maps map坐标矩阵
     * @param end 终止节点
     * @return 路径长度
     */
    private int drawPath(int[][] maps, Node end)
    {
        int pathLength = -1;
        if(end==null||maps==null) return pathLength;
        while (end != null)
        {
            Coord c = end.coord;
            maps[c.y][c.x] = PATH;
            end = end.parent;
            pathLength++;
        }
        return pathLength;
    }


    /**
     * 开始根据地图信息绘制路径并接收路径长度
     *
     * @param mapInfo 地图信息
     * @return 路径长度
     */
    public int start(MapInfo mapInfo)
    {
        if(mapInfo==null) return -1;
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.start);
        return moveNodes(mapInfo);
    }

    /**
     * 移动节点计算路径
     *
     * @param mapInfo map信息
     * @return 路径长度
     */
    private int moveNodes(MapInfo mapInfo)
    {
        while (!openList.isEmpty())
        {
            Node current = openList.poll();
            closeList.add(current);
            addNeighborNodeInOpen(mapInfo,current);
            if (isCoordInClose(mapInfo.end.coord)) // bug修正
            {
                return drawPath(mapInfo.maps, mapInfo.end);
            }
        }
        return -1;
    }


}
