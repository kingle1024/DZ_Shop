package com.dz.shop.Dao;

import com.dz.shop.Delivery.DeliveryStatus;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DeliveryStatusDAO {
	List<DeliveryStatus> list();
}
