package com.Kilsme.boot.controller;

import com.Kilsme.boot.model.Post;
import com.Kilsme.boot.model.User;
import com.Kilsme.boot.service.PostService;
import com.Kilsme.boot.service.UploadService;

import com.Kilsme.boot.service.aiService;
import jakarta.annotation.Resource;           // ✅ 修改：javax → jakarta
import jakarta.servlet.http.HttpSession;     // ✅ 修改：javax → jakarta

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Resource
    private PostService postService;
    @Autowired
    private aiService  aiService;

    // 显示上传页面
    @GetMapping("/upload")
    public String showUploadPage() {
        return "uploadPostView"; // 对应 templates/uploadPostView.html
    }

    // 处理图片上传
    @PostMapping("/upload")
    public String handleUpload(
            @RequestParam("image") MultipartFile file,
            Model model, HttpSession session,@RequestParam("title")String title,@RequestParam("content")String content) {
        if (file.isEmpty()) {
            model.addAttribute("error", "请选择图片");
            return "uploadPostView";
        }
        String str=aiService.JudgmentPostContent(content);
        if(str.contains("false")){
            model.addAttribute("error", "请上传与计算机相关的内容");
            return "uploadPostView";
        }
        User user=(User) session.getAttribute("user");
        try (InputStream in = file.getInputStream()) {
            String imageUrl = uploadService.upload(in, file.getOriginalFilename());
            //这个就是图片地址进行云存储
            if (imageUrl != null) {
                model.addAttribute("success", true);
                model.addAttribute("imageUrl", imageUrl);
                Post post=new Post();
                post.setContent(content);
                post.setUserId(user.getId());
                post.setTitle(title);
                post.setImageUrl(imageUrl);
                postService.insertPost(post);
            } else {
                model.addAttribute("error", "上传失败，请重试");
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "文件读取失败");
        }

        return "uploadPostView";
    }
    @PostMapping("ai-extract")
    @ResponseBody
    public Map<String, Object> aiExtract(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        String content = request.get("content");
        if (content == null || content.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "内容不能为空");
            return result;
        }

        try {
            // 调用 AI 提取逻辑
            Map<String, String> extracted = extractSummaryAndTitle(content);
            result.put("success", true);
            result.put("title", extracted.get("title"));
            result.put("content", extracted.get("content")); // 可以是摘要或原内容

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "处理失败：" + e.getMessage());
        }

        return result;
    }

    // 模拟 AI 提取：根据内容生成标题和摘要
    private Map<String, String> extractSummaryAndTitle(String content) {
        Map<String, String> res = new HashMap<>();
        String title = aiService.chatTitle(content);
        String contentBack=aiService.chatContent(content);

        res.put("title", "【AI】" + title);  // 加个前缀表示是 AI 生成
        res.put("content",contentBack);
        return res;
    }
}