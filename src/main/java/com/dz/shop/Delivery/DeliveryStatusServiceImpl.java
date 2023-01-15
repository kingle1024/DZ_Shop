package com.dz.shop.Delivery;

import com.dz.shop.Dao.DeliveryStatusDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {
    @Autowired
    DeliveryStatusDAO deliveryStatusDAO;


    @Override
    public List<DeliveryStatus> list() {
        return deliveryStatusDAO.list();
    }
}
