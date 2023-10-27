/**
 * 这是 mvc 模块，主要用来统一 web 接口的数据返回格式。
 * 当控制层接口返回 String / Bean / ResponseData ，或者发生异常，最后返回给客户端的都是统一的。
 * 统一的数据格式：{@link cn.com.mockingbird.robin.web.mvc.ResponseData}。
 * 同时，该包中的 {@link cn.com.mockingbird.robin.web.mvc.UniformExceptionHandler} 维护全局的异常处理。
 *
 * @author zhaopeng
 * @date 2023/10/28 0:21
 **/
package cn.com.mockingbird.robin.web.mvc;