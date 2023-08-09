package org.idea.srpc.core.server;

import bean.Address;
import bean.User;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private static List<User> users;
    static {
        users = new ArrayList<>();
        List<String> hobbys = new ArrayList<>();
        hobbys.add("篮球");
        hobbys.add("羽毛球");
        Address address1 = new Address("湖北省", "武汉市");
        Address address2= new Address("湖南省", "长沙市");
        User user1 = new User(1, "张三", '男', 25, "13398787890",hobbys, address1,1);
        User user2 = new User(2, "李四", '男', 30, "13298781111",hobbys, address2,1);
        User user3 = new User(3, "王武", '男', 35, "13298782222",hobbys, address2,1);
        User user4 = new User(4, "赵四", '男', 32, "13298783333",hobbys, address2,1);
        User user5 = new User(5, "菠萝", '男', 31, "13298784444",hobbys, address1,1);
        User user6 = new User(6, "包包", '女', 22, "13298775555",hobbys, address1,1);
        User user7 = new User(7, "荔枝", '女', 29, "13298786666",hobbys, address1,1);
        User user8 = new User(8, "tom", '男', 32, "13298787777",hobbys, address2,1);
        User user9 = new User(9, "nick", '男', 32, "13298788888",hobbys, address1,1);
        User user10 = new User(10, "sam", '男', 30, "13298789999",hobbys, address1,1);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
    }
    public static List<User> getUsers() {
        return users;
    }
}
