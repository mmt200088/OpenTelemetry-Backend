package com.tongji.javaEE.Controller;

import antlr.debug.Tracer;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tongji.javaEE.Service.*;
import com.tongji.javaEE.Domain.*;
import org.fluentd.logger.FluentLogger;
@RestController
@RequestMapping("api/value")
public class ValueController {
    JaegerTracer tracer = new JaegerTracer.Builder("myValue").build();

    @Autowired
    ValueService valueService = new ValueService();
    @RequestMapping(value = "/{kilometers}",method = RequestMethod.GET)
    public messageReturn valueGet(@PathVariable(value = "kilometers") int kilometers){

        FluentLogger log = FluentLogger.getLogger("valueLog");
        log.log("title","title","估值日志输出接口！");
        log.log("kilometers","kilometer",kilometers);
        Span span = tracer.buildSpan("doingValue").start();
        tracer.activateSpan(span);
        Span span3 = tracer.buildSpan("doingJobs").start();
        tracer.activateSpan(span3);
        span3.finish();
        span.setTag("kilometers",kilometers);
        messageReturn result = valueService.valueReturn(kilometers);
        span.setTag("value",result.code);
        span.log("这是span的日志！");
        span.finish();
        Span span1 = tracer.buildSpan("end").start();
        tracer.activateSpan(span1);
        span1.finish();
        return result;
    }

}
