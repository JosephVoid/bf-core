package com.buyersfirst.core.interfaces;

import java.util.ArrayList;

public class DesireListComplete {
    private MetaFields meta;
    private ArrayList<DesireListRsp> results;

    public DesireListComplete() {
    }

    private DesireListComplete(MetaFields meta, ArrayList<DesireListRsp> desires) {
        this.meta = meta;
        this.results = desires;
    }

    public static class DesireListBuilder {
        private MetaFields meta;
        private ArrayList<DesireListRsp> results;

        public DesireListBuilder() {
        }

        public DesireListComplete build(Integer total, Integer perPage, Integer page,
                ArrayList<DesireListRsp> desires) {
            this.meta = new MetaFields(total, perPage, page);
            this.results = desires;
            return new DesireListComplete(meta, desires);
        }

        public MetaFields getMeta() {
            return meta;
        }

        public void setMeta(MetaFields meta) {
            this.meta = meta;
        }

        public ArrayList<DesireListRsp> getResult() {
            return results;
        }

        public void setDesires(ArrayList<DesireListRsp> desires) {
            this.results = desires;
        }
    }

    public MetaFields getMeta() {
        return meta;
    }

    public void setMeta(MetaFields meta) {
        this.meta = meta;
    }

    public ArrayList<DesireListRsp> getResult() {
        return results;
    }

    public void setDesires(ArrayList<DesireListRsp> desires) {
        this.results = desires;
    }
}
