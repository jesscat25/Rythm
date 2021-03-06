package com.greenlaw110.rythm.internal.parser.build_in;

import com.greenlaw110.rythm.internal.Keyword;
import com.greenlaw110.rythm.internal.dialect.Rythm;
import com.greenlaw110.rythm.internal.parser.ParserBase;
import com.greenlaw110.rythm.spi.IContext;
import com.greenlaw110.rythm.spi.IParser;
import com.greenlaw110.rythm.spi.Token;
import com.greenlaw110.rythm.utils.TextBuilder;
import com.stevesoft.pat.Regex;

public class VerbatimParser extends KeywordParserFactory {
    
    private static final String R = "(%s%s\\s*(\\(\\s*\\))?\\s*((?@{})))";

    public VerbatimParser() {
    }
    
    protected String patternStr() {
        return R;
    }
    
    public IParser create(IContext c) {
        return new ParserBase(c) {
            public TextBuilder go() {
                Regex r = reg(dialect());
                if (r.search(remain())) {
                    step(r.stringMatched().length());
                    String s0 = r.stringMatched(3);
                    s0 = s0.substring(1); // strip '{'
                    s0 = s0.substring(0, s0.length() - 1); // strip '}'
                    final String s = s0;
                    return new Token(s, ctx(), true);
                }
                return null;
            }
        };
    }

    @Override
    public Keyword keyword() {
        return Keyword.VERBATIM;
    }

    public static void main(String[] args) {
        Regex r = new VerbatimParser().reg(new Rythm());
        String s = "@verbatim(){\n\tHello world!\n@each X {abc;} \n} xyz";
        if (r.search(s)) {
            String s0 = r.stringMatched(3);
            s0 = s0.substring(1); // strip '{'
            s0 = s0.substring(0, s0.length() - 1); // strip '}'
            System.out.println(r.stringMatched());
            System.out.println(s0);
        }
    }
}
