package boot.project;


import boot.pojo.Weixin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RequestDemo {

    @Autowired
    private RestTemplate restTemplate;


    public void downloder(){


       List<Weixin> lis =  restTemplate.getForEntity("", List.class).getBody();

//three


    }



}
