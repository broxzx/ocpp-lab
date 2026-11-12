package io.github.broxzx.ocpplab.model.response

data class AuthorizeResponse(val idTagInfo: IdTagInfo) {
    data class IdTagInfo(val status: String)
}