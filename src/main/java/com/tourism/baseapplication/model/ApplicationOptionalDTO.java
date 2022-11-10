package com.tourism.baseapplication.model;

import com.tourism.model.BaseDTO;

import java.util.Optional;


public class ApplicationOptionalDTO implements BaseDTO {

    private static final long serialVersionUID = 5717469232245333391L;

    private Optional<String> name;

    private Optional<Boolean> application;
}
