package wyship.doong2.core.tag

import org.springframework.stereotype.Service
import wyship.doong2.core.tag.port.TagPort

interface TagRegisterUseCase {
    fun findByTag(tag: String): Tag
    fun registerTag(tag: String, memberId: Long): Tag
    fun findAllByMemberId(memberId: Long): List<Tag>
}

@Service
class TagRegisterService(
    private val tagPort: TagPort,
) : TagRegisterUseCase {
    override fun findByTag(tag: String): Tag = tagPort.findByTag(tag)
    override fun registerTag(tag: String, memberId: Long): Tag = tagPort.registerTag(tag, memberId)
    override fun findAllByMemberId(memberId: Long): List<Tag> = tagPort.findAllByMemberId(memberId)
}
