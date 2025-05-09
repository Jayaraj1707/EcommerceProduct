package com.ecommerce.util;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class TimeoutHelper {

    /**
     * timeout in seconds.
     */
    private long timeoutSeconds;

    /**
     * timeout in milliseconds.
     */
    private long waitMilliseconds;

    /**
     * Initializes a new instance of the TimeoutHelper class.
     *
     * @param timeoutSeconds the expected timeout in seconds.
     * @param waitMilliseconds the wait interval in milliseconds.
     */
    public TimeoutHelper(long timeoutSeconds, long waitMilliseconds){
        this.timeoutSeconds = TimeUnit.SECONDS.toNanos(timeoutSeconds);
        this.waitMilliseconds =TimeUnit.MILLISECONDS.toMillis(waitMilliseconds);
    }

    /**
     * Initializes a new instance of the TimeoutHelper class.
     *
     * @param timeoutSeconds the expected timeout in seconds.
     */
    public TimeoutHelper(long timeoutSeconds){
        this.timeoutSeconds =TimeUnit.SECONDS.toNanos(timeoutSeconds);
        this.waitMilliseconds =TimeUnit.MILLISECONDS.toMillis(200);
    }


    /**
     * Waits for the condition to be met.
     *
     * @param conditionToCheck The functional method with the condition.
     * @return true if the condition is met otherwise false.
     */
    public Boolean waitForCondition(BooleanSupplier conditionToCheck){
        long startTime = System.nanoTime();
        LogUtil.log("Waiting for condition...", LogLevel.MEDIUM);
        long elapsedTime;
        do {
            Boolean conditionResult= conditionToCheck.getAsBoolean();
            if(conditionResult){
                return true;
            }

            waitMilliseconds(this.waitMilliseconds);
            elapsedTime=(System.nanoTime() - startTime);
        }while( elapsedTime < this.timeoutSeconds);

        return false;
    }

    /**
     * Waits for the given milliseconds.
     *
     * @param millisecondsToWait The milliseconds.
     */
    private void waitMilliseconds(long millisecondsToWait){
        try{
            TimeUnit.MILLISECONDS.sleep(millisecondsToWait);
        }catch (Exception ex){
            LogUtil.log("Error waiting milliseconds: " + ex.getMessage(), LogLevel.LOW);
        }
    }
}
