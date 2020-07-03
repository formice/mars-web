package com.formice.mars.web.tool;

import lombok.extern.log4j.Log4j2;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ShellUtils {
    /**
     * 运行shell脚本
     * @param shell 需要运行的shell脚本
     */
    public static void execShell(String shell){
        try {
            Runtime.getRuntime().exec(shell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell脚本 new String[]方式
     * @param shell 需要运行的shell脚本
     */
    public static void execShellBin(String shell){
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell},null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shStr
     *            需要执行的shell
     * @return
     */
    public static List<String> runShell(String shStr) {
        List<String> strList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr},null,null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null){
                log.info(line);
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static void main(String[] args) {
        runShell("jps");
    }
}
