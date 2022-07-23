package miku.Core;


import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class ClassTransformer implements IClassTransformer {
    protected final String CoverMC = "miku/Core/Covers/MC";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("bib") || transformedName.equals("net.minecraft.client.Minecraft")) {
            final ClassReader classReader = new ClassReader(basicClass);
            final ClassWriter classWriter = new ClassWriter(classReader, 1);
            final ClassVisitor classVisitor = new ClassVisitor(262144, classWriter) {
                public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                    if ((name.equals("t") && desc.equals("()V")) || name.equals("runTick")) {
                        return new MethodVisitor(262144, this.cv.visitMethod(access, name, desc, signature, exceptions)) {
                            public void visitCode() {
                                this.mv.visitVarInsn(25, 0);
                                this.mv.visitMethodInsn(184, CoverMC, "runTick", "(Lnet/minecraft/client/Minecraft;)V", false);
                            }
                        };
                    }
                    return this.cv.visitMethod(access, name, desc, signature, exceptions);
                }
            };
            classReader.accept(classVisitor, 262144);
            return classWriter.toByteArray();
        }
        if (transformedName.equals("net.minecraft.client.multiplayer.WorldClient")) {
            final ClassReader classReader = new ClassReader(basicClass);
            final ClassWriter classWriter = new ClassWriter(classReader, 1);
            final ClassVisitor classVisitor = new ClassVisitor(262144, classWriter) {
                public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                    if ((name.equals("e") && desc.equals("(I)Lvg;")) || name.equals("removeEntityFromWorld")) {
                        return new MethodVisitor(262144, this.cv.visitMethod(access, name, desc, signature, exceptions)) {
                            public void visitCode() {
                                this.mv.visitVarInsn(25, 0);
                                this.mv.visitVarInsn(21, 1);
                                this.mv.visitMethodInsn(184, CoverMC, "removeEntityFromWorld", "(Lnet/minecraft/client/multiplayer/WorldClient;I)V", false);
                            }
                        };
                    }
                    return this.cv.visitMethod(access, name, desc, signature, exceptions);
                }
            };
            classReader.accept(classVisitor, 262144);
            return classWriter.toByteArray();
        }
        if (transformedName.equals("net.minecraft.world.World")) {
            final ClassReader classReader = new ClassReader(basicClass);
            final ClassWriter classWriter = new ClassWriter(classReader, 1);
            final ClassVisitor classVisitor = new ClassVisitor(262144, classWriter) {
                public FieldVisitor visitField(int access, final String name, final String desc, final String signature, final Object value) {
                    if (name.equals("f")) {
                        access = 1;
                    }
                    return this.cv.visitField(access, name, desc, signature, value);
                }

                public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                    if ((name.equals("k") && desc.equals("()V")) || name.equals("updateEntities")) {
                        return new MethodVisitor(262144, this.cv.visitMethod(access, name, desc, signature, exceptions)) {
                            public void visitCode() {
                                this.mv.visitVarInsn(25, 0);
                                this.mv.visitMethodInsn(184, CoverMC, "updateEntities", "(Lnet/minecraft/world/World;)V", false);
                            }
                        };
                    }
                    return this.cv.visitMethod(access, name, desc, signature, exceptions);
                }
            };
            classReader.accept(classVisitor, 262144);
            return classWriter.toByteArray();
        }
        return basicClass;
    }
}