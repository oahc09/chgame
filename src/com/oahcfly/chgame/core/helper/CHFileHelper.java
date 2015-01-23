
package com.oahcfly.chgame.core.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class CHFileHelper {

    /**
     * 
     * <pre>
     * 读取文件下所有文件
     * 
     * date: 2015-1-14
     * </pre>
     * @author caohao
     * @param fileName
     * @return
     */
    public static FileHandle[] getFileHandles(String fileName) {
        FileHandle parentFileHandle = Gdx.files.internal(fileName);
        return parentFileHandle.list();
    }

    /**
     * 
     * <pre>
     * 根据文件名，获取FileHandle
     * [效率不怎么高]
     * date: 2015-1-14
     * </pre>
     * @author caohao
     * @param pngName
     * @return
     */
    public static FileHandle searchFileHandle(String dirName, String pngName) {
        FileHandle parentFileHandle = Gdx.files.internal(dirName);
        FileHandle[] listFileHandles = parentFileHandle.list();
        for (FileHandle tmpFileHandle : listFileHandles) {
            if (tmpFileHandle.isDirectory()) {
                // 是文件目录
                FileHandle fileHandle = searchFileHandle(tmpFileHandle.name(), pngName);
                if (fileHandle != null)
                    return fileHandle;
            } else if (tmpFileHandle.name().equals(pngName)) {
                return tmpFileHandle;
            }
        }
        return null;
    }
}
