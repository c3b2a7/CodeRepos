package me.lolico.samples.cloud.common.api;

import me.lolico.samples.cloud.common.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.AnnotatedElement;

/**
 * 不要使用{@link RequestMapping}指定公共request-path, 因为{@link RequestMapping}标注的类会
 * 被注册为Controller，其中的方法注册成HandlerMethod, 造成mapping混乱甚至冲突.
 * <p>
 * RequestMappingHandlerMapping#isHandler
 *
 * @see org.springframework.core.annotation.AnnotatedElementUtils#hasAnnotation(AnnotatedElement, Class)
 */
public interface HelloService {

    @GetMapping("hello")
    ResponseEntity<Object> hello(@RequestParam String name);

    @GetMapping("hello1")
    ResponseEntity<Object> hello(@RequestHeader String name,
                                 @RequestHeader Integer age);

    @PostMapping("hello")
    ResponseEntity<Object> hello(@RequestBody UserDTO userDTO);

}
