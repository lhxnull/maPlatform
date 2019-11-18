package com.picc.rule;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by lhx on 2019/10/9.
 */
public class test {

    public static void main(String[] args) {
        //一个key对应多个value
//        MultiValueMap<String, String> params = new LinkedMultiValueMap(20);
//        params.add("name","1");
//        params.add("name","2");
//        params.add("name","3");
//        System.out.println(params.get("name"));
//        String cords = "123.32,324;34,44";
//
//        String[] split = cords.split(";");
//        List<String> list = Arrays.asList(split);
//        list.forEach(System.out::println);

//        String str="103.52,23.21";
//        String[] split = str.split(",");

        ConcurrentLinkedQueue<String> queue=new ConcurrentLinkedQueue<String>();
                queue.offer("1");
        queue.offer("2");
    }
}
