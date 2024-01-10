package com.buyersfirst.core.interfaces;

import java.util.List;

public class DesireCache {
    public List<DesireListRsp> allDesires;

    public DesireCache() {
    }

    public DesireCache(List<DesireListRsp> allDesires) {
        this.allDesires = allDesires;
    }
}
