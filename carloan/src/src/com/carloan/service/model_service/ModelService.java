package com.carloan.service.model_service;

import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

import java.util.List;

public interface ModelService<T> {
    public T get(int id);
    public List<T> getAll();
    public void create(T model) throws CreateModelException;
    public void update(T model) throws UpdateModelException;
    public void delete(T model);
}
