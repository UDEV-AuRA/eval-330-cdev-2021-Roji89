package com.ipiecoles.java.eval.th330.service;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.repository.AlbumRepository;
import com.ipiecoles.java.eval.th330.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public Page<Artist> findAllArtists(Integer page, Integer size, String sortProperty, String sortDirection) {
        if(page < 0 || size < 0){
            throw new IllegalArgumentException("Le numéro de page et la taille des pages ne peuvent pas être négatifs !");
        }
        Long nbPageMax = albumRepository.count() / size;
        if(page > nbPageMax){
            throw new IllegalArgumentException("Avec une taille de " + size + ", le numéro de page doit être compris entre 0 et " + nbPageMax);
        }
        try {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(sortDirection),sortProperty));
            Pageable pageable = PageRequest.of(page,size,sort);
            return artistRepository.findAll(pageable);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Erreur lors de la recherche paginée ! Vérifier les paramètres !");
        }
    }

    public Artist findById(Long id) {
        Optional<Artist> artist = this.artistRepository.findById(id);
        if(!artist.isPresent()){
            throw new EntityNotFoundException("Impossible de trouver l'artiste d'identifiant " + id);
        }
        return artist.get();
    }

    public Artist findByName(String name) {
        Artist artist = this.artistRepository.findArtistByName(name);
        if(artist == null){
            throw new EntityNotFoundException("Impossible de trouver l'artiste de nom " + name);
        }
        return artist;
    }

    public Boolean existsByName(String name) {
        return artistRepository.existsByNameIgnoreCase(name);
    }

    public Long countAllArtists() {
        return artistRepository.count();
    }

    public Artist creerArtiste(Artist artist) {
        return artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    public Artist updateArtiste(Long id, Artist artist) {
        return artistRepository.save(artist);
    }

    public Page<Artist> findByNameLikeIgnoreCase(String name, Integer page, Integer size, String sortProperty, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortProperty);
        Pageable pageable = PageRequest.of(page,size,sort);
        return artistRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}
