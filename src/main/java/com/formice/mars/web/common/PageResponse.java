package com.formice.mars.web.common;

import com.formice.mars.web.model.enums.ResponseCode;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;


@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class PageResponse<T> extends Response implements Serializable{


    private Integer count;


    public Integer getCount(){
        return count;
    }


    private PageResponse(int code, T data, Integer count){
        this.code=code;
        this.data=data;
        this.count = count;
    }


    public static  <T> PageResponse<T> createBySuccess(T data, Integer count){
        return new PageResponse<T>(ResponseCode.SUCCESS.getCode(),data,count);

    }


}