
package com.oahcfly.chgame.util.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * 
 * <pre>
 * A星寻路算法
 * 
 * date: 2014-11-13
 * </pre>
 * @author caohao
 */
public class CHAStar {
    private final int COST_STRAIGHT = 10;//垂直方向或水平方向移动的路径评分

    private final int COST_DIAGONAL = 14;//斜方向移动的路径评分

    private Vector2 startVector2, endVector2;

    private int[][] mapArray;

    private ArrayList<SearchNode> openList, closedList;

    private SearchNode startNode, endNode;

    public CHAStar(Vector2 startPoint, Vector2 endPoint, int[][] map) {
        this.startVector2 = startPoint;
        this.endVector2 = endPoint;
        this.mapArray = map;
        openList = new ArrayList<SearchNode>();
        closedList = new ArrayList<SearchNode>();
        startNode = new SearchNode(startVector2);
        endNode = new SearchNode(endVector2);
    }

    public ArrayList<SearchNode> doSearch() {
        startNode.setGValue(COST_STRAIGHT);
        startNode.setHValue(getDistance((int)startNode.pointVector2.x, (int)startNode.pointVector2.y,
                (int)endNode.pointVector2.x, (int)endNode.pointVector2.y));
        //将起始点放入OpenList中；
        openList.add(startNode);
        SearchNode finalNode = null;
        //待检测队列中不空 && 目标节点不在OpenList中
        while (openList.size() > 0 && !openList.contains(endNode)) {
            //获取权值最小节点为当前节点p，当前节点从OpenList中移除；
            doSort(openList);
            SearchNode minFNode = openList.get(0);
            if (isSameNode(minFNode, endNode)) {
                // 到达终点
                finalNode = minFNode;
                break;
            }

            ArrayList<SearchNode> neighourNodeList = getNeighourNodes(minFNode);
            for (SearchNode neighourNode : neighourNodeList) {
                if (closedList.contains(neighourNode)) {
                    // 已经访问过了存在于closeList
                    continue;
                }
                if (!isPassed(neighourNode)) {
                    // 不可通过
                    closedList.add(neighourNode);
                    continue;
                }

                int indexOfNeighour = indexOfOpenList(neighourNode);
                //adj不在OpenList中
                if (indexOfNeighour == -1) {
                    openList.add(neighourNode);
                } else {
                    // If （OpenList中adj的旧的H值 > 当前路径中adj的H值）
                    if (openList.get(indexOfNeighour).getHValue() > neighourNode.getHValue()) {
                        //修正OpenList中adj的父节点为当前接节点p；
                        openList.get(indexOfNeighour).setParentNode(minFNode);
                        //修正OpenList中adj的H值为当前路径中adj的H值；
                        openList.get(indexOfNeighour).setHValue(neighourNode.getHValue());
                    }
                }
            }

            openList.remove(minFNode);
            closedList.add(minFNode);
        }

        ArrayList<SearchNode> resultList = new ArrayList<SearchNode>();
        getPath(resultList, finalNode);

        for (SearchNode node : resultList) {
            System.out.println("point:" + node.pointVector2.x + "," + node.pointVector2.y);
        }
        
        return resultList;
    }

    //从终点往返回到起点
    private void getPath(List<SearchNode> resultList, SearchNode node) {
        if (node.getParentNode() != null) {
            getPath(resultList, node.getParentNode());
        }
        resultList.add(node);
    }

    /**
     * 
     * <pre>
     * 节点是否可通过
     * 
     * date: 2014-11-13
     * </pre>
     * @author caohao
     * @param neighourNode
     * @return
     */
    private boolean isPassed(SearchNode neighourNode) {
        int x = (int)neighourNode.pointVector2.x;
        int y = (int)neighourNode.pointVector2.y;
        return this.mapArray[x][y] == 1;
    }

    private boolean isSameNode(SearchNode node1, SearchNode node2) {
        if (node1.pointVector2.x == node2.pointVector2.x && node1.pointVector2.y == node2.pointVector2.y) {
            return true;
        }
        return false;
    }

    /**
     * 
     * <pre>
     * node是否在openList
     * 
     * date: 2014-11-13
     * </pre>
     * @author caohao
     * @param node
     * @return -1代表不在列表内
     */
    private int indexOfOpenList(SearchNode node) {
        for (SearchNode openNode : openList) {
            if (openNode.pointVector2.x == node.pointVector2.x && openNode.pointVector2.y == node.pointVector2.y) {
                return openList.indexOf(openNode);
            }
        }
        return -1;
    }

    private ArrayList<SearchNode> getNeighourNodes(SearchNode minFNode) {
        int endX = (int)endNode.pointVector2.x;
        int endY = (int)endNode.pointVector2.y;
        int cX = (int)minFNode.pointVector2.x;
        int cY = (int)minFNode.pointVector2.y;

        ArrayList<SearchNode> nodeList = new ArrayList<SearchNode>();
        // 上
        if (cY - 1 >= 0) {
            SearchNode searchNode = new SearchNode(new Vector2(cX, cY - 1));
            searchNode.setParentNode(minFNode);
            searchNode.setGValue(COST_STRAIGHT+minFNode.getGValue());
            searchNode.setHValue(getDistance(cX, cY - 1, endX, endY));
            nodeList.add(searchNode);
        }
        // 下
        if (cY + 1 <= endY) {
            SearchNode searchNode = new SearchNode(new Vector2(cX, cY + 1));
            searchNode.setParentNode(minFNode);
            searchNode.setGValue(COST_STRAIGHT+minFNode.getGValue());
            searchNode.setHValue(getDistance(cX, cY + 1, endX, endY));
            nodeList.add(searchNode);
        }
        // 左
        if (cX - 1 >= 0) {
            SearchNode searchNode = new SearchNode(new Vector2(cX - 1, cY));
            searchNode.setParentNode(minFNode);
            searchNode.setGValue(COST_STRAIGHT+minFNode.getGValue());
            searchNode.setHValue(getDistance(cX - 1, cY, endX, endY));
            nodeList.add(searchNode);
        }
        // 右
        if (cX + 1 <= endX) {
            SearchNode searchNode = new SearchNode(new Vector2(cX + 1, cY));
            searchNode.setParentNode(minFNode);
            searchNode.setGValue(COST_STRAIGHT+minFNode.getGValue());
            searchNode.setHValue(getDistance(cX + 1, cY, endX, endY));
            nodeList.add(searchNode);
        }
        return nodeList;
    }

    /**
     * 
     * <pre>
     * 两点间距离
     * 
     * date: 2014-11-13
     * </pre>
     * @author caohao
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private int getDistance(int x1, int y1, int x2, int y2) {
        int a = (x1 - x2) * (x1 - x2);
        int b = (y1 - y2) * (y1 - y2);
        return (int)Math.sqrt(a + b);
    }

    /**
     * 
     * <pre>
     * F值从小到大排序
     * 
     * date: 2014-11-13
     * </pre>
     * @author caohao
     * @param nodeList
     */
    private void doSort(ArrayList<SearchNode> nodeList) {
        Collections.sort(nodeList, new Comparator<SearchNode>() {

            @Override
            public int compare(SearchNode node1, SearchNode node2) {
                if (node1.getFValue() > node2.getFValue()) {
                    return 1;
                } else if (node1.getFValue() < node2.getFValue()) {
                    return -1;
                }
                return 0;
            }
        });
    }
}
