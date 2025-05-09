package com.ecommerce.util;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class StringUtil {

    public static String addSpace(String word, int noOfCharac) {

        int wordLength = word.length();
        int noOfSpace = noOfCharac - wordLength;
        if (noOfSpace < 0) {
            return word;
        } else {
            String space = "";
            for (int val = 0; val < noOfSpace; val++) {
                space += " ";
            }
            word = word + space;
        }
        return word;
    }

    /**
     * Adds the indent.
     *
     * @param indentLevel the indent level
     * @return the string
     */
    public static String addIndent(int indentLevel) {

        indentLevel += indentLevel;
        final StringBuilder space = new StringBuilder("");
        for (int i = 0; i < indentLevel; i++) {
            space.append("  ");
        }
        return space.toString();
    }

    /**
     * Adds the underline to log statements.
     *
     * @param indentLevel the indent level
     * @return the string
     */
    public static String addUnderline(final int indentLevel) {

        final StringBuilder space = new StringBuilder("");
        for (int i = 0; i < indentLevel; i++) {
            space.append('-');
        }
        return space.toString();
    }

    /**
     * Change html to plain.
     *
     * @param text the text
     * @return the string
     */
    public static String changeHtmlToPlain(final String text) {

        String plainText = text.replaceAll("\\<.*?\\>", "");
        return plainText;
    }

    /**
     * Checks if is null or white space.
     *
     * @param string the string
     * @return true, if is null or white space
     */
    public static boolean isNullOrWhiteSpace(final String string) {

        try {
            return Strings.isNullOrEmpty(string) && string.trim().isEmpty();
        } catch (NullPointerException npe) {
            return Strings.isNullOrEmpty(string);
        }
    }

    /**
     * Checks for value.
     *
     * @param string the string
     * @return true, if successful
     */
    public static boolean hasProperValue(final String string) {

        try {
            return !Strings.isNullOrEmpty(string) && !string.equalsIgnoreCase("null") && !string.trim().isEmpty() && string.trim().length()>0;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Checks for proper required value.
     *
     * @param string the string
     * @param requiredValues the required values
     * @return true, if successful
     */
    public static boolean hasProperRequiredValueInList(final String string, List<String> requiredValues) {

        try {
            return !Strings.isNullOrEmpty(string) && !string.equalsIgnoreCase("null") && !string.trim().isEmpty() && string.trim().length()>0 && requiredValues.stream().anyMatch(x -> x.equalsIgnoreCase(string));
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Format get parameter.
     *
     * @param param the param
     * @return the string
     */
    public static String formatGetParameter(Map<String, Object> param) {

        StringBuilder result = new StringBuilder();

        for (String key : param.keySet()) {

            if (result.length() > 0) {
                result.append("&");
            }

            result.append(key + "=" + param.get(key).toString());
        }

        return result.toString();
    }

    /**
     * Gets the digits from string.
     *
     * @param s the s
     * @return the digits from string
     */
    public static int getDigitsFromString(String s) {
        return Integer.parseInt(CharMatcher.digit().retainFrom(s));
    }

    /**
     * Gets the digits from string.
     *
     * @param list the list
     * @return the digits from string
     */
    public static List<Integer> getDigitsFromString(List<String> list) {
        return list.parallelStream().map(StringUtil::getDigitsFromString).collect(Collectors.toList());
    }

    /**
     * Gets the digits from web elements.
     *
     * @param list the list
     * @return the digits from web elements
     */
    public static List<Integer> getDigitsFromWebElements(List<WebElement> list) {
        return getTextFromWebElements(list).parallelStream().map(StringUtil::getDigitsFromString).collect(Collectors.toList());
    }

    /**
     * Gets the text from web elements.
     *
     * @param source the source
     * @return the text from web elements
     */
    public static List<String> getTextFromWebElements(List<WebElement> source) {
        return source.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    /**
     * Gets the text from web elements.
     *
     * @param source the source
     * @return the text from web elements
     */
    public static String getTextFromWebElements(WebElement source) {
        return Objects.nonNull(source)? source.getText() : StringUtils.EMPTY;
    }

    /**
     * Gets the specific text from string file.
     *
     * @param content the content
     * @param specificDataLine the specific data line
     * @return the specific text from string file
     */
    public static String getSpecificTextFromStringFile(String content, String specificDataLine)
    {
        AtomicReference<String> dataVal = new AtomicReference<>();

        new BufferedReader(new StringReader(content)).lines().forEach (
                (line) -> {
                    if(line.contains(specificDataLine))
                    {
                        dataVal.set(line);
                        return;
                    }
                }
        );
        return dataVal.get();
    }

    /**
     * Gets the substring count.
     *
     * @param str the str
     * @param sub the sub
     * @return the substring count
     */
    public static int getSubstringCount(String str, String sub) {
        if (str.contains(sub)) {
            return 1 + getSubstringCount(str.replaceFirst(sub, ""), sub);
        }
        return 0;
    }

    public static String fineTune(String text)
    {
        if(text==null||text.isEmpty ())
        {
            text="";
        }else{
            text=text.trim ();
        }

        return text;
    }

    /**
     * Get number with CurrentTime in millis.
     *
     * @return number
     */
    public static String getNumberWithCurrentTimeInMillis() {

        String number = String.valueOf(System.currentTimeMillis());
        return number;
    }

    /**
     * Gets the string with specific characters deleted.
     *
     * @param text the text
     * @param startIndex the start index
     * @param endIndex the end index
     * @return the string with specific characters deleted
     */
    public static String getStringWithSpecificCharactersDeleted(String text, int startIndex, int endIndex) {

        StringBuilder sb = new StringBuilder(text);
        String formattedText = new String(sb.delete(startIndex, endIndex));
        return formattedText;
    }
}
