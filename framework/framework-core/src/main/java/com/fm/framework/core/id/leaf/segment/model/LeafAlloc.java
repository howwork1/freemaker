package com.fm.framework.core.id.leaf.segment.model;

import lombok.Data;

@Data
public class LeafAlloc {
    private String key;
    private long maxId;
    private int step;
    private String updateTime;
}
