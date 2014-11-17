
package com.oahcfly.chgame.plist;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.oahcfly.chgame.plist.element.PBoolean;
import com.oahcfly.chgame.plist.element.PDict;
import com.oahcfly.chgame.plist.element.PInteger;
import com.oahcfly.chgame.plist.element.PList;
import com.oahcfly.chgame.plist.element.PListObject;
import com.oahcfly.chgame.plist.element.PListObjectType;
import com.oahcfly.chgame.plist.element.PString;

public class CHPList {
    private String plistPath;

    public CHPList(String plistPath) {
        //"plist/newPic1.plist"
        this.plistPath = plistPath;
    }

    PList rootPList;

    public void parse() {
        XmlReader xmlReader = new XmlReader();
        try {
            Element element = xmlReader.parse(Gdx.files.internal(plistPath));
            parseElement(element);
        } catch (IOException e) {
            Gdx.app.error(getClass().getSimpleName(), e.getMessage());
        }

    }

    private void parseElement(Element element) {
        if (element.getName().equals("key") && element.getText().equals("")) {

        }

        if (PListConstants.TAG_PLIST.equals(element.getName())) {
            rootPList = new PList();
        } else if (PListConstants.TAG_DICT.equals(element.getName())) {
            PDict pDict = new PDict();
            parseDict(pDict, element);
            rootPList.addDict(pDict);
        }
        for (int i = 0, size = element.getChildCount(); i < size; i++) {
            parseElement(element.getChild(i));
        }
    }

    private void parseDict(PDict pDict, Element parentElement) {

        String keyName = null;
        for (int i = 0, size = parentElement.getChildCount(); i < size; i++) {
            Element element = parentElement.getChild(i);
            if (element.getName().equals("key")) {
                keyName = element.getText();
            } else if (element.getName().equals(PListConstants.TAG_DICT)) {
                PDict pDict2 = new PDict();
                parseDict(pDict2, element);
                pDict.addChild(keyName, pDict2);
            } else if (element.getName().equals(PListConstants.TAG_STRING)) {
                PString pString = new PString(element.getText());
                pDict.addChild(keyName, pString);
            } else if (element.getName().equals(PListConstants.TAG_INTEGER)) {
                PInteger pInteger = new PInteger(Integer.valueOf(element.getText()));
                pDict.addChild(keyName, pInteger);
            } else if (element.getName().equals(PListConstants.TAG_BOOL_FALSE)) {
                PBoolean pBoolean = new PBoolean(false);
                pDict.addChild(keyName, pBoolean);
            } else if (element.getName().equals(PListConstants.TAG_BOOL_TRUE)) {
                PBoolean pBoolean = new PBoolean(true);
                pDict.addChild(keyName, pBoolean);
            }
        }

    }

    public PList getRootPList() {
        return rootPList;
    }

    public String getPlistPath() {
        return plistPath;
    }

    public FrameParam getFrameParam(String keyName) {
        PDict pDictValue = null;
        for (PListObject pListObject : rootPList.getStack()) {
            if (pListObject.getType() == PListObjectType.DICT) {
                PDict pDict = ((PDict)pListObject);
                PListObject pListObjectValue = pDict.findValue(keyName);
                if (pListObjectValue != null) {
                    pDictValue = ((PDict)pListObjectValue);
                }
            }
        }

        if (pDictValue == null) {
            return new FrameParam();
        }

        FrameParam frameParam = new FrameParam();
        PListObject pListObject = pDictValue.findValue(PListConstants.DICT_FRAME);
        if (pListObject != null) {
            String frameString = ((PString)pListObject).getText();
            // <string>{{367,225},{104,88}}</string>
            frameString = frameString.replace("{", "").replace("}", "");
            String[] splitXY = frameString.split(",");
            frameParam.setFromX(Integer.valueOf(splitXY[0]));
            frameParam.setFromY(Integer.valueOf(splitXY[1]));
            frameParam.setWidth(Integer.valueOf(splitXY[2]));
            frameParam.setHeight(Integer.valueOf(splitXY[3]));
        }
        pListObject = pDictValue.findValue(PListConstants.DICT_ROTATED);
        if (pListObject != null) {
            //<false/>
            frameParam.setRotated(((PBoolean)pListObject).isTrue());
        }

        return frameParam;
    }
}
