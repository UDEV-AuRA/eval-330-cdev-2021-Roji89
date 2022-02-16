package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/{id}"
    )
    public ModelAndView artist(@PathVariable Long id){
        ModelAndView model = new ModelAndView("detailArtist");
        model.addObject("artist", artistService.findById(id));
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists",
            params = "name"
    )
    public ModelAndView searchByName(@RequestParam String name){
        ModelAndView model = new ModelAndView("detailArtist");
        model.addObject("artist", artistService.findByName(name));
        return model;
    }
}
