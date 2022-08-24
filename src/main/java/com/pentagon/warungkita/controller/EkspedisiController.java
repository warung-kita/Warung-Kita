package com.pentagon.warungkita.controller;


import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.service.EkspedisiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pentagon/warung-kita")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Ekspedisi")
public class EkspedisiController {

    @Autowired
    EkspedisiService ekspedisiService;

    @GetMapping("/ekspedisi/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')or hasAuthority('ROLE_SELLER')or hasAuthority('ROLE_BUYER')")
    public ResponseEntity<Object> getAllEkspedisi() {
       return this.ekspedisiService.getAll();
    }
    @GetMapping("/ekspedisi/{Id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getEkspedisiById(@PathVariable Long Id) {
        return this.ekspedisiService.getEkspedisiById(Id);
    }

    @PostMapping("/ekspedisi/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity <Object> createEkspedisi(@RequestBody Ekspedisi ekspedisi) {
        return this.ekspedisiService.createEkspedisi(ekspedisi);
       }
    @PutMapping("/ekspedisi/update/{Id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateEkspedisi(@PathVariable Long Id, @RequestBody Ekspedisi ekspedisiDetails){
        return this.ekspedisiService.updateEkspedisi(Id ,ekspedisiDetails);
    }

    @DeleteMapping("/ekspedisi/delete/{Id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteEkspedisi(@PathVariable Long Id){
        return this.ekspedisiService.deleteEkspedisiById(Id);
    }
}
