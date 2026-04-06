package com.k00282713.RoomSense_Web_Client.controllers.mvc;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class BaseController
{
    protected String getPage(Model model, String title, String contentPath)
    {
        model.addAttribute("title", title);
        model.addAttribute("content", contentPath);
        return "index";
    }
}
