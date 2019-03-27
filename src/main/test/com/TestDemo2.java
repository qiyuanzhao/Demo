package com;


import boot.MySpringApplication;
import boot.project.RequestDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringApplication.class)
public class TestDemo2 {

    @Autowired
    private RequestDemo requestDemo;


    @Test
    public void lankou() {
        requestDemo.downloder();
    }



}
