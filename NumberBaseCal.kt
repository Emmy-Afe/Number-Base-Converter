import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

class NumberBaseCal {

    private val myConstant = mutableListOf('a')
    init {
        println("""
            Welcome to my number base calculator, my name is Afeiye Emmanuel
            What would you like to convert to/from?
            
        """.trimIndent())
        for (i in 'b'..'z') {
            myConstant.add(i)
        }

    }

    fun selectTargets(source: Int): MutableList<Int> {
        println("Enter the target base of conversion: ")
        val target = readln().toInt()
        return mutableListOf(source, target)
    }

    fun toOtherBase(option: String, target: Int): String {
        val decimal = option.toBigInteger()
        return decimal.toString(target)
    }

    fun toOtherBaseWithDecimal(option: String, target: Int): String {
        var decimal = option.toBigDecimal()
        val integer = decimal.toBigInteger()
        decimal -= integer.toBigDecimal()
        val list = mutableListOf<String>()

        if (integer != BigInteger.ZERO) {
            list.add(integer.toString(target))
        } else list.add("0")
        list.add(".")

        if (decimal != BigDecimal.ZERO) {
            repeat(5) {
                val count: Int
                decimal *= BigDecimal(target)
                    .setScale( 15,  RoundingMode.HALF_UP)
                if (decimal.toInt() > 9) {
                    count = decimal.toInt() - 10
                    list.add(myConstant[count].toString())
                } else {
                    list.add( decimal.toInt().toString())
                }

                if (decimal >= BigDecimal(1.0)) {
                    decimal -= BigDecimal(decimal.toInt())
                }
            }
        } else {
            list.add("00000")
        }

        var result = ""
        for (i in list) {
            result += i
        }
        return result
    }

    fun toBaseTen(option: String, source: Int): BigInteger = option.toBigInteger(source)

    fun toBaseTenWithDecimal(option: String, source: Int): BigDecimal {
        val (first, decimal) = option.split('.')
        var result = first.toBigInteger(source).toBigDecimal()
        var count = -1

        for (i in decimal) {
            if (i in myConstant) {
                val x =  myConstant.indexOf(i) + 10
                result += BigDecimal(x) * source.toBigDecimal().pow(count, java.math.MathContext.DECIMAL64)
                result = result.setScale(10, RoundingMode.HALF_UP)
            } else {
                result += BigDecimal(i.toString()) * source.toBigDecimal().pow(count, java.math.MathContext.DECIMAL64)
                result = result.setScale(10, RoundingMode.HALF_UP)
            }
            count--
        }
        return result
    }

}