package com.formice.mars.web.common;

import com.formice.mars.web.model.enums.ResponseCode;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;


@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class Response<T> implements Serializable{

    protected int code;
    protected String msg;
    protected T data;//可以指定泛型里面的内容，也可以不指定，而且里面的类型可以是多种，map,list,string


    //编写外部访问的Public方法,之前需要写一个枚举类
    //这样外部的显示的就是这几个值啦
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
    public T getData(){
        return data;
    }
    //判断是否登陆成功
    @JsonIgnore
    public boolean isSuccess(){
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    //编写 私有 的构造方法，外部是不能new的
    // 开放供外部使用的Public方法
    public Response(){

    }
    public Response(int code){
        this.code=code;
    }
    private Response(int code, T data){
        this.code=code;
        this.data=data;
    }
    private Response(int code, String msg){
        this.code=code;
        this.msg=msg;
    }

    private Response(int code, String msg, T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    //编写成功静态的方法供外部的调用
    public static <T> Response<T> createBySuccess(){
        return new Response<T>(ResponseCode.SUCCESS.getCode());
    }

    public static  <T> Response<T> createBySuccess(T data){
        return new Response<T>(ResponseCode.SUCCESS.getCode(),data);

    }
    public static <T> Response<T> createBySuccess(String msg, T data){
        return new Response<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> Response<T> createBySuccessMessage(String msg){
        return new Response<T>(ResponseCode.SUCCESS.getCode(),msg);

    }
    //编写失败的方法
    public static <T> Response<T> createByError(){
        return new Response<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> Response<T> createByErrorMessage(String errorMessage) {
        return new Response<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }
    public static <T> Response<T> createByErrorCodeMessage(int errorcode, String erroeMessage){
        return new Response<T>(errorcode,erroeMessage);
    }
    public static <T> Response<T> createByErrorNeeDLogin(String erroeMessage){
        return new Response<T>(ResponseCode.NEED_REGISTER.getCode(),erroeMessage);
    }
}