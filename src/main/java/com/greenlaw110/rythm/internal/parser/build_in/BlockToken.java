package com.greenlaw110.rythm.internal.parser.build_in;

import com.greenlaw110.rythm.spi.IBlockHandler;
import com.greenlaw110.rythm.spi.IContext;
import com.greenlaw110.rythm.spi.Token;

public class BlockToken extends Token implements IBlockHandler {

    public BlockToken(String s, IContext context) {
        super(s, context);
        context.openBlock(this);
    }

    @Override
    public void openBlock() {
    }

    @Override
    public String closeBlock() {
        return "\np('}');\n";
    }

}
