package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.model.ProductStatus;
import com.pentagon.warungkita.repository.EkspedisiRepo;
import com.pentagon.warungkita.repository.ProductStatusRepo;
import com.pentagon.warungkita.service.EkspedisiService;
import com.pentagon.warungkita.service.ProductStatusService;

import java.util.List;
import java.util.Optional;

    public class ProductStatusServiceImpl implements ProductStatusService {
    private ProductStatusRepo productStatusRepo;

    @Override
        public List<ProductStatus> getAll(){
            List<Ekspedisi> productStatuses = productStatusRepo.findAll();
            if(productStatuses.isEmpty()){
                throw new ResourceNotFoundException("Product Status not exist with id :");
            }
            return this.productStatusRepo.findAll();
        }

        @Override
        public ProductStatus createProductStatus(ProductStatus productStatus){
            return this.productStatusRepo.save(ProductStatus);
        }

        @Override
        public Optional<ProductStatus> getProductStatusById(Long Id){
            Optional<ProductStatus> optionalUser = productStatusRepo.findById(Id);
            if(optionalUser.isEmpty()){
                throw new ResourceNotFoundException("Product Status not exist with id :" + Id);
            }
            return this.productStatusRepo.findById(Id);

            @Override
            public void deleteProductStatusById (Long Id) {
                Optional<ProductStatus> optionalProductStatus = productStatusRepo.findById(Id);
                if(optionalProductStatus.isEmpty()){
                    throw new ResourceNotFoundException("Product Status not exist with id :" + Id);
                }
                ProductStatus productstatus = productStatusRepo.getReferenceById(productstatus);
                this.productStatusRepo.delete(productstatus);
            }

            @Override
            public ProductStatus updateProductStatus(ProductStatus productStatus) throws Exception{
                Optional<ProductStatus> optionalProductStatus = EkspedisiRepo.findById(Id);
                if(optionalUser.isEmpty()){
                    throw new ResourceNotFoundException("Product Status not exist with id :" + Id);
                }
                return this.productStatusRepo.save(Id);
            }

}
