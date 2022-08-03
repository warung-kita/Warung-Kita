package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.repository.EkspedisiRepo;
import com.pentagon.warungkita.repository.OrderRepo;
import com.pentagon.warungkita.service.EkspedisiService;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

public class EkspedisiServiceImpl implements EkspedisiService {
    private EkspedisiRepo ekspedisiRepo;

    @Override
    public List<Ekspedisi> getAll() {
        List<Ekspedisi> ekspedisi = ekspedisiRepo.findAll();
        if(ekspedisi.isEmpty()){
          throw new ResourceNotFoundException("Ekspedisi not exist with id :");
       }
       return this.ekspedisiRepo.findAll();
    }

    @Override
    public Ekspedisi createEkspedisi(Ekspedisi ekspedisi) {
        return this.ekspedisiRepo.save(ekspedisi);
    }

    @Override
    public Optional<Ekspedisi> getEkspedisiById(Long Id) {
        Optional<Ekspedisi> optionalUser = ekspedisiRepo.findById(Id);
      if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Ekspedisi not exist with id :" + Id);
        }
        return this.ekspedisiRepo.findById(Id);

    @Override
    public void deleteEkspedisiById(Long Id) {
            Optional<Ekspedisi> optionalEkspedisi = ekspedisiRepo.findById(Id);
          if(optionalEkspedisi.isEmpty()){
               throw new ResourceNotFoundException("Ekspedisi not exist with id :" + Id);
          }
           Ekspedisi ekspedisi = ekspedisiRepo.getReferenceById(Id);
           this.ekspedisiRepo.delete(Id);
        }

    }

    @Override
    public Ekspedisi updateEkspedisi(Ekspedisi ekspedisi) {
        Optional<Ekspedisi> optionalEkspedisi = EkspedisiRepo.findById(ekspedisi.getEkspedisiId()Id());
            if(optionalEkspedisi.isEmpty()){
                throw new ResourceNotFoundException("Ekspedisi not exist with id :" + getEkspedisiById());
            }
            return this.ekspedisiRepo.save(ekspedisi);
        }
    }

//    /***
//     * Get All Ekspedisi
//     * @return
//     */
//    public List<Ekspedisi> getAll(){
//        List<Ekspedisi> ekspedisi = ekspedisiRepo.findAll();
//        if(ekspedisi.isEmpty()){
//            throw new ResourceNotFoundException("Ekspedisi not exist with id :");
//        }
//        return this.ekspedisiRepo.findAll();
//    }
//    /***
//     * Create Ekspedisi
//     * @param
//     * @return
//     */
//    public Ekspedisi createEkspedisi(Ekspedisi ekspedisi){
//
//        return this.ekspedisiRepo.save(ekspedisi);
//    }
//    /***
//     * Get Ekspedisi By ID
//     * @param ekspedisi_Id
//     * @return
//     */
//    public Optional<Ekspedisi> getEkspedisiById(Long ekspedisi_Id){
//        Optional<Ekspedisi> optionalUser = ekspedisiRepo.findById(ekspedisi_Id);
//        if(optionalUser.isEmpty()){
//            throw new ResourceNotFoundException("Ekspedisi not exist with id :" + ekspedisi_Id);
//        }
//        return this.ekspedisiRepo.findById(ekspedisi_Id);
//
//        /***
//         * Delete Ekspedisi
//         * @param ekspedisi_Id
//         */
//
//        public void deleteEkspedisiById(Long Id) {
//            Optional<Ekspedisi> optionalEkspedisi = ekspedisiRepo.findById(Id);
//            if(optionalEkspedisi.isEmpty()){
//                throw new ResourceNotFoundException("Ekspedisi not exist with id :" + Id);
//            }
//            Ekspedisi ekspedisi = ekspedisiRepo.getReferenceById(Id);
//            this.ekspedisiRepo.delete(Id);
//        }
//
//        /***
//         * Update ekspedisi
//         * @param ekspedisi
//         * * @return
//         * @throws Exception
//                 */
//        public Ekspedisi updateUser(Ekspedisi ekspedisi) throws Exception{
//            Optional<Ekspedisi> optionalUser = EkspedisiRepo.findById(ekspedisi.getUserId());
//            if(optionalUser.isEmpty()){
//                throw new ResourceNotFoundException("Ekspedisi not exist with id :" + ekspedisi.getUserId());
//            }
//            return this.ekspedisiRepo.save(ekspedisi);
//        }


}
