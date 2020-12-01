package com.flashex.triptrackingmicroservice.webservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.flashex.triptrackingmicroservice.lib.services.SampleService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DemoApplicationTest {

    @Autowired
    private SampleService sampleService;

    @Test
    public void contextLoads() {
    }

}
