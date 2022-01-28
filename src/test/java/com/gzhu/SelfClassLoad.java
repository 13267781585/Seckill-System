package com.gzhu;

import java.io.*;

public class SelfClassLoad extends ClassLoader {
    private String filename;

    public SelfClassLoad(String name)
    {
        filename = name;
    }

    @Override
    protected Class<?> findClass(String name) {
        File file = new File(filename);
        if(!file.exists()){
            return null;
        }

        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        Class<?> clazz = null;
        try {
            byte[] data = new byte[1024];
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            int size = 0;
            while((size = in.read(data)) != -1)
            {
                out.write(data,0,size);
            }
            byte[] classByte = out.toByteArray();
            clazz = defineClass(name,classByte,0,classByte.length);
            return clazz;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return clazz;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(in != null){
                    in.close();
                }
                if(out != null)
                {
                    out.close();
                }
            }catch (IOException e){

            }
        }
        return clazz;
    }

    public static void main(String[] args)throws Exception {
        String name = "D:\\桌面\\ClassLoadPojo.class";
        SelfClassLoad load = new SelfClassLoad(name);
        Class<?> clazz = load.loadClass("com.gzhu.ClassLoadPojo");
        System.out.println(clazz.newInstance());
    }
}
