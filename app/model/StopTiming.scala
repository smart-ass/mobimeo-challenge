package model

import java.time.LocalTime

case class StopTiming(lineId: Int, stopId: Int, time: LocalTime)
