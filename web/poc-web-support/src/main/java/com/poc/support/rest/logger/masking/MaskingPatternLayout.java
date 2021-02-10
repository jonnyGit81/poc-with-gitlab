package com.poc.support.rest.logger.masking;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// see https://stackoverflow.com/questions/25277930/mask-sensitive-data-in-logs-with-logback/25280066

public class MaskingPatternLayout extends PatternLayout {

    private String patternsProperty;
    private Optional<Pattern> pattern;

    public String getPatternsProperty() {
        return patternsProperty;
    }

    public void setPatternsProperty(String patternsProperty) {
        this.patternsProperty = patternsProperty;
        if (this.patternsProperty != null) {
            this.pattern = Optional.of(Pattern.compile(patternsProperty, Pattern.MULTILINE));
        } else {
            this.pattern = Optional.empty();
        }
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        final StringBuilder message = new StringBuilder(super.doLayout(event));

        if (pattern.isPresent()) {
            Matcher matcher = pattern.get().matcher(message);
            while (matcher.find()) {

                int group = 1;
                while (group <= matcher.groupCount()) {
                    if (matcher.group(group) != null) {
                        for (int i = matcher.start(group); i < matcher.end(group); i++) {
                            message.setCharAt(i, '*');
                        }
                    }
                    group++;
                }
            }
        }
        return message.toString();
    }


}


