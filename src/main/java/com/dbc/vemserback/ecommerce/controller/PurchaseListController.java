package com.dbc.vemserback.ecommerce.controller;

import com.dbc.vemserback.ecommerce.service.PurchaseListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase-list")
@RequiredArgsConstructor
public class PurchaseListController {

    private final PurchaseListService purchaseListService;


    @GetMapping
    public String test() {
        return "testando controller de purchase list para cotação";
    }
}
