package com.pentagon.warungkita.controller;


import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.repository.EkspedisiRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.service.EkspedisiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/teamD/v1/")
@AllArgsConstructor
public class EkspedisiController {

    private final EkspedisiService ekspedisiService;
    private final EkspedisiRepo ekspedisiRepo;

    @GetMapping("/ekspedisi/all")
    public ResponseEntity<Object> getAllEkspedisi() {
        try {
            List<Ekspedisi> result = ekspedisiService.getAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            for(Ekspedisi ekspedisi : result){
                Map<String, Object> ekspedisidata = new HashMap<>();
                ekspedisidata.put("ID            ", ekspedisi.getEkspedisiId());
                ekspedisidata.put("Name          ", ekspedisi.getName());
                maps.add(ekspedisidata);
            }
            return ResponseHandler.generateResponse("Successfully Get All Ekspedisi!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table Has No Value!");
        }
    }
    @GetMapping("/ekspedisi/{Id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long Id) {
        try {
            Ekspedisi ekspedisi = ekspedisiService.getEkspedisiById(Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ekspedisi not exist with Id :" + Id));
            Map<String, Object> ekspedisidata = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();
            ekspedisidata.put("ID            ", ekspedisi.getEkspedisiId());
            ekspedisidata.put("Name          ", ekspedisi.getName());
            maps.add(ekspedisidata);
            return ResponseHandler.generateResponse("Successfully Get Ekspedisi By ID!", HttpStatus.OK, maps);
        } catch (Exception e) {
             return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }

    @PostMapping("/ekspedisi/create")
    public ResponseEntity <Object> createEkspedisi(@RequestBody Ekspedisi ekspedisi) {

        try {
            ekspedisiService.createEkspedisi(ekspedisi);
            Ekspedisi ekspedisiresult = ekspedisiService.createEkspedisi(ekspedisi);
            Map<String, Object> ekspedisiMap = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();
            ekspedisiMap.put("ID             ", ekspedisiresult.getEkspedisiId());
            ekspedisiMap.put("Username       ", ekspedisiresult.getName());
            maps.add(ekspedisiMap);
            return ResponseHandler.generateResponse("Successfully Created Ekspedisi!", HttpStatus.CREATED, maps);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Ekspedisi Already Exist!");
        }
    }
    @PutMapping("/ekspedisi/update/{Id}")
    public ResponseEntity<Object> updateEkspedisi(@PathVariable Long Id, @RequestBody Ekspedisi ekspedisiDetails){
        try {
            Ekspedisi ekspedisi = ekspedisiService.getEkspedisiById(Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ekspedisi not exist withId :" + Id));
            ekspedisi.setName(ekspedisiDetails.getName());
             Ekspedisi updatedEkspedisi = ekspedisiRepo.save(ekspedisi);
            return ResponseHandler.generateResponse("Successfully Updated Ekspedisi!",HttpStatus.OK, ekspedisi);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!");
        }
    }

    @DeleteMapping("/ekspedisi/delete/{Id}")
    public ResponseEntity<Object> deleteEkspedisi(@PathVariable Long Id){
        try {
            ekspedisiService.deleteEkspedisiById(Id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);;
            return ResponseHandler.generateResponse("Successfully Delete Ekspedisi! ", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }
}
