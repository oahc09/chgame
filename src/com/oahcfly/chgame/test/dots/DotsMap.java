
package com.oahcfly.chgame.test.dots;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * <pre>
 * 网格地图
 * 
 * date: 2015-1-1
 * </pre>
 * @author caohao
 */
public class DotsMap {
    // 行-y，列-x
    private int mRow, mCol;

    ShapeRenderer shapeRenderer;

    private int mH = 80, mW = 100;

    int startX, startY;

    public DotsMap(int row, int col) {
        this.mCol = col;
        this.mRow = row;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        startX = 60;
        startY = 300;

    }

    public void render(Camera camera) {

        // 缩放处理
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        shapeRenderer.rect(startX, startY, mW * mCol, mH * mRow);

        for (int i = 1; i < mRow; i++) {
            int y = startY + mH * i;
            shapeRenderer.line(startX, y, startX + mW * mCol, y);
        }

        for (int i = 1; i < mCol; i++) {
            int x = startX + mW * i;
            shapeRenderer.line(x, startY, x, startY + mH * mRow);
        }

        shapeRenderer.end();
    }

    public Vector2 getNearestCellPosition(float cx, float cy, DotCell dotCell) {
        float tempX = cx - startX;
        float tempY = cy - startY;

        int pCol = (int)(tempX / mW);
        int pRow = (int)(tempY / mH);
        System.out.println("坐标(col,row)：(" + pCol + "," + pRow + ")");
        dotCell.setRow(pRow);
        dotCell.setCol(pCol);
        return new Vector2(pCol * mW + startX, pRow * mH + startY);
    }
}
