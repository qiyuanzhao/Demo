package com;


import boot.MySpringApplication;
import boot.pojo.Weixin;
import boot.project.RequestDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringApplication.class)
public class TestDemo {

    @Autowired
    private RequestDemo requestDemo;


    @Test
    public void sk2() {
        requestDemo.downloder();
    }



}
