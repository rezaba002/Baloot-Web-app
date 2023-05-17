package com.example.Baloot5Backend.controller;

import com.example.Baloot5Backend.model.Commodity;
import com.example.Baloot5Backend.model.Store;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class RateController {
    Store store = Store.getInstance();

    @PostMapping("rate")
    public Commodity RateControl(@RequestBody Map<String, Object> jsonObject) {
        int commodityId = (int) jsonObject.get("commodityId");
        String scoreString = (String) jsonObject.get("score");
        String username = store.getCurrentUser().getUsername();

        float score = Float.valueOf(scoreString);

        store.rateCommodity(username, commodityId, score);

        for (Commodity commodityObj : store.getCommodities()) {
            if (commodityObj.getId() == commodityId)
                return commodityObj;
        }
        return null;
    }
}
