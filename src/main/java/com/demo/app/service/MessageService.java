package com.demo.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.app.dao.CacheDao;
import com.demo.app.domain.Message;

@Service
public class MessageService {

	List<Message> messages = Collections.synchronizedList(new ArrayList<Message>());

	@Autowired
	CacheDao<Message, String> cacheDao;

	@PostConstruct
	public void init() {
		cacheDao.setType(Message.class);
	}

	/*
	 * public List<Message> getMessages() { return
	 * cacheDao.findOne(key)messageDao.getMessages(); }
	 */

	public Message getMessage(String id) {
		return cacheDao.findOne(id);
	}

	/*
	 * public List<Message> getMessagesByAuthor(String author) { return
	 * messageDao.getMessagesByAuthor(author); }
	 */

	public void saveMessage(Message message) {
		cacheDao.saveOne(message);
	}

}
