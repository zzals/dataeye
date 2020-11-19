//
// Built on Wed Dec 03 03:19:05 CET 2014 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.status.OnConsoleStatusListener

import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.*

import static System.err

statusListener(OnConsoleStatusListener)

def USER_HOME = System.getProperty("user.home")

err.println "### USER_HOME: ${USER_HOME}"
err.println "### hostname: ${hostname}"

def appenderList = ["ALL_LOG","LOGIN_LOG"]
def LOG_DIR = "/home/pst1552/apache-tomcat-8.5.57/logs"
def consoleAppender = true
def LOG_LEVEL = ERROR

// does hostname match ?
if (hostname =~ /development-.*/) {
    LOG_DIR = "/home/tmp/logs"
    LOG_LEVEL = DEBUG
    consoleAppender = true
    appenderList.add("CONSOLE")
}
else if (hostname =~ /bdptwas.*/) {
    LOG_DIR = "/data/app/dataeye/apache-tomcat-8.0.44/logs/dataeye"
    consoleAppender = true
    appenderList.add("CONSOLE")
}
else {
    LOG_LEVEL = DEBUG
    appenderList.add("CONSOLE")
}

if (consoleAppender) {
    appender("CONSOLE", ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
        }
    }
}

appender("ALL_LOG", RollingFileAppender) {
    file = "${LOG_DIR}/dataeye.log"
    append = true
    encoder(PatternLayoutEncoder) {
        Pattern = "%d %level %thread %mdc %logger - %m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_DIR}/dataeye-%d{yyyy-MM-dd}.log"
    }
}

appender("LOGIN_LOG", RollingFileAppender) {
    file = "${LOG_DIR}/dataeye-login.log"
    append = true 
 
    encoder(PatternLayoutEncoder) {
        Pattern = "%d %level %thread %mdc %logger - %m%n"
    }
    
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_DIR}/dataeye-login.log.%d{yyyy-MM-dd}"
        maxHistory = 10 // 10일간만 보관
    }
 
    // 특정 레벨 이상만 로깅
    filter(ch.qos.logback.classic.filter.ThresholdFilter) {
        level = DEBUG // INFO 이상 레벨만 로깅
    }
}

logger("org.springframework", ERROR, ["ALL_LOG", "CONSOLE"], false)
//logger("org.springframework.web.filter.CommonsRequestLoggingFilter", DEBUG, ["ALL_LOG", "CONSOLE"], false)
logger("org.apache", ERROR, ["ALL_LOG", "CONSOLE"], false)
logger("kr.co.penta.dataeye.spring.web.common.controller.LoginController", DEBUG, ["LOGIN_LOG"], false)
logger("kr.co.penta", DEBUG, ["ALL_LOG", "CONSOLE"], false)
logger("jdbc.sqlonly", DEBUG, ["ALL_LOG", "CONSOLE"], false)

root(LOG_LEVEL, ["CONSOLE"])