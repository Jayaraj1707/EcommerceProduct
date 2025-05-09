package com.ecommerce.util;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TerminalUtil {

    public static String execute(String command) {

        LogUtil.log("Execute command " + command , LogLevel.HIGH);
        StringBuilder sb = new StringBuilder();
        String[] commands = new String[] { "/bin/sh", "-c", command };
        try {
            Process proc = new ProcessBuilder(commands).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
                LogUtil.log(s, LogLevel.HIGH);
            }
            while ((s = stdError.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
                LogUtil.log(s, LogLevel.HIGH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String[] runCommands(final List<String> commands) {

        String[] cmdOutput = null;
        String line = null;
        try {
            final ProcessBuilder process = new ProcessBuilder(commands);
            final Process processOutput = process.start();
            final InputStreamReader reader = new InputStreamReader(processOutput.getInputStream());
            final BufferedReader bufferedReader = new BufferedReader(reader);
            final StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            cmdOutput = builder.toString().split("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmdOutput;
    }

    /**
     * Revert time zone.
     */
    public static void revertTimeZone() {

        try {
            if (SystemUtil.isWindows()) {
                LogUtil.log("Change Timezone", LogLevel.LOW);
                List<String> terminalGoal = new ArrayList<>();
                terminalGoal = new ArrayList<String>();
                terminalGoal.add("cmd");
                terminalGoal.add("/c");
                terminalGoal.add("start");
                terminalGoal.add("C:\\CommandPrompt.lnk");
                terminalGoal.add("/c");
                terminalGoal.add("tzutil");
                terminalGoal.add("/s");
                terminalGoal.add("\"Pacific Standard Time\"");
                String[] arrayVal = convertListToArray(terminalGoal);
                Runtime rt = Runtime.getRuntime();
                rt.exec(arrayVal);
            }
        } catch (Exception ex) {
            LogUtil.log("Excpetion in Change time zone in machein", LogLevel.LOW);
        }
    }

    /**
     * Convert list to array.
     *
     * @param arrayGoals the array goals
     * @return the string[]
     */
    private static String[] convertListToArray(List<String> arrayGoals) {

        String[] arrayVal = new String[arrayGoals.size()];
        for (int i = 0; i < arrayGoals.size(); i++) {
            arrayVal[i] = arrayGoals.get(i);
        }
        return arrayVal;
    }
}
