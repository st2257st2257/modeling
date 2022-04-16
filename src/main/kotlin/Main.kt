/*
 * Copyright 2022 Aleksandr Kristal.
 * Simulation of ball motion in a medium in the presence of dry
 * and viscous friction. The code below provides an API and an
 * algorithm for simulating the motion of a (3D) material point
 * in a gravity field, taking into account dry (constant) and
 * viscous (velocity dependent) friction. The dependence of the
 * accuracy on the initial parameters is also shown.
 */

import kotlin.math.sqrt
import java.io.File

const val pi:    Double     = 3.1416    // main constant
const val eta:   Double     = 0.1       // dynamic viscosity coefficient
const val m:     Double     = 1.0       // mass
const val k:     Double     = 0.03      // resistance coefficient
const val r:     Double     = 1.0       // material point radius - model constant
const val g:     Double     = 19.8      // gravitation constant
const val h:     Double     = 0.001      // step constant - main constant of precision

fun f(yn_1: Double, yn_2: Double, yn_3: Double): DoubleArray{
    // To begin with, we build a model, the description of which you can find in the documentation
    // And then, we calculate the right part of equation:
    val p = - yn_1 * ((6*pi*eta*r/m)* sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)) +
            (k/m)/sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)))
    val q = - yn_2 * ((6*pi*eta*r/m)* sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)) +
            (k/m)/sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)))
    val r = - g - yn_3 * ((6*pi*eta*r/m)* sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)) +
            (k/m)/sqrt((yn_1*yn_1 + yn_2*yn_2 + yn_3*yn_3)))
    return doubleArrayOf(p, q, r)
}

data class Point(val x: Double, val y: Double, val z: Double,
                val vx: Double, val vy: Double, val vz: Double)

class Path{
    private var track : MutableList<Point>  = mutableListOf(Point(0.0, 0.0,10.0,0.2,0.2,0.2))

    private fun calculateNewY(yn_1: Double, yn_2: Double, yn_3: Double): DoubleArray {
        // here are the calculations corresponding to the runge-kutta model
        // for calculating the parameters of the next step of the system
        val k1 = f(yn_1, yn_2, yn_3)
        val k2 = f(yn_1 + (h / 2) * k1[0], yn_2 + (h / 2) * k1[1], yn_3 + (h / 2) * k1[2])
        val k3 = f(yn_1 + (h / 2) * k2[0], yn_2 + (h / 2) * k2[1], yn_3 + (h / 2) * k2[2])
        val k4 = f(yn_1 + (h) * k3[0], yn_2 + (h) * k3[1], yn_3 + (h) * k3[2])
        return doubleArrayOf(
            yn_1 + (h / 6) * (k1[0] + 2 * h * k2[0] + 2 * h * k3[0] + h * k4[0]),
            yn_1 + (h / 6) * (k1[1] + 2 * h * k2[1] + 2 * h * k3[1] + h * k4[1]),
            yn_1 + (h / 6) * (k1[2] + 2 * h * k2[2] + 2 * h * k3[2] + h * k4[2])
        )
    }

    fun addPoint(){
        // Add new point to the array of calculated dots
        val vx = track[track.size-1].vx
        val vy = track[track.size-1].vy
        val vz = track[track.size-1].vz
        val newX: Double = track[track.size-1].x + vx*h
        val newY: Double = track[track.size-1].y + vy*h
        val newZ: Double = track[track.size-1].z + vz*h
        val newVelocity: DoubleArray = calculateNewY(vx, vy, vz)
        track.add(Point(newX, newY, newZ, newVelocity[0], newVelocity[1], newVelocity[2]))
    }

    fun print(fileName: String = ""){
        var resString = ""
        track.forEach {
            resString +=
                "${String.format("%.3f", it.x)}  " +
                        "${String.format("%.3f", it.y)}  " +
                        "${String.format("%.3f", it.z)}  " +
                        "${String.format("%.3f", it.vx)}  " +
                        "${String.format("%.3f", it.vy)}  " +
                        "${String.format("%.3f", it.vz)}  " + "\n"
            }
        if (fileName == "") {
            println(resString)
        }
        else{
            File(fileName).writeText(resString)
        }
    }
}


fun main() {
    println("Start program")

    val trackData = Path()
    for (i in 0..100000){
        trackData.addPoint()
    }

    trackData.print()
    trackData.print("C:\\Users\\MI\\IdeaProjects\\untitled17\\src\\EXCEL\\data_0_001.txt")
}
