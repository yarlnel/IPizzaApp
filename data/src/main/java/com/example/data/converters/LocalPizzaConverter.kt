package com.example.data.converters

import com.example.domain.models.Pizza
import com.example.data.models.LocalPizza
import javax.inject.Inject

class LocalPizzaConverter
@Inject constructor() :  LocalToDomainConverter<LocalPizza, Pizza> {
    override fun fromLocal (localModel: LocalPizza) : Pizza {
        return Pizza(
            price = localModel.price,
            name = localModel.name,
            imageUrls = localModel.imageUrls,
            description = localModel.description,
            id = localModel.id,
        )
    }

    override fun toLocal (domainModel: Pizza) : LocalPizza {
        return LocalPizza(
            price = domainModel.price,
            name = domainModel.name,
            imageUrls = domainModel.imageUrls,
            description = domainModel.description,
            id = domainModel.id,
        )
    }
}