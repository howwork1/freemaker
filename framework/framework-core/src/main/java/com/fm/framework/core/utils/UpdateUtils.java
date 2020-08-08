package com.fm.framework.core.utils;



import com.fm.framework.core.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 主子表时更新子表的工具
 *
 * @author clufeng
 */
public class UpdateUtils {

    public static <T extends BaseModel> SubModelCompareResult<T> compare(List<T> newModels, List<T> oldModels, UpdateKeyMatcher<T> updateKeyMatcher, UpdateMatcher<T> matcher) {

        SubModelCompareResult<T> result = new SubModelCompareResult<>();

        Map<String, T> oldModelMap = oldModels.stream().collect(Collectors.toMap(updateKeyMatcher::getKeyField, Function.identity(), (v1, v2) -> v1));

        Map<String, T> newModelMap = newModels.stream().collect(Collectors.toMap(updateKeyMatcher::getKeyField, Function.identity(), (v1, v2) -> v1));

        result.setNewList(newModels.stream().filter(t -> !oldModelMap.containsKey(updateKeyMatcher.getKeyField(t))).collect(Collectors.toList()));

        result.setDelList(oldModels.stream().filter(t -> !newModelMap.containsKey(updateKeyMatcher.getKeyField(t))).collect(Collectors.toList()));

        List<T> updateList = new ArrayList<>();

        newModelMap.forEach((k, v) -> {
            if(oldModelMap.containsKey(k) && !matcher.match(v, oldModelMap.get(k))) {
                v.setId(oldModelMap.get(k).getId());
                updateList.add(v);
            }
        });

        result.setUpdateList(updateList);

        return result;
    }

}
