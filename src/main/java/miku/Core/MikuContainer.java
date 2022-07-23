package miku.Core;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

import java.util.Collections;

public class MikuContainer extends DummyModContainer {
    public MikuContainer() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "miku";
        meta.name = "MikuCore";
        meta.version = "1.0-pre1";
        meta.authorList = Collections.singletonList("mcst12345");
        meta.description = "MikuCore";
        meta.url = "https://mcst12345.top:8443";
    }
}
