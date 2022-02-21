package com.irdaislakhuafa.kopmart.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.irdaislakhuafa.kopmart.helpers.DataToCsvHelper;
import com.irdaislakhuafa.kopmart.services.KeranjangService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private KeranjangService keranjangService;

    @GetMapping
    public String name() throws Exception {
        List<Map<String, Object>> listData = new ArrayList<>(
                Arrays.asList(
                        new HashMap<String, Object>() {
                            {
                                put("nama", "irda islakhu afa");
                                put("noTelp", "082244786497");
                                put("mLaki", true);
                            }
                        },
                        new HashMap<String, Object>() {
                            {
                                put("nama", "ana ardani");
                                put("noTelp", "089668062233");
                                put("mLaki", false);
                            }
                        }));

        DataToCsvHelper.writeDataToCsv(new PrintWriter(new File("/home/artix/.cache/statusInet.log")), listData);
        return "Hello World";
    }
}
