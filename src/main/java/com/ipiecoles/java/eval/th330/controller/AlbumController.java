package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistService artistService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/new/artist/{artistId}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView addAlbum(
            @PathVariable Long artistId,
            RedirectAttributes redirectAttributes,
            Album album
    ) {
        Artist artist = artistService.findById(artistId);
        album.setArtist(artist);
        albumService.creerAlbum(album);

        redirectAttributes.addFlashAttribute("flashMessage", "add album" + album.getTitle() + " Done !");
        return new RedirectView("/artists/" + artistId);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/delete/{albumId}/artist/{artistId}"
    )
    public RedirectView deleteAlbum(
            @PathVariable Long albumId,
            @PathVariable Long artistId,
            RedirectAttributes redirectAttributes
    ) {
        albumService.deleteAlbum(albumId);
        redirectAttributes.addFlashAttribute("flashMessage", "album deleted !");
        return new RedirectView("/artists/" + artistId);
    }
}
