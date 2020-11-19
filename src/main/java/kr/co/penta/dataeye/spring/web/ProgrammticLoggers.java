package kr.co.penta.dataeye.spring.web;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.google.common.base.Joiner;
import org.slf4j.LoggerFactory;

public enum ProgrammticLoggers {
    auth, // 인증 관련 로그
    error // 에러 관련 로그
    ;

    private final Joiner joiner = Joiner.on("/");

    private final LoggerContext loggerContext
            = (LoggerContext) LoggerFactory.getILoggerFactory();

    private final String PATTERN
            = "%d{yyyy/MM/dd} %-5p %c{0}\t[ip=%X{IP}] %m%n";

    private final String LOG_ROOTPATH = "/log/weblogic/bdp_portal/dataeye2";

    private ProgrammticLoggers() {

        // 로그 파일 경로 생성
        final String filepath
                = joiner.join(LOG_ROOTPATH, this.name()) + ".log";

        // Appender 설정
        RollingFileAppender rfAppender = new RollingFileAppender();
        rfAppender.setContext(loggerContext);
        rfAppender.setFile(filepath);

        // Policy 설정
        TimeBasedRollingPolicy rollingPolicy
                = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(rfAppender);
        rollingPolicy.setFileNamePattern(filepath + ".%d{yyMMdd}.gz");
        rollingPolicy.setMaxHistory(30);
        rollingPolicy.start();

        // Encoder 설정
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(PATTERN);
        encoder.start();

        // Appender에 Policy와 Encoder를 연결
        rfAppender.setEncoder(encoder);
        rfAppender.setRollingPolicy(rollingPolicy);
        rfAppender.start();

        // 해당 enum의 이름으로 Logger를 만들고 Appender를 연결해준다.
        Logger logbackLogger = loggerContext.getLogger(this.name());
        logbackLogger.setLevel(Level.INFO);
        logbackLogger.setAdditive(false);
        logbackLogger.addAppender(rfAppender);
    }
}
