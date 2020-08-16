package com.tal.generate.pdf.builder;


import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.tal.generate.pdf.util.FileUtil;
import org.assertj.core.util.CheckReturnValue;
import org.springframework.lang.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhaiyarong
 * 脚本执行命令
 */
public class CommandBuilder {

    URI inputs;

    String outputs;

    String scriptPath;

    final List<String> extra_args = new ArrayList<>();

    public CommandBuilder addOutput(String filename) {
        checkNotEmpty(filename, "output filename can't  be null or empty");
        this.outputs = filename;
        return this;
    }

    public CommandBuilder addHtmlPath(URI htmlUri) {
        checkNotNull(htmlUri, "uri  can't  be null or empty");
        this.inputs = htmlUri;
        return this;
    }

    public CommandBuilder addExtraArgs(String... values) {
        checkArgument(values.length > 0, "one or more values must be supplied");
        checkNotEmpty(values[0], "first extra arg may not be empty");

        for (String value : values) {
            extra_args.add(checkNotNull(value));
        }

        return this;
    }

    public CommandBuilder addScriptPath(String scriptPath) {
        checkNotEmpty(scriptPath, "static file path can't be null or empty");
        Preconditions.checkArgument(FileUtil.isFileExist(scriptPath), "static file is not exist, please check this");
        this.scriptPath = scriptPath;
        return this;
    }

    public static String checkNotEmpty(String arg, @Nullable Object errorMessage) {
        boolean empty = Strings.isNullOrEmpty(arg) || CharMatcher.whitespace().matchesAllOf(arg);
        checkArgument(!empty, errorMessage);
        return arg;
    }

    @CheckReturnValue
    public List<String> build() {

        ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

        Preconditions.checkArgument(FileUtil.isFileExist(scriptPath), "static file is not exist, please check this");
        Preconditions.checkArgument(!(inputs == null), "At least one input must be specified");
        Preconditions.checkArgument(!(outputs == null), "At least one output must be specified");
        if (scriptPath != null) {
            args.add(scriptPath);
        }

        if (inputs != null) {
            args.add("-i", inputs.toString());
        }

        if (outputs != null) {
            args.add("-o", outputs);
        }

        args.addAll(extra_args);

        return args.build();
    }

    @Override
    public String toString() {
        List<String> args = this.build();
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }
        return stringBuilder.toString();
    }
}
