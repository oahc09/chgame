
package com.oahcfly.chgame.bmob;

public interface BmobListener {

    /**保存用户信息*/
    public void saveUserInfo(Object dataObject);

    /**获取用户信息*/
    public Object getUserInfo();

    /**注册用户*/
    public void registerUser(Object userObject);

    /**保存数据*/
    public void saveData(Object dataObject);

    /**获取数据*/
    public Object getData();
}
