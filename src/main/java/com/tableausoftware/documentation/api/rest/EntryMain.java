package com.tableausoftware.documentation.api.rest;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

public class EntryMain {

	public static void main(String[] args) {
        String resource = "res/config.properties";
        Properties properties = new Properties();
        
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            properties.load(reader);
            System.out.println(properties.getProperty("server.host"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
