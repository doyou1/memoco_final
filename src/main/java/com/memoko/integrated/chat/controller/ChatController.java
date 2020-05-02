package com.memoko.integrated.chat.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memoko.integrated.chat.vo.Message;
import com.memoko.integrated.member.vo.MemberVO;

@Controller
public class ChatController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	

	@MessageMapping("/chatRoom")
	@SendTo("/subscribe/chatRoom")
	public Message sendChatMessage(Message message, SimpMessageHeaderAccessor headerAccessor) {

		logger.info("채팅 컨트롤러 시작");

		
		MemberVO userObject = (MemberVO) headerAccessor.getSessionAttributes().get("user");
		
		message.setId(userObject.getMember_id());
		message.setUsername(userObject.getMember_nickname());
		message.setChatdate(LocalDateTime.now());
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("/C:/chatUpload/chat.txt",true));) {
			
			PrintWriter pw = new PrintWriter(bw,true);		
			pw.println(message.getId());
			pw.println(message.getMessage());
			pw.println(message.getUsername());
			pw.println(message.getChatdate());
			
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(message.toString());		
		logger.info("채팅 컨트롤러 종료");
		return message;
	}
	
	@RequestMapping(value="/chat/getPrevMessage",method=RequestMethod.POST)
	@ResponseBody
	public ArrayList<Message> getPrevMessage(){
		ArrayList<Message> msgs = new ArrayList<>();
		

		try(BufferedReader br = new BufferedReader(new FileReader("/C:/chatUpload/chat.txt"))){
			String msg = null;
			int cnt = 0;
			Message msgVO = null;
			while((msg = br.readLine()) != null) {
				if(cnt == 0) {
					msgVO = new Message();
					msgVO.setId(msg);
					cnt++;
					continue;
				}else if(cnt == 1) {
					msgVO.setMessage(msg);
					cnt++;
					continue;
				}else if(cnt == 2) {
					msgVO.setUsername(msg);
					cnt++;
					continue;
				}else if(cnt == 3) {
					msgVO.setChatdate(LocalDateTime.parse(msg, DateTimeFormatter.ISO_DATE_TIME));
					msgs.add(msgVO);
					
					cnt = 0;
					continue;
				}
				System.out.println(msg);
			}
			
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return msgs;
	}
	

	public void chatTextReset() {
		
		logger.info("ChatText Reset 실행");
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("/C:/chatUpload/chat.txt"));) {
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("ChatText Reset 종료");
	}
}
