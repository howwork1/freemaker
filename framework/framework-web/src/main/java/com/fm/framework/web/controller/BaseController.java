package com.fm.framework.web.controller;


import com.fm.framework.core.model.BaseModel;
import com.fm.framework.core.query.Page;
import com.fm.framework.core.query.PageInfo;
import com.fm.framework.core.service.Service;
import com.fm.framework.web.VO;
import com.fm.framework.web.request.QueryRequest;
import com.fm.framework.web.response.ApiResponse;
import com.fm.framework.web.response.ApiStatus;

import java.util.stream.Collectors;

/**
 * <p>基础Controller</p>
 *
 * @author clufeng
 */
public abstract class BaseController<M extends BaseModel, V extends VO> {

    protected <T> ApiResponse<T> success(T t) {
        return ApiResponse.of(ApiStatus.SUCCESS, t);
    }

    protected <T> ApiResponse<T> failed(String error) {
        return ApiResponse.of(ApiStatus.FAILED, error);
    }

    public ApiResponse<Boolean> create(V form) {

        M model = convert(form);

        boolean result = this.service().save(model);

        return success(result);

    }

    public ApiResponse<Boolean> delete(Long id) {

        boolean result = this.service().delete(id);

        return success(result);

    }

    public ApiResponse<Boolean> update(V form) {

        M model = convert(form);

        boolean result = this.service().update(model);

        return success(result);

    }

    public ApiResponse<Page<V>> list(QueryRequest queryRequest) {

        Page<M> pageDatas = this.service().list(queryRequest.getQueryItems(), queryRequest.getOrderItem(),
                queryRequest.getPagination().getCurrentPage(), queryRequest.getPagination().getPageSize());

        PageInfo<V> result = new PageInfo<>();
        result.setCurrentPage(pageDatas.getCurrentPage());
        result.setPageSize(pageDatas.getPageSize());
        result.setTotal(pageDatas.getTotal());
        result.setData(pageDatas.getData().stream().map(this::convert).collect(Collectors.toList()));
        return success(result);
    }

    protected abstract Service<M> service();

    protected abstract M convert(V form);

    protected abstract V convert(M model);

}