package com.api.aquilesApi.Business;

import com.api.aquilesApi.Service.ExcusesService;
import com.api.aquilesApi.Utilities.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcusesBusiness {
    @Autowired
    private ExcusesService excusesService;

    @Autowired
    private Util util;

    private final ModelMapper modelMapper = new ModelMapper();




}
