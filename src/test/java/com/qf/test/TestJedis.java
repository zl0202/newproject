package com.qf.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class TestJedis {

    Jedis j = null;
    @Before
    public void setUp(){
        j = new Jedis();
    }
    @Test
    public void test1(){

        System.out.println(j.ping());
    }
    @Test
    public void test2(){
        System.out.println(j.set("123","456"));
        System.out.println(j.get("123"));
        System.out.println(j.get("hello"));
    }

    @Test
    public void tes3(){
        System.out.println(j.sadd("person", "zhangsan", "lisi", "wangwu"));
        System.out.println(j.sadd("person","zhangsan"));
        System.out.println(j.smembers("person"));
    }

    @Test
    public void test4(){

        System.out.println(j.zadd("fenshu", 100, "java"));

        System.out.println(j.zadd("fenshu", 88, "html5"));

        System.out.println(j.zadd("fenshu", 77, "python"));
        System.out.println(j.zadd("fenshu", 66, "testing"));

        System.out.println(j.zadd("fenshu", 99, "html5"));

        System.out.println(j.zrangeByScore("fenshu", 60, 100));
    }

    @Test
    public void testPool(){

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        //设置最大空闲数
        config.setMaxIdle(20);
        //设置最大的连接数
        config.setMaxTotal(50);
        //设置的等待时间
        config.setMaxWaitMillis(5000);

        List<JedisShardInfo> shard = new ArrayList<>();

        JedisShardInfo jedisShardInfo = new JedisShardInfo("127.0.0.1", 6379);
//        jedisShardInfo.setPassword("");

        shard.add(jedisShardInfo);

        ShardedJedisPool pool = new ShardedJedisPool(config,shard);

        ShardedJedis je = pool.getResource();
        System.out.println(je.zrangeByScore("fenshu",80,100));
    }
}
