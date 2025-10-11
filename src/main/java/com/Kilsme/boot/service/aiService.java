package com.Kilsme.boot.service;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface aiService {
    //用于聊天的方法
    @UserMessage("你是一个程序员社区助手，你的任务是返回程序员发帖内容进行内容摘要，他的发帖内容如下{{it}},如果不是关于计算机的相关内容，返回请填写关于计算机的内容")
    public String chatContent(String message);
    @UserMessage("你是一个程序员社区助手，你的任务是返回程序员发帖内容进行标题摘要，他的发帖内容如下{{it}},如果不是关于计算机的相关内容，返回请填写关于计算机的内容")
    public String chatTitle(String  message);
    @UserMessage("你是一个程序员社区助手，判断程序员发帖内容是否与计算机相关，相关返回true，不相关返回false ,他的发帖内容如下{{it}}")
    public String JudgmentPostContent(String content);
}
