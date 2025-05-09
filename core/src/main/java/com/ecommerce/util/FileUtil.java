package com.ecommerce.util;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /** The Constant SLASH. */
    private static final String SLASH = "/";

    /**
     * Gets the file from class path.
     *
     * @param fileName the file name
     * @return the file from class path
     */
    public static File getFileFromClassPath(String fileName) {

        ClassLoader classLoader = FileUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getPath());

        return file;
    }

    /**
     * Gets the string data.
     *
     * @param fileName the file name
     * @return the string data
     * @throws FileNotFoundException
     */
    public static String getStringData(String fileName) {

        InputStream input = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (input == null) {
            try {
                throw new FileNotFoundException(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String theString = getStringFromInputStream(input);

        return theString;
    }

    /**
     * Gets the input stream data.
     *
     * @param fileName the file name
     * @return the input stream data
     */
    public static InputStream getInputStreamData(String fileName) {

        InputStream input = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        return input;
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();

    }

    /**
     * Read file.
     *
     * @param filePath the file path
     * @return the string
     */
    public static String readFile(String filePath) {

        StringBuffer stringBuffer = null;
        try {
            File file = new File(filePath);
            LogUtil.log("File Path : " + file.getAbsolutePath(), LogLevel.HIGH);
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    public static Properties readPropertyFile(String filePath) {

        Properties properties = new Properties();
        try {
            // File file = new File(filePath);
            // LogUtil.log("File Path : " + file.getAbsolutePath(), LogLevel.HIGH);
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            properties.load(bufferedReader);
            fileReader.close();
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Creates the nio file.
     *
     * @param firstPath the first path
     * @param isNew     the is new
     * @param nextPath  the next path
     * @return the path
     */
    public static Path createNIOFile(final String firstPath, final boolean isNew, final String... nextPath) {

        try {
            final Path dir = Paths.get(firstPath);
            Files.createDirectories(dir);
            final Path filePath = Paths.get(firstPath, nextPath);
            if (isNew) {
                Files.deleteIfExists(filePath);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            return filePath;
        } catch (IOException e) {
            LOGGER.error("Error creating NIO file. Cause ::: " + e);
        }
        return null;
    }

    /**
     * Creates the file.
     *
     * @param fileName the file name
     */
    public static void createFile(String fileName) {

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            LOGGER.error("Error creating file. Cause ::: " + e);
        }
    }

    /**
     * Gets the file path.
     *
     * @param fileName the file name
     * @return the file path
     */
    public static String createTempFile(String fileName) {

        File currentFile = new File("");
        String xmlPath = currentFile.getAbsolutePath() + "/target/" + fileName;
        return xmlPath;
    }

    /**
     * Delete file.
     *
     * @param filePath the file path
     */
    public static void deleteFile(Path filePath) {

        try {
            Files.delete(filePath);
        } catch (Exception e) {
            LOGGER.error("Error deleting file. Cause ::: " + e);
        }
    }

    /**
     * Delete file contents.
     *
     * @param fileName the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void deleteFileContents(String fileName) throws IOException {

        try {
            FileWriter fw = new FileWriter(fileName, false);
            PrintWriter pw = new PrintWriter(fw);
            pw.write("");
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write properties to file.
     *
     * @param properties the properties
     * @param fileName   the file name
     * @param comment    the comment
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertiesToFile(final Properties properties, final String fileName, final String comment)
            throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, comment);
        fileOut.close();
    }

    /**
     * Creates the directory.
     *
     * @param dirPath the dir path
     */
    public static void createDirectory(final String dirPath) {

        Path directoryPath = Paths.get(dirPath);
        try {
            if (!directoryPath.toFile().exists()) {
                Files.createDirectory(directoryPath);
                LogUtil.log("Directory created :" + directoryPath, LogLevel.HIGH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete folder.
     *
     * @param folder the folder
     */
    public static void deleteFolder(final String folder) {

        File folderPath = new File(folder);

        try {
            final File[] filesList = folderPath.listFiles();
            for (final File file : filesList) {
                file.delete();
            }
            folderPath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all files in folder.
     *
     * @param folder the folder
     */
    public static void deleteAllFilesInFolder(final String folder) {

        File folderPath = new File(folder);

        try {
            final File[] filesList = folderPath.listFiles();
            for (final File file : filesList) {
                if (file.isDirectory()) {
                    deleteAllFilesInFolder(file.toString());
                }
                file.delete();
            }
            folderPath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Zip folder.
     *
     * @param srcFolder   the src folder
     * @param destZipFile the dest zip file
     */
    public static void zipFolder(final String srcFolder, final String destZipFile) {

        try {
            final FileOutputStream fileWriter = new FileOutputStream(destZipFile);
            final ZipOutputStream zip = new ZipOutputStream(fileWriter);
            addFolderToZip("", srcFolder, zip);
            zip.flush();
            zip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the method name of property.
     *
     * @param testClassName the test class name
     * @return the method name of property
     */
    public Properties getMethodNameOfProperty(final String testClassName) {
        Properties mProperties = new Properties();
        InputStream in = getClass().getResourceAsStream("/testdata/suite/properties/" + testClassName + ".properties");
        if (in != null) {
            try {
                mProperties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mProperties;
    }


    public Properties getMethodNameOfPropertyviaFile(final String testClassName) {
        Properties mProperties = new Properties ( );
        try {
            File file = new File ( "testdata/suite/properties/" + testClassName + ".properties" );
            InputStream in = new FileInputStream ( file );
            if ( in != null ) {
                try {
                    mProperties.load ( in );
                } catch ( IOException e ) {
                    e.printStackTrace ( );
                }
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace ();
        }
        return mProperties;
    }

    public Properties readPropertyFromJar(String filename) throws IOException {

        InputStream is = getClass().getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        isr.close();
        is.close();

        String props = sb.toString();
        Properties properties = new Properties();
        properties.load(new StringReader(props));
        return properties;
    }

    /**
     * Adds the folder to zip.
     *
     * @param path      the path
     * @param srcFolder the src folder
     * @param zip       the zip
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) {

        final File folder = new File(srcFolder);

        if (folder.list().length == 0) {
            addFileToZip(path, srcFolder, zip, true);
        } else {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                } else {
                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }

    /**
     * Adds the file to zip.
     *
     * @param path    the path
     * @param srcFile the src file
     * @param zip     the zip
     * @param flag    the flag
     */
    private static void addFileToZip(final String path, final String srcFile, final ZipOutputStream zip, boolean flag) {

        try {
            final File folder = new File(srcFile);

            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            } else {
                final byte[] buf = new byte[1024];
                int len;
                final FileInputStream inputStream = new FileInputStream(srcFile);
                zip.putNextEntry(
                        new ZipEntry(new StringBuilder(path).append(SLASH).append(folder.getName()).toString()));

                while (inputStream.read(buf) > 0) {
                    len = inputStream.read(buf);
                    zip.write(buf, 0, len);
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete file.
     *
     * @param filePath        the file path
     * @param partialFileName the partial file name
     */
    public static void deleteFile(String filePath, String partialFileName) {

        File file = new File(filePath);
        File[] fileList = file.listFiles();
        for (File afile : fileList) {

            if (afile.getName().contains(partialFileName)) {
                afile.delete();
                LogUtil.log("Deleted File name: " + afile.getName().toString(), LogLevel.LOW);

            }
        }
    }

    /**
     * Gets the full file name.
     *
     * @param filePath        the file path
     * @param partialFileName the partial file name
     * @return the full file name
     */
    public static String getFullFileName(String filePath, String partialFileName) {

        String fileName = null;
        File file = new File(filePath);
        File[] fileList = file.listFiles();
        Assert.assertTrue(fileList.length!=0, "No Files Present in the Directory");
        for (File afile : fileList) {
            LogUtil.log("The file Name "+afile,LogLevel.LOW);
            if (afile.getName().contains(partialFileName)) {
                LogUtil.log("Full file name: " + afile.getName().toString(), LogLevel.LOW);
                fileName = afile.getName().toString();
                break;

            }
        }
        return fileName;
    }

    public static void addContentToFile(String filePath, String content) throws Exception {

        FileWriter fileWriter = null;
        BufferedWriter bufferWriter = null;

        try {

            fileWriter = new FileWriter(filePath);
            bufferWriter = new BufferedWriter(fileWriter);

            bufferWriter.write(content);
        } finally {

            if (bufferWriter != null) {
                bufferWriter.close();
            }

            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    /**
     * Extract jar.
     *
     * @param jar         the jar location
     * @param destination the destination
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void extractJar(String jar, String destination) throws IOException {

        LogUtil.log("Jar Path: " + jar, LogLevel.LOW);
        JarFile jarFile = new JarFile(jar);
        Enumeration<JarEntry> enumEntries = jarFile.entries();
        while (enumEntries.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumEntries.nextElement();
            File file = new File(destination + File.separator + jarEntry.getName());

            if (jarEntry.isDirectory()) {
                file.mkdir();
                continue;
            }
            if (file.exists()) {
                LogUtil.log("File Exist: " + file, LogLevel.LOW);
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                FileOutputStream outputStream = new FileOutputStream(file);
                while (inputStream.available() > 0) {
                    outputStream.write(inputStream.read());
                }

                outputStream.close();
                inputStream.close();
            }
        }
        jarFile.close();
    }

    public static void writePdfFileFromBrowser(WebDriver driver, String filePath) {

        PageUtil.switchToNextWindow(driver);
        LogUtil.log("Switched to new window", LogLevel.HIGH);
        URL pdfurl;
        try {

            String url = driver.getCurrentUrl();
            LogUtil.log("URL : " + url, LogLevel.HIGH);
            pdfurl = new URL(url);
            URLConnection conn = pdfurl.openConnection();
            Set<Cookie> allCookies = driver.manage().getCookies();
            String cookies = "";
            for (Cookie cookie : allCookies) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
            }
            conn.setRequestProperty("Cookie", cookies);

            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            BufferedOutputStream bos = null;
            File filePdf = new File(filePath);
            filePdf.delete();
            createOrRetrieve(filePath);
            if (!filePdf.exists()) {

                filePdf.createNewFile();
                bos = new BufferedOutputStream(new FileOutputStream(filePath));
                int data;
                while ((data = bis.read()) != -1) {
                    bos.write(data);
                }

                LogUtil.log("Stream data" + bos, LogLevel.HIGH);

                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }

            }
        } catch (Exception e) {
            LogUtil.log("Sorry, Writing issue in File '" + filePath + "'", LogLevel.HIGH);
            LogUtil.log("Error : " + e.getMessage(), LogLevel.HIGH);
            e.printStackTrace();
        }

        driver.close();
        PageUtil.switchToNextWindow(driver);
        LogUtil.log("Driver Closed", LogLevel.HIGH);

    }

    private static File createOrRetrieve(String target) throws IOException {

        String changedir = "";
        if (target.contains("\\")) {
            changedir = target.substring(0, target.lastIndexOf("\\"));

        } else {
            changedir = target.substring(0, target.lastIndexOf("/"));
        }

        final Path path = Paths.get(changedir);
        try {
            if (Files.notExists(path)) {
                LogUtil.log("Target file " + changedir, LogLevel.HIGH);
                return Files.createFile(Files.createDirectories(path)).toFile();
            }
            LogUtil.log("Target file \"" + changedir + "\" will be retrieved.", LogLevel.HIGH);
        } catch (FileAlreadyExistsException e) {
            LogUtil.log("Already Exists", LogLevel.HIGH);
        }
        return path.toFile();
    }

    /**
     * Gets the file from input stream.
     *
     * @param is       the is
     * @param filePath the file path
     * @return the file from input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static File getFileFromInputStream(InputStream is, String filePath) throws IOException {

        LogUtil.log("Create file from stream data" + filePath, LogLevel.LOW);
        File file = new File(filePath);
        FileUtils.copyInputStreamToFile(is, file);
        LogUtil.log("File Path: " + file, LogLevel.LOW);
        return file;

    }

    /**
     * Read file into list.
     *
     * @param file the file
     * @return the list
     */
    public static List<String> readFileIntoList(String file)  {
        List<String> list =  null;
        try {
            InputStream inputStream = FileUtil.getInputStreamData(file);
            Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset())).lines();
            list =  new ArrayList<>();
            stream.forEachOrdered(list :: add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * Read file into property.
     *
     * @param file the file
     * @return the properties
     */
    public static Properties readFileIntoProperty(String file)  {
        Properties props = new Properties();
        try {
            InputStream inputStream = FileUtil.getInputStreamData(file);

            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * Checks if is file exists.
     *
     * @param filePath the file path
     * @return true, if is file exists
     */
    public static boolean isFileExists(String filePath)
    {
        Path path = Paths.get(filePath);
        boolean exists = Files.exists(path);
        return exists;
    }
}
