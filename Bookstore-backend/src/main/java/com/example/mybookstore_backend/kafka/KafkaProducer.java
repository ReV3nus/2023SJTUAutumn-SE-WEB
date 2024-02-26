//package com.example.mybookstore_backend.kafka;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class KafkaProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final ObjectMapper objectMapper;
//
//    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public void sendMessage(String topic, Map<String,String> message) {
//        try {
//            // 将 Map 转换为 JSON 字符串
//            String jsonMessage = objectMapper.writeValueAsString(message);
//            kafkaTemplate.send(topic, jsonMessage);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//    }
//}