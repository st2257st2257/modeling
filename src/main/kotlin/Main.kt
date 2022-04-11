import kotlin.math.sqrt

const val pi:    Double     = 3.14
const val eta:   Double     = 0.1
const val m:     Double     = 1.0
const val k:     Double     = 0.03
const val r:     Double     = 1.0
const val g:     Double     = 19.8
const val h:     Double     = 0.02

fun f(yn_1: Double, yn_2: Double, yn_3: Double): DoubleArray{
    val p = - yn_1 * ((6*pi*eta*r/m)* sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)) +
            (k/m)/sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)))
    val q = - yn_2 * ((6*pi*eta*r/m)* sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)) +
            (k/m)/sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)))
    val r = - g - yn_3 * ((6*pi*eta*r/m)* sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)) +
            (k/m)/sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)))
    return doubleArrayOf(p, q, r)
}

fun calcYN(yn_1: Double, yn_2: Double, yn_3: Double): DoubleArray{
    val k1 = f(yn_1, yn_2, yn_3)
    val k2 = f(yn_1 + (h/2) * k1[0], yn_2 + (h/2) * k1[1], yn_3 + (h/2) * k1[2])
    val k3 = f(yn_1 + (h/2) * k2[0], yn_2 + (h/2) * k2[1], yn_3 + (h/2) * k2[2])
    val k4 = f(yn_1 + (h) * k3[0], yn_2 + (h) * k3[1], yn_3 + (h) * k3[2])
    return doubleArrayOf(yn_1 + (h/6)*( k1[0] + 2*h*k2[0] + 2*h*k3[0] + h*k4[0]),
        yn_1 + (h/6)*( k1[1] + 2*h*k2[1] + 2*h*k3[1] + h*k4[1]),
        yn_1 + (h/6)*( k1[2] + 2*h*k2[2] + 2*h*k3[2] + h*k4[2]))
}

data class Point(val x: Double, val y: Double, val z: Double,
                val vx: Double, val vy: Double, val vz: Double)

class Path{
    private var track : MutableList<Point>  = mutableListOf(Point(0.0, 0.0,10.0,0.2,0.2,0.2))
    private val startTime: Double = 0.0
    private val endTime: Double = 0.0

    fun addPoint(){
        val vx = track[track.size-1].vx
        val vy = track[track.size-1].vy
        val vz = track[track.size-1].vz
        val newX: Double = track[track.size-1].x + vx*h
        val newY: Double = track[track.size-1].y + vy*h
        val newZ: Double = track[track.size-1].z + vz*h
        val newVelocity: DoubleArray = calcYN(vx, vy, vz)
        track.add(Point(newX, newY, newZ, newVelocity[0], newVelocity[1], newVelocity[2]))
    }

    fun print(){
        track.forEach { it -> println("${String.format("%.3f", it.x)}  " +
                "${String.format("%.3f", it.y)}  " +
                "${String.format("%.3f", it.z)}  " +
                "${String.format("%.3f", it.vx)}  " +
                "${String.format("%.3f", it.vy)}  " +
                "${String.format("%.3f", it.vz)}  ") }
    }
}


fun main(args: Array<String>) {
    println("Hello World!")
    var data: DoubleArray = doubleArrayOf(0.0)

    var trackData: Path = Path()
    for (i in 0..1000){
        trackData.addPoint()
    }


    trackData.print()



    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}