package kr.co.penta.dataeye.common.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;

public class ProcessRunner { 
    public ProcessRunnerResult byProcessBuilderRedirect(String command) {
        return byProcessBuilderRedirect(command.split("\\s+"));
    }
    
    public ProcessRunnerResult byProcessBuilderRedirect(String[] command) {
        ProcessRunnerResult result = new ProcessRunnerResult();
        ProcessBuilder builder = new ProcessBuilder(command);
        
        try {
            builder.redirectOutput(Redirect.INHERIT);
            builder.redirectError(Redirect.INHERIT);
            builder.start();
            result.setResult(PROCESS_RUN_STATUS.SUCCESS.value);
        } catch (IOException e) { 
        	result.setException(e);
        	result.setOutput(e.toString());
            result.setResult(PROCESS_RUN_STATUS.FAIL.value);
        }
        
        return result;
    }

    public ProcessRunnerResult byProcessBuilder(String command) {
        return byProcessBuilder(command.split("\\s+"));
    }
    
    public ProcessRunnerResult byProcessBuilder(String[] command) {
        ProcessBuilder ps=new ProcessBuilder(command);
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        ProcessRunnerResult result = new ProcessRunnerResult();
        try {
            ps.redirectErrorStream(true);
            Process pr = ps.start();  

            in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
            pr.waitFor();
            
            result.setOutput(sb.toString());
            result.setResult(PROCESS_RUN_STATUS.SUCCESS.value);
        } catch (Exception e) {
        	result.setException(e);
        	result.setOutput(e.toString());      
            result.setResult(PROCESS_RUN_STATUS.FAIL.value);
        } finally {
            try { in.close(); } catch (Exception e) {}
        }
        
        return result;
    }
    
    public static void main(String[] args)
            throws IOException,    InterruptedException {
        String bat = "D:\\penta.co.kr\\dataeye\\03.dataeye_renewal\\workspaces\\dataeye-for-BrExecutor\\runBr.bat";
        String[] command = new String[] {"cmd", "/c", bat, "-PobjTypeId=030103L", "-PobjId=030103L_d3360965-0020-46ab-8c50-9b12ffd86bc9", "-Bone=1",  "-Btwo=2" };
        ProcessRunner runner = new ProcessRunner();
        runner.byProcessBuilder(command);
    }
}