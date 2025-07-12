package wyship.doong2.persistence.tag

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import wyship.doong2.core.tag.Tag
import wyship.doong2.core.tag.port.TagPort
import wyship.doong2.core.tag.port.TagPort.TagNotFoundException
import wyship.doong2.core.tag.port.TagPort.TagRegisterFailException

@Component
class TagAdapter(
    private val tagRepository: TagRepository,
) : TagPort {
    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    @Value("\${jwt.validityMs}")
    private var validityMs: Long = 0

    override fun findByTag(tag: String): Tag =
        tagRepository.findByTag(tag)?.toDomain()
            ?: throw TagNotFoundException()

    @Transactional
    override fun registerTag(tag: String, memberId: Long): Tag =
        runCatching {
            val existing = tagRepository.findByTag(tag)
                ?: throw TagNotFoundException()
            val updated = existing.copy(memberId = memberId)
            tagRepository.save(updated).toDomain()
        }.getOrElse {
            throw TagRegisterFailException()
        }

    override fun findAllByMemberId(memberId: Long): List<Tag> =
        tagRepository.findAllByMemberId(memberId).map { it.toDomain() }
}

private fun TagEntity.toDomain() = Tag(
    id = id,
    tag = tag,
    memberId = memberId,
)
