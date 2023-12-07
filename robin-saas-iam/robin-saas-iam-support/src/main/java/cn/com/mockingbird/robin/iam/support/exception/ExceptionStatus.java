package cn.com.mockingbird.robin.iam.support.exception;

import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author zhaopeng
 * @date 2023/12/7 19:57
 **/
@Getter
public enum ExceptionStatus {

    EX900000("EX900000", "未知错误"),
    EX900001("EX900001", "系统异常，请稍后重试"),
    EX900002("EX900002", "获取配置信息错误"),
    EX900003("EX900003", "参数校验错误"),
    EX900004("EX900004", "未定义错误消息"),
    EX900005("EX900005", "数字签名校验错误"),
    EX900006("EX900006", "参数类型不对"),
    EX900007("EX900007", "演示模式，不允许操作"),
    EX900008("EX900008", "授权失败"),
    EX900009("EX900009", "数据库异常"),
    EX000100("EX000100", "文件上传失败"),
    EX000101("EX000101", "用户名或密码错误"),
    EX000102("EX000102", "验证码已失效，请重新获取验证码"),
    EX000103("EX000103", "用户被锁定，请联系管理员"),
    EX000104("EX000104", "用户被禁用，请联系管理员"),
    EX000105("EX000105", "没有可用权限，请联系管理员"),
    EX000106("EX000106", "登录被禁止，请联系管理员"),
    EX000107("EX000107", "用户不存在"),
    EX000108("EX000108", "用户已绑定"),
    EX000109("EX000109", "用户未绑定"),
    EX000201("EX000201", "参数不合法"),
    EX000202("EX000202", "系统暂未初始化"),
    EX000203("EX000203", "此会话已过期"),
    EX000205("EX000204", "系统繁忙，请稍后重试");

    private String code;
    private String message;

    ExceptionStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
