package com.buyersfirst.core.controllers.desires;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buyersfirst.core.interfaces.CreateDesiresRqB;
import com.buyersfirst.core.models.Desires;

@RestController
@RequestMapping(path = "/desires", method = RequestMethod.POST)
public class DesirePostControllers {
    
    @PostMapping(path = "/create")
    public @ResponseBody Desires createDesires (@RequestBody CreateDesiresRqB request) {
        return new Desires();
    }

    @PostMapping(path = "/{id}/recreate")
    public @ResponseBody Desires reCreateDesires (@PathVariable Integer id) {
        return new Desires();
    }

    @PostMapping(path = "/{desire}/close")
    public @ResponseBody Desires closeDesires (@PathVariable Integer desire) {
        return new Desires();
    }

    @PostMapping(path = "/want/{desire}")
    public @ResponseBody Desires wantDesires (@PathVariable Integer desire) {
        return new Desires();
    }

    @PostMapping(path = "/not-want/{desire}")
    public @ResponseBody Desires notWantDesires (@PathVariable Integer desire) {
        return new Desires();
    }
}
