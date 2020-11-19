package kr.co.penta.dataeye.spring.web.common.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {
	void download(HttpServletRequest request, HttpServletResponse response, String fileName, String downName);
    void download(HttpServletRequest request, HttpServletResponse response, File f, String downName);
}
