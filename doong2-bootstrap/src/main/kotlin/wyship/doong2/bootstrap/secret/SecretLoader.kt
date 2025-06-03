package wyship.doong2.bootstrap.secret

fun interface SecretLoader {
    fun load(): Map<String, String>
}
