package com.company.shopBastim.filter;

import java.util.ArrayList;
import java.util.List;

public class BlackList {
    private static BlackList blackList;

    private List<String> blackListList = new ArrayList<>();

    private BlackList(){

    }

    public static BlackList getInstance(){
        if(blackList == null){
            blackList = new BlackList();
        }
        return blackList;
    }

    public List<String> getBlackListList() {
        return blackListList;
    }

    public void setBlackListList(List<String> blackListList) {
        this.blackListList = blackListList;
    }
}
