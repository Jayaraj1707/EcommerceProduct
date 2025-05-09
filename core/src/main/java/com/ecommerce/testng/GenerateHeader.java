package com.ecommerce.testng;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;
import com.ecommerce.util.FileUtil;
import org.testng.annotations.Test;

import java.io.*;

public class GenerateHeader {

    @Test
    public void generateSummaryHeaderTest() {
        writeHeader();
    }

    /**
     * Write header.
     */
    public static void writeHeader() {

        LogUtil.log("Create header File", LogLevel.HIGH);
        File headerDir = new File("..testresult/");
        LogUtil.log("Header File path :" + headerDir.getAbsolutePath(), LogLevel.HIGH);
        FileUtil.createDirectory(headerDir.getAbsolutePath());
        String path = headerDir.getAbsolutePath();
        PrintWriter headerWriter = createWriter(path);
        String content = writeDocumentHead();
        content += closeBody();
        headerWriter.println(content.toString());
        headerWriter.close();
    }

    /**
     * Creates the writer.
     *
     * @param outdir the outdir
     * @return the prints the writer
     */
    private static PrintWriter createWriter(String outdir) {
        try {
            new File(outdir).mkdirs();
            return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "summaryHeader.html"))));
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Write document head.
     *
     * @return the string
     */
    private static String writeDocumentHead() {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        content.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        content.append("<head>");
        content.append("<title>Header</title>");
        content.append("</head><body>");
        return content.toString();
    }

    /**
     * Close body.
     *
     * @return the string
     */
    private static String closeBody() {
        StringBuilder content = new StringBuilder();
        content.append("</body>");
        content.append("</html>");
        return content.toString();
    }

    public static void main(String[] args) {
        writeHeader();
    }
}
