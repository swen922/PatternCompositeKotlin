import java.util.*

enum class Rank {
    GENERAL,
    COLONEL,
    MAJOR,
    SERGEANT,
    SOLDIER
}

interface Military {
    fun getRank() : Rank
    fun order(ord : String)
    fun perform(orderedRank : Rank, ord : String)
    fun receiveSub(sub : Military)
    fun getSubject(number : Int) : Military?
    fun getMyBand() : List<Military>
    fun bandSize() : Int
}

class MilitaryMan : Military {

    var myRank : Rank
    var band : MutableList<Military> = mutableListOf()

    constructor(myRank: Rank) {
        this.myRank = myRank
        //println("My rank = " + myRank + ", my ordinal = " + myRank.ordinal)
    }

    override fun getRank() : Rank {
        return myRank
    }


    override fun order(ord: String) {
        for (m in this.band) {
            m.perform(this.getRank(), ord)
        }
    }

    override fun perform(orderedRank: Rank, ord: String) {
        if (myRank.ordinal > orderedRank.ordinal) {
            var answer = ""
            for (z in 0..myRank.ordinal - 1) {
                answer = answer + "*"
            }
            println(answer + " - Yes! Perform You Order, Sir!\nOrder = " + ord + "\nI'm " + myRank + "\n")
            this.order(ord)
        }
        else if (myRank.ordinal == orderedRank.ordinal) {
            println("- Please show Your authority, Sir! You can't order me Yourself!\n")
        }
        else {
            println("- You are crazy! 10 squeezings right now!\n")
        }
    }

    override fun receiveSub(sub: Military) {
        band.add(sub)
    }

    override fun getSubject(number: Int): Military? {
        if (number <= 0 && number < band.size) {
            return band[number]
        }
        return null
    }

    override fun getMyBand(): List<Military> {
        return band
    }

    override fun bandSize(): Int {
        var result = 0
        for (m in band) {
            result++
            result += m.bandSize()
        }
        return result
    }

    override fun toString(): String {
        return "i MilitaryMan\nmyRank = $myRank\ni have ${band.size} subjects\nand i have band with size ${bandSize()}\n"
    }
}

/**  Компоновщик, также известен как: Дерево, Composite */

fun main(args : Array<String>) {
    var general : Military = MilitaryMan(Rank.GENERAL)

    for (i in 0..1) {
        var colonel = MilitaryMan(Rank.COLONEL)
        general.receiveSub(colonel)
        for (j in 0..1) {
            var major = MilitaryMan(Rank.MAJOR)
            colonel.receiveSub(major)
            for (k in 0..1) {
                var sergeant = MilitaryMan(Rank.SERGEANT)
                colonel.receiveSub(sergeant)
                for (l in 0..1) {
                    var soldier = MilitaryMan(Rank.SOLDIER)
                    sergeant.receiveSub(soldier)
                }
            }
        }
    }

    println("\n\ngeneral.bandSize() = " + general.bandSize() + "\n")
    general.order("GO TO WAR!!!")
    general.getMyBand().get(0).perform(Rank.COLONEL, "Let's go drink wiskey!")
    general.getMyBand().get(0).perform(Rank.SERGEANT, "Fuck You!")
}