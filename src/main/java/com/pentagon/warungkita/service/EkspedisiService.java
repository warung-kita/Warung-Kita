package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Ekspedisi;

import java.util.List;
import java.util.Optional;

public interface EkspedisiService {
    List<Ekspedisi> getAll();
    Ekspedisi createEkspedisi(Ekspedisi ekspedisi);
    Optional<Ekspedisi> getEkspedisiById(Long Id);
    void deleteEkspedisiById(Long Id);
    Ekspedisi updateEkspedisi(Ekspedisi ekspedisi);
}
