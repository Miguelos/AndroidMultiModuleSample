package me.miguelos.sample.data.source.remote.entity.mapper

import me.miguelos.base.mappers.Mapper
import me.miguelos.punk_api_client.model.BeerEntity
import me.miguelos.sample.domain.model.Beer
import javax.inject.Singleton


@Singleton
class BeerMapper : Mapper<BeerEntity, Beer>() {

    override fun mapFrom(from: BeerEntity) = Beer(
        id = from.id,
        name = from.name,
        description = from.description,
        abv = from.abv,
        thumbnail = from.thumbnail
    )
}
