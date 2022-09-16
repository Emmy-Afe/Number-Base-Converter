package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode


fun main() {
    var option = ""
    val const = mutableListOf('a')

    for (i in 'b'..'z') {
        const.add(i)
    }

    while (option != "/exit") {
        print("\nEnter two numbers in format: {source base} {target base} (To quit type /exit) > ")
        option = readln()

        if(option != "/exit") {
            val (source, target) = option.split(' ')

            while (option != "/back") {
                print("\nEnter number in base $source to convert to base $target (To go back type /back) > ")
                option = readln()

                if (option != "/back" && source == "10") {

                    if (option.contains(".")) {
                        var decimal = option.toBigDecimal()
                        val integer = decimal.toBigInteger()
                        decimal -= integer.toBigDecimal()
                        val list = mutableListOf<String>()

                        if (integer != BigInteger.ZERO) {
                            list.add(integer.toString(target.toInt()))
                        } else list.add("0")
                        list.add(".")

                        if (decimal != BigDecimal.ZERO) {
                            repeat(5) {
                                var count = 0
                                decimal *= BigDecimal(target.toInt())
                                    .setScale( 15,  RoundingMode.HALF_UP)
                                if (decimal.toInt() > 9) {
                                    count = decimal.toInt() - 10
                                    list.add(const[count].toString())
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
                        println("Conversion result: $result")

                    } else {
                        val decimal = option.toBigInteger()
                        val result = decimal.toString(target.toInt())
                        println("Conversion result: $result")
                    }

                } else if (option != "/back" && target == "10") {
                    val number = option.lowercase()

                    if (number.contains('.')) {
                        val (first, decimal) = number.split('.')
                        var result = first.toBigInteger(source.toInt()).toBigDecimal()
                        var count = -1

                        for (i in decimal) {
                            if (i in const) {
                                val x =  const.indexOf(i) + 10
                                result += BigDecimal(x) * source.toBigDecimal().pow(count, MathContext.DECIMAL64)
                                result = result.setScale(10, RoundingMode.HALF_UP)
                            } else {
                                result += BigDecimal(i.toString()) * source.toBigDecimal().pow(count, MathContext.DECIMAL64)
                                result = result.setScale(10, RoundingMode.HALF_UP)
                            }
                            count--
                        }
                        result = result.setScale(5, RoundingMode.DOWN)

                        println("Conversion result: $result")
                    } else {
                        val result = number.toBigInteger(source.toInt())
                        println("Conversion result: $result")
                    }

                } else if (option != "/back") {
                    val number = option.lowercase()

                    if (number.contains('.')) {
                        val (first, decimal) = number.split('.')
                        var stageOne = first.toBigInteger(source.toInt()).toBigDecimal()
                        var count = -1

                        for (i in decimal) {
                            if (i in const) {
                                val x =  const.indexOf(i) + 10
                                stageOne += BigDecimal(x) * source.toBigDecimal().pow(count, MathContext.DECIMAL64)
                                stageOne = stageOne.setScale(15, RoundingMode.HALF_UP)
                            } else {
                                stageOne += BigDecimal(i.toString()) * source.toBigDecimal().pow(count, MathContext.DECIMAL64)
                                stageOne = stageOne.setScale(15, RoundingMode.HALF_UP)
                            }
                            count--
                        }

                        var decimalStage2 = stageOne
                        val integer = decimalStage2.toBigInteger()
                        decimalStage2 -= integer.toBigDecimal()
                        val list = mutableListOf<String>()

                        if (integer != BigInteger.ZERO) {
                            list.add(integer.toString(target.toInt()))
                        } else list.add("0")
                        list.add(".")

                        if (decimalStage2 != BigDecimal.ZERO) {
                            repeat(5) {
                                var count = 0
                                decimalStage2 *= BigDecimal(target.toInt())
                                    .setScale( 15,  RoundingMode.HALF_UP)
                                if (decimalStage2.toInt() > 9) {
                                    count = decimalStage2.toInt() - 10
                                    list.add(const[count].toString())
                                } else {
                                    list.add(decimalStage2.toInt().toString())
                                }

                                if (decimalStage2 >= BigDecimal(1.0)) {
                                    decimalStage2 -= BigDecimal(decimalStage2.toInt())
                                }
                            }
                        } else {
                            list.add("00000")
                        }

                        var result = ""
                        for (i in list) {
                            result += i
                        }
                        println("Conversion result: $result")

                    } else {
                        val result = number.toBigInteger(source.toInt()).toString(target.toInt())

                        println("Conversion result: $result")
                    }
                }
            }
        }
    }
}
