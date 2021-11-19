package com.example.data.converters

interface LocalToDomainConverter<LOCAL, DOMAIN> {
    fun fromLocal (localModel: LOCAL) : DOMAIN
    fun toLocal (domainModel: DOMAIN) : LOCAL
}