
package com.oahcfly.chgame.util.astar;

import com.badlogic.gdx.math.Vector2;

/**
 * 
 * <pre>
 * 用于搜索的节点
 * 
 * date: 2014-11-13
 * </pre>
 * @author caohao
 */
public class SearchNode {
    public SearchNode(Vector2 vector2) {
        setPointVector2(vector2);
    }

    Vector2 pointVector2;

    SearchNode parent;

    // 起点到当前点
    int gValue = 0;

    // 当前点到终点 估计值
    int hValue = 0;

    public Vector2 getPointVector2() {
        return pointVector2;
    }

    public void setPointVector2(Vector2 pointVector2) {
        this.pointVector2 = pointVector2;
    }

    public SearchNode getParentNode() {
        return parent;
    }

    public void setParentNode(SearchNode parent) {
        this.parent = parent;
    }

    public int getGValue() {
        return gValue;
    }

    public void setGValue(int gValue) {
        this.gValue = gValue;
    }

    public int getHValue() {
        return hValue;
    }

    public void setHValue(int hValue) {
        this.hValue = hValue;
    }

    public int getFValue() {
        return gValue + hValue;
    }
}
