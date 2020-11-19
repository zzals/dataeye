package kr.co.penta.dataeye.spring.web.common.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/stream", method={RequestMethod.POST, RequestMethod.GET})
public class StreamController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/list")
    public void listStream(HttpServletResponse response) {
        try {
            ServletOutputStream os = response.getOutputStream();

            log.debug("rest/listStream");
            Map<String, Object> con = new LinkedHashMap<String, Object>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(int i = 0; i < 100000; i++) {
                Map<String, Object> data = new LinkedHashMap<String, Object>();
                Map<String, Object> pdata = new LinkedHashMap<String, Object>();
                data.put("idx", i);
                data.put("id", "A" + String.valueOf(i));
                data.put("data", String.valueOf(i));
                pdata.put("row", data);
                list.add(pdata);
            }
            con.put("body", list);
            Map<String, Object> msg = new LinkedHashMap<String, Object>();
            msg.put("res_cd", "succ");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", con);
            jsonObject.put("messages", msg);

            log.debug("jsonObject length : {}", jsonObject.toString().length());
            log.debug("jsonObject : {}", jsonObject);

            String res = jsonObject.toString();
            int res_len = res.length();
            int buf_size = 10000;

            for(int i = 0; i < res_len; i = i + buf_size) {
                if((i + buf_size) > res_len) {
                    os.write(res.getBytes("UTF-8"), i, (res_len - i));
                    os.flush();
                } else {
                    os.write(res.getBytes("UTF-8"), i, buf_size);
                    os.flush();
                }
            }
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/listbuffer")
    public void listBufferStream(ServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();

            response.setCharacterEncoding("UTF-8");
            response.setBufferSize(10);

            log.debug("rest/listStream");
            Map<String, Object> con = new LinkedHashMap<String, Object>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for(int i = 0; i < 100; i++) {
                Map<String, Object> data = new LinkedHashMap<String, Object>();
                Map<String, Object> pdata = new LinkedHashMap<String, Object>();
                data.put("id", "A" + String.valueOf(i));
                data.put("data", String.valueOf(i));
                pdata.put("row", data);
                list.add(pdata);
            }
            con.put("body", list);
            Map<String, Object> msg = new LinkedHashMap<String, Object>();
            msg.put("res_cd", "succ");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result_msg", msg);
            jsonObject.put("result", con);

            writer.println(jsonObject.toString());
            response.flushBuffer();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
