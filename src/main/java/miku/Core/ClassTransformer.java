package miku.Core;


import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return basicClass;
    }
}