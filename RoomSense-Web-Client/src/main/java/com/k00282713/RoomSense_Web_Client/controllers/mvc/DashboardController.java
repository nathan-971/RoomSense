package com.k00282713.RoomSense_Web_Client.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController extends BaseController
{
    @GetMapping({"", "/"})
    public String getHome(Model model)
    {
        return getPage(model, "Dashboard", "pages/dashboard :: content");
    }
}
