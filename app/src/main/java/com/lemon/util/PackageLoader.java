package com.lemon.util;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.util]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/17 10:01]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/17 10:01]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class PackageLoader {

    public Context mContext;
    public List<String> packages;

    public List<Class> autoLoadClass(){
        List<Class> clsList = new ArrayList<>();
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();
            Object pathList = ReflectionUtils.getFieldValue(classLoader,"pathList");
            if(!ParamUtils.isNull(pathList)){
                Object[]  dexElements = (Object[]) ReflectionUtils.getFieldValue(pathList,"dexElements");
                List<String> paths = new ArrayList();
                if(!ParamUtils.isEmpty(dexElements)) {
                    for (Object dexElement : dexElements) {
                        Object dexFile = ReflectionUtils.getFieldValue(dexElement, "dexFile");
                        Object mFileName = ReflectionUtils.getFieldValue(dexFile, "mFileName");
                        paths.add(mFileName.toString());
                        clsList.addAll(loadClass(classLoader, mFileName.toString()));
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return clsList;
    }

    private List<Class> loadClass(PathClassLoader classLoader,String path) throws IOException, ClassNotFoundException {
        List<Class> clsList = new ArrayList<>();
        DexFile df = new DexFile(path);
        Enumeration<String> n=df.entries();
        while(n.hasMoreElements()){
            String className = n.nextElement();
            if(!ParamUtils.isEmpty(packages)){
                for(String packageName:packages){
                    if(className.contains(packageName)){
                        clsList.add(classLoader.loadClass(className));
                        break;
                    }
                }
            }else{
                clsList.add(classLoader.loadClass(className));
            }
        }
        return clsList;
    }

}