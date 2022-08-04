package com.pentagon.warungkita.repository;


import com.pentagon.warungkita.model.Ekspedisi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EkspedisiRepo extends JpaRepository<Ekspedisi, Long> {


}
