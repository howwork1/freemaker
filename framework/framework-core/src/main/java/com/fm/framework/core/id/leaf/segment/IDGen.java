package com.fm.framework.core.id.leaf.segment;

public interface IDGen {
    Result get(String key);
    boolean init();
}
