import java.math.RoundingMode

fun main(args: Array<String>) {
    var option: String
    val numberBaseCal = NumberBaseCal()

    do {
        println("Enter the number base of the number you would like to convert from (To quit type exit): ")
        option = readln()
        if (option != "exit") {
            
            //To collect targets
            val targets = numberBaseCal.selectTargets(option.toInt())

            do {
                when {
                    //To convert to other base from Base 10
                    targets[0] == 10 -> {
                        println("Enter number in base ${targets[0]} to convert to base ${targets[1]} (To go back type back)")
                        option = readln()
                        when {
                            option != "back" && option.contains(".") -> {
                                val result = numberBaseCal.toOtherBaseWithDecimal(option, targets[1])
                                println("Conversion result: $result")
                            }
                            option != "back" -> {
                                val result = numberBaseCal.toOtherBase(option, targets[1])
                                println("Conversion result: $result")
                            }
                        }
                    }

                    //To convert to Base 10 from other base
                    targets[1] == 10 -> {
                        println("Enter number in base ${targets[0]} to convert to base ${targets[1]} (To go back type back)")
                        option = readln()
                        when {
                            option != "back" && option.contains(".") -> {
                                var result = numberBaseCal.toBaseTenWithDecimal(option, targets[0])
                                result = result.setScale(5, RoundingMode.DOWN)
                                println("Conversion result: $result")
                            }
                            option != "back" -> {
                                val result = numberBaseCal.toBaseTen(option, targets[0])
                                println("Conversion result: $result")
                            }
                        }
                    }

                    //To convert from other base to base 10 and then to desired base
                    else -> {
                        println("Enter number in base ${targets[0]} to convert to base ${targets[1]} (To go back type back)")
                        option = readln()
                        when {
                            option != "back" && option.contains(".") -> {
                                val tempResult = numberBaseCal.toBaseTenWithDecimal(option, targets[0])
                                val result = numberBaseCal.toOtherBaseWithDecimal(tempResult.toString(), targets[1])
                                println("Conversion result: $result")
                            }
                            option != "back" -> {
                                var result = numberBaseCal.toBaseTen(option, targets[0])
                                result = numberBaseCal.toOtherBase(result.toString(), targets[1]).toBigInteger()
                                println("Conversion result: $result")
                            }
                        }
                    }
                }
            } while (option != "back")
        }

    } while (option != "exit")

}

