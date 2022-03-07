package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;

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
        ModelAndView model = new ModelAndView("listeArtists");
        model.addObject("artists", artistService.findByNameLikeIgnoreCase(name, 0, artistService.countAllArtists().intValue(), "name", "ASC"));
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists"
    )
    public ModelAndView getAllArtists(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "Id") String sortProperty,
            @RequestParam(defaultValue = "ASC") String  sortDirection
    ){
        Page<Artist> artists = artistService.findAllArtists(page, size, sortProperty, sortDirection);
        ModelAndView model = new ModelAndView("listeArtists");
        model.addObject("start", page * size + 1);
        model.addObject("end", page * size + artists.getNumberOfElements());
        model.addObject("artists", artists);
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/new"
    )
    public ModelAndView newArtist(){
        ModelAndView model = new ModelAndView("detailArtist");
        model.addObject("artist", new Artist());
        return model;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/artists",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView addOrUpdate(Artist artist){
        if(artist.getId() == null){
            artistService.creerArtiste(artist);
            return new RedirectView("/artists/" + artist.getId());
        }
        else{
            artistService.updateArtiste(artist.getId(), artist);
            return new RedirectView("/artists/" + artist.getId());
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/{id}/delete"
    )
    public RedirectView deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
    }

}
