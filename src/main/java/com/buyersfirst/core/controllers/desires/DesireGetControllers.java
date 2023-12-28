package com.buyersfirst.core.controllers.desires;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buyersfirst.core.interfaces.DesireListRsp;
import com.buyersfirst.core.interfaces.SingleDesire;

@RestController
@RequestMapping(path = "/desires", method = RequestMethod.GET)
public class DesireGetControllers {

    @GetMapping(path = "/")
    public @ResponseBody DesireListRsp listDesires () {
        return new DesireListRsp();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody SingleDesire getDesire (@PathVariable Integer id) {
        return new SingleDesire();
    }
}
