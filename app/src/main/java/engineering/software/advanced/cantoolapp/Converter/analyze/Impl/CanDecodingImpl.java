package engineering.software.advanced.cantoolapp.Converter.analyze.Impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.Converter.analyze.CanDecoding;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.analyze.Entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class CanDecodingImpl implements CanDecoding {
    @Override
    public CanMessage messageDecoding(String message) {
        Pattern pattern = Pattern.compile("^(.*) (\\d+) (.*) ?(:) ?(\\d+) (.*)$");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
//            System.out.println(matcher.groupCount());
//            for (int i = 0; i <= matcher.groupCount(); i++) {
//                System.out.println("group " + i + ": " + matcher.group(i));
//            }

            CanMessage canMessage = new CanMessage(
                    matcher.group(1),
                    Long.parseLong(matcher.group(2)),
                    matcher.group(3),
                    matcher.group(4),
                    Integer.parseInt(matcher.group(5)),
                    matcher.group(6)
            );
            return canMessage;
        }
        else {
            return null;
        }
    }

    @Override
    public CanSignal signalDecoding(String signal) {
        Pattern pattern = Pattern.compile(
                "^(.+) (.+) ? (:) ?(\\d+\\|\\d+@\\d\\+) \\((.+),(.+)\\) \\[(.+)\\|(.+)\\] (\".*\") +(.+)$");
        Matcher matcher = pattern.matcher(signal);
        if (matcher.find()) {

//            System.out.println(matcher.groupCount());
//            for (int i = 0; i <= matcher.groupCount(); i++) {
//                System.out.println("group " + i + ": " + matcher.group(i));
//            }

            CanSignal canSignal = new CanSignal(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4),
                    Double.parseDouble(matcher.group(5)),
                    Double.parseDouble(matcher.group(6)),
                    Double.parseDouble(matcher.group(7)),
                    Double.parseDouble(matcher.group(8)),
                    matcher.group(9),
                    matcher.group(10)
            );
            return canSignal;
        }
        else {
            return null;
        }
    }
}
