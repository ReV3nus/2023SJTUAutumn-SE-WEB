import React, {useEffect, useState} from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import {useNavigate} from "react-router";
export const WebSocketProcessor=()=>{
    const username = JSON.parse(localStorage.getItem('user'));
    const navigate=useNavigate();
    let stompClient;
    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, (frame) => {
            if (frame) {
                // 订阅个性化消息地址
                stompClient.subscribe(`/user/${username}/queue/order-result`, (message) => {
                    console.log(message);
                    console.log("message.body=",message.body);
                    if(message.body==="Succeed!") {
                        navigate('/Orders');
                    }
                });
            } else {
                console.error('WebSocket connection failed!');
            }
        }, (error) => {
            console.error('WebSocket connection error:', error);
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, [username, stompClient]);
    return(
        <div>

        </div>
    );
}