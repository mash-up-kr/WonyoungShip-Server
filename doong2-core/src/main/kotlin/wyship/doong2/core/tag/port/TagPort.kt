package wyship.doong2.core.tag.port

import wyship.doong2.core.tag.Tag

interface TagPort {
    fun findByTag(tag: String): Tag
    fun registerTag(tag: String, memberId: Long): Tag
    fun findAllByMemberId(memberId: Long): List<Tag>

    sealed class TagPortException(message: String? = null) : RuntimeException(message)
    class TagRegisterFailException : TagPortException("태그 등록에 실패했습니다.")
    class TagAlreadyRegisteredException : TagPortException("이미 등록된 태그입니다.")
    class TagNotFoundException : TagPortException("존재하지 않는 태그입니다.")
}
