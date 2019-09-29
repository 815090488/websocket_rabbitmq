package com.websocket.springboot.controller;



import com.websocket.springboot.mapper.UsersMapper;
import com.websocket.springboot.model.Users;
import com.websocket.springboot.service.MessageReceiver;
import com.websocket.springboot.service.MessageSender;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@ServerEndpoint("/websocket")
public class WebSocketController {

    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();

    @Autowired
    private UsersMapper usersMapper;

    private Session session;


    @Autowired
    private MessageSender helloSender;

    @GetMapping("/")
    public String list(Model model){
        List<Users> users = usersMapper.selectList(null);
        model.addAttribute("users",users);
        return "index";
    }

    @GetMapping("/list")
    public String list2(Model model){
        List<Users> users = usersMapper.selectList(null);
        model.addAttribute("user",users);
        return "index2";
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(Users users) {
        int insert = usersMapper.insert(users);
        if (insert > 0) {
            helloSender.send("exchange2","",users);
            for(WebSocketController item: webSocketSet){
                try {
                    item.session.getBasicRemote().sendText("1");
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            return "ok";
        }else {
            System.out.println("失败");
            return "error";
        }
    }






    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session){
        webSocketSet.remove(this);  //从set中删除
        System.out.println("Session " +session.getId()+" has closed!");
    }
    @OnOpen
    public void onOpen(Session session){
        System.out.println("Session " + session.getId() + " has opened a connection");
        try {
            this.session=session;
            webSocketSet.add(this);     //加入set中
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
