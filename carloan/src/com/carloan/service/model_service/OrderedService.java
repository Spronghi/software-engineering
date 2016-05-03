package com.carloan.service.model_service;

import java.util.List;

public interface OrderedService<T> extends ModelService<T> {
    public List<T> getAllBy(int id);
}
