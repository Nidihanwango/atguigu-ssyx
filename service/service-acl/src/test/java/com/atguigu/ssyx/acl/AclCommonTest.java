package com.atguigu.ssyx.acl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AclCommonTest {
    @Autowired
    private PermissionMapper permissionMapper;
    @Test
    public void test01(){
        System.out.println(permissionMapper.getChildrenIds(1L));
    }
    @Test
    public void test02(){
        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.forEach(i -> {
            if (i == 2 || i == 5) {
                list.add(5);
            }
        });
        System.out.println(list);
    }
}
