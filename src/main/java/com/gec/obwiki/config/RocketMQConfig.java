package com.gec.obwiki.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RocketMQConfig {

    @Value("${spring.rocketmq.name-server}")
    private String nameServer;

    @Value("${spring.rocketmq.producer.group}")
    private String producerGroup;

    @Value("${spring.rocketmq.producer.send-message-timeout:3000}")
    private Integer sendMsgTimeout;

    @Value("${spring.rocketmq.producer.retry-times-when-send-failed:2}")
    private Integer retryTimesWhenSendFailed;

    @Value("${spring.rocketmq.producer.retry-times-when-send-async-failed:2}")
    private Integer retryTimesWhenSendAsyncFailed;

    @Value("${spring.rocketmq.producer.max-message-size:4194304}")
    private Integer maxMessageSize;

    @Value("${spring.rocketmq.producer.compress-message-body-threshold:4096}")
    private Integer compressMessageBodyThreshold;

    @Value("${spring.rocketmq.producer.retry-next-server:true}")
    private Boolean retryNextServer;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        if (StringUtils.isEmpty(producerGroup)) {
            throw new IllegalArgumentException("producerGroup is empty");
        }
        if (StringUtils.isEmpty(nameServer)) {
            throw new IllegalArgumentException("nameServer is empty");
        }

        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServer);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendAsyncFailed);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setCompressMsgBodyOverHowmuch(compressMessageBodyThreshold);
        producer.setRetryAnotherBrokerWhenNotStoreOK(retryNextServer);
        
        // // 启动生产者
        // producer.start();
        
        return producer;
    }

    @Bean
    public RocketMQTemplate rocketMQTemplate(DefaultMQProducer defaultMQProducer) {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(defaultMQProducer);
        return rocketMQTemplate;
    }
}
