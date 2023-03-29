package com.lucas.restspringboot.shared.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

public class ObjectMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static <T, S> S parseObject(T origin, Class<S> destination) {

        return modelMapper.map(origin, destination);
    }

    public static <T, S> List<S> parseListObjects(List<T> origin, Class<S> destination) {
        List<S> destinationObjects = new ArrayList<S>();
        for (T t : origin) {
            destinationObjects.add(modelMapper.map(t, destination));
        }

        return destinationObjects;
    }
}
