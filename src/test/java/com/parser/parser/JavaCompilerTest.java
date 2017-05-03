package com.parser.parser;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaCompilerTest {
    @Test
    public void testCalculator() throws Exception {
        Calculator calculator = generateClass("CalculatorTool", "(1 + 2) * 3 + 4 / 2");
        assertEquals(11, calculator.calc());
    }

    private static Calculator generateClass(String className, String source) throws IllegalAccessException, InstantiationException, IOException {
        JavaCompiler javaCompiler = new JavaCompiler(className);
        byte[] data = javaCompiler.generate(source);
        generateClassFile(className, data);
        Class clazz = new TestClassLoader().defineClass(className, data);
        return (Calculator) clazz.newInstance();

    }

    private static void generateClassFile(String className, byte[] data) throws IOException, IllegalAccessException, InstantiationException {
        File dir = new File("generatedClass/");
        dir.mkdir();
        File file = new File(dir.getName() + "/" + className + ".class");
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

