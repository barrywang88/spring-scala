package com.github.barry.web.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.barry.core.utils.ScalaFunction;
import com.github.barry.core.utils.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FormMappingJackson2HttpMessageConverter
 * @Description json object 转换
 * @Author wangxuexing
 * @Date 2020/5/2 16:46
 * @Version 1.0
 */
public class FormMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    private static final String defaultFormDataPrefix = "form_data=";

    public FormMappingJackson2HttpMessageConverter(){
        super();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(new MediaType("application", "x-www-form-urlencoded", parameters));
        this.setSupportedMediaTypes(mediaTypeList);
        // 如果为null，转成空字符串
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });
        this.setObjectMapper(objectMapper);
    }
    /**
     * 1.避免json convert 处理二进制流，导致乱码问题
     * 2.二进制流数据使用ByteArrayHttpMessageConverter 来处理
     *
     * @param clazz
     * @param mediaType
     * @return
     */
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return !clazz.equals(byte[].class);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        MediaType contentType = inputMessage.getHeaders().getContentType();
        if (!isFormHead(contentType)) {//非form提交走父类
            return super.read(type, contextClass, inputMessage);
        }
        JavaType javaType = getJavaType(type, contextClass);
        if(ScalaFunction.isJavaClass(javaType.getRawClass())){
            return readJavaType(javaType, inputMessage);
        } else {
            return readCaseClass(type, contextClass, inputMessage);
        }
    }

    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
        try {
            //修改
            byte[] bytes = inputStream2Bytes(inputMessage.getBody());
            String decode = URLDecoder.decode(new String(bytes), "UTF-8");
            if (StringUtils.isNotBlank(decode) && decode.startsWith(defaultFormDataPrefix)) {//去掉多余=号
                decode = decode.replaceFirst(defaultFormDataPrefix, "");
            }
            return this.objectMapper.readValue(decode, javaType);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex, inputMessage);
        }
    }

    private byte[] inputStream2Bytes(InputStream in) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int offset = 0;
        while ((offset = in.read(buff)) > 0) {
            outputStream.write(buff, 0, offset);
        }
        return outputStream.toByteArray();
    }

    private boolean isFormHead(MediaType contentType) {
        return MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType);
    }

    /**
     * x-www-form-urlencoded 类型参数转 scala case class
     * @param type
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    private Object readCaseClass(Type type, Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        JavaType originJavaType = getJavaType(type, clazz);
        byte[] bytes = inputStream2Bytes(inputMessage.getBody());
        String jsonStr = StringUtils.urlParam2Json(new String(bytes));
        return ObjectMapperSingleton.getObjectMapperInstance().readValue(jsonStr, originJavaType.getRawClass());
    }
}

