
package com.oahcfly.chgame.core.async;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

public abstract class CHAsyncTask implements AsyncTask<String> {
    private CHAsyncManager asyncManager;

    private AsyncResult<String> depsFuture = null;

    // 处理OK?
    volatile boolean asyncDone = false;

    public CHAsyncTask() {

    }

    public CHAsyncTask(CHAsyncManager manager) {
        asyncManager = manager;
    }

    @Override
    public String call() throws Exception {
        String result = doInBackground();
        asyncDone = true;
        return result;
    }

    /**开始执行*/
    public abstract void onPreExecute();

    /**执行结束返回结果*/
    public abstract void onPostExecute(String result);

    /**异步执行*/
    public abstract String doInBackground();

    public boolean update() {
        if (!asyncDone) {
            if (depsFuture == null) {
                onPreExecute();
                depsFuture = asyncManager.getExecutor().submit(this);
            }
        } else {
            if (depsFuture.isDone()) {
                try {
                    onPostExecute(depsFuture.get());
                } catch (Exception e) {
                    throw new GdxRuntimeException("depsFuture.get() failed!!!!");
                }

            }
        }

        return asyncDone;
    }

    public void setAsyncManager(CHAsyncManager asyncManager) {
        this.asyncManager = asyncManager;
    }

    public CHAsyncManager getAsyncManager() {
        return asyncManager;
    }
}
