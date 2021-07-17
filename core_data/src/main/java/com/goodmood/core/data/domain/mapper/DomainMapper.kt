package com.goodmood.core.data.domain.mapper

import com.goodmood.core.data.datasource.entities.VideoEntity
import com.goodmood.core.data.domain.model.Video

interface DomainMapper<T, DomainModel> {
    fun toDataModel(domainModel: DomainModel): T
    fun toDomainModel(data: T): DomainModel
}

object VideoMapper : DomainMapper<VideoEntity, Video> {
    override fun toDataModel(domainModel: Video): VideoEntity {
        return VideoEntity(path = domainModel.path, timestamp = System.currentTimeMillis())
    }

    override fun toDomainModel(data: VideoEntity): Video {
        return Video(data.path)
    }
}