package wyship.doong2.core.auth.port

import wyship.doong2.core.exception.CommonException

interface MemberTokenIdPort {
    fun getTokenId(email: String): Result<String>

    fun getIdByTokenId(tokenId: String): Result<Long>

    class MemberNotFoundException : CommonException()

    class MemberQueryInternalException : CommonException()
}
