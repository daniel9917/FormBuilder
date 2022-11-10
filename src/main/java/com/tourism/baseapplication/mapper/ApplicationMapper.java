package com.tourism.baseapplication.mapper;

import com.tourism.baseapplication.domain.Application;
import com.tourism.baseapplication.model.ApplicationDTO;
import com.tourism.baseapplication.model.ApplicationOptionalDTO;
import com.tourism.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper
public abstract class ApplicationMapper extends BaseMapper<Application, ApplicationDTO, ApplicationOptionalDTO> {
}
