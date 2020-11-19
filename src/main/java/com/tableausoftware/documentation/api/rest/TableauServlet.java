// -----------------------------------------------------------------------------
// 
// This file is the copyrighted property of Tableau Software and is protected 
// by registered patents and other applicable U.S. and international laws and 
// regulations.
// 
// Unlicensed use of the contents of this file is prohibited. Please refer to 
// the NOTICES.txt file for further details.
// 
// -----------------------------------------------------------------------------
package com.tableausoftware.documentation.api.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: TableauServlet
 *
 */
public class TableauServlet extends javax.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws ServletException {
		final String user = "user1";
        final String wgserver = "192.168.40.221";
        
		String ticket = getTrustedTicket(wgserver, user, "192.168.40.70");
		System.out.println(ticket);
	}
	
    public TableauServlet() {
        super();
    }       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String user = "user1";
        final String wgserver = "192.168.40.221";
        final String dst = "views/Book1/Sheet1";
        final String params = ":embed=yes&:toolbar=yes";
        
        String ticket = getTrustedTicket(wgserver, user, request.getRemoteAddr());
        if ( !ticket.equals("-1") ) {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "http://" + wgserver + "/trusted/" + ticket + "/" + dst + "?" + params);
        }
        else
            // handle error
            throw new ServletException("Invalid ticket " + ticket);
    }   
    
    // the client_ip parameter isn't necessary to send in the POST unless you have
    // wgserver.extended_trusted_ip_checking enabled (it's disabled by default)
    private static String getTrustedTicket(String wgserver, String user, String remoteAddr) 
        throws ServletException 
    {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        try {
            // Encode the parameters
            StringBuffer data = new StringBuffer();
            data.append(URLEncoder.encode("username", "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(user, "UTF-8"));
            data.append("&");
            data.append(URLEncoder.encode("client_ip", "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(remoteAddr, "UTF-8"));
            
            // Send the request
            URL url = new URL("http://" + wgserver + "/trusted");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data.toString());
            out.flush();
            
            // Read the response
            StringBuffer rsp = new StringBuffer();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ( (line = in.readLine()) != null) {
                rsp.append(line);
            }
            
            return rsp.toString();
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
        finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            }
            catch (IOException e) {}
        }
    }

}
