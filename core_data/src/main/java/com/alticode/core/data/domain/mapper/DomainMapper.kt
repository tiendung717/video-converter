package com.alticode.core.data.domain.mapper

import com.alticode.core.data.datasource.entities.VideoEntity
import com.alticode.core.data.domain.model.Media

interface DomainMapper<T, DomainModel> {
    fun toDataModel(domainModel: DomainModel): T
    fun toDomainModel(data: T): DomainModel
}

object VideoMapper : DomainMapper<VideoEntity, Media> {
    override fun toDataModel(domainModel: Media): VideoEntity {
        return VideoEntity(path = domainModel.path, timestamp = System.currentTimeMillis())
    }

    override fun toDomainModel(data: VideoEntity): Media {
        return Media(data.path)
    }
}