package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AccueilController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/"
    )
    public ModelAndView Home(){
        ModelAndView model = new ModelAndView("home");
        model.addObject("number", artistService.countAllArtists());
        return model;
    }
}
