package com.baidu.test.aop.buildsrc;

import android.app.Activity;

import android.os.Bundle;

import java.io.File;


import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 测试Javassist 注入
 * Created by chenzhiqin on 17/3/1.
 */

public class TestJavassistInject {

    private static ClassPool pool = ClassPool.getDefault();
    private static String injectStr = "System.out.println(\"Test Javassist Inject \" ); ";

    private static String fieldT1Str = "private int t1;";
    private static String fieldT2Str = "private int t2;";
    private static String injectTimeBefore = "t1 = System.currentTimeMillis();";
    private static String injectTimeAfter = "t2 = System.currentTimeMillis();\n" +
            "        long t = t2-t1;\n" +
            "        System.out.println(\"ActivityTime\"+ this.toString()+\", oncreate \" + t);";


    public static void injectDir(String topPath,String path, String packageName) throws NotFoundException {
        pool.appendClassPath(path);
        File dir = new File(path);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if(file.isDirectory()) {
                    injectDir(topPath,file.getPath(),packageName);
                } else {
                    injectFile(topPath,file, packageName);
                }
            }
        }

    }

    private static void injectFile(String topPath, File file, String packageName)  {
        try {
            String filePath = file.getAbsolutePath();
            System.out.println("filePath:" + filePath);
            //确保当前文件是class文件，并且不是系统自动生成的class文件,且不是内部类
            if (filePath.endsWith(".class")
                    && !filePath.contains("R.class")
                    && !filePath.contains("BuildConfig.class")
                    && !filePath.contains("$")) {
                int index = filePath.indexOf(packageName);
                boolean isPackageClass = index != -1; // 是这个包里面的class
                if (isPackageClass) {
                    int end = filePath.length() - 6;// .class = 6
                    String className = filePath.substring(index, end)
                            .replace('\\', '.').replace('/', '.');
                    System.out.println("className:" + className);
                    // 开始修改class文件
                    CtClass c = pool.getCtClass(className);
                    if (c.isFrozen()) {
                        c.defrost();
                    }
                    CtConstructor[] cts = c.getDeclaredConstructors();
                    if (cts == null || cts.length <= 0) {
                        CtConstructor ctConstructor = new CtConstructor(new CtClass[0], c);
                        ctConstructor.insertBeforeBody(injectStr);
                        c.addConstructor(ctConstructor);
                    } else {
                        cts[0].insertBeforeBody(injectStr);
                    }
                    c.writeFile(topPath);
                    c.detach();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void injectDir(String topPath,String path, String packageName,String methodName) {
        try {
            pool.appendClassPath(path);
            ClassClassPath activityCcpath = new ClassClassPath(Activity.class);
            pool.insertClassPath(activityCcpath);
            ClassClassPath bundleCcpath = new ClassClassPath(Bundle.class);
            pool.insertClassPath(bundleCcpath);
            pool.insertClassPath(new ClassClassPath(android.util.Log.class));
            pool.insertClassPath(new ClassClassPath(android.support.v4.app.FragmentActivity.class));

            File dir = new File(path);
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isDirectory()) {
                        injectDir(topPath, file.getPath(), packageName, methodName);
                    } else {
                        injectActivity(topPath, file, packageName, methodName);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void injectActivity(String topPath, File file, String packageName, String methodName) {
        try {
            String filePath = file.getAbsolutePath();
            System.out.println("filePath:" + filePath);
            //确保当前文件是class文件，并且不是系统自动生成的class文件,且不是内部类
            if (filePath.endsWith(".class")
                    && !filePath.contains("R.class")
                    && !filePath.contains("BuildConfig.class")
                    && !filePath.contains("$")) {
                int index = filePath.indexOf(packageName);
                boolean isPackageClass = index != -1; // 是这个包里面的class
                if (isPackageClass) {
                    int end = filePath.length() - 6;// .class = 6
                    String className = filePath.substring(index, end)
                            .replace('\\', '.').replace('/', '.');
                    System.out.println("className:" + className);

                    CtClass activityClass = pool.getCtClass("android.app.Activity");
                    // 开始修改class文件
                    CtClass c = pool.getCtClass(className);
//                    if (!c.getSuperclass().equals(activityClass)) {
//                        return;
//                    }
                    if (c.isFrozen()) {
                        c.defrost();
                    }
                    CtMethod ctMethod = c.getDeclaredMethod(methodName);
                    if (ctMethod != null) {
                        c.addField(CtField.make(fieldT1Str, c));
                        c.addField(CtField.make(fieldT2Str, c));
                        System.out.println("injectActivity");
                        ctMethod.insertBefore(injectTimeBefore);
                        ctMethod.insertAfter(injectTimeAfter);
                    }

                    c.writeFile(topPath);
                    c.detach();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
