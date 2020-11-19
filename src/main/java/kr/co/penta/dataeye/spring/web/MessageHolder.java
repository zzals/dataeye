package kr.co.penta.dataeye.spring.web;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
public class MessageHolder {
    private static final MessageHolder holder = new MessageHolder();

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageHolder.messageSource = messageSource;
    }
    
    private static MessageSourceAccessor accessor;
    
    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.KOREAN);
    }
    
    public static MessageHolder getInstance() {
        return holder;
    }
    
    public String get(String code) {
        return accessor.getMessage(code, "undefined message("+code+")");
    }
}