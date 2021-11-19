package com.example.data.converters

import com.example.domain.models.Order
import com.example.data.models.LocalOrder
import javax.inject.Inject

class LocalOrderConverter
@Inject constructor() : LocalToDomainConverter<LocalOrder, Order> {
    override fun fromLocal(localModel: LocalOrder): Order {
        return Order(
            pizzaId = localModel.pizzaId,
            quantity = localModel.quantity,
        )
    }

    override fun toLocal(domainModel: Order): LocalOrder {
        return LocalOrder(
            pizzaId = domainModel.pizzaId,
            quantity = domainModel.quantity,
        )
    }
}