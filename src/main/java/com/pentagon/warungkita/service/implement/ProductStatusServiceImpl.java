package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Ekspedisi;
import com.pentagon.warungkita.model.ProductStatus;
import com.pentagon.warungkita.repository.EkspedisiRepo;
import com.pentagon.warungkita.repository.ProductStatusRepo;
import com.pentagon.warungkita.service.EkspedisiService;
import com.pentagon.warungkita.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
    public class ProductStatusServiceImpl implements ProductStatusService {
    private ProductStatusRepo productStatusRepo;

    @Override
        public List<ProductStatus> getAll(){
            List<ProductStatus> productStatuses = productStatusRepo.findAll();
            if(productStatuses.isEmpty()){
                throw new ResourceNotFoundException("Product Status not exist with id :");
            }
            return this.productStatusRepo.findAll();
        }

        @Override
        public ProductStatus createProductStatus(ProductStatus productStatus){
            return this.productStatusRepo.save(productStatus);
        }

        @Override
        public Optional<ProductStatus> getProductStatusById(Long Id) {
            Optional<ProductStatus> optionalUser = productStatusRepo.findById(Id);
            if (optionalUser.isEmpty()) {
                throw new ResourceNotFoundException("Product Status not exist with id :" + Id);
            }
            return this.productStatusRepo.findById(Id);
        }
            @Override
            public void deleteProductStatusById (Long Id) throws ResourceNotFoundException{
                Optional<ProductStatus> optionalProductStatus = productStatusRepo.findById(Id);
                if (optionalProductStatus.isEmpty()) {
                    throw new ResourceNotFoundException("Product Status not exist with id :" + Id);
                }
                ProductStatus productStatus = productStatusRepo.getReferenceById(Id);
                this.productStatusRepo.delete(productStatus);
            }

            @Override
            public ProductStatus updateProductStatus (ProductStatus productStatus) throws ResourceNotFoundException {
                Optional<ProductStatus> optionalProductStatus = productStatusRepo.findById(productStatus.getProductStatusId());
                if (optionalProductStatus.isEmpty()) {
                    throw new ResourceNotFoundException("Product Status not exist with id :");
                }
                return this.productStatusRepo.save(productStatus);
            }


}
