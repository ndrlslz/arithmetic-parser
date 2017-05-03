package com.parser.parser;

import com.parser.ast.ExpressionNode;
import com.parser.ast.Node;
import com.parser.ast.NumberNode;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

class JavaCompiler implements Visitor {
    private ClassWriter classWriter;
    private MethodVisitor methodVisitor;

    JavaCompiler(String className) {
        this.classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        classWriter.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC,
                className, null, "java/lang/Object", new String[]{"com/parser/parser/Calculator"});

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

    }

    byte[] generate(String source) throws IOException, IllegalAccessException, InstantiationException {
        new Parser(source).run().accept(this);

        methodVisitor.visitInsn(IRETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();

        classWriter.visitEnd();

        return classWriter.toByteArray();
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
