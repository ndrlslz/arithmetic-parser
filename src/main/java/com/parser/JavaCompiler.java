package com.parser;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class JavaCompiler implements Visitor{
    private ClassWriter classWriter;
    private MethodVisitor methodVisitor;

    public JavaCompiler() {
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        classWriter.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC,
                "CalculatorTool", null, "java/lang/Object", new String[]{"com/parser/Calculator"});

        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();


        methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "calc", "()I", null, null);
        new Parser(new Lexer("(1 + 2)*3")).run().accept(this);


        methodVisitor.visitInsn(IRETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();

        classWriter.visitEnd();
    }


    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        JavaCompiler javaCompiler = new JavaCompiler();

        javaCompiler.generate("CalculatorTool");

    }

    private class TestClassLoader extends ClassLoader {
        Class defineClass(String name, byte[] value) {
            return defineClass(name, value, 0, value.length);
        }
    }

    public void generate(String className) throws IOException, IllegalAccessException, InstantiationException {
        byte[] data = classWriter.toByteArray();
        File file = new File("gen/" + className + ".class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();

        Class c = new TestClassLoader().defineClass(className, data);
        Calculator calculator = (Calculator) c.newInstance();
        System.out.println(calculator.calc());
    }

    @Override
    public Object visit(Node node) {
        return node.accept(this);
    }

    @Override
    public Object visit(NumberNode numberNode) {
        methodVisitor.visitLdcInsn(numberNode.getNumber());
        return null;
    }

    @Override
    public Object visit(ExpressionNode expressionNode) {
        expressionNode.left.accept(this);
        expressionNode.right.accept(this);
        switch (expressionNode.operator) {
            case '+':
                methodVisitor.visitInsn(IADD);
                break;
            case '-':
                methodVisitor.visitInsn(ISUB);
                break;
            case '*':
                methodVisitor.visitInsn(IMUL);
                break;
            case '/':
                methodVisitor.visitInsn(IDIV);
                break;
            default:
                throw new RuntimeException("cannot identify operator: " + expressionNode.operator);
        }
        return null;
    }
}
