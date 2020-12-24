package com.mon4cc.utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * transform string to input stream transform
 */
@Component
public class TrasStringToInputStream {

    public InputStream getInputStream(String str){
        InputStream myIn=new ByteArrayInputStream(str.getBytes());
        return myIn;
    }
}
