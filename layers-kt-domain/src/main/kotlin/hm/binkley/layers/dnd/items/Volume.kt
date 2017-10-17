package hm.binkley.layers.dnd.items

class Volume(numerator: Int, denominator: Int)
    : Fraction<Volume>(::Volume, numerator, denominator) {

    override fun toString() = "${super.toString()} cu.ft."

    companion object {
        val SPACELESS = inCuft(0)

        fun inCuft(cuft: Int) = Volume(cuft, 1)
        fun asFraction(numerator: Int, denominator: Int)
                = Volume(numerator, denominator)
    }
}
