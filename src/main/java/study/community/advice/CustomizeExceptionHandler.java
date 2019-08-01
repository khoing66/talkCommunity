package study.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import study.community.dto.ResultDTO;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName CustomizeExceptionHandle
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 10:58
 * @Version 1.0
 **/
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
//    希望访问接口时，返回message信息，访问页面异常进行跳转。
//    而两种请求的区别是ContentType不一样，
//    ModelAndView包含model 和 veiw 两个对象，，
//    这个返回的是modelandview，需要加个@ResponseBody，但加了之后下面那个错误信息跳转就不能用了。所以要自定义个方法
     ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Throwable e, Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回JSON
            ResultDTO resultDTO = null;
            if (e instanceof CustomizeException) {//这里的Throwable e 是自定义CustomizeException的一个实例
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
            }
            return null;

        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());

            } else {

                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR);
            }
            return new ModelAndView("error");

        }
//        HttpStatus status = getStatus(request);

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
