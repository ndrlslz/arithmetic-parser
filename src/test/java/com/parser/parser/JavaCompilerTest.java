package com.parser.parser;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaCompilerTest {
    private JavaCompiler javaCompiler;

    @Before
    public void setUp() throws Exception {
        javaCompiler = new JavaCompiler("CalculatorTool");
        }


    @Test
    public void testCalculator() throws Exception {
        String className = "CalculatorTool";
        byte[] data = javaCompiler.generate("(1 + 2) * 3 + 4 / 2");
        generateClassFile(className, data);
        Calculator calculator = generateClass(className, data);
        assertEquals(11, calculator.calc());
    }

    private static Calculator generateClass(String className, byte[] data) throws IllegalAccessException, InstantiationException {
        Class clazz = new TestClassLoader().defineClass(className, data);
        return (Calculator) clazz.newInstance();

    }

    private static void generateClassFile(String className, byte[] data) throws IOException, IllegalAccessException, InstantiationException {
        File file = new File("gen/" + className + ".class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}

class TestClassLoader extends ClassLoader {
    Class defineClass(String name, byte[] value) {
        return defineClass(name, value, 0, value.length);
    }
}

