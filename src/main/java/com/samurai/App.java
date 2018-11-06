package com.samurai;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * samurai
 *
 */
public class App 
{
    public static void main(String[] args) {


        for(int i=0; i<args.length; i++) {

            System.out.println(args[i]);

        }

        //查找域名真实IP
        //查找域名
        //java -jar distrubtedscanner www.a.com  asp,jsp  4
        //                              域名      页面类型  子域名层数

        //subdomains_t 子域名的topic
        //ports_t      端口的topic
        //urls_t       url的topic

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.83.3.157:9092,10.83.3.25:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //生产者发送消息
        String topic = "forTestTarget";
        Producer<String, String> procuder = new KafkaProducer<String,String>(props);
        for (int i = 1; i <= 10; i++) {
            String value = "value_" + i;
            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);
            procuder.send(msg);
        }
        //列出topic的相关信息
        List<PartitionInfo> partitions = new ArrayList<PartitionInfo>() ;
        partitions = procuder.partitionsFor(topic);
        for(PartitionInfo p:partitions)
        {
            System.out.println(p);
        }

        System.out.println("send message over.");
        procuder.close(100, TimeUnit.MILLISECONDS);
    }
}
