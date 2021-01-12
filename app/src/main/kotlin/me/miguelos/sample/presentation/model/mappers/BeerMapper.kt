package me.miguelos.sample.presentation.model.mappers

import me.miguelos.base.mappers.TwoWaysMapper
import me.miguelos.sample.presentation.model.Beer
import javax.inject.Inject
import javax.inject.Singleton
import me.miguelos.sample.domain.model.Beer as DomainBeer

@Singleton
class BeerMapper
@Inject constructor() : TwoWaysMapper<DomainBeer, Beer>() {

    override fun mapFrom(from: DomainBeer) = Beer(
        id = from.id,
        name = from.name,
        description = from.description,
        abv = from.abv,
        thumbnail = from.thumbnail
    )

    override fun inverseMapFrom(from: Beer) = DomainBeer(
        id = from.id,
        name = from.name,
        description = from.description,
        abv = from.abv,
        thumbnail = from.thumbnail
    )
}
