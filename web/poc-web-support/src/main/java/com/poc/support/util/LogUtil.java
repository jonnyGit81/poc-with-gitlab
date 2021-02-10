package com.poc.support.util;

import com.poc.support.dto.MsgInfo;
import org.slf4j.Logger;

/**
 * http://development.wombatsecurity.com/development/2018/12/20/json-logging-for-spring-boot/
 */

public enum LogUtil {
    ERROR {
        @Override
        public void apply(Logger log, String message) {
            printLog( () -> log.error(message) );
        }

        @Override
        public void apply(Logger log, MsgInfo msgInfo) {
            printLog( () -> log.error(msgInfo.getMsgFormat(), msgInfo.getDatas()) );
        }

        @Override
        public void apply(Logger log, String message, Throwable throwable) {
            printLog( () -> log.error(message, throwable) );
        }
    },
    WARN {
        @Override
        public void apply(Logger log, String message) {
            printLog( () -> log.warn(message) );
        }

        @Override
        public void apply(Logger log, MsgInfo msgInfo) {
            printLog( () -> log.warn(msgInfo.getMsgFormat(), msgInfo.getDatas()) );
        }

        @Override
        public void apply(Logger log, String message, Throwable throwable) {
            printLog( () -> log.warn(message, throwable) );
        }
    },
    INFO {
        @Override
        public void apply(Logger log, String message) {
            printLog( () -> log.info(message) );
        }

        @Override
        public void apply(Logger log, MsgInfo msgInfo) {
            printLog( () -> log.info(msgInfo.getMsgFormat(), msgInfo.getDatas()) );
        }

        @Override
        public void apply(Logger log, String message, Throwable throwable) {
            printLog( () -> log.info(message, throwable) );
        }
    },
    DEBUG {
        @Override
        public void apply(Logger log, String message) {
            printLog( () -> log.debug(message) );
        }

        @Override
        public void apply(Logger log, MsgInfo msgInfo) {
            printLog( () -> log.debug(msgInfo.getMsgFormat(), msgInfo.getDatas()) );
        }

        @Override
        public void apply(Logger log, String message, Throwable throwable) {
            printLog( () -> log.debug(message, throwable) );
        }
    },
    TRACE {
        @Override
        public void apply(Logger log, String message) {
            printLog( () -> log.trace(message) );
        }

        @Override
        public void apply(Logger log, MsgInfo msgInfo) {
            printLog( () -> log.trace(msgInfo.getMsgFormat(), msgInfo.getDatas()) );
        }

        @Override
        public void apply(Logger log, String message, Throwable throwable) {
            printLog( () -> log.trace(message, throwable) );
        }
    };

    public abstract void apply(final Logger log, final String message);
    public abstract void apply(final Logger log, MsgInfo msgInfo);
    public abstract void apply(final Logger log, final String message, final Throwable throwable);


    private static void printLog(Runnable runnable) {
        runnable.run();
    }
}
