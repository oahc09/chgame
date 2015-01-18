
package com.oahcfly.chgame.core.async;

import java.util.Stack;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.async.AsyncExecutor;

/**
 * 
 * <pre>
 * 处理异步任务中心
 * 【参考AssetManager实现的】
 * 主要依托在CHGame中进行update处理，可以在CHScreen中随时加入，并且可以调取CHAsyncTask.isDone()检测是否执行完毕。
 * date: 2015-1-17
 * </pre>
 * @author caohao
 */
public class CHAsyncManager implements Disposable {
    private final Stack<CHAsyncTask> tasks = new Stack<CHAsyncTask>();

    private AsyncExecutor executor;

    public CHAsyncManager() {
        // 池中允许的最大线程数1。
        executor = new AsyncExecutor(1);
    }

    public boolean update() {
        if (tasks.size() == 0)
            return true;

        return updateTask() && tasks.size() == 0;
    }

    private boolean updateTask() {
        CHAsyncTask task = tasks.peek();
        if (task.update()) {
            tasks.pop();
            return true;
        }
        return false;
    }

    public void loadTask(CHAsyncTask task) {
        if (task.getAsyncManager() == null) {
            task.setAsyncManager(this);
        }
        tasks.push(task);
    }

    @Override
    public void dispose() {
        executor.dispose();
    }

    public AsyncExecutor getExecutor() {
        return executor;
    }

}
