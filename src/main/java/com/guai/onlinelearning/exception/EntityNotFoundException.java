package com.guai.onlinelearning.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> objectClass, String field, Integer id) {
        super(String.format("%s %s : %d not found.", objectClass.getSimpleName(), field, id));
    }
}
