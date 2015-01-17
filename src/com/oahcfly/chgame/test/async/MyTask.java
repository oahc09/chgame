
package com.oahcfly.chgame.test.async;

import com.oahcfly.chgame.core.async.CHAsyncTask;

public class MyTask extends CHAsyncTask {

    @Override
    public void onPreExecute() {
        System.out.println("onPreExecute-" + System.currentTimeMillis());
    }

    @Override
    public void onPostExecute(String result) {
        System.out.println("onPostExecute-" + result + "," + System.currentTimeMillis());

    }

    @Override
    public String doInBackground() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("doInBackground-" + System.currentTimeMillis());
        return "处理完毕。。。。";
    }

}
