package com.example.lesson7.models

data class Hero(
    val id: Int,
    val name: String,
    val slug: String,
    val powerstats: Powerstats,
    val appearance: Appearance,
    val biography: Biography,
    val work: Work,
    val connections: Connections,
    val images: Images,
) {
    fun allInfo() :String = """
        Slug: $slug
        Powerstats:
            intelligence: ${powerstats.intelligence}
            strength: ${powerstats.strength}
            speed: ${powerstats.speed}
            durability: ${powerstats.durability}
            power: ${powerstats.power}
            combat: ${powerstats.combat}
        Appearance:     
            gender: ${appearance.gender}
            race: ${appearance.race}
            height: ${appearance.height.last()}
            weight: ${appearance.weight.last()}
            eyeColor: ${appearance.eyeColor}
            hairColor: ${appearance.hairColor}
        Biography:
            fullName: ${biography.fullName}
            alterEgos: ${biography.alterEgos}
            aliases: ${biography.aliases.joinToString(", ")}
            placeOfBirth: ${biography.placeOfBirth}
            firstAppearance: ${biography.firstAppearance}
            publisher: ${biography.publisher}
            alignment: ${biography.alignment}
        Work:
            occupation: ${work.occupation} 
            base: ${work.base}   
        Connections:
            groupAffiliation: ${connections.groupAffiliation}
            relatives: ${connections.relatives}           
        """.trimIndent()
}
